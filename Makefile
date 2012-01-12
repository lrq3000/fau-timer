all:
	gcc -Wall fau_timer.c -o fau_timer -O2
        
linux:
	swig -python fau_timer.i
	gcc -c fau_timer.c fau_timer_wrap.c -I/usr/include/python2.7
	ld -shared fau_timer.o fau_timer_wrap.o -o _fau_timer.so 
clean:
	rm -f fau_timer *.o *.so

osx:
	swig -python fau_timer.i
	python setup.py build_ext --inplace
        
