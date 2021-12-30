package Model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Map;
import java.util.Scanner;

import View.Viewer;
import Controller.Exceptions.InvalidException;
import Model.Catalogos.Business_Struct;
import Model.Catalogos.GestReviews;
import Model.Catalogos.Review_Struct;
import Model.Catalogos.User_Struct;
import Model.Interfaces.IBusiness_Struct;
import Model.Interfaces.IReview_Struct;
import Model.Interfaces.IUser_Struct;



public class Parser {
    public static String path_busi = "business_full.csv";
    public static String path_review = "reviews_1M.csv";
    public static String path_users = "users_full.csv";
    public static GestReviews sgr = new GestReviews();

    public static int zero_rev =0;
    public static int erros_totais =0;
    public static int validos = 0;

    public static final boolean ler_friends = false;

    public static GestReviews getGestReviews()
    {
        return sgr;
    }

    /**
     * 
     * 
     *  BUSINESS
     */
    
    public static void readBusi(String path) throws InvalidException
    {
        //Decidir se usar o path predefinido ou o do utilizador
        if (path.compareTo("") != 0) path_busi = path;

        //Abrir ficheiro para ler
        try(BufferedReader in = new BufferedReader(new FileReader(path_busi))) {
            String linha;
            //Ler linha a linha
            while ((linha = in.readLine()) != null) {

            //Ocorreu algum erro a processar a linha, entao é para ser tratado
            if (parseBusi(linha) > 0){
                erros_totais++;
            }
            else
            {
                validos++;
            }

            }
        }
        catch(Exception e)
        {
            throw new InvalidException("File name Business Incorreto");
        }
        sgr.setErros_Business(erros_totais);
        erros_totais = 0;
        sgr.setValidos_Business(validos);
        validos = 0;
        return;
    }

    public static int parseBusi(String Busi)
    {
        //Dividir a String em blocos de informacao
        String parts[] = Busi.split(";");
        //Se nao tiver informacao suficiente
        if (parts.length <= 3) return 1;

        //Criar estrutura de dados
        IBusiness_Struct b = new Business_Struct(parts[1],parts[2],parts[3]);

        //Adicionar no HashMap
        sgr.getHash_map().put(parts[0],b);

        return 0;   
    }


    /**
     * 
     * 
     * REVIEW
     * 
     */
     
    public static void readReview(String path) throws InvalidException
    {
        if (path.compareTo("") != 0) path_review = path;
        //Abrir ficheiro de Review para ler
        try(BufferedReader in = new BufferedReader(new FileReader(path_review))) {
            String linha;
            //Ler linha a linha do ficheiro
            while ((linha = in.readLine()) != null) {
                // Processar aqui a linha
               if (parseReview(linha) >0) 
               {
                   //Foi encontrada uma linha invalida
                    erros_totais++;
               }
               else{
                   validos++;
               }
            }
            
        }
        catch(Exception e){
            throw new InvalidException("File name Review Incorreto");
        }
        adjust_Stars(sgr.getHash_map());
        sgr.setErros_Review(erros_totais);
        erros_totais = 0;
        sgr.setValidos_Review(validos);
        validos = 0;
        sgr.setZero_revs(zero_rev);
    }

    public static int parseReview(String Review)
    {
        //Dividir a String em pedacos de informacao
        String parts[] = Review.split(";");

        //Nao foi possivel dividir nos pedacos desejados
        if (parts.length < 8) return 1;

        //Buscar no HashMap a estrutura
        IBusiness_Struct b = sgr.getHash_map().get(parts[2]);
        if (b == null) return 2;
        
        float stars = Float.parseFloat(parts[3]);
        int cool =Integer.parseInt(parts[4]);
        int funny = Integer.parseInt(parts[5]);
        int usefull = Integer.parseInt(parts[6]);

        //Criar um estrutura auxiliar
        IReview_Struct r = new Review_Struct(parts[0],parts[1],stars,
                                cool,funny,usefull,
                                parts[7]);
        //Adicionar Review ao arraylist do Negocio
        b.addReview(r);

        //Adicionar 1 se a review tiver impacto 0 -> 0 cool,0 funny, 0 usefull
        zero_rev += Review_Struct.zero_Impact(cool,funny,usefull);

        //Adicionar o Business_ID ao Usuario
        IUser_Struct u1 = sgr.getUser_list().get(r.getUser_ID());
        if (u1 != null )
        u1.addBusiReviewed(parts[2]);
        else
        {
            Viewer.print_Info("Usuario nao existe na base de dados :(\n");
        }

        //Aumentar as estrelas do Negocio
        b.increaseStars(Float.parseFloat(parts[3]));
        return 0;   
    }




    /**
     * 
     * 
     * USERS
     * 
     */

    public static void readUser(String path) throws InvalidException
    {
        if (path.compareTo("") != 0) path_users = path;
        try(BufferedReader in = new BufferedReader(new FileReader(path_users))) {
            Scanner sc = new Scanner(in).useDelimiter(";");
            
            String linha;
            //Carrega tambem os amigos para a estrutura
            if(ler_friends)
            while ((linha = in.readLine()) != null) {
                if (parseUser_Friends(linha) > 0) erros_totais++;
                else
                validos++;
                
            }

            else
            while ( (linha = in.readLine()) != null ) {
                if (parseUser(linha) > 0) erros_totais++;
                else
                validos++;
                
            }
            sc.close();


        }
        catch(Exception e)
        {
            throw new InvalidException("File name User Incorreto");
        }
        sgr.setErros_Users(erros_totais);
        erros_totais = 0;
        sgr.setValidos_User(validos);
        validos = 0;
    }


    public static int parseUser(String User)
    {
        String parts[] = User.split(";");
        if( parts.length < 2) return 1;
        IUser_Struct u = new User_Struct(parts[1]);
        //Adicionar o User à estrutura de dados
        sgr.getUser_list().put(parts[0],u);
        return 0;
    }


    public static int parseUser_Friends(String User)
    {
        String parts[] = User.split(";");
        if( parts.length < 2) return 1;
        IUser_Struct u = new User_Struct(parts[1]);
        

        //Esta parte serve para incrementar os amigos do Usuario a uma lista
        String friends[] = parts[2].split(",");
        for (int i = 0; i < friends.length; i++) {
                u.addFriend(friends[0]);
            }

        //Adicionar o User à estrutura de dados
        sgr.getUser_list().put(parts[0],u);

        //Serve para libertar alguma memoria da lista de amigos
        u.setFriends(null);
        return 0;
    }

    /**
     * 
     * 
     * @param hashmap Um hashmap populado 
     * @return Todas as entradas foram atualizadas com o valor correto de stars
     */

    public static void adjust_Stars(Map<String,IBusiness_Struct> hashmap)
    {
        for (IBusiness_Struct b : hashmap.values()) {

            if (b.getRates() != 0)
            b.setStars( Math.round(( b.getStars() / b.getRates()) * 100.0f) / 100.0f );
        }
    }
    

    public static void reset()
    {
        zero_rev =0;
        erros_totais =0;
        validos = 0;
    }

    public static void setUserpath(String user_path)
    {
        path_users = user_path;
    }

    public static void setBusinesspath(String Business_path)
    {
        path_busi = Business_path;
    }

    public static void setReviewpath(String Review_path)
    {
        path_review = Review_path;
    }    
}
