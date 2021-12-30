#include "queryPai.h"


entry_table *ht_pair7(const char *key, char *state) {
    // allocate the entry
    entry_table *entry = malloc(sizeof(entry_table) * 1);
    
    entry->b = malloc(sizeof(struct busi));
    reset_Busi(entry->b);
    entry->key = strdup(key);
    entry->b->key = strdup(key);

    entry->b->state = strdup(state);

    entry->b->rates = -3;
    entry->next = NULL;

    return entry;
}



int ht_set7(hash_table *hashtable, char *key, char *state) {
    unsigned int slot = hash(key);

    entry_table *entry = hashtable->entries[slot];

    if (entry == NULL) {
        hashtable->entries[slot] = ht_pair7(key, state);
        return 0;
    }
    entry_table *prev;
    while (entry) {
        if (!strcmp(entry->key,key))
        {
            if (!strcmp(entry->b->state,state))
            {
                //Found a user review in a business from the same state
                //Discard it
                return 0;
            }
            else
            
                //Same user reviewing a diferent state

                //Check first if its the first time entering the this condition;
                if (entry->b->rates == -3)
                {
                //To make sure he doesnt enter the cycle again
                entry->b->rates = -2;
                return 1;
                }
                //User has already entered been a International
                else return 0;
                
                
            
        }
        prev = entry;
        entry = prev->next;
    }

    prev->next = ht_pair7(key, state);
    return 0;
}




Table international_users(Sgr *sgr){
    hash_table *ht= sgr->ht;
    int result = 0;

    //Hash table para ser populada com uma key = UserID
    hash_table *new = ht_create();
    for (size_t i = 0; i < TABLE_SIZE; i++)
    {
        entry_table *entry = ht->entries[i];
        while (entry)
        {
            Busi *b = entry->b;

            for (int j = 0; j < b->pos_id; j++)
            {
                //Podemos aceder ao userID de um Busi b de um slot i
                result += ht_set7(new,b->user_id[j],b->state);
            }
            entry = entry->next;
        }
        
    }
    Table t = hash_2_table_users(new);
    return t;
}