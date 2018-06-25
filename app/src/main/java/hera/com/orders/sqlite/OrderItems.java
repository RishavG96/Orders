package hera.com.orders.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import hera.com.orders.MainActivity;

public class OrderItems {
    SQLiteDatabase db;
    public static int item_count=0;
    public void addOrders(Context context, hera.com.orders.model.OrderItems orderItems)
    {
        MainActivity.db.execSQL("create table if not exists orderitems(articleId integer, articleName varchar(1000), articleCode varchar(1000)," +
                "articleUnits varchar(1000), articlePacking varchar(1000), articleWeight varchar(1000),units varchar(1000), packaging varchar(1000)," +
                " price varchar(1000))");
        Cursor c = MainActivity.db.rawQuery("select * from orderitems", null);
        int count=0;
        while(c.moveToNext())
        {
            if(orderItems.articleId == c.getInt(0))
                count++;
        }
        if(count==0) {
            MainActivity.db.execSQL("insert into orderitems values(" + orderItems.articleId + ",'" + orderItems.articleName + "'," +
                    "'" + orderItems.articleCode + "','" + orderItems.articleUnits + "','" + orderItems.articlePacking + "'," +
                    "'" + orderItems.articleWeight + "','" + orderItems.quantity + "','" + orderItems.packaging + "','" + orderItems.price + "')");
        }
        else
        {
            ContentValues cv = new ContentValues();
            cv.put("units",orderItems.quantity); //These Fields should be your String values of actual column names
            cv.put("packaging",orderItems.packaging);
            cv.put("price",orderItems.price);
            MainActivity.db.update("orderitems", cv, "articleId="+orderItems.articleId, null);
        }
        Toast.makeText(context,"Item added",Toast.LENGTH_SHORT).show();
    }
    public Iterable<hera.com.orders.model.OrderItems> showOrders(Context context)
    {
        Cursor c = MainActivity.db.rawQuery("select * from orderitems", null);
        item_count=0;
        List<hera.com.orders.model.OrderItems> orderItemsList=new ArrayList<>();
        while(c.moveToNext())
        {
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
            item_count++;
        }
        return orderItemsList;
    }
    public double calculateTotalPrice()
    {
        double total=0.0;
        try {
            Cursor c = MainActivity.db.rawQuery("select * from orderitems", null);
            while(c.moveToNext())
            {
                total+=Double.parseDouble(c.getString(8));
            }
        }catch (Exception e){}


        return total;
    }
    public static void deleteItem(int articleId)
    {
        try {
            MainActivity.db.delete("orderitems", "articleId" + "=" + articleId, null);
        }catch (Exception e){}

    }
}
