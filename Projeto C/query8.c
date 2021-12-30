#include "query8.h"


hash_table* read_business_category(int Buff_business,char *name_business,char *category)
{
    hash_table *ht = ht_create();
    FILE *business_f = fopen(name_business,"r+");
    if ( !business_f)
    {
        printf("Erro abrir ficheiro\n");
        return ht;
    }
    else
    {
        char *linha;
        char *key;
        char *city;
        char *name;
        char *state;
        char **categories;
        while(!feof(business_f))
        {
            for (int i = 0; i < Buff_business; i++)
            {
                linha = malloc(sizeof(char)*5000);
                if( fgets(linha,5000,business_f) )
                {
                    if (strlen(linha)>=23 && linha[22] == ';')
                    {
                    key = strdup(strsep(&linha,";"));
                    name = strdup(strsep(&linha,";"));
                    city = strdup(strsep(&linha,";"));
                    state = strdup(strsep(&linha,";"));
                    //int tam = get_categories2(linha,categories);
                    int i =0,go=1,found=0;
                    categories = malloc(sizeof(char *) * 20);
                    while  (go)
                    {
                        if (linha)
                        {        
                            categories[i] = strdup(strsep(&linha,",\n"));
                            if (!strcmp(categories[i],category)) found =1;
                            i++;
                        }
                        else go =0;
                    }
                    //Se a categoria pretendida foi encontrada, 
                    //entao adiciona o Negócio à hashtable
                    if (found)
                    {
                        Busi *b = make_busi(state,city,name,-1,0);
                        //Insere as Categorias no sitio do UserID
                        //É uma implementação versatil
                        for (size_t j = 0; j < i; j++)
                        {
                            userID_insert(b,categories[j]);
                        }
                        ht_set(ht,key,b);
                    }
                    free(key);
                    free(name);
                    free(city);
                    free(state);
                    free(linha);    
                   }
                } 
                else 
                {
                    free(linha);
                    fclose(business_f);
                    return ht; 
                }
            
            }
        }
        fclose(business_f);
        return ht;

        
    }
    
    //Função nao deixa dar fclose senão dá um erro de corrupted size
    if (business_f) fclose(business_f);
    
    return ht;
    }

void fill_ht_stars(hash_table *ht1,hash_table *ht2)
{
    for (size_t i = 0; i < TABLE_SIZE; i++)
    {
        entry_table *entry = ht1->entries[i];
        while(entry)
        {
           //Procurar na outra hashtable o valor da star
           int slot =  hash(entry->key);
           entry_table *new_entry = ht2->entries[slot];
           while(new_entry)
           {
               
               if (!strcmp(entry->key,new_entry->key))
               {
                   //Found the same key in the 2 hashtables
                   //Change the stars to the first hashtable
                   entry->b->stars = new_entry->b->stars;
               }
               new_entry = new_entry->next;
           }
            entry = entry->next;
        }
    }
    
}

Table get_best_Business(hash_table *ht,int top){
    Busi *highest= malloc(sizeof(Busi));
    char *key_highest= malloc(sizeof(char)*24);
    reset_Busi(highest);
    Table t = create_table(top);
    for (size_t j = 0; j < top; j++)
    {
        for (size_t i = 0; i < TABLE_SIZE; i++)
        {
            entry_table *entry = ht->entries[i];
            while(entry)
            {
                if (entry->b->stars>highest->stars && entry->b->rates != -5)
                {
                    strcpy(key_highest,entry->key);
                    replace_busi(highest,entry->b);
                }
                entry = entry->next;
            }
        }
        //Condição para guarantir que existe um Highest
        if (highest->name)
        {
            add_B_table(t,highest);
            entry_table *rm = ht->entries[hash(key_highest)];
            while (rm)
            {
                if (!strcmp(rm->key,key_highest))
                {
                    rm->b->rates=-5;
                    break;
                }
                rm =rm->next;
            }
            reset_Busi(highest);
            }

        }
    return t;
}


Table top_businesses_with_category(Sgr *sgr, int top, char *category){
    hash_table *ht = sgr->ht;
    print_arr("aqui");
    hash_table *new = read_business_category(100000,"basedados/business_full.csv",category);
    fill_ht_stars(new,ht);
    print_arr("aqui");
    Table t = get_best_Business(new,top);
    print_arr("aqui");
    return t;
}