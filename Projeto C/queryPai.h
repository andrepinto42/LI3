#ifndef QUERYPAI
#define QUERYPAI
#include "basedados/hashtable.h"
#include "basedados/sgr.h"
#include <ctype.h>

typedef struct table{
    int i;
    int size;
    Busi *b;
}*Table;


Table hash_2_table_categories(hash_table* ht);
Table hash_2_table_users(hash_table* ht);
Table hash_2_table(hash_table* ht);
int add_B_table(Table t,Busi *b);
Table create_table(int size);
void dump_table(Table t);
void dump_table_file(FILE *f,Table *t,char delim);
void dump_table_pag(Table *t,char* start,int next);
int print_table_begin(char *starting,int next);



void create_found_busi(Busi *b,char* user_id,char *state,char *city,char *name,float stars,int rates);
int num_entry_key(hash_table *ht, const char *key);
entry_table* get_entry(Sgr *sgr,int i);
int size_table(Table t);
void remove_lower_busi(entry_table *entry,Busi *substituto,char *key);



#endif