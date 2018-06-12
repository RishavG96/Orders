package hera.com.orders.infrastructure.service;

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
import hera.com.orders.MainActivity;
import hera.com.orders.PartnersActivity;

import static android.content.Context.MODE_PRIVATE;

public class Article {
    RequestQueue requestQueue;  // This is our requests queue to process our HTTP requests.
    SQLiteDatabase db;
    String jwtToken;
    hera.com.orders.infrastructure.sqlite.Article sqlite_article;
    Context context;
    public void connect(Context con)
    {
        context=con;
        db=context.openOrCreateDatabase("order",MODE_PRIVATE,null);
        sqlite_article=new hera.com.orders.infrastructure.sqlite.Article();
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
        JsonArrayRequest strRequest = new JsonArrayRequest(Request.Method.GET, ArticleActivity.article_url, (String) null,
                new Response.Listener<JSONArray>()
                {
                    @Override
                    public void onResponse(JSONArray response)
                    {
                        try {
                            Toast.makeText(context, "here"+response.length(), Toast.LENGTH_SHORT).show();
                            db.execSQL("delete from articles");
                            for(int i=0;i<response.length();i++)
                            {

                                JSONObject ob= (JSONObject) response.opt(i);

                                int Id=Integer.parseInt(ob.optString("id"));
                                String code=ob.optString("code");
                                String name=ob.optString("name");
                                String brutto=ob.optString("brutto");
                                String netto=ob.optString("netto");
                                String packing=ob.optString("packing");
                                String price=ob.optString("price");
                                String shortname=ob.optString("shortname");
                                String units=ob.optString("units");
                                String weigh=ob.optString("weigh");

                                sqlite_article.addArticle(context, Id, code, name, shortname, units, packing, brutto,
                                        netto, weigh, price);

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
