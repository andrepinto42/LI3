#include "basedados/hashtable.h"
#include "query4.h"

#define starting_size 10



Table businesses_reviewed(Sgr *sgr, char *user_id){

    Table t = create_table(starting_size);
    hash_table *ht = sgr->ht; 
    //Percorrer a hashtable toda
    for (size_t i = 0; i < TABLE_SIZE; i++)
    {
       entry_table *entry =  ht->entries[i];  
        while(entry)
        {
            Busi *b = entry->b;
            int j=0;
            while(j<b->pos_id)
            {
                if (!strcmp(b->user_id[j],user_id) )
                {
                    //Foi encontrado aqui uma correspondencia
                    Busi *found = malloc(sizeof(Busi));
                    reset_Busi(found);
                    create_found_busi(found,user_id,b->state,b->city,b->name,b->stars,b->rates);
                    add_key_B(found,entry->key);
                    add_B_table(t,found);
                    free(found);
                }
                j++;
            }
            entry = entry->next;
        }
    }
    return t;
}