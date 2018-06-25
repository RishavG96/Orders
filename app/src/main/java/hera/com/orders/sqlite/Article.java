package hera.com.orders.sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by rishavg on 6/12/18.
 */

public class Article {
    SQLiteDatabase db;
    public void addArticle(Context context, hera.com.orders.model.Article article){
        hera.com.orders.service.Article.db.execSQL("create table if not exists articles(id integer, code varchar(1000), name vharchar(1000), shortName varchar(1000)," +
                "units vharchar(1000), packing varchar(1000), brutto varchar(1000), netto varchar(1000), weight varchar(1000)," +
                "price varchar(1000))");
        hera.com.orders.service.Article.db.execSQL("insert into articles values("+article.id+",'"+article.code+"','"+article.name+"'," +
                "'"+article.shortName+"','"+article.units+"','"+article.packing+"','"+article.brutto+"'," + "'"+article.netto+"'," +
                "'"+article.weight+"','"+article.price+"')");
    }
    public Iterable<hera.com.orders.model.Article> showArticle(Context context) {
        db=context.openOrCreateDatabase("order",MODE_PRIVATE,null);
        Cursor c = db.rawQuery("select * from articles order by name asc", null);
        List<hera.com.orders.model.Article> articleList=new ArrayList<>();
        while (c.moveToNext()) {
            hera.com.orders.model.Article article=new hera.com.orders.model.Article();
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
    public Iterable<hera.com.orders.model.Article> showArticle(Context context, int id) {
        db=context.openOrCreateDatabase("order",MODE_PRIVATE,null);
        Cursor c = db.rawQuery("select * from articles where id="+id+" order by name asc", null);
        List<hera.com.orders.model.Article> articleList=new ArrayList<>();
        while (c.moveToNext()) {
            hera.com.orders.model.Article article=new hera.com.orders.model.Article();
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
