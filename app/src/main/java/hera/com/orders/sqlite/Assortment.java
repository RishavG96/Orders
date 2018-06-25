package hera.com.orders.sqlite;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import hera.com.orders.MainActivity;
import hera.com.orders.module.Article;

import static android.content.Context.MODE_PRIVATE;

public class Assortment {
    //public static ArrayList<String> assortmentId, assortmentItemId, partnerId, articleId;
    //public static ArrayList<String> id, code, name, shortName, units, packing, brutto, netto, weight, price;
    public void addAssortment(Context context, hera.com.orders.module.Assortment assortment){
        hera.com.orders.service.Assortment.db.execSQL("create table if not exists assortment(assortmentId varchar(1000)," +
                " assortmentItemId varchar(1000), partnerId vharchar(1000), articleId varchar(1000))");
        hera.com.orders.service.Assortment
                .db.execSQL("insert into assortment values('"+assortment.assortmentId+"','"+assortment.assortmentItemId+"','"+
                assortment.partnerId+"','"+assortment.articleId+"')");
    }
    public Iterable<Article> showAssortment(Context context, int Id) {

        String identity=Id+"";
        int count=0;
        Cursor c = MainActivity.db.rawQuery("select * from assortment where partnerId = '"+identity+"'", null);
        List<hera.com.orders.module.Assortment> assortmentList = new ArrayList<>();
        while (c.moveToNext()) {
            hera.com.orders.module.Assortment assortment=new hera.com.orders.module.Assortment();
            assortment.assortmentId=c.getString(0);
            assortment.partnerId=c.getString(2);
            assortment.assortmentItemId=c.getString(1);
            assortment.articleId=c.getString(3);
            assortmentList.add(assortment);
            count++;
        }
        List<Article> articleList=new ArrayList<>();
        for( int j=0;j<assortmentList.size();j++)
        {
            int i=Integer.parseInt(assortmentList.get(j).articleId);
            Cursor c1 = MainActivity.db.rawQuery("select * from articles where id="+i+" order by name asc", null);
            while (c1.moveToNext()) {
                Article article=new Article();
                article.id=c1.getInt(0);
                article.name=c1.getString(2);
                article.code=c1.getString(1);
                article.packing=c1.getString(5);
                article.shortName=c1.getString(3);
                article.units=c1.getString(4);
                article.brutto=c1.getString(6);
                article.netto=c1.getString(7);
                article.weight=c1.getString(8);
                article.price=c1.getString(9);
                articleList.add(article);
            }
        }
        return articleList;
    }
    public Iterable<hera.com.orders.module.Article> showAssortmentDetails(Context context, int id) {
        MainActivity.db=context.openOrCreateDatabase("order",MODE_PRIVATE,null);
        Cursor c = MainActivity.db.rawQuery("select * from articles where id="+id+" order by name asc", null);
        List<hera.com.orders.module.Article> articleList=new ArrayList<>();
        while (c.moveToNext()) {
            hera.com.orders.module.Article article=new hera.com.orders.module.Article();
            article.id=c.getInt(0);
            article.name=c.getString(2);
            article.code=c.getString(1);
            article.packing=c.getString(5);
            article.shortName=c.getString(3);
            article.units=c.getString(4);
            article.brutto=c.getString(6);
            article.netto=c.getString(7);
            article.weight=c.getString(8);
            article.price=c.getString(9);
            articleList.add(article);
        }
        return articleList;
    }
}
