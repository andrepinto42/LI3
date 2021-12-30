package Model.Interfaces;
import java.util.ArrayList;
import java.util.Objects;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
// import Struct.*;

public interface IUser_Struct{

   
    void addFriend(String friend);
    String getName();
    void addBusiReviewed(String busi_reviewed);

    void setName(String name);
    List<String> getFriends();
    void setFriends(List<String> lista);
    Set<String> getBusi_reviewed();
    void setBusi_reviewed(Set<String> busi_reviewed);
    int hashCode();
    String toString();


}