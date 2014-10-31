#!/usr/bin/env bash
javac -cp alpv_ws1415 $(find ./* | grep .java) && echo "Compiled!"