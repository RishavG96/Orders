package hera.com.orders.infrastructure.sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.util.ArrayList;

import hera.com.orders.MainActivity;

public class OrderItems {
    SQLiteDatabase db;
    public static ArrayList<String> articleId, articleName, articleCode,articleUnits, units, packaging, price;
    public void addOrders(Context context, int articleId, String articleName, String articleCode,String articleUnits, String units, String packaging,
                            String price)
    {
        MainActivity.db.execSQL("create table if not exists orderitems(articleId integer, articleName varchar(1000), articleCode varchar(1000)," +
                "articleUnits varchar(1000),units varchar(1000), packaging varchar(1000), price varchar(1000))");
        MainActivity.db.execSQL("insert into orderitems values("+articleId+",'"+articleName+"','"+articleCode+"','"+
                articleUnits+"','"+units+"','"+packaging+"','"+price+"')");
        Toast.makeText(context,"Item added",Toast.LENGTH_SHORT).show();
    }
    public void showOrders(Context context)
    {
        articleId=new ArrayList();
        articleName=new ArrayList();
        articleCode=new ArrayList();
        articleUnits=new ArrayList();
        units=new ArrayList();
        packaging=new ArrayList();
        price=new ArrayList();
        Cursor c = MainActivity.db.rawQuery("select * from orderitems", null);
        while(c.moveToNext())
        {
            articleId.add(c.getString(0));
            articleName.add(c.getString(1));
            articleCode.add(c.getString(2));
            articleUnits.add(c.getString(3));
            units.add(c.getString(4));
            packaging.add(c.getString(5));
            price.add(c.getString(6));
        }
    }
}
