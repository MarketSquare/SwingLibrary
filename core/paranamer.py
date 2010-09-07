import os
import re
import sys

base = os.path.abspath(os.path.dirname(__file__))

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

def add_dependencies_to_classpath():
    paranamer_jar = os.path.join(base, '..', 'lib', 'swing-library-paranamer.jar')
    dependencies =  get_deps() + [paranamer_jar] + [os.path.join(base, 'target', 'classes')]
    for deb in dependencies:
        sys.path.append(deb)
    os.environ['CLASSPATH'] = os.pathsep.join(dependencies)

def run_paranamer():
    print 'PARANAMING!'
    this = os.path.join(base, 'src', 'main', 'java')
    to = os.path.join(base, 'target', 'classes')
    from com.nsn.robot.paraname import Paranamer
    gen = Paranamer()
    gen.processSourcePath(this, to)

def generate_parameter_names():
    paraname_file = os.path.join(base, 'target', '.paraname')
    if not os.path.exists(paraname_file):
        open(paraname_file , 'a')
        run_paranamer()

if __name__ == '__main__':
    add_dependencies_to_classpath()
    generate_parameter_names()
