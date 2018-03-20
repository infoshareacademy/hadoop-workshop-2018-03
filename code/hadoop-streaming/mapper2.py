#!/usr/bin/env python

import sys

for line in sys.stdin:
	letter, count = line.strip().split('\t')
	print '{}\t{}'.format(count, letter)
