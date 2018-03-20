#!/usr/bin/env python

import sys

current_key = None
current_value = 0;

for line in sys.stdin:
	key, value = line.strip().split('\t')
	
	if key != current_key:
		if current_key != None:
			print '{}\t{}'.format(current_key, current_value)
		
		current_key = key
		current_value = int(value)

	else:
		current_value += int(value)

if current_key != None:
	print '{}\t{}'.format(current_key, current_value)

