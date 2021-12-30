package Model.Catalogos;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;
import java.util.TreeSet;
import java.util.List;
import java.util.Set;
import Model.Interfaces.IUser_Struct;

public class User_Struct implements IUser_Struct,Serializable{
    
    private String name;
    private Set<String> busi_reviewed; 
    private List<String> friends;


    public void addFriend(String friend)
    {
        this.friends.add(friend);
    }
    public void addBusiReviewed(String busi_reviewed)
    {
        this.busi_reviewed.add(busi_reviewed);
    }

    public User_Struct() {
        name = "";
        friends = new ArrayList<>();
        busi_reviewed = new TreeSet<>();
    }

    public User_Struct(String name)
    {
        this.name = name;
        this.busi_reviewed = new TreeSet<>();
    }
    public User_Struct(String name, List<String> friends) {
        this.name = name;
        this.friends = friends;
    }

    public String getName() {return this.name;}
    public void setName(String name) {this.name = name;}
    public List<String> getFriends() {return this.friends;}
    public void setFriends(List<String> friends) {this.friends = friends;}
    public Set<String> getBusi_reviewed() {return this.busi_reviewed;}
    public void setBusi_reviewed(Set<String> busi_reviewed) {this.busi_reviewed = busi_reviewed;}

  

    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof User_Struct)) {
            return false;
        }
        User_Struct user_Struct = (User_Struct) o;
        return Objects.equals(name, user_Struct.name) && 
        Objects.equals(friends, user_Struct.friends);
    }

    public int hashCode() {
        return Objects.hash(name, friends,busi_reviewed);
    }

    public String toString() {
        return "{" +
            " name='" + getName() + "'" +
            ", friends='" + getFriends() + "'" +
            ", busi_Reviewed = " + getBusi_reviewed() + "," +
            "}";
    }


    

        
}
