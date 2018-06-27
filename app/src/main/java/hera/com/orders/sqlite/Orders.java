package hera.com.orders.sqlite;

import android.content.Context;
import android.database.Cursor;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import hera.com.orders.MainActivity;

public class Orders {
    public void addOrder(Context context, hera.com.orders.model.Orders orders)
    {
        MainActivity.db.execSQL("create table if not exists orders1(orderId integer, partnerId integer, partnerName varchar(1000)," +
                "date varchar(1000), note varchar(1000), sended varchar(1000))");
        int orderId = getNextOrderId();
        MainActivity.orderID=orderId;
        //Toast.makeText(context,"here",Toast.LENGTH_SHORT).show();
        MainActivity.db.execSQL("insert into orders1 values("+orderId+","+orders.partnerId+",'"+orders.partnerName+"','"+orders.dates+"'," +
                "'"+orders.note+"','N')");

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
        Cursor c=MainActivity.db.rawQuery("select * from orders1", null);
        List<hera.com.orders.model.Orders> ordersList=new ArrayList<>();
        while(c.moveToNext())
        {
            hera.com.orders.model.Orders orders=new hera.com.orders.model.Orders();
            Cursor c1=MainActivity.db.rawQuery("select * from orderdetails1 where orderId="+c.getInt(0),null);
            List<hera.com.orders.model.OrderItems> orderItemsList=new ArrayList<>();
            while(c1.moveToNext())
            {
                hera.com.orders.model.OrderItems orderItems=new hera.com.orders.model.OrderItems();
                orderItems.articleId=c1.getInt(1);
                orderItems.articleName=c1.getString(2);
                orderItems.articleCode=c1.getString(3);
                orderItems.articleUnits=c1.getString(4);
                orderItems.articlePacking=c1.getString(5);
                orderItems.articleWeight=c1.getString(6);
                orderItems.quantity=c1.getString(7);
                orderItems.packaging=c1.getString(8);
                orderItems.price=c1.getString(9);
                orderItemsList.add(orderItems);
            }
            Cursor c2= MainActivity.db.rawQuery("select * from partners where id="+c.getInt(1),null);
            hera.com.orders.model.Partner partner=new hera.com.orders.model.Partner();
            while(c2.moveToNext())
            {
                partner.id = c2.getInt(0);
                partner.name=c2.getString(2);
                partner.code=c2.getString(1);
                partner.amount=c2.getString(5);
                partner.address=c2.getString(3);
                partner.city=c2.getString(4);
                partner.type=c2.getString(6);
                partner.discount=c2.getString(7);
                partner.status=c2.getString(8);
                partner.businessHours=c2.getString(9);
                partner.timeOfReceipt=c2.getString(10);
                partner.responsiblePerson=c2.getString(11);
                partner.forMobile=c2.getString(12);
            }
            orders.orderId=c.getInt(0);
            orders.partnerId=c.getInt(1);
            orders.partnerName=c.getString(2);
            orders.dates=c.getString(3);
            orders.note=c.getString(4);
            orders.orderItemsList=orderItemsList;
            orders.partner=partner;
            orders.sended=c.getString(5);
            ordersList.add(orders);
        }
        return ordersList;
    }
    public hera.com.orders.model.Orders showOrders( int id)
    {
        Cursor c=MainActivity.db.rawQuery("select * from orders1 where orderId="+id+"", null);
        hera.com.orders.model.Orders orders=new hera.com.orders.model.Orders();
        while(c.moveToNext())
        {

            Cursor c1=MainActivity.db.rawQuery("select * from orderdetails1 where orderId="+c.getInt(0),null);
            List<hera.com.orders.model.OrderItems> orderItemsList=new ArrayList<>();
            while(c1.moveToNext())
            {
                hera.com.orders.model.OrderItems orderItems=new hera.com.orders.model.OrderItems();
                orderItems.articleId=c1.getInt(1);
                orderItems.articleName=c1.getString(2);
                orderItems.articleCode=c1.getString(3);
                orderItems.articleUnits=c1.getString(4);
                orderItems.articlePacking=c1.getString(5);
                orderItems.articleWeight=c1.getString(6);
                orderItems.quantity=c1.getString(7);
                orderItems.packaging=c1.getString(8);
                orderItems.price=c1.getString(9);
                orderItemsList.add(orderItems);
            }
            Cursor c2= MainActivity.db.rawQuery("select * from partners where id="+c.getInt(1),null);
            hera.com.orders.model.Partner partner=new hera.com.orders.model.Partner();
            while(c2.moveToNext())
            {
                partner.id = c2.getInt(0);
                partner.name=c2.getString(2);
                partner.code=c2.getString(1);
                partner.amount=c2.getString(5);
                partner.address=c2.getString(3);
                partner.city=c2.getString(4);
                partner.type=c2.getString(6);
                partner.discount=c2.getString(7);
                partner.status=c2.getString(8);
                partner.businessHours=c2.getString(9);
                partner.timeOfReceipt=c2.getString(10);
                partner.responsiblePerson=c2.getString(11);
                partner.forMobile=c2.getString(12);
            }
            orders.orderId=c.getInt(0);
            orders.partnerId=c.getInt(1);
            orders.partnerName=c.getString(2);
            orders.dates=c.getString(3);
            orders.note=c.getString(4);
            orders.orderItemsList=orderItemsList;
            orders.partner=partner;
            orders.sended=c.getString(5);
        }
        return orders;
    }
    public static String getPartnerName(Context context, int orderId)
    {
        Cursor c=MainActivity.db.rawQuery("select * from orders1 where orderId="+orderId+"", null);
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
        //Toast.makeText(context, "there"+c,Toast.LENGTH_SHORT).show();
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
        Cursor c=MainActivity.db.rawQuery("select * from orders1", null);
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
