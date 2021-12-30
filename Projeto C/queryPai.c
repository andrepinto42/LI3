
#include "queryPai.h"
#define starting_size 10000


Table hash_2_table_users(hash_table* ht){
    Table t = create_table(starting_size);
    for (size_t i = 0; i < TABLE_SIZE; i++)
    {
        entry_table *entry = ht->entries[i];
        while(entry)
        {
            if (entry->b->rates ==-2)
            {
            if (t->i + 3 > t->size)
            {
                t->b = realloc(t->b,sizeof(Busi)*t->size*2);
                t->size *= 2;
            }
            reset_Busi(t->b+t->i);
            (t->b+t->i)->key = strdup(entry->b->key);
            ((t->b)+t->i)->state = strdup(entry->b->state);
            t->i++;
            }
            entry = entry->next;


        }
    }
    return t;
    
}

Table hash_2_table_categories(hash_table* ht){
    Table t = create_table(starting_size);
    for (size_t i = 0; i < TABLE_SIZE; i++)
    {
        entry_table *entry = ht->entries[i];
        while(entry)
        {
            add_key_B(entry->b,entry->key);
            add_B_table(t,entry->b);
            entry = entry->next;
        }
    }
    return t;
    
}



Table hash_2_table(hash_table* ht){
    Table t = create_table(starting_size);
    for (size_t i = 0; i < TABLE_SIZE; i++)
    {
        entry_table *entry = ht->entries[i];
        while(entry)
        {
            add_key_B(entry->b,"");
            add_B_table(t,entry->b);
            entry = entry->next;
        }
    }
    return t;
    
}


Table create_table(int size){
    Table t = malloc(sizeof(Table));
    if(size <1) return NULL;
    t->size = size;
    t->i = 0;
    t->b = malloc(sizeof(Busi)*size);
    return t;
}

int add_B_table(Table t,Busi *b){
    reset_Busi(t->b+t->i);
    if (t->i + 3 > t->size)
    {
        t->b = realloc(t->b,sizeof(Busi)*t->size*2);
        t->size *= 2;
    }
    replace_busi((t->b)+t->i,b);
    t->i++;
    return t->i;
}
void dump_table_file(FILE *f,Table *t,char delim){
    for (size_t i = 0; i < (*t)->i; i++)
    {
        if (((*t)->b +i)->key) fprintf(f,"%s%c",((*t)->b+i)->key,delim);
        if (((*t)->b +i)->state) fprintf(f,"%s%c",((*t)->b+i)->state,delim);
        if (((*t)->b +i)->city) fprintf(f,"%s%c",((*t)->b+i)->city,delim);
        if (((*t)->b +i)->name) fprintf(f,"%s%c",((*t)->b+i)->name,delim);
        if (((*t)->b +i)->stars) fprintf(f,"%.2f%c",((*t)->b+i)->stars,delim);
        for (size_t j = 0; j < ((*t)->b+i)->pos_id; j++)
        {
        if (((*t)->b +i)->user_id[j]) fprintf(f,"%s%c",((*t)->b+i)->user_id[j],delim);   
        }
        fputs("\n",f);
    }   
}

void dump_table(Table t){
    for (size_t i = 0; i < t->i; i++)
    {
        if ((t->b +i)->key){
            print_arr("[");
            print_arr((t->b +i)->key);
            print_arr("] ");

        }
        print_busi(t->b +i);
        print_arr("\n");
    }
    print_arr("Tamanho da Table: ");
    print_num(t->i);
    print_arr("\n");
}
int print_table_begin(char *starting,int next){
    char *start = malloc(sizeof(char) * 200);
    memset(start,0,200);
    if (next) 
    strcpy(start,"|      Business_ID     | State |          City          |                          Name                           | Stars | Nº Users |");
    else {
        strcat(start,"|");
        int offset = (22 - strlen(starting) ) / 2;

        for (int i = 0; i < offset; i++)
        {
            strcat(start," ");
        }
        strcat(start,starting);
        for (int i = 0; i < offset; i++)
        {
            strcat(start," ");
        }
        if (((22 -strlen(starting)) % 2) == 1) strcat(start," ");
        strcat(start,"|");
        }
    
    for (size_t i = 0; i < strlen(start)-1; i++)
    {
        print_arr("-");
    }
    print_arr("\n");
    print_arr(start);
    print_arr("\n");
    return (strlen(start));
    
    
    
}

