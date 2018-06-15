package hera.com.orders.infrastructure.sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by rishavg on 6/12/18.
 */

public class Article {
    SQLiteDatabase db;
    public static ArrayList<String> id, code, name, shortName, units, packing, brutto, netto, weight, price;
    public void addArticle(Context context, int id, String code, String name, String shortName, String units, String packing
                            , String brutto, String netto, String weight, String price){
        db=context.openOrCreateDatabase("order",MODE_PRIVATE,null);
        db.execSQL("create table if not exists articles(id integer, code varchar(1000), name vharchar(1000), shortName varchar(1000)," +
                "units vharchar(1000), packing varchar(1000), brutto varchar(1000), netto varchar(1000), weight varchar(1000)," +
                "price varchar(1000))");
        db.execSQL("insert into articles values("+id+",'"+code+"','"+name+"','"+shortName+"','"+units+"','"+packing+"','"+brutto+"'," +
                "'"+netto+"','"+weight+"','"+price+"')");
    }
    public void showArticle(Context context) {
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
        db=context.openOrCreateDatabase("order",MODE_PRIVATE,null);
        Cursor c = db.rawQuery("select * from articles order by name asc", null);
        StringBuffer stringBuffer = new StringBuffer();
        while (c.moveToNext()) {
            //stringBuffer.append(c.getString(0) + "    ");
            id.add(c.getString(0));
            name.add(c.getString(2));
            code.add(c.getString(1));
            packing.add(c.getString(5));
            shortName.add(c.getString(3));
            units.add(c.getString(4));
            brutto.add(c.getString(6));
            netto.add(c.getString(7));
            weight.add(c.getString(8));
            price.add(c.getString(9));
        }
        //Toast.makeText(context, stringBuffer, Toast.LENGTH_SHORT).show();
    }
}
