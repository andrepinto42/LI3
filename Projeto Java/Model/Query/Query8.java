package Model.Query;
import Model.*;
import Model.Catalogos.GestReviews;
import View.Viewer;

import java.util.Map;
import java.util.TreeSet;
import java.util.Map.Entry;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class Query8 implements Query_Interface {
    
    private GestReviews m_s;
    private int max_dif_user = 3;
    //Set<String> Cada string equivale a um usuario
    private Comparator<String> comparador = (s1,s2) -> {
        return m_s.getUser_list().get(s2).getBusi_reviewed().size() - m_s.getUser_list().get(s1).getBusi_reviewed().size();
    };
    
    private Set<String> lista = new TreeSet<>(comparador);

    public Query8(GestReviews m_s)
    {
        this.m_s = m_s;   
    }

    public Query8(GestReviews m_s,int max_dif_user)
    {
        this.m_s = m_s;   
        this.max_dif_user = max_dif_user;
    }

    public void query()
    {
        
        for (String user_id : m_s.getUser_list().keySet()) lista.add(user_id);
        
    }

    public void print_resultado()
    {
        Iterator<String> it = lista.iterator();
        List<String> reversed = new ArrayList<String>();
        for (int i = 0; i < max_dif_user && it.hasNext();i++)
        reversed.add(it.next());
        
        for (int i = 0; i < max_dif_user; i++) 
        Viewer.print_Info("Nº " + (max_dif_user - i) + " -> " + reversed.get(max_dif_user - (i+1)) + "\n");
            
        
        reversed.clear();
        lista.clear();
    }



    public int getMax_dif_user() {
        return this.max_dif_user;
    }

    public void setMax_dif_user(int max_dif_user) {
        this.max_dif_user = max_dif_user;
    }


}