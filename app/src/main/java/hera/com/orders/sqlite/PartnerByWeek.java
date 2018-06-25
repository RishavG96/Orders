package hera.com.orders.sqlite;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import hera.com.orders.MainActivity;
import hera.com.orders.model.Partner;

import static android.content.Context.MODE_PRIVATE;

public class PartnerByWeek {
    public void addPartnerByWeek(Context context, hera.com.orders.model.PartnerByWeek partnerByWeek)
    {
        hera.com.orders.service.PartnerByWeek.db.execSQL("create table if not exists partnerbyweek(partnerId integer, " +
                "weekDay varchar(1000))");
        hera.com.orders.service.PartnerByWeek.db.execSQL("insert into partnerbyweek values ("+partnerByWeek.partnerId+"," +
                "'"+partnerByWeek.weekDay+"')");
    }
    public Iterable<hera.com.orders.model.PartnerByWeek> showPartner(Context context)
    {
        Cursor c= MainActivity.db.rawQuery("select * from partnerbyweek",null);
        List<hera.com.orders.model.PartnerByWeek> partnerByWeekList = new ArrayList<>();
        while(c.moveToNext())
        {
            hera.com.orders.model.PartnerByWeek partnerByWeek=new hera.com.orders.model.PartnerByWeek();
            partnerByWeek.partnerId=c.getInt(0);
            partnerByWeek.weekDay=c.getString(1);
            partnerByWeekList.add(partnerByWeek);
        }
        return partnerByWeekList;
    }
    public static Iterable<hera.com.orders.model.Partner> showPartner(Context context, int week)
    {
        String weeks[]={"PON","UTO","SRI","CET","PET","SUB"};
        Cursor c = MainActivity.db.rawQuery("select * from partnerbyweek where weekDay =\""+ weeks[week] + "\"", null);
        List<Partner> partnerList=new ArrayList<>();
        while (c.moveToNext()) {
            Cursor c1=MainActivity.db.rawQuery("select * from partners where id="+ c.getInt(0)+"",null);
            {
                while(c1.moveToNext()) {
                    Partner partner=new Partner();
                    partner.id=c1.getInt(0);
                    partner.name=c1.getString(2);
                    partner.code=c1.getString(1);
                    partner.amount=c1.getString(5);
                    partner.address=c1.getString(3);
                    partner.city=c1.getString(4);
                    partner.type=c1.getString(6);
                    partner.discount=c1.getString(7);
                    partner.status=c1.getString(8);
                    partner.businessHours=c1.getString(9);
                    partner.timeOfReceipt=c1.getString(10);
                    partner.responsiblePerson=c1.getString(11);
                    partner.forMobile=c1.getString(12);
                    partnerList.add(partner);
                }
            }
        }
        return  partnerList;
    }
    public void deletePartner(Context context)
    {
        hera.com.orders.service.PartnerByWeek.db=context.openOrCreateDatabase("order",MODE_PRIVATE,null);
        hera.com.orders.service.PartnerByWeek.db.execSQL("delete from partnerbyweek");
    }
}
