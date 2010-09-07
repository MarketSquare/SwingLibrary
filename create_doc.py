import os
import re
import sys

base = os.path.abspath(os.path.dirname(__file__))

VERSION = '1.1.2-SNAPSHOT'

def exists(file_name):
    file = os.path.join(base, file_name)
    return os.path.exists(file)

def get_deps():
    os.environ['MAVEN_OPTS'] = '-DoutputAbsoluteArtifactFilename=true'
    mvn_output = sh('mvn -f core/pom.xml dependency:list').splitlines()
    jars = [re.sub('.*:((:?C:)?)', '\\1', file) for file in mvn_output if re.search('jar', file)]
    return jars

def sh(command):
    process = os.popen(command)
    output = process.read()
    process.close()
    return output

def add_dependencies_to_classpath():
    dependencies =  get_deps() + [os.path.join(base, 'core', 'target', 'classes')]
    for deb in dependencies:
        sys.path.append(deb)
    os.environ['CLASSPATH'] = os.pathsep.join(dependencies)

def assert_doc_ok():
    doc_name = 'SwingLibrary-%s-doc.html' % VERSION
    docfile = open(os.path.join(base, 'doc', doc_name), 'r')
    for line in docfile.read().splitlines():
        if '*<unknown>' in line:
            raise "Errors in documentation: " + doc_name + " contains *<unknown>-tags."

def doc():
    add_dependencies_to_classpath()
    libdoc = os.path.join(base, 'lib', 'libdoc', 'libdoc.py')
    output = os.path.join(base, 'doc', 'SwingLibrary-%s-doc.html' % (VERSION))
    command = 'jython -Dpython.path=%s %s --output %s %s' % (os.path.join(base, 'lib', 'robotframework-2.5.2.jar', 'Lib'), libdoc, output, 'SwingLibrary')
    print command
    return os.system(command)

if __name__ == '__main__':
    ret_code = doc()
    assert_doc_ok()
    sys.exit(ret_code)

