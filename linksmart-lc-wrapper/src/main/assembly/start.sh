#!/bin/bash

CP=""

for jar in lib/*.jar; do
CP+=$jar:
done

java -client -cp $CP de.fhg.e3.dc.DeviceConnector "$@";