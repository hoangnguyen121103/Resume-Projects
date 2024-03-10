#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <assert.h>
#include "Tree.h"

//Function used to create empty treenode
treenode *createEmptyNode (){
    treenode *result = malloc(sizeof(treenode));
    result -> commonBits = 0;
    result -> PrefixChar = NULL;
    result -> BranchA = NULL;
    result -> BranchB = NULL;
    //Linked list is used to store data
    result -> Data = malloc(sizeof(Listnode));
    return result;
}

//Function used to insert entry_t input into a new node
treenode *ConvertIntoNode(entry_t *Input){
    treenode *InputNode = createEmptyNode();
    //Not minus 1 here b/c of null byte
    InputNode -> PrefixChar = ConvertBits(Input -> trading_name, 0 , strlen(Input -> trading_name));
    InputNode -> commonBits = strlen(InputNode -> PrefixChar);
    InputNode -> Data -> info = Input;
    InputNode -> Data -> next = NULL;
    return InputNode;
}

//Function used to insert a new node into the radix tree
treenode *insertNode(treenode *root, treenode *Input){
    if (root == NULL){
        root = Input;
    }
    else if (root != NULL){
        //This if statement occur when root is now at leaf node
        if ((root -> BranchA == NULL) && (root -> BranchB == NULL)){
            int n_commonBits = 0;
            //If data of the trading_name already at the leaf node, append the new data at the end of
            //the linked list
            if ((n_commonBits = CountCommonBits(root -> PrefixChar, Input -> PrefixChar))== (root -> commonBits)
                &&(root -> commonBits == Input -> commonBits)){
                root -> Data -> next = Input -> Data;
            }
                //If data of the trading_name is not at the leaf node, insert the data into the node
                //new root should be insert here
            else {
                //Create a new temp node
                treenode *temp = malloc(sizeof(treenode));
                n_commonBits = CountCommonBits(root -> PrefixChar, Input -> PrefixChar);
                //Update Input with new PrefixChar and commonBits
                char *CommonBits = splitStem(Input -> PrefixChar,0,n_commonBits);
                char *tempPrefix = splitStem(Input -> PrefixChar,n_commonBits,strlen(Input -> PrefixChar));
                //free old Input PrefixChar
                free(Input -> PrefixChar);
                Input -> PrefixChar = tempPrefix;
                Input -> commonBits = (Input -> commonBits) - n_commonBits;

                //transfer all data from the root to the newly created temp node
                //Updating root node with new CommonBits and commonBits (capital C is Char*, commonBits with normal c is int)
                transferNode(root,temp);
                root -> PrefixChar = CommonBits;
                root -> commonBits = n_commonBits;

                //Updating the temp node with new data
                temp -> commonBits = (temp -> commonBits) - n_commonBits;
                tempPrefix = splitStem(temp -> PrefixChar,n_commonBits,strlen(temp -> PrefixChar));
                free(temp -> PrefixChar);
                temp -> PrefixChar = tempPrefix;

                //If the Input PrefixChar start with '1' after processing
                //Then insert Input into BranchB
                //Insert newly created temp node into BranchA
                if(Input -> PrefixChar[0] == '1'){
                    root -> BranchB = Input;
                    root -> BranchA = temp;
                }
                    //vice versa
                else if(Input -> PrefixChar[0] == '0'){
                    root -> BranchB = temp;
                    root -> BranchA = Input;
                }
            }
        }
            //This else if statement occur when the current node is a leaf node
        else if ((root -> BranchA != NULL) && (root -> BranchB != NULL)){
            int n_commonBits = 0;
            //If the Input commonBits >= # of commonBits between root PrefixChar and Input PrefixChar
            //Input PrefixChar and # commonBits gets updated then move to either BranchA or BranchB
            if ((n_commonBits = CountCommonBits(root -> PrefixChar, Input -> PrefixChar)) >= root -> commonBits){
                char *tempPrefix = splitStem(Input -> PrefixChar,n_commonBits,strlen(Input -> PrefixChar));
                free(Input -> PrefixChar);
                Input -> PrefixChar = tempPrefix;
                Input -> commonBits = (Input -> commonBits) - n_commonBits;
                //move to the next Branch recursively
                if(Input -> PrefixChar[0] == '1'){
                    root -> BranchB = insertNode(root -> BranchB, Input);
                }
                else {
                    root -> BranchA = insertNode(root-> BranchA, Input);
                }
            }
                //If the Input commonBits < # of commonBits between root PrefixChar and Input PrefixChar
                //new root should be insert here
            else if ((n_commonBits = CountCommonBits(root -> PrefixChar, Input -> PrefixChar))  < (root -> commonBits)){
                //Create a new temp node
                //Update Input with new PrefixChar and commonBits
                treenode *temp = malloc(sizeof(treenode));
                char *CommonBits = splitStem(Input -> PrefixChar,0,n_commonBits);
                char *tempPrefix = splitStem(Input -> PrefixChar,n_commonBits,strlen(Input -> PrefixChar));
                free(Input -> PrefixChar);
                Input -> PrefixChar = tempPrefix;
                Input -> commonBits = (Input -> commonBits) - n_commonBits;

                //transfer all data from the root to the newly created temp node
                //Updating root node with new CommonBits and commonBits
                transferNode(root,temp);
                root -> PrefixChar = CommonBits;
                root -> commonBits = n_commonBits;

                //Updating the temp node with new data, deleting old PrefixChar
                tempPrefix = splitStem(temp -> PrefixChar,n_commonBits,strlen(temp -> PrefixChar));
                free(temp -> PrefixChar);
                temp -> PrefixChar = tempPrefix;
                temp -> commonBits = (temp -> commonBits) - n_commonBits;

                //If the Input PrefixChar start with '1' after processing
                //Then insert Input into BranchB
                //Insert newly created temp node into BranchA
                if(Input -> PrefixChar[0] == '1'){
                    root -> BranchB = Input;
                    root -> BranchA = temp;

                }
                else if(Input -> PrefixChar[0] == '0'){
                    root -> BranchB = temp;
                    root -> BranchA = Input;

                }
            }
        }
    }
    return root;
}

