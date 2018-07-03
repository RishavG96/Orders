package hera.com.orders.service;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import hera.com.orders.ArticleActivity;
import hera.com.orders.LoginActivity;
import hera.com.orders.MainActivity;

import static android.content.Context.MODE_PRIVATE;

public class Article {
    RequestQueue requestQueue;  // This is our requests queue to process our HTTP requests.
    public static SQLiteDatabase db;
    String jwtToken;
    hera.com.orders.sqlite.Article sqlite_article;
    Context context;
    String url;
    public void connect(final Context con)
    {
        context=con;
        db=context.openOrCreateDatabase("order",MODE_PRIVATE,null);
        sqlite_article=new hera.com.orders.sqlite.Article();
        requestQueue = Volley.newRequestQueue(context); // This setups up a new request queue which we will need to make HTTP requests
        RequestQueue queue = Volley.newRequestQueue(context);
        JSONObject parameters = new JSONObject();
        Cursor c=db.rawQuery("select * from user1",null);
        while(c.moveToNext())
        {
            if(c.getInt(0)== MainActivity.Id)
            {
                jwtToken=c.getString(3);
            }
        }
        Cursor cursor=db.rawQuery("select * from url",null);
        if(cursor.moveToNext())
        {
            url=cursor.getString(0);
        }
        JsonArrayRequest strRequest = new JsonArrayRequest(Request.Method.GET, url+"protected/artikli", (String) null,
                new Response.Listener<JSONArray>()
                {
                    @Override
                    public void onResponse(JSONArray response)
                    {
                        //Toast.makeText(context,""+response,Toast.LENGTH_SHORT).show();
                        try {
                            if(LoginActivity.art==0) {
                                try {
                                    db.execSQL("delete from articles");
                                }catch (Exception e){}
                                db.beginTransaction();
                                for (int i = 0; i < response.length(); i++) {

                                    JSONObject ob = (JSONObject) response.opt(i);
                                    hera.com.orders.model.Article article_mod=new hera.com.orders.model.Article();
                                    article_mod.id = Integer.parseInt(ob.optString("id"));
                                    article_mod.code = ob.optString("code");
                                    article_mod.name = ob.optString("name");
                                    article_mod.brutto = ob.optString("brutto");
                                    article_mod.netto = ob.optString("netto");
                                    article_mod.packing = ob.optString("packing");
                                    article_mod.price = ob.optString("price");
                                    article_mod.shortName = ob.optString("shortname");
                                    article_mod.units = ob.optString("units");
                                    article_mod.weight = ob.optString("weight");
                                    sqlite_article.addArticle(context,article_mod);
                                }
                                db.setTransactionSuccessful();
                                db.endTransaction();
                                db.close();
                                LoginActivity.art=1;
                            }
                            //sqlite_article.showArticle(context);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        Toast.makeText(context,"Error= "+ error.toString(), Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                //headers.put("Content-Type", "application/json");
                headers.put("jwtToken", jwtToken);
                return headers;
            }
        }
                ;
        queue.add(strRequest);
    }
}
