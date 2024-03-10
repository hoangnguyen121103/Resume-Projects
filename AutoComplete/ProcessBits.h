#ifndef _PROCESSBITS_H_
#define _PROCESSBITS_H_

/*
 * Purpose: Focuses on convert an input trading_name into bits and process those bits according to
 * the Radix tree implementation (detailed in ReadMe.txt)
 */

#define MAX_BITS 8

char *getBits(char *Input, int n);
char *ConvertBits(char *Input, int start, int end);
char *splitStem (char *bitsInput, int start, int length);
int CountCommonBits(char *BitsInput1, char *BitsInput2);
int max(int int1, int int2);
void freeBits(char *queryBits);

#endif