#!/usr/bin/env python

import os
import sys
from subprocess import call
from glob import glob


if __name__ == '__main__':
    dir = os.path.dirname(__file__)
    jars = glob(os.path.join(dir, 'target', 'swinglibrary-*-jar-with-dependencies.jar'))
    os.environ['CLASSPATH'] = os.pathsep.join(jars)
    call(["jybot", "--outputdir",  "results"] + sys.argv[1:],
            shell=os.name=='nt')
