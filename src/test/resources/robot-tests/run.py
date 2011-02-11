import os
import re
import sys
import glob

base = os.path.abspath(os.path.normpath(os.path.split(sys.argv[0])[0]))  

def root_dir():
    this_dir = os.path.abspath(os.path.dirname(__file__))
    root = os.path.join(this_dir, '..', '..', '..', '..')
    return os.path.normpath(root)

def exists(file_name):
    file = os.path.join(base, file_name)
    return os.path.exists(file)

def sh(command):
    process = os.popen(command)
    output = process.read()
    process.close()
    return output

def get_jar_for(project):
    pattern = os.path.join(project, 'target', '*-jar-with-dependencies.jar')
    paths = glob.glob(pattern)
    if paths:
        paths.sort()
        return paths[-1]
    else:
        raise RuntimeError('Please run "mvn assembly:assembly first')

def get_swinglibrary_jar():
    return get_jar_for(root_dir())

def get_test_app_jar():
    return get_jar_for(os.path.join(root_dir(), '..', 'test-application'))

def get_test_kws_jar():
    return get_jar_for(os.path.join(root_dir(), '..', 'test-keywords'))

def get_test_deps():
    deps = open('dependencies.txt', 'rb').read().splitlines()
    return [ dep for dep in deps if 'swinglibrary' in dep or 'org/mortbay' in dep ]

def add_dependencies_to_classpath():
    if not exists('dependencies.txt'):
        os.environ['MAVEN_OPTS'] = '-DoutputAbsoluteArtifactFilename=true'
        mvn_output = sh('mvn dependency:list').splitlines()

        jars = [re.sub('.*:((:?C:)?)', '\\1', file) for file in mvn_output if re.search('jar', file)]
        dependencies_txt = open(os.path.join(base, 'dependencies.txt'), 'w')
        for jar in jars:
            if exists(jar):
                dependencies_txt.write(jar + '\n')
        dependencies_txt.flush()

    test_classes = os.path.join('target', 'test-classes')
    if not exists(test_classes):
        sh('mvn test-compile')

    dependencies =  [get_swinglibrary_jar()] + [get_test_app_jar()] + [get_test_kws_jar()] + [test_classes] + get_test_deps()
    os.environ['CLASSPATH'] = os.pathsep.join(dependencies)

def run_robot_tests(args):
    cmd = 'jybot --loglevel TRACE --outputdir /tmp --noncritical development %s' % (args)
    os.system(cmd)

if __name__ == '__main__':
   add_dependencies_to_classpath()
   run_robot_tests('.')
