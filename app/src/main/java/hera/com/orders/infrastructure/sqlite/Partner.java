package hera.com.orders.infrastructure.sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import hera.com.orders.infrastructure.service.Sorting_Partner;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by rishavg on 6/7/18.
 */

public class Partner {
    SQLiteDatabase db;
    public static ArrayList<String> id, code, name, address, city, amount, type, discount, status, businessHours, timeOfReceipt, responsiblePerson, forMobile;
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
        id=new ArrayList();
        code=new ArrayList();
        name=new ArrayList();
        type=new ArrayList();
        amount=new ArrayList();
        address=new ArrayList();
        city=new ArrayList();
        discount=new ArrayList();
        status=new ArrayList();
        businessHours=new ArrayList();
        timeOfReceipt=new ArrayList();
        responsiblePerson=new ArrayList();
        forMobile=new ArrayList();
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
            id.add(c.getString(0));
            name.add(c.getString(2));
            code.add(c.getString(1));
            amount.add(c.getString(5));
            address.add(c.getString(3));
            city.add(c.getString(4));
            type.add(c.getString(6));
            discount.add(c.getString(7));
            status.add(c.getString(8));
            businessHours.add(c.getString(9));
            timeOfReceipt.add(c.getString(10));
            responsiblePerson.add(c.getString(11));
            forMobile.add(c.getString(12));

            //Collections.sort(name);
//            ArrayList<Sorting_Partner> sortingList = new ArrayList<>();
//            int len=name.size();
//            for (int i = 0; i < len; i++) {
//                Sorting_Partner sorting = new Sorting_Partner(id.get(i), code.get(i), name.get(i), address.get(i), city.get(i),
//                        amount.get(i), type.get(i), discount.get(i), status.get(i), businessHours.get(i), timeOfReceipt.get(i),
//                        responsiblePerson.get(i), forMobile.get(i));
//                sortingList.add(sorting);
//            }
//            Collections.sort(sortingList, new Comparator<Sorting_Partner>() {
//                @Override
//                public int compare(Sorting_Partner o1, Sorting_Partner o2) {
//                    return o1.name.compareToIgnoreCase(o2.name);
//                }
//
//            });
//            id.clear();
//            code.clear();
//            name.clear();
//            address.clear();
//            city.clear();
//            amount.clear();
//            type.clear();;
//            discount.clear();
//            status.clear();
//            businessHours.clear();
//            timeOfReceipt.clear();
//            responsiblePerson.clear();
//            forMobile.clear();
//            for (int i = 0; i < len; i++) {
//                id.add(sortingList.get(i).id);
//                code.add(sortingList.get(i).code);
//                name.add(sortingList.get(i).name);
//                address.add(sortingList.get(i).address);
//                city.add(sortingList.get(i).city);
//                amount.add(sortingList.get(i).amount);
//                type.add(sortingList.get(i).type);
//                discount.add(sortingList.get(i).discount);
//                status.add(sortingList.get(i).status);
//                businessHours.add(sortingList.get(i).businessHours);
//                timeOfReceipt.add(sortingList.get(i).timeOfReceipt);
//                responsiblePerson.add(sortingList.get(i).responsiblePerson);
//                forMobile.add(sortingList.get(i).forMobile);
//
//            }
        }
//        Toast.makeText(context, stringBuffer, Toast.LENGTH_SHORT).show();
    }
    public void deletePartner(Context context)
    {
        db=context.openOrCreateDatabase("order",MODE_PRIVATE,null);
        db.execSQL("delete from partners");
    }
}
