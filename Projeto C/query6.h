#ifndef QUERY6
#define QUERY6
#include "queryPai.h"

Table top_businesses_by_city(Sgr *sgr, int top);
int num_entry_cities(hash_table *ht, const char *key);
void ht_set6(hash_table *hashtable, const char *key, Busi *b);




#endif