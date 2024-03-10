CC = gcc
CFLAGS = -Wall -g
SRC = main.c List.c Data.c ProcessBits.c Tree.c
OBJ = $(SRC:.c=.o)
EXE = dict
dict: main.o List.o Data.o ProcessBits.o Tree.o
	gcc -o dict main.o List.o Data.o ProcessBits.o Tree.o
main.o: main.c
	gcc -c main.c
Data.o: Data.c Data.h
	gcc -c Data.c
List.o: List.h List.c
	gcc -c List.c
ProcessBits.o: ProcessBits.h ProcessBits.c
	gcc -c ProcessBits.c
Tree.o: Tree.h Tree.c
	gcc -c Tree.c
clean:
	rm -f *.o dict