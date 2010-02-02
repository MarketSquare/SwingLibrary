#!/bin/bash 

target=`pwd`/target

echo cleaning target...
buildr clean
echo buildr dist
buildr dist
jar=`echo $target/swinglibrary-*.jar`
jarjar=${jar%.*}-jarjar.jar
echo java -jar ${target}/../release/jarjar-1.0.jar process ${target}/../release/jarjar_rules.txt $jar $jarjar
java -jar ${target}/../release/jarjar-1.0.jar process ${target}/../release/jarjar_rules.txt $jar $jarjar
echo unzip -d $target $jar META-INF/MANIFEST.MF
unzip -d $target $jar META-INF/MANIFEST.MF
echo jar ufm $jarjar $target/META-INF/MANIFEST.MF
jar ufm $jarjar $target/META-INF/MANIFEST.MF
echo jarjarring the test-keywords...
java -jar release/jarjar-1.0.jar process release/jarjar_rules.txt test-keywords/target/swinglibrary-test-keywords*.jar test-keywords/target/jarjared-swinglibrary-test-keywords.jar
echo test-keywords jarjarred.
