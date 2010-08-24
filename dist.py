#!/usr/bin/env python

import os
import subprocess
import glob

VERSION = '1.1.1'
SWINGLIB_DIST_JAR_NAME = 'swinglibrary-%s-jar-with-dependencies.jar' % VERSION
RELAT_DIST_JAR_PATH = 'core/target/%s' % SWINGLIB_DIST_JAR_NAME

def call(cmd, cwd='.'):
    print " ".join(cmd)
    return subprocess.call(cmd, cwd=cwd)

def build_projects():
    call(['mvn', 'clean', 'assembly:assembly'], cwd='core')
    call(['mvn', 'clean', 'assembly:assembly'], cwd='test-application')
    call(['mvn', 'clean', 'assembly:assembly'], cwd='test-keywords')
    call(['mvn', 'clean', 'assembly:assembly'], cwd='demo-application')

def jarjar(jar):
    call(['java', '-jar', 'lib/jarjar-1.0.jar', 'process', 'lib/jarjar_rules.txt', '%s' %(jar), '%s' %(jar)])

def get_jar_with(pattern):
    paths = glob.glob(pattern)
    if paths:
        paths.sort()
        path = paths[-1]
    return os.path.abspath(path)

def setup_acceptance_test_env():
    test_keywords = os.path.abspath(get_jar_with('test-keywords/target/*-jar-with-dependencies.jar'))
    jarjar(test_keywords)
    test_app = os.path.abspath(get_jar_with('test-application/target/*-jar-with-dependencies.jar'))
    dist_jar = os.path.abspath(RELAT_DIST_JAR_PATH)
    os.environ['CLASSPATH'] = os.pathsep.join([test_keywords, test_app, dist_jar])

def get_output_dir():
  try:
      return os.environ['ROBOT_OUTPUTDIR']
  except KeyError:
      return os.path.join(os.path.abspath(os.path.dirname(__file__)), 'results')

def run_acceptance_tests():
    output_dir = get_output_dir()
    test_dir = os.path.abspath(os.path.join('core', 'src', 'test', 'resources', 'robot-tests'))
    call(['xvfb-run', 'jybot', '--exclude', 'display-required', '--loglevel', 'TRACE', '--monitorcolors', 'off', 
          '--outputdir', output_dir, '--debugfile', 'debug.txt', '--noncritical', 'development',
          '--tagstatcombine', '*NOTdevelopment:regression', test_dir])

def init_dirs():
    call(['rm', '-r', 'target'])
    call(['mkdir', 'target'])

def copy_jars_to_target():
    call(['cp', 'core/target/swinglibrary-%s.jar'% VERSION, 'target'])
    call(['cp', RELAT_DIST_JAR_PATH, 'target'])

def jarjar_dist_jar():
    jarjar('core/target/%s' % SWINGLIB_DIST_JAR_NAME)

if __name__ == '__main__':
    init_dirs()
    build_projects()
    jarjar_dist_jar()
    setup_acceptance_test_env()
    run_acceptance_tests()
    copy_jars_to_target()


