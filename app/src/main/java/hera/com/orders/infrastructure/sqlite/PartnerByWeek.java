package hera.com.orders.infrastructure.sqlite;

import android.content.Context;
import android.database.Cursor;
import android.widget.Toast;

import java.util.ArrayList;

import hera.com.orders.MainActivity;
import hera.com.orders.PartnersActivity;

import static android.content.Context.MODE_PRIVATE;

public class PartnerByWeek {
    public static ArrayList<String> id, code, name, address, city, amount, type, discount, status, businessHours, timeOfReceipt, responsiblePerson, forMobile;
    public static ArrayList<String> partnerId,weekDay;
    public void addPartnerByWeek(Context context, int partnerId, String weekDay)
    {
        hera.com.orders.infrastructure.service.PartnerByWeek.db.execSQL("create table if not exists partnerbyweek(partnerId integer, " +
                "weekDay varchar(1000))");
        hera.com.orders.infrastructure.service.PartnerByWeek.db.execSQL("insert into partnerbyweek values ("+partnerId+",'"+weekDay+"')");
//        Toast.makeText(context,"here",Toast.LENGTH_SHORT).show();
    }
    public void showPartner(Context context)
    {
        partnerId=new ArrayList();
        weekDay=new ArrayList();

        //db=context.openOrCreateDatabase("order",MODE_PRIVATE,null);
        Cursor c= MainActivity.db.rawQuery("select * from partnerbyweek",null);
        while(c.moveToNext())
        {
            partnerId.add(c.getString(0));
            weekDay.add(c.getString(1));
        }
    }
    public static void showPartner(Context context, int week)
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
        //db=context.openOrCreateDatabase("order",MODE_PRIVATE,null);
        String weeks[]={"PON","UTO","SRI","CET","PET","SUB"};
            Cursor c = MainActivity.db.rawQuery("select * from partnerbyweek where weekDay =\""+ weeks[week] + "\"", null);
            while (c.moveToNext()) {
                Cursor c1=MainActivity.db.rawQuery("select * from partners where id="+ c.getInt(0)+"",null);
                {
                    while(c1.moveToNext()) {
                        id.add(c1.getString(0));
                        name.add(c1.getString(2));
                        code.add(c1.getString(1));
                        amount.add(c1.getString(5));
                        address.add(c1.getString(3));
                        city.add(c1.getString(4));
                        type.add(c1.getString(6));
                        discount.add(c1.getString(7));
                        status.add(c1.getString(8));
                        businessHours.add(c1.getString(9));
                        timeOfReceipt.add(c1.getString(10));
                        responsiblePerson.add(c1.getString(11));
                        forMobile.add(c1.getString(12));
                    }
                }
            }

    }
    public void deletePartner(Context context)
    {
        hera.com.orders.infrastructure.service.PartnerByWeek.db=context.openOrCreateDatabase("order",MODE_PRIVATE,null);
        hera.com.orders.infrastructure.service.PartnerByWeek.db.execSQL("delete from partnerbyweek");
    }
}
