package Model.Query;
import Model.*;
import Model.Catalogos.Business_Struct;
import Model.Catalogos.GestReviews;
import Model.Catalogos.Review_Struct;
import Model.Interfaces.IBusiness_Struct;
import Model.Interfaces.IReview_Struct;
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

public class Query4 implements Query_Interface {
    
    private GestReviews m_s;
    private String business_ID = "ZnT8Fiyb0oCkhbygsq_Q6Q";
    private int num[] = new int[12];
    private float stars[] = new float[12];
    private List<Set<String>> distinct_month = new ArrayList<Set<String>>();

    public Query4(GestReviews m_s)
    {
        this.m_s = m_s;   
    }
    public Query4(GestReviews m_s,String business_ID)
    {
        this.m_s = m_s;   
        this.business_ID = business_ID;
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
         IBusiness_Struct b = m_s.getHash_map().get(business_ID);

         //Iterar sobre cada  Review do negocio
         for (IReview_Struct r : b.getUsers_rev()) {
            calendario.setTime(r.getData());
            int mes = calendario.get(Calendar.MONTH);
            num[mes]++;
            stars[mes] += r.getStars();
            distinct_month.get(mes).add(r.getUser_ID());

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
        
           Viewer.print_Info(dfs.getMonths()[i] + "-> Reviews " + num[i] +
            " Stars : " + stars[i] + " Usuarios : "+distinct_month.get(i)+"\n");
            num[i] = 0;
            stars[i] = 0;
            distinct_month.get(i).clear();
        }
    }

    public String getBusiness_ID() {
        return this.business_ID;
    }

    public void setBusiness_ID(String business_ID) {
        this.business_ID = business_ID;
    }

}
