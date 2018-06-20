package hera.com.orders.infrastructure.sqlite;

import android.content.Context;
import android.database.Cursor;
import android.widget.Toast;

import java.util.ArrayList;

import hera.com.orders.MainActivity;

public class Orders {
    public static ArrayList partnerId, orderId, partnerName, date, note;
    public static ArrayList articleId, articleName, articleCode, articleUnits, quantity, packaging, price;
    public void addOrder(Context context, int partnerId,  String partnerName, String date, String note)
    {
        MainActivity.db.execSQL("create table if not exists orders(orderId integer, partnerId integer, partnerName varchar(1000)," +
                "date varchar(1000), note varchar(1000))");
        int orderId = getNextOrderId();
        MainActivity.db.execSQL("insert into orders values("+orderId+","+partnerId+",'"+partnerName+"','"+date+"','"+note+"')");

        MainActivity.db.execSQL("create table if not exists orderdetails(orderId integer, articleId integer, articleName varchar(1000)," +
                "articleCode varchar(1000), articleUnits varchar(1000), quantity varchar(1000), packaging varchar(1000)," +
                "price varchar(1000))");
        Cursor cursor=MainActivity.db.rawQuery("select * from orderitems",null);
        while(cursor.moveToNext()) {
            MainActivity.db.execSQL("insert into orderdetails values(" + orderId + ","+cursor.getInt(0)+"," +
                    "'"+cursor.getString(1)+"','"+cursor.getString(2)+"','"+cursor.getString(3)+"'," +
                    "'"+cursor.getString(4)+"','"+cursor.getString(5)+"','"+cursor.getString(6)+"')");
        }
        Toast.makeText(context,"Order Placed!",Toast.LENGTH_SHORT).show();
    }
    public void showOrders(Context context)
    {
        partnerId=new ArrayList();
        orderId=new ArrayList();
        partnerName=new ArrayList();
        date=new ArrayList();
        note=new ArrayList();
        Cursor c=MainActivity.db.rawQuery("select * from orders", null);
        while(c.moveToNext())
        {
            orderId.add(c.getString(0));
            partnerId.add(c.getString(1));
            partnerName.add(c.getString(2));
            date.add(c.getString(3));
            note.add(c.getString(4));
        }
    }
    public static void showOrderItems(Context context, int orderId)
    {
        articleId=new ArrayList();
        articleName=new ArrayList();
        articleCode=new ArrayList();
        articleUnits=new ArrayList();
        quantity=new ArrayList();
        packaging=new ArrayList();
        price=new ArrayList();
        Cursor c=MainActivity.db.rawQuery("select * from orderdetails where orderId="+orderId+"",null);
        while(c.moveToNext())
        {
            articleId.add(c.getString(1));
            articleName.add(c.getString(2));
            articleCode.add(c.getString(3));
            articleUnits.add(c.getString(4));
            quantity.add(c.getString(5));
            packaging.add(c.getString(6));
            price.add(c.getString(7));
        }
    }
    public int getNextOrderId()
    {
        Cursor c=MainActivity.db.rawQuery("select * from orders", null);
        int count=0;
        while(c.moveToNext())
        {
            count++;
        }
        return ++count;
    }
    public static double calculateTotalPrice(int orderId)
    {
        Cursor c = MainActivity.db.rawQuery("select * from orderdetails where orderId="+orderId+"", null);
        double total=0.0;
        while(c.moveToNext())
        {
            total+=Double.parseDouble(c.getString(7));
        }
        return total;
    }
}
