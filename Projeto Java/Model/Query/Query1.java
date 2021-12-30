package Model.Query;
import Model.Catalogos.GestReviews;
import Model.Interfaces.IBusiness_Struct;
import View.Viewer;

import java.util.Map;
import java.util.TreeSet;
import java.util.Set;

public class Query1 implements Query_Interface {
    
    private int total =0;
    private GestReviews m_s;
    Set<String> bus_id = new TreeSet<>();



    public Query1(GestReviews m_s)
    {
        this.m_s = m_s;
    }

    public void query()
    {
        Map<String,IBusiness_Struct> hash_map = m_s.getHash_map();
        for (Map.Entry<String, IBusiness_Struct> entry : hash_map.entrySet()) {
            if (entry.getValue().getUsers_rev().size() == 0)
            {
                bus_id.add(entry.getKey());
            }
        }
        
        total = bus_id.size();
    }
    public void print_resultado()
    {
        for (String s : bus_id) {
            System.out.print(s + " ");
        }
        bus_id.clear();

        Viewer.print_Info("Negocios totais não avaliados : " + total +"\n");
    }

    public int getTotal() {return this.total;}


}
