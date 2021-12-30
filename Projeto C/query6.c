#include "query6.h"



void ht_set6(hash_table *hashtable, const char *key, Busi *b) {
    unsigned int slot = hash(key);

    entry_table *entry = hashtable->entries[slot];

    if (entry == NULL) {
        hashtable->entries[slot] = ht_pair(key, b);
        return;
    }

    entry_table *prev;

    while (entry != NULL) {
        
        prev = entry;
        entry = prev->next;
    }

    prev->next = ht_pair(key, b);
}





Table top_businesses_by_city(Sgr *sgr, int top){
    hash_table *ht1 = ht_create();
    
    for (size_t i = 0; i < TABLE_SIZE; i++)
    {
           entry_table *inicio=  sgr->ht->entries[i];
           
           while(inicio)
           {
                int tam = num_entry_key(ht1,inicio->b->city);               
                int slot = hash(inicio->b->city);
               
                if (tam < top) ht_set6(ht1, inicio->b->city, inicio->b);
                //Comparar com os que existem e substituir o primeiro mais fraco
                else remove_lower_busi(ht1->entries[slot],inicio->b,inicio->b->city);
                //ht_print_slot(ht1->entries[slot]);
                //Se existir mais de 2 entao comparar os 3 elementos e tirar o menor
                inicio = inicio->next;
           }
    }
    Table t =hash_2_table(ht1);
    return t;
    
}

/*
int main(){
    Sgr *sgr = init_sgr();
    hash_table *ht1 =top_businesses_by_city(sgr,2);
    //ht_dump(ht1);
    return 1;
}*/