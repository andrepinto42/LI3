#include "query1.h"

Sgr* load_sgr(char *users, char *businesses, char *reviews){
    Sgr *data_struct = malloc(sizeof (struct sgr) );

    data_struct->ht = ht_create();
     
    read_business_struct(data_struct,data_struct->ht,businesses);

    free(get_Business_Struct(data_struct));
    
    read_review_struct(data_struct,data_struct->ht,reviews);
    free(get_Review_Struct(data_struct));
    
    read_users_struct(data_struct,users);
    free(get_Users_Struct(data_struct));
   
    return data_struct;
}