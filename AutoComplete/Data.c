#include <stdlib.h>
#include <string.h>
#include "Data.h"

//Function used to initilize each member of entry_t
entry_t *entryInitialization(entry_t *input){
    input -> census_year = 0;
    input -> block_id = 0;
    input -> property_id = 0;
    input -> base_property_id = 0;
    input -> business_address = NULL;
    input -> building_address = NULL;
    input -> clue_small_area = NULL;
    input -> trading_name = NULL;
    input -> industry_code = 0;
    input -> industry_description = NULL;
    input -> seating_type = NULL;
    input -> number_of_seats = 0;
    input -> longtitude = 0;
    input -> latitude = 0;
    return input;
}

//Function used to process input line. Data is saved into its corresponding field in entry_t
entry_t *readEntry(char* line){
    //Create an array to save processed data.
    char entryArray[14][MAX_CHAR];

    //Create an entry_t to store data
    entry_t *dataEntry = NULL;
    dataEntry = (entry_t*)malloc(sizeof(*dataEntry));
    dataEntry = entryInitialization(dataEntry);

    //data is first processed and saved into entryArray, which will then be "imported" into entry_t
    int counter = 0;
    int arrayPos = 0;
    //processFlag used to indicate whether the data is accompanied by '"' or not
    int processFlag = 0;
    int i = 0;
    while(counter <= strlen(line)){
        //data is not accompanied by '"', not at the end of the data yet
        if (line[counter] != ',' && processFlag == 0){
            entryArray[arrayPos][i] = line[counter];
            i ++;
            counter ++;
        }
        //data is not accompanied by '"', the end of the data
        if ((line[counter] == ',' && processFlag == 0)){
            counter ++;
            entryArray[arrayPos][i] = '\0';
            i = 0;
            arrayPos++;
        }
        //data is accompanied by '"', only the beginning of the data
        if (line[counter] == '"' && processFlag == 0){
            processFlag = 1;
            i = 0;
            counter ++;
        }
        //data is accompanied by '"', not at the end of the data yet
        if (line[counter] != '"' && processFlag == 1){
            entryArray[arrayPos][i] = line[counter];
            i ++;
            counter ++;
        }
        //data is accompanied by '"', the end of the data
        if(line[counter] == '"' && processFlag == 1){
            processFlag = 0;
            entryArray[arrayPos][i] = '\0';
            i = 0;
            //counter = counter + 2 to skip the comma after '"'
            counter = counter + 2;
            arrayPos ++;
        }

    }
    //process of "importing" data into entry_t
    dataEntry -> census_year = atoi(entryArray[0]);
    dataEntry -> block_id = atoi(entryArray[1]);
    dataEntry -> property_id = atoi(entryArray[2]);
    dataEntry -> base_property_id = atoi(entryArray[3]);
    dataEntry -> business_address = strdup(entryArray[6]);
    dataEntry -> building_address = strdup(entryArray[4]);
    dataEntry -> clue_small_area = strdup(entryArray[5]);
    dataEntry -> trading_name = strdup(entryArray[7]);
    dataEntry -> industry_code = atoi(entryArray[8]);
    dataEntry -> industry_description = strdup(entryArray[9]);
    dataEntry -> seating_type = strdup(entryArray[10]);
    dataEntry -> number_of_seats = atoi(entryArray[11]);
    char *longtptr;
    char *latptr;
    dataEntry -> longtitude = strtod(entryArray[12],&longtptr);
    dataEntry -> latitude = strtod(entryArray[13],&latptr);

    return dataEntry;
}

