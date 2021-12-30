#include "main.h"

int main(int argc,char *argv[]){
    Sgr *sgr = NULL;
    Table t = NULL;

    // sgr = init_sgr();
    // t = international_users(sgr);
    // dump_table(t);
    // dump_table_pag(&t,"Users_ID",0);
    // return 0;
   
    int comando = read_input(sgr,t);
    if (comando == -1) return -1;
    
    return 1;
}
