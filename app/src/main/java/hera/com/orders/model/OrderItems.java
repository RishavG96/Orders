package hera.com.orders.model;

import java.io.Serializable;
import com.google.gson.annotations.SerializedName;

public class OrderItems implements Serializable{

    public int orderId;

    public int articleId;

    public String articleName;

    public String articleCode;

    public String articleUnits;

    public String articlePacking;

    public String articleWeight;

    @SerializedName("quantaty")
    public String quantity;

    @SerializedName("transQuantaty")
    public String packaging;

    public String price;
}
