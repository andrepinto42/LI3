#include "queryPai.h"
#include "query2.h"

#define starting_size 30

Table  businesses_started_by_letter(Sgr *sgr, char letter){
    
    Table t = create_table(starting_size);
    for (size_t i = 0; i < TABLE_SIZE; i++)
    {
        entry_table *entry_t = get_entry(sgr,i);
        
        while(entry_t)
        {            
            if (tolower(entry_t->b->name[0]) == tolower(letter))
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
    Table *t =businesses_started_by_letter(sgr,'z');
    print_num(size_table(t));
    return 1;
}*/
