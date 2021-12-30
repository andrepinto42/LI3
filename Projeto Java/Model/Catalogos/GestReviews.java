package Model.Catalogos;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import Model.Interfaces.IBusiness_Struct;
import Model.Interfaces.IUser_Struct;
import View.Viewer;

public class GestReviews implements Serializable {
    private Map<String,IBusiness_Struct> hash_map;
    private Map<String,IUser_Struct> user_list;
    private int erros_Business = 0;
    private int erros_Review = 0;
    private int erros_Users = 0;
    private int validos_Business = 0;
    private int validos_Review = 0;
    private int validos_User = 0;
    private int zero_revs = 0;
  
    

    public  void serialize() {

        try {
            FileOutputStream fileOut = new FileOutputStream("./obj/li3.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(this);
            out.close();
            fileOut.close();
            System.out.printf("Serialized data is saved in ./obj/li3.ser\n");
         } catch (IOException i) {
            i.printStackTrace();
         }
    }

   /* Lê de um ficheiro serializable */
    public  void deserialize(String s) throws ClassNotFoundException {
    
         GestReviews l;
         try {
            if (s.compareTo("") == 0) s = "./obj/li3.ser";

            FileInputStream fileIn = new FileInputStream(s);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            l = (GestReviews) in.readObject();
            in.close();
            fileIn.close();
         } catch (IOException i) 
         {
            i.printStackTrace();
            return;
         }
         this.erros_Business = l.getErros_Business();
         this.erros_Review = l.getErros_Review();
         this.erros_Users = l.getErros_Users();
         this.validos_Business = l.getValidos_Business();
         this.validos_Review = l.getValidos_Review();
         this.validos_User = l.getValidos_User();
         this.zero_revs = l.getZero_revs();
         this.hash_map = l.getHash_map();
         this.user_list = l.getUser_list();
         Viewer.print_Info("Ficheiro deserializado com sucesso\n");
    }

    public void clear()
    {
        for (IBusiness_Struct b : hash_map.values()) {
            b.getUsers_rev().clear();
        }
        hash_map.clear();
        for (IUser_Struct u : user_list.values()) {
            u.getBusi_reviewed().clear();

            if (u.getFriends() != null)
            u.getFriends().clear();
        }
        user_list.clear();
        erros_Business = 0;
        erros_Review = 0;
        erros_Users = 0;
        validos_Business = 0;
        validos_Review = 0;
        validos_User = 0;
        zero_revs = 0;
    }




    public GestReviews() {
        hash_map = new HashMap<String,IBusiness_Struct>();
        user_list = new HashMap<String,IUser_Struct>();
    }

    public GestReviews(Map<String,IBusiness_Struct> hash_map) {this.hash_map = hash_map;}
    public Map<String,IBusiness_Struct> getHash_map() {return this.hash_map;}
    public void setHash_map(Map<String,IBusiness_Struct> hash_map) {this.hash_map = hash_map;}
    public Map<String,IUser_Struct> getUser_list() {return this.user_list;}
    public void setUser_list(Map<String,IUser_Struct> user_list) {this.user_list = user_list;}
    public int getErros_Business() {return this.erros_Business;}
    public void setErros_Business(int erros_Business) {this.erros_Business = erros_Business;}
    public int getErros_Review() {return this.erros_Review;}
    public void setErros_Review(int erros_Review) {this.erros_Review = erros_Review;}
    public int getErros_Users() {return this.erros_Users;}
    public void setErros_Users(int erros_Users) {this.erros_Users = erros_Users;}
    public int getValidos_Business() {return this.validos_Business;}
    public void setValidos_Business(int validos_Business) {this.validos_Business = validos_Business;}
    public int getValidos_Review() {return this.validos_Review;}
    public void setValidos_Review(int validos_Review) {this.validos_Review = validos_Review;}
    public int getValidos_User() {return this.validos_User;}
    public void setValidos_User(int validos_User) {this.validos_User = validos_User;}
    public int getZero_revs() {return this.zero_revs;}
    public void setZero_revs(int zero_revs) {this.zero_revs = zero_revs;}

}
