package Model.Query;
import Model.*;
import Model.Catalogos.Business_Struct;
import Model.Catalogos.GestReviews;
import Model.Catalogos.Review_Struct;
import Model.Catalogos.User_Struct;
import Model.Interfaces.IBusiness_Struct;
import Model.Interfaces.IReview_Struct;
import Model.Interfaces.IUser_Struct;
import View.Viewer;

import java.util.Map;
import java.util.Calendar;
import java.util.TreeSet;
import java.util.Map.Entry;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeMap;
import java.text.DateFormatSymbols;

public class Query5 implements Query_Interface {
    
    private GestReviews m_s;
    private String user_ID = "dK1WGJRl80i0ymRTIUNJ5w";
    Map<String,Integer> lista = new TreeMap<>();

    public Query5(GestReviews m_s)
    {
        this.m_s = m_s;   
    }

    public Query5(GestReviews m_s,String user_ID)
    {
        this.m_s = m_s; 
        this.user_ID = user_ID;
    }

    public void query()
    {
       IUser_Struct u = m_s.getUser_list().get(user_ID);
       Set<String> set_reviewed = u.getBusi_reviewed();
       for (String string : set_reviewed) {
        //  
        int count =0;
        IBusiness_Struct b = m_s.getHash_map().get(string);
          for (IReview_Struct r  : b.getUsers_rev()) {
            //Foi encontrada a review do usuario neste negocio  
            if (r.getUser_ID().compareTo(user_ID) == 0)
            {
                count++;
            }
          }
        lista.put(string,count);
        count = 0; 
       }
    }

    public void print_resultado()
    {
        int lowest = -1;
        Map.Entry<String,Integer> lowest_entrada = null;

        Viewer.print_Info("User_ID : " + user_ID+"\n");
        int tam = lista.size();
        for(int i=0;i<tam;i++)
        {
            //Getting head of Map
            for ( Map.Entry<String,Integer> fake : lista.entrySet()){
                lowest = fake.getValue();
                lowest_entrada = fake;
                break;
            }

            for (Map.Entry<String,Integer> entrada: lista.entrySet()) {
                if (entrada.getValue() < lowest)
                lowest_entrada = entrada;

            }
            Viewer.print_Info(lowest_entrada.getKey() + " foi criticado " + lowest_entrada.getValue() + " vezes"+"\n");
            lista.remove(lowest_entrada.getKey());
            lowest_entrada = null;
        }
    }
    
    public String getUser_ID() {
        return this.user_ID;
    }

    public void setUser_ID(String user_ID) {
        this.user_ID = user_ID;
    }


}