package hera.com.orders.infrastructure.sqlite;

import android.content.Context;
import android.database.Cursor;
import android.widget.Toast;

import java.util.ArrayList;

import hera.com.orders.MainActivity;

public class Orders {
    public static ArrayList partnerId, orderId, partnerName, date, note;
    public static ArrayList articleId, articleName, articleCode, articleUnits, articlePacking, articleWeight, quantity, packaging, price;
    public void addOrder(Context context, int partnerId,  String partnerName, String date, String note)
    {
        MainActivity.db.execSQL("create table if not exists orders(orderId integer, partnerId integer, partnerName varchar(1000)," +
                "date varchar(1000), note varchar(1000))");
        int orderId = getNextOrderId();
        MainActivity.orderID=orderId;
        //Toast.makeText(context,"here",Toast.LENGTH_SHORT).show();
        MainActivity.db.execSQL("insert into orders values("+orderId+","+partnerId+",'"+partnerName+"','"+date+"','"+note+"')");

        //Toast.makeText(context,"Order Placed!",Toast.LENGTH_SHORT).show();
    }
    public void addToOrderDetails(Context context)
    {
        MainActivity.db.execSQL("create table if not exists orderdetails1(orderId integer, articleId integer, articleName varchar(1000)," +
                "articleCode varchar(1000), articleUnits varchar(1000), articlePacking varchar(1000)," +
                "articleWeight varchar(1000), quantity varchar(1000), packaging varchar(1000)," +
                "price varchar(1000))");
        MainActivity.db.execSQL("delete from orderdetails1 where orderId="+MainActivity.orderID+"");
        Cursor cursor=MainActivity.db.rawQuery("select * from orderitems",null);
        while(cursor.moveToNext()) {
            MainActivity.db.execSQL("insert into orderdetails1 values(" + MainActivity.orderID + ","+cursor.getInt(0)+"," +
                    "'"+cursor.getString(1)+"','"+cursor.getString(2)+"','"+cursor.getString(3)+"'," +
                    "'"+cursor.getString(4)+"','"+cursor.getString(5)+"','"+cursor.getString(6)+"'," +
                    "'"+cursor.getString(7)+"','"+cursor.getString(8)+"')");
        }
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
    public static String getPartnerName(Context context, int orderId)
    {
        Cursor c=MainActivity.db.rawQuery("select * from orders where orderId="+orderId+"", null);
        while(c.moveToNext())
        {
            return c.getString(2);
        }
        return "null";
    }
    public static void showOrderItems(Context context, int orderId)
    {
        articleId=new ArrayList();
        articleName=new ArrayList();
        articleCode=new ArrayList();
        articleUnits=new ArrayList();
        articlePacking=new ArrayList();
        articleWeight=new ArrayList();
        quantity=new ArrayList();
        packaging=new ArrayList();
        price=new ArrayList();
        Cursor c=MainActivity.db.rawQuery("select * from orderdetails1 where orderId="+orderId+"",null);
        while(c.moveToNext())
        {
            articleId.add(c.getString(1));
            articleName.add(c.getString(2));
            articleCode.add(c.getString(3));
            articleUnits.add(c.getString(4));
            articlePacking.add(c.getString(5));
            articleWeight.add(c.getString(6));
            quantity.add(c.getString(7));
            packaging.add(c.getString(8));
            price.add(c.getString(9));
        }
    }
    public static void pushOrderItems(Context context, int orderId)
    {
        Cursor c=MainActivity.db.rawQuery("select * from orderdetails1 where orderId="+orderId+"",null);
        while(c.moveToNext())
        {
            MainActivity.db.execSQL("insert into orderitems values(" + c.getInt(1) + ",'" + c.getString(2) + "','" + c.getString(3) + "','" +
                    c.getString(4) + "','" + c.getString(5) + "','" + c.getString(6) + "','" + c.getString(7) + "'," +
                    "'" + c.getString(8) + "','" + c.getString(9) + "')");
            OrderItems.articleId=new ArrayList();
            OrderItems.articleName=new ArrayList();
            OrderItems.articleCode=new ArrayList();
            OrderItems.articleUnits=new ArrayList();
            OrderItems.articlePacking=new ArrayList();
            OrderItems.articleWeight=new ArrayList();
            OrderItems.units=new ArrayList();
            OrderItems.packaging=new ArrayList();
            OrderItems.price=new ArrayList();
            OrderItems.articleId.add(c.getString(0));
            OrderItems.articleName.add(c.getString(1));
            OrderItems.articleCode.add(c.getString(2));
            OrderItems.articleUnits.add(c.getString(3));
            OrderItems.articlePacking.add(c.getString(4));
            OrderItems.articleWeight.add(c.getString(5));
            OrderItems.units.add(c.getString(6));
            OrderItems.packaging.add(c.getString(7));
            OrderItems.price.add(c.getString(8));
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
        Cursor c = MainActivity.db.rawQuery("select * from orderdetails1 where orderId="+orderId+"", null);
        double total=0.0;
        while(c.moveToNext())
        {
            total+=Double.parseDouble(c.getString(9));
        }
        return total;
    }
}
