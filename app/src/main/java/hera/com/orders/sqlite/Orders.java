package hera.com.orders.sqlite;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import hera.com.orders.MainActivity;

public class Orders {
    public void addOrder(Context context, hera.com.orders.model.Orders orders)
    {
        MainActivity.db.execSQL("create table if not exists orders(orderId integer, partnerId integer, partnerName varchar(1000)," +
                "date varchar(1000), note varchar(1000))");
        int orderId = getNextOrderId();
        MainActivity.orderID=orderId;
        //Toast.makeText(context,"here",Toast.LENGTH_SHORT).show();
        MainActivity.db.execSQL("insert into orders values("+orderId+","+orders.partnerId+",'"+orders.partnerName+"','"+orders.dates+"'," +
                "'"+orders.note+"')");

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
    public Iterable<hera.com.orders.model.Orders> showOrders(Context context)
    {
        Cursor c=MainActivity.db.rawQuery("select * from orders", null);
        List<hera.com.orders.model.Orders> ordersList=new ArrayList<>();
        while(c.moveToNext())
        {
            hera.com.orders.model.Orders orders=new hera.com.orders.model.Orders();
            orders.orderId=c.getInt(0);
            orders.partnerId=c.getInt(1);
            orders.partnerName=c.getString(2);
            orders.dates=c.getString(3);
            orders.note=c.getString(4);
            ordersList.add(orders);
        }
        return ordersList;
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
    public static Iterable<hera.com.orders.model.OrderItems> showOrderItems(Context context, int orderId)
    {
        Cursor c=MainActivity.db.rawQuery("select * from orderdetails1 where orderId="+orderId+"",null);
        List<hera.com.orders.model.OrderItems> orderItemsList=new ArrayList<>();
        while(c.moveToNext())
        {
            hera.com.orders.model.OrderItems orderItems=new hera.com.orders.model.OrderItems();
            orderItems.articleId=c.getInt(1);
            orderItems.articleName=c.getString(2);
            orderItems.articleCode=c.getString(3);
            orderItems.articleUnits=c.getString(4);
            orderItems.articlePacking=c.getString(5);
            orderItems.articleWeight=c.getString(6);
            orderItems.quantity=c.getString(7);
            orderItems.packaging=c.getString(8);
            orderItems.price=c.getString(9);
            orderItemsList.add(orderItems);
        }
        return orderItemsList;
    }
    public static Iterable<hera.com.orders.model.OrderItems> pushOrderItems(Context context, int orderId)
    {
        Cursor c=MainActivity.db.rawQuery("select * from orderdetails1 where orderId="+orderId+"",null);
        List<hera.com.orders.model.OrderItems> orderItemsList=new ArrayList<>();
        while(c.moveToNext())
        {
            MainActivity.db.execSQL("insert into orderitems values(" + c.getInt(1) + ",'" + c.getString(2) + "','" + c.getString(3) + "','" +
                    c.getString(4) + "','" + c.getString(5) + "','" + c.getString(6) + "','" + c.getString(7) + "'," +
                    "'" + c.getString(8) + "','" + c.getString(9) + "')");
            hera.com.orders.model.OrderItems orderItems=new hera.com.orders.model.OrderItems();
            orderItems.articleId=c.getInt(0);
            orderItems.articleName=c.getString(1);
            orderItems.articleCode=c.getString(2);
            orderItems.articleUnits=c.getString(3);
            orderItems.articlePacking=c.getString(4);
            orderItems.articleWeight=c.getString(5);
            orderItems.quantity=c.getString(6);
            orderItems.packaging=c.getString(7);
            orderItems.price=c.getString(8);
            orderItemsList.add(orderItems);
        }
        return orderItemsList;
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
        double total=0.0;
        try {
            Cursor c = MainActivity.db.rawQuery("select * from orderdetails1 where orderId=" + orderId + "", null);

            while (c.moveToNext()) {
                total += Double.parseDouble(c.getString(9));
            }
        }catch (Exception e){}
        return total;
    }
}
