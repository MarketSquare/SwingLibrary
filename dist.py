#!/usr/bin/env python

import os
import subprocess
import glob

VERSION = '1.1.2-SNAPSHOT'

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
    call(['cp', 'core/swinglibrary-%s-jar-with-dependencies.jar' % VERSION, 'target'])

if __name__ == '__main__':
    init_dirs()
    build_projects()
    copy_jars_to_target()


