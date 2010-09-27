#!/bin/bash

PATHS="lib/*.jar"

CLASSPATH=""
for resource in `echo $PATHS`; do
  if [ -z "$CLASSPATH" ]; then
    CLASSPATH="$resource"
  else
    CLASSPATH="$CLASSPATH:$resource"
  fi
done

CLASSPATH=$CLASSPATH jybot -d /tmp/ $*