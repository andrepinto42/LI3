package Model.Query;
import Model.*;
import Model.Catalogos.Business_Struct;
import Model.Catalogos.GestReviews;
import Model.Catalogos.Review_Struct;
import Model.Interfaces.IBusiness_Struct;
import Model.Interfaces.IReview_Struct;

import java.util.Map;
import java.util.TreeSet;
import java.util.Map.Entry;
import java.util.Calendar;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

import Model.Pair.*;
import View.Viewer;

public class Query6 implements Query_Interface {
    
    private GestReviews m_s;
    /**
     * Estrutura auxiliar para filtrar os X melhores negocios por ano
     * Integer contem a chave ANO
     * Set<String> contem os negocios todos desse ano ordenados por reviews que tiveram
     */
    private Map<Integer,Set<String>> resultado = new TreeMap<>();
    /**
        Primeira chave corresponde ao Ano |
        Map com  chave = Business_ID e um estrutura Par
        No par Set<Strings> corresponde aos reviews feitos a um negocio em um certo ano
        O Integer do Par corresponde ao número total de reviews desse negocio
        O tamanho do Set<String> corresponde ao número total de reviews distintas 
    */
    private Map<Integer,Map<String,Pair<Set<String>,Integer>>>lista = new TreeMap<>();
    private int size_array = 2;
    public Query6(GestReviews m_s)
    {
        this.m_s = m_s;
    }

    public Query6(GestReviews m_s,int size_array)
    {
        this.m_s = m_s;
        this.size_array = size_array;
    }

    public void query()
    {
        Calendar calendario = Calendar.getInstance();
        int ano;
        String user_id;
        
        for (Map.Entry <String,IBusiness_Struct> entrada : m_s.getHash_map().entrySet()){
            for (IReview_Struct r : entrada.getValue().getUsers_rev())
            {
                calendario.setTime(r.getData());
                ano = calendario.get(Calendar.YEAR);
                user_id = r.getUser_ID();
                if (lista.get(ano) == null)
                {
                    //Se ainda nao existe para um Map para o ano entao é necessario inserir
                    Pair<Set<String>,Integer> novo = Pair.make(new TreeSet<>(),1);
                    novo.getFirst().add(user_id);
                    Map<String,Pair<Set<String>,Integer>> mapa = new HashMap<>();
                    mapa.put(entrada.getKey(),novo);
                    lista.put(ano,mapa);
                }
                else
                {
                    //Chave desse ano já existe
                    Map<String,Pair<Set<String>,Integer>> ano_reviews = lista.get(ano);

                    //Buscar o par do negocio que esta na HashMap
                    Pair<Set<String>,Integer> pair_t = ano_reviews.get(entrada.getKey());
                    if (pair_t == null)
                    {
                        //Se nao existir a chave
                        pair_t = Pair.make(new TreeSet<>(),1);
                        ano_reviews.put(entrada.getKey(),pair_t);

                    }
                    else//Chave existe, entao é incrementada
                    pair_t.setSecond(pair_t.getSecond() + 1);
                    
                    //Adicionar a um TreeSet, se já existir entao é descartado
                    pair_t.getFirst().add(user_id);

                }
            }
        }

        choose_max();

    }
    private void choose_max()
    {
        for (Integer i : lista.keySet()) {
            //Comparador 
            Comparator<String> compuser =
            (p1, p2) -> {
                return   lista.get(i).get(p2).getSecond() - lista.get(i).get(p1).getSecond() ;
            };
            //Preencher cada ano com um TreeSet Ordenado
            resultado.put(i,new TreeSet<>(compuser));
        }
        
        for(Map.Entry<Integer,Map<String,Pair<Set<String>,Integer>>> entrada_ano : lista.entrySet())
        {
            int ano = entrada_ano.getKey();
            Set<String> negocios =  resultado.get(ano);

            //Iterar sobre cada negocio de um certo ano, Verificar se ele é possivel ser inserido
            //no Map resultado
            for (Map.Entry<String,Pair<Set<String>,Integer>> business : entrada_ano.getValue().entrySet())
            {
                String business_ID = business.getKey();

                negocios.add(business_ID);
                if (negocios.size() > size_array)
                {
                    Iterator<String> it = negocios.iterator();

                    for (int j = 0; j < negocios.size() -1; j++) it.next();
                    
                    //Remover o ultimo elemento do Set
                    negocios.remove(it.next());
                }
            }
        }
    }

    public void print_resultado()
    {
        for (Map.Entry<Integer,Set<String>> entrada : resultado.entrySet())
        {
            Viewer.print_Info("Ano " + entrada.getKey() +  " :"+"\n");
            for (String s : entrada.getValue()) {
                int num_revs = lista.get(entrada.getKey()).get(s).getSecond();
                int num_revs_dif = lista.get(entrada.getKey()).get(s).getFirst().size();
                Viewer.print_Info("["+ s + "] Reviewed: " + num_revs + " Diferent Users : " + num_revs_dif+"\n");
            }
            Viewer.print_Info("-------"+"\n");
            entrada.getValue().clear();
        }
        resultado.clear();

        for(Map.Entry<Integer,Map<String,Pair<Set<String>,Integer>>> entrada_ano : lista.entrySet())
        {
            for (Pair<Set<String>,Integer>  p : entrada_ano.getValue().values()) {
                p.getFirst().clear();
            }
            entrada_ano.getValue().clear();
        }
        lista.clear();
    }

    

    public int getSize_array() {
        return this.size_array;
    }

    public void setSize_array(int size_array) {
        this.size_array = size_array;
    }

}
