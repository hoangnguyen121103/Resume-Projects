#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "Tree.h"
#include "Data.h"
#include "List.h"
#include "ProcessBits.h"

//main function, where everything comes together
int main (int argc, char *argv[]){
    //Open files
    FILE *inFile  = fopen(argv[2], "r"),
            *outFile = fopen(argv[3], "w");

    //Skip csv header line
    char *line = calloc(MAX_FIELD,sizeof(*line));
    fgets(line,MAX_FIELD,inFile);

    entry_t *e = NULL;
    treenode *datatree = NULL;
    //Read through each line in input file and process the input data, save them into entry_t
    //Insert obtained entry into radix tree
    while(fgets(line,MAX_FIELD,inFile)){
        e = readEntry(line);
        treenode *InputNode = ConvertIntoNode(e);
        datatree = insertNode(datatree,InputNode);
    }

    //Loop through the list to find the entry that matches the query
    int print1 = 0;
    char query[MAX_CHAR];
    while(fgets(query,MAX_CHAR,stdin) != NULL){
        query[strlen(query)-1] = '\0';
        //Convert query into Bits
        char *queryBits = ConvertBits(query,0,strlen(query)-1);
        int n_string = 0;
        int n_bits = 0;
        int n_char = 0;
        int n_queryBits = 0;
        //Search the query in Bits in the radix tree
        searchTree(&n_char, &n_bits, datatree, queryBits, &n_queryBits, outFile);
        n_string =1;
        printf("%s --> b%d c%d s%d\n",query, n_bits, n_char, n_string);
    }
    //free data stored in datatree
    freeTree(datatree);
    //freeEntry(e);
    free(line);
    //close files
    fclose(inFile);
    fclose(outFile);
    return 0;
}