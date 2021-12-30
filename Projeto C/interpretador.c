#include "interpretador.h"
#define num_variable 20
#define size_buffer_linha 200


int pos_ocupacacao = 0;



void store_parametros(Var v,char **parametros)
{
    for (size_t i = 0; i < v->num_para; i++)
    {
        v->parametros[i] = strdup(parametros[i]);
    }
}

void zerar_tudo(char *input,char **parametros)
{
    bzero(input,strlen(input));
    for (size_t i = 0; i < 3; i++)
    {
        for (size_t j = 0; strlen(parametros[i]); j++)
        {
            parametros[i][j] = 0;
        }
    }
}

int create_var(Var v,int pos,char *name,int funcao,int num_p){
    for (size_t i = 0; i < pos; i++)
    {
        if (!strcmp((v+i)->name,name)) 
        {
            print_arr("Variavel já existe, insera uma com nome diferente\n");
            return 0;
        }
    }
    strcpy((v+pos)->name,name);
    (v+pos)->tipo = funcao;
    (v+pos)->funcao = funcao;
    (v+pos)->num_para = num_p;
    if(num_p > 0) 
    {
        (v+pos)->parametros = malloc(sizeof(char *) * num_p);
        for (size_t i = 0; i < num_p; i++)
        {
            (v+pos)->parametros[i] = NULL; 
        }
    }
    
    else (v+pos)->parametros =NULL;
    return 1;
}




void dump_var(Var v)
{
    print_arr("\nNome var : ");
    print_arr(v->name);
    print_arr("\n");
    print_arr("Função : ");
    print_num(v->tipo);
    print_arr("\n");
    if (v->parametros)
    {
        
        for (size_t i = 0; i<v->num_para; i++)
        {
            print_arr("Parametro ");
            print_num(i+1);
            print_arr(" : ");
            print_arr(*((v->parametros) + i));
            print_arr("\n");
        }
    }
    print_arr("\n");

}


char* name_sgr(Var *v,int pos){
    
    for (size_t i = 0; i<pos_ocupacacao ; i++)
    {
        if (((*v)+i)->tipo == 1)
        {
            return ((*v)+i)->name;
        }
        
    }
    return NULL;
    
}

void reset_var(Var v){
    for (size_t i = 0; i < num_variable; i++)
    {
        (v+i)->tipo = 0;
        (v+i)->funcao =0;
        (v+i)->num_para = 0;
        (v+i)->parametros = NULL;
    }
}


Var init_var(){
    Var v = malloc(sizeof(struct var_interpretador) * num_variable);
    reset_var(v);
    return v;
}

void clean_up(Var v, int *pos_v,char **parametros,char *input)
{
    store_parametros(v+(*pos_v),parametros);
    dump_var(v+(*pos_v));
    pos_ocupacacao++;
    (*pos_v)++;
    zerar_tudo(input,parametros);
}


/**
 * 
 * Função Principal para chamar as query todas
 * 
 */
void interpretador(int num,Table *t,Sgr **sgr,char **parametros){
    switch (num)
    {
    case 0:
        for (size_t i = 0; i < 3; i++)
        {
            print_arr(parametros[i]);
            print_arr("\n");
        }
        
        *sgr = load_sgr(parametros[0],parametros[1],parametros[2]);
        break;
    case 1:
        //free_ht(sgr);
        //free(sgr);
        *sgr = load_sgr("basedados/users_full.csv","basedados/business_full.csv","basedados/reviews_1M.csv");
        break;
    case 2:
        *t = businesses_started_by_letter(*sgr,parametros[1][0]);
        break;
    case 3:
        *t = business_info(*sgr,parametros[1]);
        break;
    case 4:
        *t = businesses_reviewed(*sgr,parametros[1]);
        break;
    case 5:
        *t = businesses_with_stars_and_city(*sgr, atof(parametros[1]),parametros[2]);
        break;
    case 6:
        *t = top_businesses_by_city(*sgr,atoi(parametros[1]));
        break;
    case 7:
        *t = international_users(*sgr);
        break;
    case 8:
        *t = top_businesses_with_category(*sgr,atoi(parametros[1]),parametros[2]);
        break;
    case 9:
        *t = reviews_with_word(*sgr,atoi(parametros[1]),parametros[2]);
        break;
    default:
        print_arr("Verifique que numero inseriu\n");
        return ;
    }
    if (num != 1){
        
    }
}

