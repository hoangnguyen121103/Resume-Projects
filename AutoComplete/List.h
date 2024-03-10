#ifndef _LIST_H_
#define _LIST_H_

/*
 * Contains structure for each Listnode
 * Purpose: Used to print info stored in entry_t in each Listnode and free entry_t afterward
 */


#include "Data.h"

typedef struct node Listnode;
struct node {
    entry_t *info;
    Listnode *next;
};

void printData (Listnode *Input, FILE *outFile);
void freeEntry(entry_t* input);

#endif