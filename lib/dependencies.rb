JRETROFIT = 'jretrofit:jretrofit:jar:1.0'
JEMMY = 'org.netbeans:jemmy:jar:2.2.7.5'
PARANAMER = 'com.thoughtworks.paranamer:paranamer:jar:1.1.2'
JAVALIB_CORE = 'org.robotframework:javalib-core:jar:0.8.1'
JDOM = 'jdom:jdom:jar:1.0'
HAMCREST = 'org.hamcrest:hamcrest-core:jar:1.1'

ABBOT = ['abbot:abbot:jar:1.0.2', 'abbot:costello:jar:1.0.2', 'gnu-regexp:gnu-regexp:jar:1.1.4']

JAVALIB_CORE_DEPENDENCIES =
 ['aopalliance:aopalliance:jar:1.0',
  'avalon-framework:avalon-framework:jar:4.1.3',
  JAVALIB_CORE,
  'commons-collections:commons-collections:jar:3.2',
  'commons-logging:commons-logging:jar:1.1',
  'commons-io:commons-io:jar:1.4',
  'junit:junit:jar:4.5',
  'log4j:log4j:jar:1.2.12',
  'logkit:logkit:jar:1.0.1',
  'org.netbeans:jemmy:jar:2.2.7.5',
  JEMMY,
  'org.springframework:spring-aop:jar:2.5.4',
  'org.springframework:spring-beans:jar:2.5.4',
  'org.springframework:spring-context:jar:2.5.4',
  'org.springframework:spring-core:jar:2.5.4']

DEPENDENCIES =
  [JAVALIB_CORE_DEPENDENCIES,
   ABBOT,
   JEMMY,
   JDOM,
   PARANAMER,
   JRETROFIT].flatten

TEST_DEPENDENCIES =
 ['beaninject:beaninject:jar:0.3',
  'junit:junit-dep:jar:4.4',
  'org.hamcrest:hamcrest-core:jar:1.1',
  'org.hamcrest:hamcrest-library:jar:1.1',
  'org.jdave:jdave-core:jar:1.1-rc1',
  'org.jdave:jdave-junit4:jar:1.1-rc1',
  'org.jmock:jmock:jar:2.4.0',
  'cglib:cglib-nodep:jar:2.1_3',
  'org.objenesis:objenesis:jar:1.0',
  'org.robotframework:javatools-test:jar:0.2',
  PARANAMER]

TRANSLATOR =
 'net.sf.retrotranslator:retrotranslator-transformer:jar:1.2.4'

TRANSLATOR_RUNTIME =
 ['net.sf.retrotranslator:retrotranslator-runtime:jar:1.2.4',
  'backport-util-concurrent:backport-util-concurrent:jar:3.1']

PARANAMER_GENERATOR =
 ['com.thoughtworks.qdox:qdox:jar:1.6.3',
  'com.thoughtworks.paranamer:paranamer-generator:jar:1.1.2',
  'asm:asm:jar:3.0',
  PARANAMER,]

HSQLDB = 'hsqldb:hsqldb:jar:1.8.0.10'
