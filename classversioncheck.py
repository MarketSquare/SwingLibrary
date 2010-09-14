import os
import glob
import shutil
import subprocess


MIN_VERSION = 45

class ClassVersionCheck(object):

  def __init__(self, max_version):
     self._max_version = max_version

  def assert_classes_have_correct_version(self, target):
    if os.path.isdir(target):
      self.verify_directory(target)
    elif os.path.isfile(target):
      self.verify_jar(target)

  def is_class_versions_ok(self, target):
    try:
      self.assert_classes_have_correct_version(target)
      return True
    except:
      return False

  def verify_directory(self, directory, src=None):
    if not src:
      src = directory

    classes = []
    for dirpath, _, fnames in os.walk(directory):
      for fname in fnames:
        if fname.endswith('.class'):
          classes.append(os.path.join(dirpath, fname))
    for clss in classes:
      print 'Class: ', clss
      actual_version = self.get_class_version(clss)
      if actual_version > self._max_version or actual_version < MIN_VERSION:
        raise RuntimeError('bad class version: max is %s but found %s from %s in %s' % (self._max_version, self.get_class_version(clss), clss, src))

  def verify_jar(self, jar):
    self.in_temp_dir(self.unzip_and_verify, jar)

  def unzip_and_verify(self, jar):
    subprocess.call(['unzip', '-qo', '%s' % jar])
    self.verify_directory('.', jar)

  def in_temp_dir(self, func, jar):
    tmp_dir = '%s/%s_%s' % (self.get_temp_dir(), os.path.basename(__file__), os.getpid())
    if not os.path.exists(tmp_dir):
      os.mkdir(tmp_dir)
    back = os.getcwd()
    os.chdir(tmp_dir)
    try:
      func(jar)
    finally:
      os.chdir(back)
      shutil.rmtree(tmp_dir) 

  def get_temp_dir(self):
    try:
      return os.environ['TMP'];
    except KeyError:
      return '/tmp'

  def get_class_version(self, some_file):
    f = open(some_file, 'r')
    f.seek(6)
    c = ord(f.read(1))
    return ((0xFF & c) << 8) | (0xFF & c)
      
