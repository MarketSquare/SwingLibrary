import platform

is_osx = platform.java_ver()[-1][0] == 'Mac OS X'
CTRL_CMD = 'META_MASK' if is_osx else 'CTRL_MASK'
