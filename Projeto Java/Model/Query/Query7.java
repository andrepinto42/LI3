package Model.Query;
import Model.Interfaces.IBusiness_Struct;
import Model.Catalogos.GestReviews;

import java.util.Map;
import java.util.TreeSet;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class Query7 implements Query_Interface {
    
    private GestReviews m_s;
    private Map<String,Set<IBusiness_Struct>> lista = new HashMap<>();
    private final int max_famous = 3;


    public Query7(GestReviews m_s)
    {
        this.m_s = m_s;   
    }

   


    public void query()
    {
        Comparator<IBusiness_Struct> compuser =
        (p1, p2) -> {
            return  p2.getUsers_rev().size() -p1.getUsers_rev().size();
        };

        for (IBusiness_Struct b : m_s.getHash_map().values()) {
            if (lista.get(b.getCity())== null)
            {
                //Chave nao existe é necessario inserir
                Set<IBusiness_Struct> best_cities = new TreeSet<>(compuser);
                best_cities.add(b);
                lista.put(b.getCity(), best_cities);
            }
            else
            {
                //Chave existe, precisa ser adicionada
                lista.get(b.getCity()).add(b);
            }
        }
    }

    public void print_resultado()
    {
        for (Map.Entry<String,Set<IBusiness_Struct>> entradas : lista.entrySet()) {
            
            System.out.print(entradas.getKey() + " most famous : ");
            Iterator<IBusiness_Struct> it = entradas.getValue().iterator();
            int i =0;
           
                while (i < max_famous && it.hasNext() )
                {
                  System.out.print("| " + it.next().getName() + " ");
                  i++;
                }
                System.out.print("|\n");
                entradas.getValue().clear();
        }
        lista.clear();
    }


   
    public int getMax_famous() {
        return this.max_famous;
    }



}
