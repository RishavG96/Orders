package hera.com.orders.infrastructure.sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.util.ArrayList;

import hera.com.orders.AssortmentActivity;

import static android.content.Context.MODE_PRIVATE;

public class Assortment {
    public static ArrayList<String> assortmentId, assortmentItemId, partnerId, articleId;
    public void addAssortment(Context context, String assortmentId, String assortmentItemId, String partnerId, String articleId){
        AssortmentActivity.db.execSQL("create table if not exists assortment(assortmentId varchar(1000), assortmentItemId varchar(1000), partnerId vharchar(1000), " +
                "articleId varchar(1000))");
        AssortmentActivity.db.execSQL("insert into assortment values('"+assortmentId+"','"+assortmentItemId+"','"+partnerId+"','"+articleId+"')");
    }
    public void showAssortment(Context context) {
        assortmentId=new ArrayList();
        assortmentItemId=new ArrayList();
        partnerId=new ArrayList();
        articleId=new ArrayList();

        Cursor c = AssortmentActivity.db.rawQuery("select * from assortment order by partnerId asc", null);
        while (c.moveToNext()) {
            //stringBuffer.append(c.getString(0) + "    ");
            assortmentId.add(c.getString(0));
            partnerId.add(c.getString(2));
            assortmentItemId.add(c.getString(1));
            partnerId.add(c.getString(3));
        }
    }
}
