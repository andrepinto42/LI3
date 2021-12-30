package Controller;
import java.util.List;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import java.text.DateFormatSymbols;
import Model.*;
import Model.Catalogos.Business_Struct;
import Model.Catalogos.GestReviews;
import Model.Catalogos.Review_Struct;
import Model.Catalogos.User_Struct;
import Model.Interfaces.IBusiness_Struct;
import Model.Interfaces.IReview_Struct;
import Model.Interfaces.IUser_Struct;
import View.Viewer;

public class Information {


    public static int Users_Reviewed(GestReviews m)
    {
        int count=0;
        Map<String,IUser_Struct> hashmap = m.getUser_list();
        for (IUser_Struct u1: hashmap.values()) {
            if (u1.getBusi_reviewed().size() > 0) count++;
        }
        //Viewer.print_Info("Numero de usuarios que realizaram pelo menos 1 review : " +count);
        return count;
    }


    public static int Business_Reviewed(GestReviews m)
    {
        int count=0;
        Map<String,IBusiness_Struct> hashmap = m.getHash_map();
    
        for (IBusiness_Struct b : hashmap.values()) {
            if (b.getUsers_rev().size() > 0)
            count++;
        }
        return count;

    }


    public static void getUsersMonth(GestReviews m,int mes)
    {
        Map<String,IBusiness_Struct> hashmap = m.getHash_map();
        int count=0;
        Calendar calendario = Calendar.getInstance();
        for (IBusiness_Struct b : hashmap.values()) {
            for (IReview_Struct r : b.getUsers_rev()) {
                Date d = r.getData();
                calendario.setTime(d);
                if (calendario.get(Calendar.MONTH) == mes)
                {
                    //Mes encontrada
                    //System.out.println(r.getUser_ID());
                    count++;
                }         
            }

        }
        String s = "Numero de reviews no mes " + mes +" = "+ count;
        Viewer.print_Info(s);
    }

    /**
     * 
     * 
     * @param hashmap Um estrutura de dados populada
     * @return Imprime para o terminal o numero de reviews feitas em cada mes por utilizadores distintos
     */
    public static int[] getAvgMonth_distinct(GestReviews m)
    {
        Map<String,IBusiness_Struct> hashmap = m.getHash_map();
        int array[] = new int[12];
        int count=0;
        Map<Integer,Map<String,Integer>> lista = new HashMap<>();
        
        for (int k = 0; k < 12; k++) {
            Map<String,Integer> n_lista = new HashMap<>();
            lista.put(k,n_lista);
        }

        for (int i = 0; i < array.length; i++) array[i] = 0;
        
        Calendar calendario = Calendar.getInstance();
        for (IBusiness_Struct b : hashmap.values()) {
            for (IReview_Struct r : b.getUsers_rev()) {
                
                calendario.setTime(r.getData());
                int mes = calendario.get(Calendar.MONTH);
                Map<String,Integer> temp = lista.get(mes);
                
                if (temp.putIfAbsent(r.getUser_ID(),1) == null)
                array[mes]++;
            }

        }
        // 
        
        DateFormatSymbols dfs = new DateFormatSymbols();

        // for (int i = 0; i < array.length; i++) {
        //     String s = dfs.getMonths()[i] + " : " + array[i];
        //     Viewer.print_Info(s);
        // }

        return array;
    }

    /**
     * 
     * 
     * @param hashmap Um estrutura de dados populada
     * @return Imprime para o terminal o numero de reviews feitas em cada mes
     */
    public static float[] getAvgMonth(GestReviews m)
    {
        Map<String,IBusiness_Struct> hashmap = m.getHash_map();
        int array[] = new int[12];
        float stars[] = new float[12];
        int count=0;

        for (int i = 0; i < array.length; i++) array[i] = 0;
        for (int i = 0; i < stars.length; i++) stars[i] = 0;

        
        Calendar calendario = Calendar.getInstance();
        for (IBusiness_Struct b : hashmap.values()) {
            for (IReview_Struct r : b.getUsers_rev()) {
                
                calendario.setTime(r.getData());
                array[calendario.get(Calendar.MONTH)]++;
                stars[calendario.get(Calendar.MONTH)] += r.getStars();

            }

        }

        for (int i = 0; i < array.length; i++) {
            stars[i] /= array[i];
            stars[i] = Math.round(stars[i] * 1000f) / 1000f ;
        }

        return stars;
    }

}
