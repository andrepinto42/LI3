package Controller;
import java.util.Map;
import Model.*;
import Model.Catalogos.GestReviews;
import Model.Interfaces.IBusiness_Struct;
import Model.Query.*;
import View.*;
import java.util.Scanner;


import Controller.Exceptions.InvalidException;


public class Controller {
    public static GestReviews main_S;
    public static Map<String,IBusiness_Struct> h_m;
    public static final boolean read_friends = false;

    public static boolean read_files(String User_path,String Busi_path,String Review_path)
    {
        
        try{
        Viewer.print_Info("Loading Users file...\n");
        //Popular um HashMap apenas com uma chave User_ID e o valor é o nome do Usuario
        Parser.readUser(User_path);
       

        Viewer.print_Info("Reading Business file...\n");
        //Primeiro é necessario preencher a Estrutura com BusinessID,nome,cidade e estado
        Parser.readBusi(Busi_path);
        
        Viewer.print_Info("Loading Reviews file...\n");
        //Preenche agora com as Reviews dos utilizadores
        Parser.readReview(Review_path);
   
        if (read_friends){
        //Para ser Implementado    

        }
        main_S = Parser.getGestReviews();

        Viewer.print_Info("All structs have been read.\n");

        } 
        catch (InvalidException e)
        {
            Viewer.print_Info(e.toString());
            Viewer.print_Info("\nEstrutura de dados vai ser toda reiniciada\n");
            clear_struct();
            return false;
        }
        return true;
    }

    public static void wait_instruction()
    {
        String instruction = "";
        boolean ok;
        Scanner sc;
        String user_id ,business_id,mes,ano,num;
        user_id = business_id = mes = ano = null;        
        do{
            ok = false;
            System.out.print("Insira um comando: ");
            sc = new Scanner(System.in);
            instruction = sc.nextLine();
            Query_Interface q = null;
            switch(instruction)
            {   
                case "1":
                q = new Query1(main_S);
                break;
                
                case "2":
                Viewer.print_Info("Insira um mes (em numerico) : ");
                mes = sc.nextLine();
                Viewer.print_Info("Insira um ano : ");
                ano = sc.nextLine();
                //Construtor por definicao
                if (ano.compareTo("") == 0 && mes.compareTo("") == 0)
                q = new Query2(main_S);
                else
                {
                    try{
                        q = new Query2(main_S, Integer.parseInt(mes), Integer.parseInt(ano));
                    } catch (Exception e) {Viewer.print_Info("Insira numeros por favor :)\n");}
                }
                break;
                
                case "3":
                Viewer.print_Info("Insira um user_ID : ");
                user_id = sc.nextLine();

                if (user_id.compareTo("") == 0) q = new Query3(main_S);
                
                else q = new Query3(main_S,user_id);      
                break;
                
                case "4":
                Viewer.print_Info("Insira um Business_ID : ");
                business_id = sc.nextLine();
                
                if (business_id.compareTo("") == 0) q = new Query4(main_S);
                
                else  q = new Query4(main_S,business_id);
                break;
                
                case "5":
                Viewer.print_Info("Insira um user_ID : ");
                user_id = sc.nextLine();

                if (user_id.compareTo("") == 0) q = new Query5(main_S);
                
                else q = new Query5(main_S,user_id);    
                break;
                
                case "6":
                Viewer.print_Info("Insira o numero maximo para negocios mais avaliados em um ano : ");
                num = sc.nextLine();
                if (num.compareTo("") == 0)
                q = new Query6(main_S);
                else
                {
                    try {
                        q = new Query6(main_S,Integer.parseInt(num));
                    } catch (Exception e) {
                        Viewer.print_Info(e.toString());
                    }
                }
                break;
                
                case "7":
                q = new Query7(main_S);
                break;
                
                case "8":
                Viewer.print_Info("Quantos usuarios que avaliaram negocios diferentes pretende ver ? : ");
                num = sc.nextLine();
                if (num.compareTo("") == 0)
                q = new Query8(main_S);
                else
                {
                    try {
                        q = new Query8(main_S,Integer.parseInt(num));
                    } catch (Exception e) {
                        Viewer.print_Info(e.toString());
                    }
                }
                break;

                case "9":
                Viewer.print_Info("Insira um código de um negócio : ");
                business_id = sc.nextLine();
                Viewer.print_Info("Quantos utilizadores que mais avaliaram deseja ver? : ");
                num = sc.nextLine();
                if (num.compareTo("") == 0)
                q = new Query9(main_S,business_id , -1);
                else
                try {
                    q = new Query9(main_S,business_id,Integer.parseInt(num));
                } catch (Exception e) {
                    Viewer.print_Info(e.toString());
                }
                break;

                case "10":
                q = new Query10(main_S);
                break;
                
                case "quit":
                ok = true;
                clear_struct();
                break;

                case "help":
                Viewer.help_commands();
                break;

                case "reload":
                reload(sc);
                break;

                case "serialize":
                Viewer.print_Info("Serializando a estrutura de dados...");
                main_S.serialize();
                break;

                case "deserialize":
                Viewer.print_Info("Insira o path para deserializar a estrutura de dados : ");
                String data = sc.nextLine();
                try {main_S.deserialize(data);}
                catch (Exception e)
                {
                    Viewer.print_Info("Erro no tipo de classe\n");
                }
                break;

                case "":
                break;
                default:
                Viewer.print_Info("Comando invalido -> use help para saber comandos disponiveis\n");
                break;
            }

            //Executar query a mostrar o tempo demorado para terminal
            if (q != null)
            {
                Viewer.print_Info("\n-------------\n\n");
                q.run_query();
            }
    
        } while( !ok);
        sc.close();
        

    }
    public static void clear_struct()
    {
        main_S.clear();
        Parser.reset();
    }

    public static void reload(Scanner sc)
    {

        clear_struct();
        Viewer.print_Info("Introduza o Business path -> ");
        String busi_path   = sc.nextLine();
        Viewer.print_Info("Introduza o Review path -> ");
        String review_path = sc.nextLine();
        Viewer.print_Info("Introduza o User path -> ");
        String user_path   = sc.nextLine();
        Viewer.print_Info("\n");
        initialize_struct(user_path, busi_path,review_path);

    }

    public static void initialize_struct(String path_u,String path_b,String path_r)
    {
        float start_r = System.nanoTime();

        if ( read_files(path_u,path_b,path_r ) )
        {

            float end_r = System.nanoTime();
            Viewer.print_GestReviews(main_S);
            Information.getAvgMonth(main_S);
            float end_p = System.nanoTime();

            float elapsedTime_p = (end_p - end_r) / 1000000000.0f;
            float elapsedTime_r = (end_r - start_r) / 1000000000.0f;
            Viewer.print_Info("\nTempo a carregar estrutura de dados : " + elapsedTime_r + "s\n");
            Viewer.print_Info("\nTempo a imprimir estrutura de dados : " + elapsedTime_p + "s\n");
        }
        
    }

}
