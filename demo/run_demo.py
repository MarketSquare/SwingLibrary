#!/usr/bin/env python

import os
import sys
from subprocess import call
from glob import glob

TESTAPP = 'org.robotframework.swing.testapp.examplesut.TodoListApplication'

def _get_jars():
    dir = os.path.dirname(__file__)
    return glob(os.path.join(dir, 'lib',
                'swinglibrary-*-jar-with-dependencies.jar'))

def _set_classpath():
    os.environ['CLASSPATH'] = os.pathsep.join(_get_jars())

def _parse_command():
    if sys.argv[1] == 'startapp':
        return ['java', TESTAPP]
    return ['jybot', '--outputdir',  'results'] + sys.argv[1:]

if __name__ == '__main__':
    if len(sys.argv) == 1:
        print 'Usage: run_demo.py [startapp|path_to_tests]'
        sys.exit(1)
    _set_classpath()
    call(_parse_command(), shell=os.name=='nt')