//Function used to transfer data between 2 nodes
void transferNode(treenode *Input, treenode *Output){
    Output -> PrefixChar = Input -> PrefixChar;
    Input -> PrefixChar = NULL;
    Output -> BranchA = Input -> BranchA;
    Input -> BranchA = NULL;
    Output -> BranchB = Input -> BranchB;
    Input -> BranchB = NULL;
    Output -> commonBits = Input -> commonBits;
    Input -> commonBits = 0;
    Output -> Data = Input -> Data;
    Input -> Data = NULL;
}

//Function used to search query(which converted into Bits) in radix tree
void searchTree (int *n_char, int *n_bits, treenode *root, char *queryBits, int *n_queryBits, FILE *outFile){
    if (root == NULL){
        return;
    }
    else if (root != NULL){
        //This is not a leaf node
        if ((root -> BranchA != NULL) && (root -> BranchB != NULL)){
            //Update the queryBits
            int n_commonBits = CountCommonBits(root -> PrefixChar, queryBits);
            char *temp = splitStem(queryBits,n_commonBits,strlen(queryBits));
            free(queryBits);
            queryBits = temp;
            //Determine whether it is a few character or not then update the counter n_char
            int a = n_commonBits / 8;
            int i = n_commonBits % 8;
            *n_bits = *n_bits + n_commonBits;
            if (i > 0){
                i = 1;
            }
            *n_char = *n_char + a + i;

            // Determine which Branch to travel next
            if (queryBits != NULL){
                if (queryBits[0] == '1'){
                    searchTree(n_char,n_bits,root -> BranchB,queryBits,n_queryBits, outFile);
                }
                else {
                    searchTree(n_char,n_bits,root -> BranchA,queryBits,n_queryBits, outFile);
                }
            }
                // This is where queryBits ends, but has not finished traversing the tree(not a the leaf node)
            else {
                //Traverse from the node to all leaf nodes that this node connected to
                TraverseToLeafNode (root, outFile);
            }
        }
            // At the leaf node
        else if ((root -> BranchA == NULL) && (root -> BranchB == NULL)){
            //Update n_bits and n_char
            int n_commonBits = CountCommonBits(root -> PrefixChar, queryBits);
            char *temp = splitStem(queryBits,n_commonBits,strlen(queryBits));
            free(queryBits);
            queryBits = temp;
            if ((queryBits == NULL)){
                *n_bits = *n_bits + n_commonBits;
                int a = n_commonBits / 8;
                int i = n_commonBits % 8;
                if (i > 0){
                    i = 1;
                }
                *n_char = *n_char + a + i;
            }
            //Print Data at leaf node
            fprintf(outFile,"%s\n", root -> Data -> info -> trading_name);
            printData (root -> Data, outFile);
        }

    }
}

//Function used to traverse to all leaf nodes connected to the current node
void TraverseToLeafNode(treenode *root, FILE *outFile){
    if ((root -> BranchA == NULL) && (root -> BranchB == NULL)) {
        fprintf(outFile,"%s\n", root -> Data -> info -> trading_name);
        printData (root -> Data, outFile);
    }
    if ((root -> BranchA != NULL) && (root -> BranchB != NULL)){
        TraverseToLeafNode (root -> BranchA,outFile);
        TraverseToLeafNode (root -> BranchB,outFile);
    }
}

//Function used to free allocated space to store the radix tree
void freeTree(treenode *root){
    if (root != NULL){
        free(root -> PrefixChar);
        freeList(root -> Data);
        root -> PrefixChar = NULL;
        root -> commonBits = 0;
        freeTree(root -> BranchA);
        freeTree(root -> BranchB);
        free(root);
    }
    if (root == NULL){
        return;
    }
}

//Function used to free the linked list
void freeList(Listnode *root){
    Listnode* tmp;
    while (root != NULL)
    {
        freeEntry(root -> info);
        tmp = root;
        root = root->next;
        free(tmp);
    }
    free(root);
}






