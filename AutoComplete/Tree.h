#ifndef _TREE_H_
#define _TREE_H_

/*
 * Contains structure for each treenode
 * Purpose: Used to perform operations on the radix tree.
 */

#include "Data.h"
#include "List.h"
#include "ProcessBits.h"

typedef struct treenode {
    int commonBits;
    //PrefixChar refers to bits that both BranchA and BranchB have in common
    char *PrefixChar;
    //BranchA and BranchB contains the remaining different bits
    struct treenode *BranchA;
    struct treenode *BranchB;
    //Only leaf nodes in the tree contain Data
    //List data structure is used as a trading_name might have multiple restaurant locations.
    Listnode *Data;
}treenode;

treenode *createEmptyNode ();
treenode *ConvertIntoNode(entry_t *Input);
treenode *insertNode(treenode *root, treenode *Input);
void transferNode(treenode *Input, treenode *Output);
void searchTree (int *n_char, int *n_bits, treenode *root,
                 char *queryBits, int *n_queryBits, FILE *outFile);
void TraverseToLeafNode(treenode *root,FILE *outFile);
void freeTree(treenode *root);
void freeList(Listnode *root);

#endif

