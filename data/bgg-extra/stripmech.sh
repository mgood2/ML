#!/usr/bin/env bash

DIR=../bgg

PATTERN="\"mechanics\" : \["

grep $PATTERN $DIR/k??/games-* | cut -c50- | sed -e 's/\[//' -e 's/\],//' | sort -u
