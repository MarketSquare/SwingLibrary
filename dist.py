#!/usr/bin/env python

""" Create a distribution of the SwingLibrary.

Usage: jython dist.py [task]

Optional task argument can have the following value:

 - doc  Creates the documentation for the library

If no task is specified, the whole dist build will be run, which means:

 - building of all the subprojects with maven
   (Subprojects: core, test-application, test-keywords, demo-application)
 - packaging them with and without 3rd party classes
 - installing them into local maven repository
 - running the acceptance tests
 - generating the keyword documentation
 - packaging the demo application into distribution zip file

The swinglibrary.jar with and without depencencies and the demo application
zip are copied into the target directory and documentation html will be
generated to the doc directory.
"""

import os
import re
import sys
import subprocess
from classversioncheck import ClassVersionCheck

VERSION = '1.1.3'
base = os.path.abspath(os.path.normpath(os.path.dirname(__file__)))

def call(cmd):
    print " ".join(cmd)
    return subprocess.call(cmd)

def build():
    call(['mvn', 'clean', 'assembly:assembly'])

def init_dirs():
    call(['rm', '-r', 'target'])
    call(['mkdir', 'target'])
    call(['rm', '-r', 'doc'])
    call(['mkdir', 'doc'])

def doc():
    create_doc()
    assert_doc_ok()

def create_doc():
    add_dependencies_to_classpath()
    libdoc = os.path.join(base, 'lib', 'libdoc', 'libdoc.py')
    output = os.path.join(base, 'doc', 'SwingLibrary-%s-doc.html' % (VERSION))
    command = 'jython -Dpython.path=%s %s --output %s %s' % (os.path.join(base, 'lib', 'robotframework-2.5.2.jar', 'Lib'), libdoc, output, 'SwingLibrary')
    print os.environ['CLASSPATH']
    print command
    return os.system(command)

def add_dependencies_to_classpath():
    dependencies =  get_deps() + [os.path.join(base, 'target', 'classes')]
    for deb in dependencies:
        sys.path.append(deb)
    os.environ['CLASSPATH'] = os.pathsep.join(dependencies)

def get_deps():
    os.environ['MAVEN_OPTS'] = '-DoutputAbsoluteArtifactFilename=true'
    mvn_output = sh('mvn dependency:list').splitlines()
    jars = [re.sub('.*:((:?C:)?)', '\\1', file) for file in mvn_output if re.search('jar', file)]
    return jars

def sh(command):
    process = os.popen(command)
    output = process.read()
    process.close()
    return output

def assert_doc_ok():
    doc_name = 'SwingLibrary-%s-doc.html' % VERSION
    docfile = open(os.path.join(base, 'doc', doc_name), 'r')
    for line in docfile.read().splitlines():
        if '*<unknown>' in line:
            raise RuntimeError("Errors in documentation: " + doc_name + " contains *<unknown>-tags.")

def default():
    init_dirs()
    build()
    doc()
    try:
      ClassVersionCheck(49).assert_classes_have_correct_version(os.path.abspath(os.path.join('target', 'swinglibrary-%s.jar' % VERSION)))
    except RuntimeError, err:
      print err

if __name__ == '__main__':
    try:
        name = sys.argv[1] if len(sys.argv) == 2 else 'default'
        {'doc': doc, 'default': default}[name]()
    except (KeyError, IndexError):
        print __doc__



