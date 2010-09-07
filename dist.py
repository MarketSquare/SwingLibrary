#!/usr/bin/env python

import os
import re
import sys
import subprocess
import glob

from create_doc import doc, assert_doc_ok

VERSION = '1.1.2-SNAPSHOT'

base = os.path.abspath(os.path.normpath(os.path.dirname(__file__)))

def call(cmd):
    print " ".join(cmd)
    return subprocess.call(cmd)

def build_projects():
    call(['mvn', '-Ddist.version=%s' % VERSION, '-f', 'core/pom.xml', 'clean', 'package', 'install', 'assembly:assembly'])
    call(['mvn', '-Ddist.version=%s' % VERSION, '-f', 'test-application/pom.xml', 'clean', 'package', 'install'])
    call(['mvn', '-Ddist.version=%s' % VERSION, '-f', 'test-keywords/pom.xml', 'clean', 'package', 'install', 'assembly:assembly']) 

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
    call(['cp', os.path.join('core', 'target', 'swinglibrary-%s.jar' % VERSION), 'target'])
    call(['cp', os.path.join('core', 'target', 'swinglibrary-%s-jar-with-dependencies.jar' % VERSION), 'target'])

def create_doc():
    doc()
    assert_doc_ok()

if __name__ == '__main__':
    init_dirs()
    build_projects()
    create_doc()
    copy_jars_to_target()


