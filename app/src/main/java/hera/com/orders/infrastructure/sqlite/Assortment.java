package hera.com.orders.infrastructure.sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.util.ArrayList;

import hera.com.orders.AssortmentActivity;
import hera.com.orders.MainActivity;

import static android.content.Context.MODE_PRIVATE;

public class Assortment {
    public static ArrayList<String> assortmentId, assortmentItemId, partnerId, articleId;
    public static ArrayList<String> id, code, name, shortName, units, packing, brutto, netto, weight, price;
    public void addAssortment(Context context, String assortmentId, String assortmentItemId, String partnerId, String articleId){
        hera.com.orders.infrastructure.service.Assortment.db.execSQL("create table if not exists assortment(assortmentId varchar(1000)," +
                " assortmentItemId varchar(1000), partnerId vharchar(1000), articleId varchar(1000))");
        hera.com.orders.infrastructure.service.Assortment
                .db.execSQL("insert into assortment values('"+assortmentId+"','"+assortmentItemId+"','"+partnerId+"','"+articleId+"')");
    }
    public void showAssortment(Context context, int Id) {

        assortmentId=new ArrayList();
        assortmentItemId=new ArrayList();
        partnerId=new ArrayList();
        articleId=new ArrayList();

        id=new ArrayList();
        code=new ArrayList();
        name=new ArrayList();
        shortName=new ArrayList();
        units=new ArrayList();
        packing=new ArrayList();
        brutto=new ArrayList();
        netto=new ArrayList();
        weight=new ArrayList();
        price=new ArrayList();
        String id=Id+"";
        int count=0;
        Cursor c = MainActivity.db.rawQuery("select * from assortment where partnerId = '"+id+"'", null);
        while (c.moveToNext()) {
//            assortmentId.add(c.getString(0));
//            partnerId.add(c.getString(2));
//            assortmentItemId.add(c.getString(1));
            count++;
            articleId.add(c.getString(3));
        }
        Toast.makeText(context, "here="+count+"="+id,Toast.LENGTH_SHORT).show();
        for( String s : articleId)
        {
            int i=Integer.parseInt(s);
            Cursor c1 = MainActivity.db.rawQuery("select * from articles where id="+i+" order by name asc", null);
            while (c1.moveToNext()) {
//                id.add(c1.getString(0));
                name.add(c1.getString(2));
                code.add(c1.getString(1));
//                packing.add(c1.getString(5));
//                shortName.add(c1.getString(3));
                units.add(c1.getString(4));
//                brutto.add(c1.getString(6));
//                netto.add(c1.getString(7));
//                weight.add(c1.getString(8));
                price.add(c1.getString(9));
            }
        }
    }
}
