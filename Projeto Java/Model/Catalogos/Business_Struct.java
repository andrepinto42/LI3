package Model.Catalogos;
import Model.Interfaces.IBusiness_Struct;
import Model.Interfaces.IReview_Struct;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class Business_Struct implements IBusiness_Struct,Serializable {
    private String key_business_extra;
    private String name;
    private String city;
    private String state;
    private List<IReview_Struct> users_rev;
    private float stars;
    private int rates;

   

    public Business_Struct() {
        users_rev = new ArrayList<IReview_Struct>();
        stars = 0;
        rates = 0;
    }

    public Business_Struct(String name, String city, String state)
    {
        this.name = name;
        this.city = city;
        this.state = state;
        this.users_rev = new ArrayList<IReview_Struct>();
        this.stars = 0;
        this.rates = 0;
        
    }

    public Business_Struct(String key_business_extra, String name, String city, String state,
                           List<IReview_Struct> users_rev, float stars, int rates)
    {
        this.key_business_extra = key_business_extra;
        this.name = name;
        this.city = city;
        this.state = state;
        this.users_rev = users_rev;
        this.stars = stars;
        this.rates = rates;
    }

    public void increaseStars(float stars)
    {
        setRates(getRates()+1);
        setStars(getStars() + stars);
    }

    public void addReview(IReview_Struct r)
    {
        this.users_rev.add(r);
    }
    


 
    /**
     * 
     * 
     * Getters e Setters
     */

    public String getKey_business_extra() {return this.key_business_extra;}

    public void setKey_business_extra(String key_business_extra) {this.key_business_extra = key_business_extra;}

    public String getName() {return this.name;}

    public void setName(String name) {this.name = name;}

    public String getCity() {return this.city;}

    public void setCity(String city) {this.city = city;}

    public String getState() {return this.state;}

    public void setState(String state) {this.state = state;}

    public List<IReview_Struct> getUsers_rev() {return this.users_rev;}

    public float getStars() {return this.stars;}

    public void setStars(float stars) {this.stars = stars;}

    public int getRates() {return this.rates;}

    public void setRates(int rates) {this.rates = rates;}
   
    public void setUsers_rev(List<IReview_Struct> users_rev) {this.users_rev = users_rev;}

/**
 * 
 * 
 * 
 * 
 */

    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Business_Struct)) {
            return false;
        }
        Business_Struct business_Struct = (Business_Struct) o;
        return 
        Objects.equals(key_business_extra, business_Struct.key_business_extra) &&
        Objects.equals(name, business_Struct.name) &&
        Objects.equals(city, business_Struct.city) &&
        Objects.equals(state, business_Struct.state) &&
        stars == business_Struct.stars &&
        rates == business_Struct.rates;
    }

    public int hashCode() {
        return Objects.hash(key_business_extra, name, city, state, stars, rates);
    }


    public String toString() {
        return "{" +
            " key_business_extra='" + getKey_business_extra() + "'" +
            ", name='" + getName() + "'" +
            ", city='" + getCity() + "'" +
            ", state='" + getState() + "'" +
            ", stars='" + getStars() + "'" +
            ", rates='" + getRates() + "'" +
            "}";
    }



}