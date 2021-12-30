#include "query9.h"
#define starting_size 50

void review_2_table_users(Table t,char *key){
    if (t->i + 3 > t->size)
    {
        t->b = realloc(t->b,sizeof(Busi)*t->size*2);
        t->size *= 2;
    }
    reset_Busi(t->b+t->i);
    (t->b+t->i)->key = strdup(key);
    t->i++;    
}



Table read_review_9(Sgr *sgr,char *word)
{
    
    Table t = create_table(starting_size);
    FILE *users = fopen((sgr)->nome_reviews,"r+");
    if ( !users)
    {
        printf("Erro abrir ficheiro das Reviews\n");
        return t;
    }
    else
        {
        while(!feof(users))
        {
            for (int i = 0; i < 100000; i++)
            {
                char *newline = malloc(sizeof(char) * 101);//tamanho standart sem o texto
                if ( fgets(newline,100,users) )
                {
                    if (strlen(newline) > 98 && newline[98] == ';')
                    {
                        Review *r = malloc(sizeof(Review));
                        char *fake = malloc(sizeof(char) * 200);
                        char *review_id  =strsep(&newline,";");
                        for (size_t i = 0; i < 6; i++)
                        {   
                            strsep(&newline,";");
                        }
                        free(fake);
                        search_text(users,r);
                        char *begin = r->text;
                        int tam = strlen(r->text);
                        char *found =strstr(r->text,word);
                        if(found)
                        {
                            //Foi encontrado uma correspondencia
                            review_2_table_users(t,review_id);
                        }
                        bzero(begin,tam);
                        free(r);
                        free(review_id);
                    }
                    else{
                        // Vai buscar o resto da review incorreta
                        char *linha = malloc(sizeof(char) * 5000);
                        fgets(linha,5000,users);
                        free(linha);
                    }
                }
                else break;
            }
        }
        // Se o ficheiro acabou ou ainda há mais para ler
        fclose(users);
        return t;         
    }
    if (users) fclose(users);
    return t;
}



Table reviews_with_word(Sgr *sgr, int top, char *word){
    print_arr("passa aqui\n");
    Table t =read_review_9(sgr,word);
    return t;
}
