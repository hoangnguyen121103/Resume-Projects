#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <assert.h>
#include "ProcessBits.h"

//Convert a char into Bits
char *getBits(char *Input, int n){
    char *returnBits = calloc(MAX_BITS, sizeof(char));
    char c = Input[n];
    for (int i = 0; i < 8; i++) {
        returnBits[i] = ((c >> (7 - i)) & 1) + '0';
    }
    return returnBits;
}

//Convert a string or char into a string of Bits
char *ConvertBits(char *Input, int start, int end){
    //May need plus 1 here
    int size = MAX_BITS*((end - start)+1)+1;
    char *returnBits = (char*)malloc(size*sizeof(char));
    int counter = 0;
    char *temp;
    while (start <= end){
        temp = getBits(Input,start);
        int i = 0 ;
        while (i < MAX_BITS){
            returnBits[counter] = temp[i];
            counter ++;
            i++;
        }
        ++start;
        //has no use for temp, hence free
        free(temp);
    }
    returnBits[size-1] = '\0';
    return returnBits;
}

//Function used to split a section of Bits from a string of Bits
char *splitStem (char *bitsInput, int start, int length){
    if (bitsInput == NULL){
        return NULL;
    }
    if (start > (length -1)){
        return NULL;
    }
    int size = length - 1;
    //Allocate space for the new string of Bits
    char *bitsOutput = malloc((length+1)*sizeof(char));
    int counter = 0;
    while(start <= size){
        bitsOutput[counter]= bitsInput[start] ;
        counter ++;
        start ++;
    }
    bitsOutput[counter] = '\0';
    return bitsOutput;
}

//Function used to count common Bits
int CountCommonBits(char *BitsInput1, char *BitsInput2){
    int SizeInput1 = strlen(BitsInput1);
    int SizeInput2 = strlen(BitsInput2);
    int common = 0;
    int counter = 0;
    int max_counter = max(SizeInput1,SizeInput2);
    while(counter < max_counter){
        if (BitsInput1[counter] == BitsInput2[counter]){
            common ++;
            counter ++;
        }
        else {
            break;
        }
    }
    return common;
}

//Function used to determine which input is larger
int max(int int1, int int2){
    int max_counter = 0;
    if (int1 < int2){
        max_counter = int1;
    }
    else if (int2 < int1){
        max_counter = int2;
    }
    else if (int1 == int2){
        max_counter = int1;
    }
    return max_counter;
}

void freeBits(char *queryBits){
    free(queryBits);
}
