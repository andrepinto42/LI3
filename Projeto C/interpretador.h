#ifndef INTERPRETADOR
#define INTERPRETADOR
#include "queryPai.h"
#include "query1.h"
#include "query2.h"
#include "query3.h"
#include "query5.h"
#include "query6.h"
#include "query4.h"
#include "query7.h"
#include "query8.h"
#include "query9.h"

typedef struct var_interpretador{
    char name[200];
    int funcao;
    int num_para;
    int ocupacao;
    int tipo;
    char **parametros;

}*Var;


void interpretador(int num,Table *t,Sgr **sgr,char **parametros);
int read_input(Sgr *sgr,Table t);
#endif