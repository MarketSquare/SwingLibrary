#!/usr/bin/env python

import os
import re
import sys
import subprocess
import glob

VERSION = '1.1.2-SNAPSHOT'

base = os.path.abspath(os.path.normpath(os.path.split(sys.argv[0])[0]))

def call(cmd, cwd='.'):
    print " ".join(cmd)
    return subprocess.call(cmd, cwd=cwd)

def mvn(project):
    call(['mvn', '-Ddist.version=%s' % VERSION, 'clean', 'package', 'install', 'integration-test'], cwd=project)

def build_projects():
    mvn('core')
    mvn('test-application')
    mvn('test-keywords')
    mvn('demo-application')

def get_jar_with_dependencies_for(project):
    pattern = '%s/target/*-jar-with-dependencies.jar' % project
    paths = glob.glob(pattern)
    if paths:
        paths.sort()
        path = paths[-1]
    return os.path.abspath(os.path.abspath(path))

def init_dirs():
    call(['rm', '-r', 'target'])
    call(['mkdir', 'target'])

def copy_jars_to_target():
    call(['cp', 'core/target/swinglibrary-%s.jar'% VERSION, 'target'])
    call(['cp', 'core/target/swinglibrary-%s-jar-with-dependencies.jar' % VERSION, 'target'])

def exists(file_name):
    file = os.path.join(base, file_name)
    return os.path.exists(file)

def sh(command):
    process = os.popen(command)
    output = process.read()
    process.close()
    return output

def get_swinglib_jar():
    pattern = os.path.join(os.path.dirname(__file__),
                           'core/target', '*-jar-with-dependencies.jar')
    paths = glob.glob(pattern)
    if paths:
        paths.sort()
        return paths[-1]
    else:
        raise RuntimeError('Please run "mvn -f core/pom.xml package first')

def get_test_deps():
    deps = open('dependencies.txt', 'rb').read().splitlines()
    return [ dep for dep in deps 
             if 'beaninject' in dep 
             or 'hamcrest' in dep 
             or 'jdave' in dep 
             or 'jmock' in dep 
             or 'junit' in dep 
             or 'cglib' in dep 
             or 'objenesis' in dep 
             or 'javatools-test' in dep
             or 'paranamer' in dep ]

def add_dependencies_to_classpath():
    if not exists('dependencies.txt'):
        os.environ['MAVEN_OPTS'] = '-DoutputAbsoluteArtifactFilename=true'
        mvn_output = sh('mvn -f core/pom.xml dependency:list').splitlines()

        jars = [re.sub('.*:((:?C:)?)', '\\1', file) for file in mvn_output if re.search('jar', file)]
        dependencies_txt = open(os.path.join(base, 'dependencies.txt'), 'w')
        for jar in jars:
            if exists(jar):
                dependencies_txt.write(jar + '\n')
        dependencies_txt.flush()

    test_classes = os.path.join('core','target', 'test-classes')
    if not exists(test_classes):
        sh('mvn -f core/pom.xml test-compile')

    paranamer_jar = os.path.join('lib', 'swing-library-paranamer.jar')
    dependencies =  [get_swinglib_jar()] + [test_classes] + get_test_deps() + [paranamer_jar]
    os.environ['CLASSPATH'] = os.pathsep.join(dependencies)

def get_robot_installation_path():
    import robot
    return os.path.dirname(robot.__file__)

def assert_doc_ok():
    doc_name = 'swinglibrary-%s-doc.html' % VERSION
    dependencies_txt = open(os.path.join(base, 'dependencies.txt'), 'r')
    for line in dependencies_txt.read().splitlines():
        if '*<unknown>' in line:
            raise "Errors in documentation: " + doc_name + " contains *<unknown>-tags."

def generate_parameter_names(this, to):
    paraname_file = os.path.join('core', 'target', '.paraname')
    if not os.path.exists(paraname_file):
        open(paraname_file, 'a')
        from com.nsn.robot.paraname import Paranamer
        gen = Paranamer()
        gen.processSourcePath(this, to)

def doc():
    add_dependencies_to_classpath()
    generate_parameter_names(os.path.join('core', 'src', 'main', 'java'), os.path.join('core', 'target', 'classes'))
    base = os.path.dirname(__file__)
    libdoc = os.path.join(base, 'lib', 'libdoc', 'libdoc.py')
    output = os.path.join(base, 'doc', 'SwingLibrary-%s-doc.html' % (VERSION))
    lib = 'SwingLibrary'
    command = 'jython -Dpython.path=%s %s --output %s %s' % ('/usr/local/lib/python2.6/dist-packages', libdoc, output, lib)
    print command
    sys.exit(os.system(command))
    assert_doc_ok()

if __name__ == '__main__':
    init_dirs()
    build_projects()
    copy_jars_to_target()


