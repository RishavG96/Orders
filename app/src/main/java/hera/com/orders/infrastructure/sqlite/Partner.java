package hera.com.orders.infrastructure.sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by rishavg on 6/7/18.
 */

public class Partner {
    SQLiteDatabase db;
    public static ArrayList name, code, amount, address, city;
    public void addPartner(Context context, int Id, String code, String name, String address, String city, String amount,
                           String type, String discount, String status, String businessHours, String timeOfReceipt,
                           String responsiblePerson, String forMobile)
    {
        db=context.openOrCreateDatabase("order",MODE_PRIVATE,null);
        db.execSQL("create table if not exists partners(id integer, code varchar(1000), name varchar(1000)," +
                " address varchar(1000), city varchar(1000), amount varchar(1000), type varchar(1000), discount varchar(1000)" +
                ", status varchar(1000), businessHours varchar(1000), timeOfReceipt varchar(1000), responsiblePerson varchar(10000)," +
                " forMobile varchar(1000))");
        db.execSQL("insert into partners values ("+Id+",'"+code+"','"+name+"','"+address+"','"+city+"','"+amount+"'," +
                "'"+type+"','"+discount+"','"+status+"','"+businessHours+"','"+timeOfReceipt+"','"+responsiblePerson+"'" +
                ",'"+forMobile+"')");

    }
    public void showPartner(Context context)
    {
        name=new ArrayList();
        code=new ArrayList();
        amount=new ArrayList();
        address=new ArrayList();
        city=new ArrayList();
        db=context.openOrCreateDatabase("order",MODE_PRIVATE,null);
        Cursor c=db.rawQuery("select * from partners",null);
        StringBuffer stringBuffer=new StringBuffer();
        while(c.moveToNext())
        {
//            stringBuffer.append(c.getInt(0)+"   "+c.getString(1)+" " +
//                    " "+c.getString(2)+"  "+c.getString(3)+"  "+c.getString(4)
//                    +"  "+c.getString(5)+"  "+c.getString(6)+"  "+c.getString(7)
//                    +"  "+c.getString(8)+"  "+c.getString(9)+"  "+c.getString(10)
//                    +"  "+c.getString(11)+"  "+c.getString(12));
            name.add(c.getString(2));
            code.add(c.getString(1));
            amount.add(c.getString(5));
            address.add(c.getString(3));
            city.add(c.getString(4));

            Collections.sort(name);
        }
//        Toast.makeText(context, stringBuffer, Toast.LENGTH_SHORT).show();
    }
    public void deletePartner(Context context)
    {
        db=context.openOrCreateDatabase("order",MODE_PRIVATE,null);
        db.execSQL("delete from partners");
    }
}
