#ifndef _DATA_H_
#define _DATA_H_

/*
 * Contains structure for entry_t
 * Purpose: Used to read info from an input file and store them in entry_t
 */


//Constant
#define MAX_CHAR 129
#define MAX_FIELD 513

typedef struct entry entry_t;
struct entry {
    int census_year , block_id , property_id , base_property_id ;
    char *building_address ;
    char *clue_small_area ;
    char *business_address;
    char *trading_name;
    int industry_code;
    char *industry_description;
    char *seating_type;
    int number_of_seats;
    double longtitude;
    double latitude;
};

entry_t *readEntry(char *line);
entry_t *entryInitialization(entry_t *input);



#endif