void decide_dump_table(Table *t,int tipo){

   if ( (tipo >= 2 && tipo <= 6) || tipo == 8)
   {
       dump_table_pag(t,"Business_ID",1);
   }
   else if(tipo == 7)
   dump_table_pag(t,"User_ID",0);

   else if (tipo == 9)
   {
       dump_table_pag(t,"Review_ID",0);
   }
   else
   print_arr("Não existe esse tipo.\n");

}




int find_command(char *s){
    if (strcasecmp("quit\n",s) == 0) {
        print_arr("Quitting...\n");
        return -1;
    }
    else if (strcasecmp("help\n",s) == 0)
    {
        print_arr("\n");
        print_arr("Type show(\"var_name\"); to display what is stored in the variable. \n");
        print_arr("Type \"var_name\" = init_sgr(); to inicialize the data_struct\n");
        print_arr("Type \"var_name\" = businesses_started_by_letter(sgr,'caracter');\n");
        print_arr("Type \"var_name\" = business_info(sgr,\"business_id\");\n");
        print_arr("Type \"var_name\" = businesses_reviewed(sgr,\"user_id\");\n");
        print_arr("Type \"var_name\" = businesses_with_stars_and_city(sgr, stars, \"city\");\n");
        print_arr("Type \"var_name\" = top_businesses_by_city(sgr,max_num);\n");
        print_arr("Type \"var_name\" = international_users(sgr);\n");
        print_arr("Type \"var_name\" = top_businesses_with_category(sgr,max_num,\"category\");\n");
        print_arr("Type \"var_name\" = reviews_with_word(sgr,num,\"word\");\n");
        print_arr("Type \"var_name\" = toCSV(var_query,delim,\"filepath\");\n");
        print_arr("Type -quit to exit the program.\n");
        print_arr("Type -clear to wipe the screen.\n");
        print_arr("Type -help to view manual of commands.\n");
        print_arr("\n");
        return 1;
    }
    else if (strcasecmp("clear\n",s) == 0)
    {
        system("clear");
        return 2;
    }
    else return 0;
}



