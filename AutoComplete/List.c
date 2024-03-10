#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <assert.h>
#include "List.h"

//Function used to print data
void printData (Listnode *Input, FILE *outFile){
    if (Input != NULL){
        entry_t *inEntry = Input -> info;
        fprintf(outFile,"--> ");
        fprintf(outFile,"census_year: %d || ",inEntry -> census_year);
        fprintf(outFile,"block_id: %d || ",inEntry -> block_id);
        fprintf(outFile,"property_id: %d || ",inEntry -> property_id);
        fprintf(outFile,"base_property_id: %d || ",inEntry -> base_property_id);
        fprintf(outFile,"building_address: %s || ",inEntry -> building_address);
        fprintf(outFile,"clue_small_area: %s || ", inEntry -> clue_small_area);
        fprintf(outFile,"business_address: %s || ", inEntry -> business_address);
        fprintf(outFile,"trading_name: %s || ", inEntry -> trading_name);
        fprintf(outFile,"industry_code: %d || ",inEntry -> industry_code);
        fprintf(outFile,"industry_description: %s || ",inEntry -> industry_description);
        fprintf(outFile,"seating_type: %s || ",inEntry -> seating_type);
        fprintf(outFile,"number_of_seats: %d || ",inEntry -> number_of_seats);
        fprintf(outFile,"longitude: %.5lf || ",inEntry -> longtitude);
        fprintf(outFile,"latitude: %.5lf || ",inEntry -> latitude);
        fprintf(outFile,"\n");
    }
    if (Input -> next != NULL){
        printData(Input -> next, outFile);

    }
}

//Function used to free entry stored in entry_t
void freeEntry(entry_t* input){
    free(input -> building_address);
    free(input -> business_address);
    free(input -> clue_small_area);
    free(input -> trading_name);
    free(input -> industry_description);
    free(input -> seating_type);
    input -> building_address = NULL;
    input -> business_address = NULL;
    input -> clue_small_area = NULL;
    input -> trading_name = NULL;
    input -> industry_description = NULL;
    input -> seating_type = NULL;
    free(input);
}
