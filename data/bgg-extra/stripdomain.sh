#!/usr/bin/env bash

DIR=../bgg

PATTERN="\"subdomains\" : \["

grep $PATTERN $DIR/k??/games-* | cut -c52- | sed -e 's/\[//' -e 's/\],//' | sort -u
