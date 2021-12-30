package Model.Query;
import Model.*;
import Model.Catalogos.Business_Struct;
import Model.Catalogos.GestReviews;
import Model.Interfaces.IBusiness_Struct;
import View.Viewer;

import java.util.Map;
import java.util.TreeSet;
import java.util.Map.Entry;
import java.util.HashMap;
import java.util.Set;

public class Query10 implements Query_Interface {
    
    private int total =0;
    private GestReviews m_s;
    /**
     * String1 = Estado
     * String2 = Cidade
     * String3 = Business_ID
     * Float = Rating
     */
    private Map<String,Map<String,Map<String,Float>>> lista = new HashMap<>();


    public Query10(GestReviews m_s)
    {
        this.m_s = m_s;   
    }

    public void query()
    {
        for (IBusiness_Struct b : m_s.getHash_map().values()) {

            Map<String,Map<String,Float>> state_map = lista.get(b.getState());
            
            //Nao existe estrutura para este estado
            if ( state_map == null )
            {
                state_map = new HashMap<>();
                //Criar um map da cidade e preencher 
                Map<String,Float> cidade_map = new HashMap<>();
                cidade_map.put(b.getName(),b.getStars());
                state_map.put(b.getCity() ,cidade_map);

                lista.put(b.getState(),state_map);
            }
            else
            {
                Map<String,Float> cidade_map = state_map.get(b.getCity());
                
                //Ainda nao existe estrutura para essa cidade
                if ( cidade_map == null)
                {
                    //Criar um novo map e popular-lo
                    cidade_map = new HashMap<>();
                    cidade_map.put(b.getName(),b.getStars());
                    state_map.put(b.getCity(),cidade_map);

                }
                else
                {
                    //Adicionar um novo negocio
                    cidade_map.put(b.getName(),b.getStars());
                }
            }
        }
    }

    public void print_resultado()
    {

        for (Map.Entry<String, Map<String,Map<String,Float>>> state_map : lista.entrySet()) {
            System.out.print("\n       ------------------" + state_map.getKey() + "------------------\n");
            for (Map.Entry <String,Map<String,Float>> city_map : state_map.getValue().entrySet()) {
            System.out.print("                     ****" + city_map.getKey() + "****\n" );
                for (Map.Entry <String,Float> business_map : city_map.getValue().entrySet()) {
                    Viewer.print_Info( business_map.getKey() + " -> " + business_map.getValue() +"\n");
                    
                }
                city_map.getValue().clear();
            }
            state_map.getValue().clear();
        }
    }

}
