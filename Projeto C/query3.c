

/*Dado um id de negócio, determinar a sua informação: nome, cidade, estado, stars,
e número total reviews.*/
#include "query3.h"

#define starting_size 1

Table business_info(Sgr *sgr, char *business_id){
    Table t = create_table(starting_size);
    int num = hash(business_id);
    entry_table *entry_t= sgr->ht->entries[num];
    while(entry_t)
    {
        if (!strcmp(entry_t->key,business_id))
        {
            add_key_B(entry_t->b,entry_t->key);
            add_B_table(t,entry_t->b);
            break;
        }
        entry_t = entry_t->next;
    }
    return t;
}
/*
int main(){
    Sgr *sgr = init_sgr();
    business_info(sgr,"PE9uqAjdw0E4-8mjGl3wVA");
    ht_dump(sgr->ht);
return 1;
}*/