#!/bin/bash

#MESSAGE="Release $1"

#TODO: build.xml, mcmod.info, src/eu/tomylobo/ccredbus/CCRedbus.java
git commit -m "Release $1" build.xml mcmod.info src/eu/tomylobo/ccredbus/CCRedbus.java && git tag -am "Release $1" $1
