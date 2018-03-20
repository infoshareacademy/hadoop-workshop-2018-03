#!/usr/bin/env python

import sys

for line in sys.stdin:
	for letter in line.strip():
		if letter.isalnum():
			print '{}\t{}'.format(letter, 1)
