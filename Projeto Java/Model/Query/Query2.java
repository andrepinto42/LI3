package Model.Query;
import Model.Interfaces.IBusiness_Struct;
import Model.Interfaces.IReview_Struct;
import Model.Catalogos.GestReviews;
import View.Viewer;

import java.util.Map;
import java.util.Calendar;
import java.util.TreeSet;
import java.util.Set;
import java.text.DateFormatSymbols;

public class Query2 implements Query_Interface {
    
    private int total =0;
    private GestReviews m_s;
    private int mes = 0;
    private int ano = 2010;
    Set<String> users = new TreeSet<>();

    public Query2(GestReviews m_s)
    {
        this.m_s = m_s;   
    }

    public Query2(GestReviews m_s,int mes, int ano)
    {
        this.m_s = m_s;   
        this.mes = mes;
        this.ano = ano;

    }

    public void query()
    {
        Calendar calendario = Calendar.getInstance();
        Map<String,IBusiness_Struct> hash_map = m_s.getHash_map();
        for (IBusiness_Struct b : hash_map.values()) {
            for (IReview_Struct r : b.getUsers_rev()) {
                calendario.setTime(r.getData());
                if ( calendario.get(Calendar.YEAR) == ano && calendario.get(Calendar.MONTH) == mes)
                {
                    total++;
                    users.add(r.getUser_ID());
                }
            } 
        }
    }

    public void print_resultado()
    {
        DateFormatSymbols dfs = new DateFormatSymbols();

        for (String s : users) {
            Viewer.print_Info(s+ " ");
        }
        users.clear();
        Viewer.print_Info("\nNumero reviews realizadas em " + dfs.getMonths()[mes] + " do ano " + ano 
                            + " = " +  total + "\n");
    }

    public int getTotal() {
        return this.total;
    }

   

    

    public int getMes() {
        return this.mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public int getAno() {
        return this.ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }


}
