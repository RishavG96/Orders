package hera.com.orders.model;

import java.io.Serializable;
import java.util.List;

import com.google.gson.annotations.SerializedName;

public class Orders implements Serializable{

    @SerializedName("id")
    public int orderId;

    @SerializedName("broj")
    public String number;

    @SerializedName("partnerId")
    public int partnerId;

    @SerializedName("partnerName")
    public String partnerName;

    @SerializedName("datum")
    public String dates;

    @SerializedName("note")
    public String note;

    @SerializedName("broj_dana")
    public String numberDays;

    @SerializedName("documentItems")
    public List<OrderItems> orderItemsList;

    @SerializedName("kupac")
    public Partner partner;

    @SerializedName("poslan")
    public String sended;

}
