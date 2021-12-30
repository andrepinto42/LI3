package Model.Query;
import Model.*;
import Model.Catalogos.Business_Struct;
import Model.Catalogos.GestReviews;
import Model.Catalogos.Review_Struct;
import Model.Interfaces.IBusiness_Struct;
import Model.Interfaces.IReview_Struct;
import Model.Pair.Pair;
import View.Viewer;

import java.util.Map;
import java.util.TreeSet;
import java.util.Map.Entry;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class Query9 implements Query_Interface {
    
    private GestReviews m_s;
    private String Business_ID = "ZnT8Fiyb0oCkhbygsq_Q6Q";
    private int max_users = 3;
    /*String = User_ID
    Integer 1 = Numero de reviews realizadas;
    Integer 2 = Classificação média
    */
    Comparator<Pair<String,Pair<Integer,Float>>> comparador = (p1,p2) ->
    {
        int num_revs1 = p1.getSecond().getFirst();
        int num_revs2 = p2.getSecond().getFirst();

        if ( num_revs2 - num_revs1 == 0)
        {
            //Retorna ordenados alfabeticamente 
            return p1.getFirst().compareTo(p2.getFirst());
        } //Retorna ordenados por numero de reviews decrescente
        else  return num_revs2 - num_revs1;
    };
    Set<Pair<String,Pair<Integer,Float>>> lista = new TreeSet<>(comparador);
    Map<String,Pair<Integer,Float>> mapa_valores = new HashMap<>();


    public Query9(GestReviews m_s)
    {
        this.m_s = m_s;   
    }

    public Query9(GestReviews m_s,String Business_ID,int max_users)
    {
        this.m_s = m_s;
        if (Business_ID.compareTo("") != 0)
        this.Business_ID = Business_ID;
        if (max_users != -1)
        this.max_users = max_users;
    }
    public Query9(GestReviews m_s,int max_users)
    {
        this.m_s = m_s;   
        this.max_users = max_users;
    }
    
    public Query9(GestReviews m_s,String Business_ID)
    {
        this.m_s = m_s;   
        this.Business_ID = Business_ID;
    }
    

    

    public void query()
    {
        IBusiness_Struct b = m_s.getHash_map().get(Business_ID);
        for (IReview_Struct r : b.getUsers_rev()) {
            Pair<Integer,Float> p = mapa_valores.get(r.getUser_ID());
            if (p == null)
            {
                //Nao existe chave, tem de ser inserida
                mapa_valores.put(r.getUser_ID(),Pair.make(1, r.getStars()));
            }
            else
            {
                //Chave já existe então é necessário atualizar
                p.setFirst(p.getFirst() + 1);
                p.setSecond(p.getSecond() + r.getStars());
            }
        }

        for (Map.Entry<String,Pair<Integer,Float>> entrada : mapa_valores.entrySet()) {
            lista.add(Pair.make( entrada.getKey(), entrada.getValue() ) );
        }

    }

    public void print_resultado()
    {
        Iterator< Pair<String,Pair<Integer,Float>> >  it = lista.iterator();
        for (int i = 0; it.hasNext() && i<max_users ; i++) {
            Pair<String,Pair<Integer,Float>> p = it.next();
            float average = p.getSecond().getSecond() / p.getSecond().getFirst();

            Viewer.print_Info("Nº " + (i+1) + " -> " + p.getFirst() + " reviewed " +
            p.getSecond().getFirst() + " times, AVG : " + average + "\n");
        }

        lista.clear();
        mapa_valores.clear();
    }

    public String getBusiness_ID() {
        return this.Business_ID;
    }

    public void setBusiness_ID(String Business_ID) {
        this.Business_ID = Business_ID;
    }

    public int getMax_users() {
        return this.max_users;
    }

    public void setMax_users(int max_users) {
        this.max_users = max_users;
    }

   
    

}