//Se devolver 1 então a leitura foi invalida,dessa forma continuando o ciclo
int read_input(Sgr *sgr,Table t){
    Var v = init_var();
    char *buffer = malloc(sizeof(char) * size_buffer_linha);
    char *input = malloc(sizeof(char) * size_buffer_linha);
    char *variable = malloc(sizeof(char) *size_buffer_linha);
    char **parametros = malloc(sizeof(char*) * 3);
    for (size_t i = 0; i < 3; i++)
    {
        parametros[i] = malloc(sizeof(char) * size_buffer_linha);
        for (size_t j = 0; strlen(parametros[i]); j++)
        {
            parametros[i][j] = 0;
        }
    }

    int pos_v = 0;
    int r = 0;
    int go =1;
    int token=0;
    //Infelizmente o sgr só pode ser inicializado 1 vez
    int sgr_init = 0;
    system("clear");
    print_arr("Insira um comando:\n");
    while(go)
    {
        bzero(buffer,strlen(buffer));
        print_arr("$ ");
        if (fgets(buffer,size_buffer_linha,stdin))
        {
            int tam = strlen(buffer);
            //Se introduzir apenas um \n
            if(tam < 2){
                continue;
            }  
            //Se texto introduzido for um comando
            if (buffer[0] == '-'){
               if ( -1 ==find_command(&buffer[1])) 
               {
                   go =0;
                   r = -1;
               }
               continue;
            }
            /**
             * 
             * Se texto introduzido for o Show
             * 
             */ 
            else if ( !strncmp(buffer,"show(",5) && !strncmp(buffer + tam -3,");\n",3))
            {
                strncpy(input,&buffer[5],tam-3-5);
                //Verificar se não tem espaços
                for (size_t j = 0; j < tam-3-5; j++)
                {
                    if (isspace(input[j])){
                        print_arr("Insira uma variavel sem espaços\n");
                        bzero(input,strlen(input));
                        continue;
                    } 
                }
                //z = business_started_by_letter(sgr,'b');
                size_t k;
                for (k = 0; k < pos_v; k++)
                {
                    if (!strcmp(input,(v+k)->name))
                    {
                        /**Foi encontrado uma variavel dentro da nossa estrutura do interpretador
                         *
                         * Reproduzir o resultado no terminal
                         */
                        //Verificar se o sgr foi inicializado
                        if(sgr_init)
                        {
                            //Se for para dar show do init_sgr()
                            if ((v+k)->tipo == 1) break;

                            //Quando for show(Conteudo do CSV)
                            if ((v+k)->tipo == 10)
                            {
                                char *fake = malloc(sizeof(char) * 2000); 
                                FILE *f = fopen((v+k)->parametros[0],"r");
                                if (!f) {
                                    print_arr("Introduziu um ficheiro que não existe.\n");
                                    break;
                                    }
                                while (!feof(f))
                                {
                                   if (fgets(fake,2000,f))
                                   {
                                    print_arr(fake);
                                   }
                                   bzero(fake,strlen(fake));
                                }
                                print_arr("\n");
                                fclose(f);
                                break;
                            }
                            //Se o sgr tem o mesmo nome que a função que o chama
                            //Funcao tem um bug, a estrutura v está vazia
                            if ( strcmp( name_sgr(&v,pos_v-1),(v+k)->parametros[0]) ) 
                            {
                                print_arr("Nome do sgr ---> ");
                                print_arr(name_sgr(&v,pos_v-1));
                                print_arr("\nNome da variavel introduzida ---> ");
                                print_arr((v+k)->parametros[0]);
                                print_arr("\n");
                                break;
                            }
                            //p = top_businesses_with_category(sgr,1,"Pizza");
                          
                            print_num(k);
                            print_arr("\n");
                            print_arr("Executando query");
                            print_num((v+k)->tipo);
                            print_arr("...\n");
                            interpretador((v+k)->tipo,&t,&sgr,(v+k)->parametros);
                            decide_dump_table(&t,(v+k)->tipo);
                            //dump_table_pag(&t,"Review_ID",1);
                            free(t);
                            break;
                        }
                        else 
                        {
                            print_arr("A estrutura de dados ainda não foi inicializada.\n");
                            break;
                        }
                        break;
                    } 
                }
                if  (k >= num_variable || strlen((v+k)->name) <= 0)
                print_arr("Variavel não existe. Por favor inicialize\n");
                continue;
            }
            /**
             * 
             * toCSV();
             * 
             */
            else if (sscanf(buffer,"toCSV(%[^ ,],'%[^']',\"%[^ \n]);",parametros[0],parametros[1],parametros[2] ) > 2)
            {
                    int tam_para2 = strlen(parametros[2]);
                   if (strncmp(parametros[2] + tam_para2 -3,"\");",3) != 0 ){
                       print_arr("Verifique os ultimos 3 carateres\n");
                       continue;
                   } 
                   bzero(parametros[2] + tam_para2 -3,3);
                   int found =0;
                   if (!sgr_init){
                       print_arr("Inicie a estrutura de dados por favor.\n");
                       continue;
                   }
                   for (size_t k = 0; k < pos_v; k++)
                   {
                       if ( !strcmp((v+k)->name,parametros[0]) )
                       {
                           //z = business_started_by_letter(sgr,'b');
                            //Found the variable we are looking for
                            print_arr("Found it\n");
                            found =1;
                            FILE *f = fopen(parametros[2],"w+");
                            interpretador((v+k)->tipo,&t,&sgr,parametros);
                            dump_table_file(f,&t,';');
                       }
                   }
                   if (!found) print_arr("Variavel não existe\n");
                   
            }
            /**
             * 
             * Atribuição x = a();
             * 
             * 
             */
            else if ( (token = sscanf(buffer,"%s = %[^\n]",variable,input) ) > 1 )
            {
                //Processar informaçao para atribuir à variavel
                if (!strcmp(input,"init_sgr();"))
                {
                    if (sgr_init){
                        print_arr("A estrutura de dados já foi inicializada.\n");
                        continue;
                    }
                    sgr_init = 1;
                    if (!create_var(v,pos_v,variable,1,0)) continue;
                    clean_up(v,&pos_v,parametros,input);
                    print_arr("Executando query");
                    print_num((v+pos_v -1)->tipo);
                    print_arr("...\n");
                    interpretador(1,&t,&sgr,NULL);
                }
                else if (sscanf(input,"load_sgr(\"%[^\"]\",\"%[^\"]\",\"%[^ \n]",parametros[0],parametros[1],parametros[2] ) > 2)
                {
                    int tam_para2 = strlen(parametros[2]);
                    if (sgr_init){
                        print_arr("A estrutura de dados já foi inicializada.\n");
                        continue;
                    }
                    sgr_init = 1;
                    if (strncmp(parametros[2] + tam_para2 -3,"\");",3) != 0 ){
                        print_arr("Verifique os ultimos 3 carateres\n");
                        continue;
                    } 
                    bzero(parametros[2] + tam_para2 -3,3);
                    if (!create_var(v,pos_v,variable,0,3)) continue;
                    clean_up(v,&pos_v,parametros,input);
                    print_arr("Executando query");
                    print_num((v+pos_v -1)->tipo);
                    print_arr("...\n");
                   
                    
                    interpretador(0,&t,&sgr,v->parametros);

                }
                else if( sscanf(input,"businesses_started_by_letter(%[a-zA-Z0-9^ ],'%[^ \n]",parametros[0],parametros[1] ) > 1 )
                {
                    if (strncmp(parametros[1] + 1,"');",3) != 0 ){
                        print_arr("Verifique os ultimos 3 carateres\n");
                        continue;
                    } 
                    bzero(parametros[1] + 1,strlen(parametros[1]) - 1);
                    if (!create_var(v,pos_v,variable,2,2)) continue;
                    clean_up(v,&pos_v,parametros,input);
                    continue;
                }
                else if ( sscanf(input,"business_info(%[a-zA-Z0-9^ ],\"%[^ \n]",parametros[0],parametros[1] ) > 1 )
                {
                    int tam_para2 = strlen(parametros[1]);
                    if (strncmp(parametros[1] + tam_para2 -3,"\");",3) != 0 ){
                        print_arr("Verifique os ultimos 3 carateres\n");
                        continue;
                    } 
                    bzero(parametros[1] + tam_para2 -3,3);
                    if (!create_var(v,pos_v,variable,3,2)) continue;
                    clean_up(v,&pos_v,parametros,input);
                    continue;

                }
                else if (sscanf(input,"businesses_reviewed(%[a-zA-Z0-9^ ],\"%[^ \n]",parametros[0],parametros[1] ) > 1)
                {
                    int tam_para2 = strlen(parametros[1]);
                    if (strncmp(parametros[1] + tam_para2 -3,"\");",3) != 0 ){
                        print_arr("Verifique os ultimos 3 carateres\n");
                        continue;
                    } 
                    bzero(parametros[1] + tam_para2 -3,3);
                    if (!create_var(v,pos_v,variable,4,2)) continue;
                    clean_up(v,&pos_v,parametros,input);
                }
                else if (sscanf(input,"businesses_with_stars_and_city(%[a-zA-Z0-9^ ],%[^,],\"%[^ \n]",parametros[0],parametros[1],parametros[2] ) > 2)
                {
                    int tam_para2 = strlen(parametros[2]);
                    if (strncmp(parametros[2] + tam_para2 -3,"\");",3) != 0 ){
                        print_arr("Verifique os ultimos 3 carateres\n");
                        continue;
                    } 
                    bzero(parametros[2] + tam_para2 -3,3);
                    if (!create_var(v,pos_v,variable,5,3)) continue;
                    clean_up(v,&pos_v,parametros,input);
                }
                else if (sscanf(input,"top_businesses_by_city(%[a-zA-Z0-9^ ],%[^ \n]",parametros[0],parametros[1] ) > 1)
                {
                    print_arr(parametros[1]);
                    int tam_para1 = strlen(parametros[1]);
                    if (strncmp(parametros[1] + tam_para1 -2,");",2) != 0 ){
                        print_arr("Verifique os ultimos 2 carateres\n");
                        continue;
                    } 
                    bzero(parametros[1] + tam_para1 -2,2);
                    if (!create_var(v,pos_v,variable,6,2)) continue;
                    clean_up(v,&pos_v,parametros,input);
                }
                else if (sscanf(input,"international_users(%[^ \n]",parametros[0] ) > 0)
                {
                    int tam_para0 = strlen(parametros[0]);
                    if (strncmp(parametros[0] + tam_para0 -2,");",2) != 0 ){
                        print_arr("Verifique os ultimos 2 carateres\n");
                        continue;
                    } 
                    bzero(parametros[0] + tam_para0 -2,2);
                    if (!create_var(v,pos_v,variable,7,1)) continue;
                    clean_up(v,&pos_v,parametros,input);
                }
                else if (sscanf(input,"top_businesses_with_category(%[a-zA-Z0-9^ ],%[0-9],\"%[^ \n]",parametros[0],parametros[1],parametros[2] ) > 2)
                {
                    int tam_para2 = strlen(parametros[2]);
                    if (strncmp(parametros[2] + tam_para2 -3,"\");",3) != 0 ){
                        print_arr("Verifique os ultimos 3 carateres\n");
                        continue;
                    } 
                    bzero(parametros[2] + tam_para2 -3,3);
                    if (!create_var(v,pos_v,variable,8,3)) continue;
                    clean_up(v,&pos_v,parametros,input);
                }
                else if (sscanf(input,"reviews_with_word(%[a-zA-Z0-9^ ],%[0-9],\"%[^ \n]",parametros[0],parametros[1],parametros[2] ) > 2)
                {
                    int tam_para2 = strlen(parametros[2]);
                    if (strncmp(parametros[2] + tam_para2 -3,"\");",3) != 0 ){
                        print_arr("Verifique os ultimos 3 carateres\n");
                        continue;
                    } 
                    bzero(parametros[2] + tam_para2 -3,3);
                    if (!create_var(v,pos_v,variable,9,3)) continue;
                    clean_up(v,&pos_v,parametros,input);
                }
                else if (sscanf(input,"fromCSV(\"%[a-zA-Z0-9./]\",'%[^ \n]\n",parametros[0],parametros[1] ) > 1)
                {
                    int tam_para1 = strlen(parametros[1]);
                    if (strncmp(parametros[1] + tam_para1 -3,"');",3) != 0 ){
                        print_arr("Verifique os ultimos 3 carateres\n");
                        continue;
                    } 
                    bzero(parametros[1] + tam_para1 -3,3);
                    if (!create_var(v,pos_v,variable,10,2)) continue;
                    clean_up(v,&pos_v,parametros,input);
                }
                else
                {
                    print_arr("Atribuição mal feita escreva denovo :(\nEscreva -help\n");
                    continue;
                }
               
            }
            else print_arr("Comando inválido :(\nEscreva -help\n");
        }
        else go =0;
        bzero(buffer,strlen(buffer));
    }
     
    if(sgr)
    {    
        if (sgr->ht) free_ht(&sgr);
        free(sgr);
    }
    free(input);
    free(buffer);
    free(variable);
    return r;
}
