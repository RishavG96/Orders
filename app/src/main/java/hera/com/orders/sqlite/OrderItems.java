package hera.com.orders.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.util.ArrayList;

import hera.com.orders.MainActivity;

public class OrderItems {
    SQLiteDatabase db;
    public static ArrayList<String> articleId, articleName, articleCode,articleUnits, articlePacking, articleWeight, units, packaging, price;
    public static int item_count=0;
    public void addOrders(Context context, int articleId, String articleName, String articleCode,String articleUnits, String articlePacking,
            String articleWeight, String units, String packaging, String price)
    {
        MainActivity.db.execSQL("create table if not exists orderitems(articleId integer, articleName varchar(1000), articleCode varchar(1000)," +
                "articleUnits varchar(1000), articlePacking varchar(1000), articleWeight varchar(1000),units varchar(1000), packaging varchar(1000)," +
                " price varchar(1000))");
        Cursor c = MainActivity.db.rawQuery("select * from orderitems", null);
        int count=0;
        while(c.moveToNext())
        {
            if(articleId == c.getInt(0))
                count++;
        }
        if(count==0) {
            MainActivity.db.execSQL("insert into orderitems values(" + articleId + ",'" + articleName + "','" + articleCode + "','" +
                    articleUnits + "','" + articlePacking + "','" + articleWeight + "','" + units + "','" + packaging + "','" + price + "')");
        }
        else
        {
            ContentValues cv = new ContentValues();
            cv.put("units",units); //These Fields should be your String values of actual column names
            cv.put("packaging",packaging);
            cv.put("price",price);
            MainActivity.db.update("orderitems", cv, "articleId="+articleId, null);
        }
        Toast.makeText(context,"Item added",Toast.LENGTH_SHORT).show();
    }
    public void showOrders(Context context)
    {
        articleId=new ArrayList();
        articleName=new ArrayList();
        articleCode=new ArrayList();
        articleUnits=new ArrayList();
        articlePacking=new ArrayList();
        articleWeight=new ArrayList();
        units=new ArrayList();
        packaging=new ArrayList();
        price=new ArrayList();
        Cursor c = MainActivity.db.rawQuery("select * from orderitems", null);
        item_count=0;
        while(c.moveToNext())
        {
            articleId.add(c.getString(0));
            articleName.add(c.getString(1));
            articleCode.add(c.getString(2));
            articleUnits.add(c.getString(3));
            articlePacking.add(c.getString(4));
            articleWeight.add(c.getString(5));
            units.add(c.getString(6));
            packaging.add(c.getString(7));
            price.add(c.getString(8));
            item_count++;
        }
    }
    public double calculateTotalPrice()
    {
        Cursor c = MainActivity.db.rawQuery("select * from orderitems", null);
        double total=0.0;
        while(c.moveToNext())
        {
            total+=Double.parseDouble(c.getString(8));
        }
        return total;
    }
    public static void deleteItem(int articleId)
    {
        MainActivity.db.delete("orderitems", "articleId" + "=" + articleId, null);

    }
}
