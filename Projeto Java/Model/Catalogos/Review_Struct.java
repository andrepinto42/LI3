package Model.Catalogos;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

import Model.Interfaces.IReview_Struct;

public class Review_Struct implements IReview_Struct,Serializable {
    private String review_ID;
    private String user_ID;
    private int[] revs = new int[3];
    private Date data;
    private float stars;


    public Date String_toDate(String s) throws Exception
    {
        // 2015-09-23 23:21:45
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formatter.parse(s);
    }

    public static int zero_Impact(int a,int b,int c)
    {
        return (a == 0 && b == 0  && c == 0) ? 1 : 0;
    }
    public Review_Struct(String review_ID, String user_ID,float stars, int cool,int funny,int usefull, String data)
     {
        this.stars = stars;
        this.review_ID = review_ID;
        this.user_ID = user_ID;
        this.revs[0] =cool;
        this.revs[1] =funny;
        this.revs[2] =usefull;

        try{this.data =String_toDate(data);}
        catch(Exception e)
        {
            //Erro na formatacao da data
            this.data = null;
        }
    }

    public Review_Struct(String review_ID, String user_ID,float stars, int[] revs, String data)
     {
        this.review_ID = review_ID;
        this.stars = stars;
        this.user_ID = user_ID;
        this.revs = revs;
        try{this.data =String_toDate(data);}
        catch(Exception e)
        {
            //Erro na formatacao da data
            this.data = null;
        }
    }
  

    public String getReview_ID() {return this.review_ID;}
    public void setReview_ID(String review_ID) {this.review_ID = review_ID;}

    public String getUser_ID() {return this.user_ID;}
    public void setUser_ID(String user_ID) {this.user_ID = user_ID;}

    public int[] getRevs() {return this.revs;}
    public void setRevs(int[] revs) {this.revs = revs;}

    public Date getData() {return this.data;}
    public void setData(Date data) {this.data = data;}

    public float getStars() {return this.stars;}
    public void setStars(float stars) {this.stars = stars;}
    

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Review_Struct)) {
            return false;
        }
        Review_Struct review_Struct = (Review_Struct) o;
        return Objects.equals(review_ID, review_Struct.review_ID) && Objects.equals(user_ID, review_Struct.user_ID) && Objects.equals(revs, review_Struct.revs) && Objects.equals(data, review_Struct.data) && stars == review_Struct.stars;
    }

    @Override
    public int hashCode() {
        return Objects.hash(review_ID, user_ID, revs, data, stars);
    }

}
