package Model.Interfaces;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import Model.Catalogos.Review_Struct;

public interface IBusiness_Struct {
    void increaseStars(float stars);
    void addReview(IReview_Struct r);

    String getKey_business_extra();
    void setKey_business_extra(String key_business_extra);
    String getName();
    void setName(String name);
    String getCity();
    void setCity(String city);
    String getState();
    void setState(String state);
    List<IReview_Struct> getUsers_rev();
    float getStars();
    void setStars(float stars);
    int getRates();
    void setRates(int rates);   
    void setUsers_rev(List<IReview_Struct> users_rev);
}
