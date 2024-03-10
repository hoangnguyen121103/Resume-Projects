Overview: 
This is a program that implements Autocomplete feature using binary Radix tree to return all informations that matches a keyword in an input file. For example: there are 2 restaurants Domino’s Pizza and Domino’s Pasta, if the string ‘Domino’ is found in the input file, all info related to these 2 restaurants will be printed out in an output file. The dataset in this project is a subset from https://data.melbourne.vic.gov.au/explore/dataset/cafes-and-restaurants-with-seating-capacity/information/ .The dataset has been processed into a CSV file with 14 fields: 
	census_year          - The year the information was recorded for (integer)
	block_id             - The city block ID. (integer)
	property_id          - The ID of the property. (integer)
	base_property_id     - The ID of the building the business is in. (integer)
	building_address     - The address of the building. (string)
	clue_small_area      - The CLUE area of Melbourne that the building is in.(string)
	business_address     - The address of the business itself. (string)
	trading_name         - The name of the business. (string)
	industry_code        - The ID for the category of the business. (integer)
	industry_description - The description of the category of the business. (string)
	seating_type         - The type of seating the record describes. (string)
	number_of_seats      - The number of seats provided of this type. (integer)
	longitude            - The longitude (x) of the seating location. (double)
	latitude  	     - The latitude (y) of the seating location (double)
Note: any field containing a comma will begin with a double quotation mark (") and end with a quotation mark (")

How does it work?
In short, the program works by converting a trading_name into bits. Common bits would then become a parent node in the radix tree, and the remaining different bits would be stored in BranchA or BranchB depending on whether the sequence of bits start with 0(BranchA) or 1(BranchB). For example: if 01101111 is the first sequence of bits and 01111111 is the second one, 011 would become a parent node in the radix tree, 01111 would become BranchA and 11111 would be BranchB. Only the leaf nodes will contain data.

How to use?
Three datasets vary in size have been included in the Data Sets folder along with all the names in those datasets( found in 100Names.txt). dataset_2.csv would include infos about the first 2 trading_names in 100Names.txt and so on. Put the keyword you want to search in Input.txt. Multiple keywords can be included in the file, with each keyword on seperate new line. Then run the following in console:
 1. make -B dict
 2. ./dict 3 Data_Sets/selected dataset.csv output.out < Data_Sets/Input.txt > output.stdout.out
The data related to the keyword (or keywords) should be in output.out.