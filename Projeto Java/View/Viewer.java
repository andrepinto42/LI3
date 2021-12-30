package View;
import Model.*;
import Model.Catalogos.GestReviews;
import Model.Interfaces.IBusiness_Struct;
import Model.Interfaces.IReview_Struct;
import Controller.Information;
import java.util.Map;

import java.text.DateFormatSymbols;

public class Viewer{


    //Funcao reaproveitada do projecto C de LI3
    public static StringBuilder print_Spaces(int tamanho,String array){
        StringBuilder sb = new StringBuilder();
        int offset = (tamanho - array.length()) / 2;
        for (int i = 0; i < offset; i++)
        {
            sb.append(" ");
        }
        sb.append(array);
        for (int i = 0; i < offset; i++)
        {
            sb.append(" ");
        }
        if (((tamanho -array.length()) % 2) == 1) sb.append(" ");

        return sb;
    }

    //Funcao reaproveitada do projeto C de LI3
    public static  StringBuilder makeBox(int start_size){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < start_size; i++) {
            sb.append("-");
        }
        sb.append("\n");
        return sb;
    }


    public static void help_commands()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("\nComandos disponíveis : \n");
        sb.append("* Para executar query insira um numero entre 1 e 10\n");
        sb.append("* quit -> sai do programa\n");
        sb.append("* reload -> recarrega a estrutura de dados com o path do Usuario\n");
        sb.append("* serialize -> serializa a estrutura de dados e guarda em obj/li3.ser\n");
        sb.append("* deserialize -> deserializa o argumento dado e se possivel converte na estrutura de dados principal\n\n***");
        sb.append(print_Spaces(70,"Ao fornecer argumentos pode optar por inserir uma linha vazia")).append("***\n***");
        sb.append(print_Spaces(70, "dessa forma executa um argumento predefinido")).append("***\n");

        System.out.print(sb.toString());
    }
    public static void printTable(Map<String,IBusiness_Struct> hashmap)
    {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String,IBusiness_Struct> entry : hashmap.entrySet()) {
            sb.append(entry.getKey()).append(" | ");
            IBusiness_Struct b = entry.getValue();
            sb.append(b.getName()).append(" | ");
            sb.append(b.getCity()).append(" | ");
            sb.append(b.getState()).append(" | Stars: ");
            sb.append(b.getStars()).append(" Rates: ");
            sb.append(b.getRates()).append(" [ ");
            for (IReview_Struct r : entry.getValue().getUsers_rev()) {
                sb.append(printReview(r));
            }
            sb.append(" ]\n");
            System.out.println(sb.toString());
            sb.delete(0, sb.length());
        }
    }
    public static void print_Info(String s,int num){
        System.out.print(s + num);
    }
    public static void print_Info(String s)
    {
        System.out.print(s);
    }
    public static StringBuilder print_Avg_Month(GestReviews m_s)
    {
        //O comprimento de uma linha de ---------
        int tamanho = 70;
        //O comprimento da string mes que é imprimida
        int espaco_mes = 15;

        StringBuilder sb = new StringBuilder();
        DateFormatSymbols dfs = new DateFormatSymbols();
        float arr[] = Information.getAvgMonth(m_s);
        int distinct[] = Information.getAvgMonth_distinct(m_s);
        sb.append(makeBox(tamanho));
        for (int i = 0; i < arr.length; i++) {
            String mes = dfs.getMonths()[i];
            sb.append("|");
            sb.append(print_Spaces(espaco_mes,mes));
            sb.append("|");
            sb.append(print_Spaces(tamanho-espaco_mes-3, "Usuarios : " + distinct[i] + " -> Stars : " + arr[i]));
            sb.append("|\n");
            sb.append(makeBox(tamanho));
            // sb.append(" -> Usuarios : ").append(distinct[i]).append("-> Stars : ").append(arr[i]).append("|\n");
        }
        return sb;
    }

    public static void print_GestReviews(GestReviews m_s)
    {
        
    StringBuilder sb = new StringBuilder();
    sb.append(makeBox(70));
    sb.append(print_Spaces(70,"Recolhendo estatisticas da estrutura de dados") + "\n");
    sb.append(makeBox(70));
    sb.append("Linhas erradas do ficheiro Business -> ").append(Parser.path_busi).append(" : ").append(m_s.getErros_Business());
    sb.append("\nLinhas erradas do ficheiro Review -> ").append(Parser.path_review).append(" : ").append(m_s.getErros_Review());
    sb.append("\nLinhas erradas do ficheiro Users -> ").append(Parser.path_users).append(" : ").append(m_s.getErros_Users());
    sb.append("\nBusiness validados : ").append(m_s.getValidos_Business());
    sb.append("\nReviews validados : ").append(m_s.getValidos_Review());
    sb.append("\nUsers validados : ").append(m_s.getValidos_User());
    int bus_rev = Information.Business_Reviewed(m_s);
    sb.append("\nNº de Business avaliados pelo menos 1 vez : ").append(bus_rev);
    sb.append("\nNº de Business não avaliados : ").append(m_s.getValidos_Business()- bus_rev);
    int user_rev = Information.Users_Reviewed(m_s);
    sb.append("\nNº de Users que avaliaram : ").append(user_rev);
    sb.append("\nNº de Users que nada avaliaram : ").append( m_s.getValidos_User() -user_rev);
    sb.append("\nReviews com Impacto 0 : ").append(m_s.getZero_revs()).append("\n");
    sb.append(makeBox(70));
    sb.append(print_Spaces(70,"*** Media de classificação por mês ***")).append("\n");
    
    sb.append(print_Avg_Month(m_s));


    System.out.println(sb.toString());
    }


    public static void printReviews_Business(Map<String,IBusiness_Struct> hashmap)
    {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String,IBusiness_Struct> entry : hashmap.entrySet()) {
            //Ciclo para imprimir todas as reviews de cada entrada
            sb.append(entry.getKey()).append(" -> ");
            for (IReview_Struct r : entry.getValue().getUsers_rev()) {
                //Ciclo para imprimir cada Review
                sb.append("| ID ");
                sb.append(r.getUser_ID()).append(" ");
                for (int i = 0; i < 3; i++) {
                    sb.append((r.getRevs())[i]).append(" ");
                }
                sb.append(r.getData()).append(" |");

            }
            sb.append(" |\n");
            System.out.println(sb.toString());
            sb.delete(0, sb.length());
        }
    }

    public static StringBuilder printReview(IReview_Struct r)
    {
        StringBuilder sb = new StringBuilder();
        sb.append(r.getUser_ID()).append(", ");
        return sb;
    }
}