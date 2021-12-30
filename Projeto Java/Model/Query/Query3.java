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
import java.util.TreeSet;
import java.util.Map.Entry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.Calendar;
import java.util.List;
import java.text.DateFormatSymbols;


public class Query3 implements Query_Interface {
    
    private GestReviews m_s;
    private String user_ID = "dK1WGJRl80i0ymRTIUNJ5w";
    int num[] = new int[12];
    float stars[] = new float[12];
    List<Set<String>> distinct_month = new ArrayList<Set<String>>();

    public Query3(GestReviews m_s)
    {
        this.m_s = m_s;   
    }

    public Query3(GestReviews m_s,String user_ID)
    {
        this.m_s = m_s;   
        this.user_ID = user_ID;
    }

    public void query()
    {
        
        
        //Inicializar os arrays a 0
        for (int i = 0; i < 12; i++) {
           distinct_month.add(new TreeSet<>());
           num[i] = 0;
           stars[i] = 0;
        }

        Calendar calendario = Calendar.getInstance();
        IUser_Struct u = m_s.getUser_list().get(user_ID);
        for (String s : u.getBusi_reviewed()) {
            //Encontrar o negocio que o utilizador fez review
            IBusiness_Struct b = m_s.getHash_map().get(s);
            
            for (IReview_Struct r : b.getUsers_rev()) {
                //Se o utilizador corresponder
                if ( user_ID.equals(r.getUser_ID()) )
                {
                    calendario.setTime(r.getData());
                    int mes = calendario.get(Calendar.MONTH);
                    distinct_month.get(mes).add(s);
                    num[mes]++;
                    stars[mes] += r.getStars();
                }
            }
            
        } 
        for (int i = 0; i < 12; i++) {
            if (num[i] >0 )
            stars[i] /= num[i];
        }

    }

    public void print_resultado()
    {
        DateFormatSymbols dfs = new DateFormatSymbols();

        for (int i = 0; i < 12; i++) {
        
            Viewer.print_Info(dfs.getMonths()[i] + "-> Reviews " +num[i] + " Stars: " +
            stars[i] + " Business : "+distinct_month.get(i) + "\n");
            num[i] =  0;
            stars[i] = 0;
            distinct_month.get(i).clear();
        }
        
    }


    public String getUser_ID() {
        return this.user_ID;
    }

    public void setUser_ID(String user_ID) {
        this.user_ID = user_ID;
    }

    

}
