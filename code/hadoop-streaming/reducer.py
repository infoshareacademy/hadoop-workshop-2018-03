#!/usr/bin/env python3

import sys

current_key = None
current_sum = 0

for line in sys.stdin:
	key, value = line.strip().split('\t', 1)
	value = int(value)
	if current_key == key:
		current_sum += value
	else:
		if current_key is not None:
			print('%s\t%d' % (current_key, current_sum))

		current_sum = value
		current_key = key

if current_key is not None:
	print('%s\t%d' % (current_key, current_sum))

