package hera.com.orders.sqlite;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import hera.com.orders.MainActivity;
import hera.com.orders.PartnersActivity;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by rishavg on 6/7/18.
 */

public class Partner {
    public void addPartner(Context context, hera.com.orders.model.Partner partner)
    {
        hera.com.orders.service.Partner.db.execSQL("create table if not exists partners(id integer, code varchar(1000), name varchar(1000)," +
                " address varchar(1000), city varchar(1000), amount varchar(1000), type varchar(1000), discount varchar(1000)" +
                ", status varchar(1000), businessHours varchar(1000), timeOfReceipt varchar(1000), responsiblePerson varchar(10000)," +
                " forMobile varchar(1000))");
        if(partner.name.contains("\""))
            partner.name.replace("\"","'");
        hera.com.orders.service.Partner.db.execSQL("insert into partners values ("+partner.id+",'"+partner.code+"','"+partner.name+"'" +
                ",'"+partner.address+"','"+partner.city+"','"+partner.amount+"'," + "'"+partner.type+"','"+partner.discount+"'" +
                ",'"+partner.status+"','"+partner.businessHours+"','"+partner.timeOfReceipt+"','"+partner.responsiblePerson+"'" +
                ",'"+partner.forMobile+"')");

    }
    public Iterable<hera.com.orders.model.Partner> showPartner(Context context)
    {
        Cursor c= MainActivity.db.rawQuery("select * from partners order by name asc",null);
        StringBuffer stringBuffer=new StringBuffer();
        List<hera.com.orders.model.Partner> partnerList=new ArrayList<hera.com.orders.model.Partner>();
        while(c.moveToNext())
        {
            hera.com.orders.model.Partner partner=new hera.com.orders.model.Partner();
            partner.id = c.getInt(0);
            partner.name=c.getString(2);
            partner.code=c.getString(1);
            partner.amount=c.getString(5);
            partner.address=c.getString(3);
            partner.city=c.getString(4);
            partner.type=c.getString(6);
            partner.discount=c.getString(7);
            partner.status=c.getString(8);
            partner.businessHours=c.getString(9);
            partner.timeOfReceipt=c.getString(10);
            partner.responsiblePerson=c.getString(11);
            partner.forMobile=c.getString(12);
            partnerList.add(partner);
        }
        return partnerList;
    }
    public Iterable<hera.com.orders.model.Partner> showPartner(Context context, int id)
    {
        Cursor c= MainActivity.db.rawQuery("select * from partners where id="+id+" order by name asc",null);
        StringBuffer stringBuffer=new StringBuffer();
        List<hera.com.orders.model.Partner> partnerList=new ArrayList<hera.com.orders.model.Partner>();
        while(c.moveToNext())
        {
            hera.com.orders.model.Partner partner=new hera.com.orders.model.Partner();
            partner.id = c.getInt(0);
            partner.name=c.getString(2);
            partner.code=c.getString(1);
            partner.amount=c.getString(5);
            partner.address=c.getString(3);
            partner.city=c.getString(4);
            partner.type=c.getString(6);
            partner.discount=c.getString(7);
            partner.status=c.getString(8);
            partner.businessHours=c.getString(9);
            partner.timeOfReceipt=c.getString(10);
            partner.responsiblePerson=c.getString(11);
            partner.forMobile=c.getString(12);
            partnerList.add(partner);
        }
        return partnerList;
    }
    public static Iterable<hera.com.orders.model.Partner> showPartner(Context context, ArrayList n)
    {
        List<hera.com.orders.model.Partner> partnerList=new ArrayList<hera.com.orders.model.Partner>();
        for(int i =0; i< n.size(); i++) {
            Cursor c = PartnersActivity.db.rawQuery("select * from partners where name =\""+ n.get(i) + "\"", null);
            while (c.moveToNext()) {
                hera.com.orders.model.Partner partner=new hera.com.orders.model.Partner();
                partner.id = c.getInt(0);
                partner.name=c.getString(2);
                partner.code=c.getString(1);
                partner.amount=c.getString(5);
                partner.address=c.getString(3);
                partner.city=c.getString(4);
                partner.type=c.getString(6);
                partner.discount=c.getString(7);
                partner.status=c.getString(8);
                partner.businessHours=c.getString(9);
                partner.timeOfReceipt=c.getString(10);
                partner.responsiblePerson=c.getString(11);
                partner.forMobile=c.getString(12);
                partnerList.add(partner);
            }
        }
        return partnerList;
    }
    public void deletePartner(Context context)
    {
        PartnersActivity.db=context.openOrCreateDatabase("order",MODE_PRIVATE,null);
        try {
            PartnersActivity.db.execSQL("delete from partners");
        }
        catch (Exception e){}
    }
}