void dump_table_pag(Table *t,char *start,int next){
    int b_per_page = 10;
    int lower=0;
    int upper=b_per_page;
    int go=1;
    int tamanho = (*t)->i;
    while(go)
    { 
        print_arr("Tamanho da Table: ");
        print_num(tamanho);
        print_arr("\n");
        int size = print_table_begin(start,next);
        for (; lower <upper && lower< tamanho; lower++)
        {
            if ( (((*t)->b +lower)->key) && strlen(((*t)->b +lower)->key) > 0){
                print_arr("|");
                print_arr(((*t)->b +lower)->key);
                print_arr("| ");

            }
            else print_arr("|         NONE         | ");
            if(!next)print_arr(" ");
            print_busi_table((*t)->b +lower);
            print_arr("\n");
        }
        for (size_t i = 0; i < size; i++)
        {
        print_arr("-");
        }
        print_arr("\nInicio : ");
        print_num((lower-b_per_page <=0) ? 0 : lower);
        print_arr(" Fim : ");
        print_num((tamanho-upper <=0) ? tamanho : upper);
        print_arr("\n");
        char *c  = malloc(sizeof(char) * 100);
        if  (fgets(c,100,stdin))
        {
            if(!strcmp(c,"d\n"))
            {
                if (upper+b_per_page > tamanho)
                {
                    if ( upper>=tamanho) 
                    {
                        lower = (lower-b_per_page <=0) ? 0 : lower;
                        upper = tamanho;
                    }
                    else
                    upper += tamanho -upper;
                }
                else
                upper +=b_per_page;

            }
            else if (!strcmp(c,"a\n"))
            {
                if(lower-2*b_per_page < 0)
                {
                    lower = 0;
                }
                else 
                {
                lower -= 2*b_per_page;
                upper -= b_per_page;
                }
            }
            else if(!strcmp(c,"q\n"))
            {
                go = 0;
            }
            else
            {
                if(lower-b_per_page <= 0)
                {
                    lower = 0;
                }
                else lower -= b_per_page;
                system("clear");
                print_arr("\nUse 'a' ou 'd' para visualizar,'q' para sair da visualizaçao.\n");

                continue;
            }

        }
        else break;
        

        system("clear");
    }

    
}


int num_entry_key(hash_table *ht, const char *key) {
   int i=0;
   entry_table *entry = ht->entries[hash(key)];
    if (entry == NULL) {
        return 0;
    }

    while (entry != NULL) {
        
        if (strcmp(entry->key, key) == 0) {
            i++;
        }

        entry = entry->next;
    }

    return i;
}

void create_found_busi(Busi *b,char* user_id,char *state,char *city,char *name,float stars,int rates)
{
    b->city = strdup(city);
    b->name = strdup(name);
    b->state = strdup(state);
    b->user_id = malloc(sizeof(char*) *2);
    b->user_id[0] = strdup(user_id);
    b->stars = stars;
    b->rates = rates;
    b->pos_id = 1;
    b->size_user_id = 2;
}


/**
 * 
 *
 * Substitui o negocio com pior rating por substituito
 * 
 */

void remove_lower_busi(entry_table *entry,Busi *substituto,char *key){
   //Se as Stars forem 0 não vale a pena comparar pois nunca será substituido
   if (substituto->stars<=0) return;
   Busi *weakest = substituto;
    while(entry){
        if (!strcmp(entry->key,key))
        {
            if(weakest->stars > entry->b->stars)
            {
                weakest = entry->b;
            }
        }
        entry = entry->next;
    }

    if (weakest == substituto) return;
    else{
        replace_busi(weakest,substituto);
    }


}

int size_table(Table t){
    return t->i;
}

entry_table* get_entry(Sgr *sgr,int i){
    return sgr->ht->entries[i];
}