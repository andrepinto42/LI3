
package Model.Interfaces;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

public interface IReview_Struct {
    Date String_toDate(String s) throws Exception;

    String getReview_ID();
    void setReview_ID(String review_ID);
    String getUser_ID();
    void setUser_ID(String user_ID);
    int[] getRevs();
    void setRevs(int[] revs);
    Date getData();
    void setData(Date data);
    public float getStars();
    public void setStars(float stars);
    public int hashCode();

}
