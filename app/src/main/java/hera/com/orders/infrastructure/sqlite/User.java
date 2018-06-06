package hera.com.orders.infrastructure.sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import static android.content.Context.MODE_PRIVATE;

public class User {

    SQLiteDatabase db;
    public void addUser(Context context,String Id, String Username, String Password, String Url, String Token)
    {
        db=context.openOrCreateDatabase("orders",MODE_PRIVATE,null);
        db.execSQL("create table if not exists users(id integer, username varchar(50), password varchar(50), " +
                "url varchar(500), token varchar(500))");
        db.execSQL("insert into users values("+Id+",'"+Username+"','"+Password+"','"+Url+"','"+Token+"')");
        Toast.makeText(context, "User Added", Toast.LENGTH_SHORT).show();
        Cursor c=db.rawQuery("select * from users",null);
        StringBuffer stringBuffer=new StringBuffer();
        while(c.moveToNext())
        {
            stringBuffer.append(c.getInt(0)+"   "+c.getString(1)+" " +
                    " "+c.getString(2)+"  "+c.getString(3)+"  "+c.getString(4));
        }
        Toast.makeText(context, stringBuffer, Toast.LENGTH_SHORT).show();
    }
}
