#!/usr/bin/env python

import os
import sys
from glob import glob

def main(args):
    root = os.path.abspath(os.path.join(os.path.dirname(__file__), '..', '..', 
                                        '..', '..', '..'))
    jarjar = glob(os.path.join(root, 'target', '*jarjar.jar'))[0]
    test_app = os.path.join(root, 'test-application', 'target', 'classes')
    test_kws = os.path.join(root, 'test-keywords', 'target', 'classes')
    jars = [jarjar, test_app]
    # jars = [jarjar, test_app, test_kws]
    os.environ['CLASSPATH'] = os.pathsep.join(jars)
    outputdir = os.path.join(root, 'results')
    os.system('jybot --loglevel TRACE --outputdir "%s" %s' % (outputdir, ' '.join(args)))

if __name__ == '__main__':
    main(sys.argv[1:])
