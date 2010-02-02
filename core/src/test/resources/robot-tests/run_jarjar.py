#!/usr/bin/env python

import os
import sys
from glob import glob
from tempfile import gettempdir

def main(args):
    root = os.path.abspath(os.path.join(os.path.dirname(__file__), '..', '..', 
                                        '..', '..', '..'))
    jarjar = glob(os.path.join(root, 'target', '*jarjar.jar'))[0]
    test_app = os.path.join(root, 'test-application', 'target', 'classes')
    test_kws = os.path.join(root, 'test-keywords', 'target', 'jarjared-swinglibrary-test-keywords.jar')
    jars = [jarjar, test_app, test_kws]
    os.environ['CLASSPATH'] = os.pathsep.join(jars)
    os.system('jybot --loglevel TRACE --critical regression --outputdir "%s" %s' % (gettempdir(), ' '.join(args)))

if __name__ == '__main__':
    main(sys.argv[1:])
