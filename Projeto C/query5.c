#include "basedados/hashtable.h"
#include "query5.h"

#define starting_size 30

Table businesses_with_stars_and_city(Sgr *sgr,float stars,char *city){
    
    Table t = create_table(starting_size);
    for (size_t i = 0; i < TABLE_SIZE; i++)
    {
        entry_table *entry_t = get_entry(sgr,i);
        
        while(entry_t)
        {
            if(entry_t->b->stars >= stars && !strcasecmp(entry_t->b->city,city) )
            {
                add_key_B(entry_t->b,entry_t->key);
                add_B_table(t,entry_t->b);
            }
            entry_t = entry_t->next;
        }
    }
    return t;
    
}


/*
int main(){
    Sgr *sgr = init_sgr();
    businesses_with_stars_and_city(sgr,3.0,"Boston");
    return 1;
}*/