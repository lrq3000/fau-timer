README for building fau_timer python module

Preconditions
=======================================
- have at least python 2.7 installed (maybe older, but not tested. for other verisons please change line in makefile)
	get it here: http://python.org/download/releases/2.7.2/
- have swig installed (http://www.swig.org/exec.html)
	get swig here: http://www.swig.org/download.html

How to build Python module of fau_timer.c
=======================================
On UNIX machines:
	$ make linux

On MAC OSX machines:
	$ make osx

How to use the Python module
=======================================
        $ python runme.py

Look at runme.py to learn about the usage of the library.
