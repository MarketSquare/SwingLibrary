import os
import re
import sys
import glob

base = os.path.abspath(os.path.dirname(__file__))

VERSION = '1.1.2-SNAPSHOT'

def get_swinglib_jar():
    pattern = os.path.join(os.path.dirname(__file__),
                           os.path.join('core', 'target'), '*-jar-with-dependencies.jar')
    paths = glob.glob(pattern)
    if paths:
        paths.sort()
        return paths[-1]
    else:
        raise RuntimeError('Please run "mvn -f core/pom.xml package first')

def exists(file_name):
    file = os.path.join(base, file_name)
    return os.path.exists(file)

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
             or 'paranamer' in dep
             or 'paranamer-generator' in dep
             or 'qdox' in dep
             or 'asm' in dep ]

def sh(command):
    process = os.popen(command)
    output = process.read()
    process.close()
    return output

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

    test_classes = os.path.join(base, 'core','target', 'test-classes')
    if not exists(test_classes):
        sh('mvn -f core/pom.xml test-compile')

    paranamer_jar = os.path.join(base, 'lib', 'swing-library-paranamer.jar')
    dependencies =  [get_swinglib_jar()] + [test_classes] + get_test_deps() + [paranamer_jar]
    for deb in dependencies:
        sys.path.append(deb)
    os.environ['CLASSPATH'] = os.pathsep.join(dependencies)

def assert_doc_ok():
    doc_name = 'swinglibrary-%s-doc.html' % VERSION
    dependencies_txt = open(os.path.join(base, 'dependencies.txt'), 'r')
    for line in dependencies_txt.read().splitlines():
        if '*<unknown>' in line:
            raise "Errors in documentation: " + doc_name + " contains *<unknown>-tags."

def generate_parameter_names(this, to):
    paraname_file = os.path.join(base, 'core', 'target', '.paraname')
    if not os.path.exists(paraname_file):
        open(paraname_file, 'a')
        from com.nsn.robot.paraname import Paranamer
        gen = Paranamer()
        gen.processSourcePath(this, to)

def doc():
    add_dependencies_to_classpath()
    generate_parameter_names(os.path.join(base, 'core', 'src', 'main', 'java'), os.path.join(base, 'core', 'target', 'classes'))
    libdoc = os.path.join(base, 'lib', 'libdoc', 'libdoc.py')
    output = os.path.join(base, 'doc', 'SwingLibrary-%s-doc.html' % (VERSION))
    command = 'jython -Dpython.path=%s %s --output %s %s' % (os.path.join(base, 'lib', 'robotframework-2.5.2.jar', 'Lib'), libdoc, output, 'SwingLibrary')
    print command
    assert_doc_ok()
    sys.exit(os.system(command))

if __name__ == '__main__':
    doc()
    assert_doc_ok()

