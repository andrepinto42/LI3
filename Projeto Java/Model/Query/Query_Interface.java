package Model.Query;
import java.text.DateFormatSymbols;

import Model.*;
import View.Viewer;

public interface Query_Interface {
    public void query();
    public void print_resultado();
    default public void run_query()
    {
        float start_r = System.nanoTime();
        query();
        float end_r = System.nanoTime();

        float start_p = System.nanoTime();
        print_resultado();
        float end_p = System.nanoTime();

        float elapsedTime_r = (end_r - start_r) / 1000000000.0f;
        float elapsedTime_p = (end_p - start_p) / 1000000000.0f;
        Viewer.print_Info("Time spent running : " + elapsedTime_r + "s\n");
        Viewer.print_Info("Time spent printing : " + elapsedTime_p + "s\n");
        Viewer.print_Info("Total time spent : " + (elapsedTime_r +elapsedTime_p) + "s\n");
        String class_type = this.getClass().getSimpleName();
        switch (class_type)
        {
            case "Query1":
            
            Viewer.print_Info("Query1 executada\n");
            break;

            case "Query2":
            DateFormatSymbols dfs = new DateFormatSymbols();
            Query2 q2 = (Query2) this;
            Viewer.print_Info("Query2 executada para o mes: " + dfs.getMonths()[q2.getMes()] + 
            " no ano " + q2.getAno() +"\n");
            break;
            
            case "Query3":
            Query3 q3 = (Query3) this;
            Viewer.print_Info("Query3 executada para o usuario: " + q3.getUser_ID() +"\n") ;
            break;

            case "Query4":
            Query4 q4 = (Query4) this;
            Viewer.print_Info("Query4 executada para o negocio:  " + q4.getBusiness_ID() +" \n");
            break;

            case "Query5":
            Query5 q5 = (Query5) this;
            Viewer.print_Info("Query5 executada para o usuario: " +q5.getUser_ID() +"\n" );
            break;
           
            case "Query6":
            Query6 q6 = (Query6) this;
            Viewer.print_Info("Query6 executada para o tamanho: " +q6.getSize_array() + "\n");
            break;
            case "Query7":
            Query7 q7 = (Query7) this;
            Viewer.print_Info("Query7 executada para os " +q7.getMax_famous() + " famosos\n" );
            break;

            case "Query8":
            Query8 q8 = (Query8) this;
            Viewer.print_Info("Query8 executada para os " +q8.getMax_dif_user() +
            " utilizadores que avaliaram mais negócios diferentes\n" );
            break;

            case "Query9":
            Query9 q9 = (Query9) this;
            Viewer.print_Info("Query9 executada para os " +q9.getMax_users() +
            " utilizadores que mais avaliaram " +q9.getBusiness_ID() +"\n");
            break;

            case "Query10":
            Viewer.print_Info("Query10 executada para toda a estrutura \n");
            break;

            default:
            Viewer.print_Info("Ocorreu algum erro\n");
            break;
        }
       

    }
}
