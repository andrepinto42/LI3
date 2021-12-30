package Model.Interfaces;
import Model;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.text.DecimalFormat;


//Isto esta mal implementado

public interface IParser {

    static int readBusi(String path);
    static int parseBusi(String Busi);
    static void readReview(String path);
    static int parseReview(String Review);
    static void readUser(String path);
    static int parseUser(String User);
    static int parseUser_Friends(String User);
    
    /**
     * @param hashmap Um hashmap populado 
     * @return Todas as entradas foram atualizadas com o valor correto de stars
     */
    static void adjust_Stars(Map<String,IBusiness_Struct> hashmap);
}
