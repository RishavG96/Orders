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
import hera.com.orders.AssortmentActivity;
import hera.com.orders.LoginActivity;
import hera.com.orders.MainActivity;

import static android.content.Context.MODE_PRIVATE;

public class Assortment {
    RequestQueue requestQueue;  // This is our requests queue to process our HTTP requests.
    String jwtToken;
    hera.com.orders.infrastructure.sqlite.Assortment sqlite_assortment;
    Context context;
    public void connect(Context con)
    {
        context=con;
        sqlite_assortment=new hera.com.orders.infrastructure.sqlite.Assortment();
        requestQueue = Volley.newRequestQueue(context); // This setups up a new request queue which we will need to make HTTP requests
        RequestQueue queue = Volley.newRequestQueue(context);
        JSONObject parameters = new JSONObject();
        Cursor c=AssortmentActivity.db.rawQuery("select * from user1",null);
        while(c.moveToNext())
        {
            if(c.getInt(0)== MainActivity.Id)
            {
                jwtToken=c.getString(3);
            }
        }
        JsonArrayRequest strRequest = new JsonArrayRequest(Request.Method.GET, AssortmentActivity.assortment_url, (String) null,
                new Response.Listener<JSONArray>()
                {
                    @Override
                    public void onResponse(JSONArray response)
                    {
                        try {
                            if(LoginActivity.assort==0) {
                                Toast.makeText(context,"here",Toast.LENGTH_SHORT).show();
                                //db.execSQL("delete from assortment");
                                for (int i = 0; i < response.length(); i++) {
                                    JSONObject ob = (JSONObject) response.opt(i);

                                    String assortmentId = ob.optString("assortmentId");
                                    String assortmentItemId = ob.optString("assortmentItemId");
                                    String partnerId = ob.optString("partnerId");
//                                    String validFrom = ob.optString("netto");
//                                    String validTo = ob.optString("packing");
                                    String articleId = ob.optString("articleId");
                                    sqlite_assortment.addAssortment(context, assortmentId, assortmentItemId, partnerId, articleId);
                                }
                                LoginActivity.assort=1;
                                Toast.makeText(context,"Table populated",Toast.LENGTH_SHORT).show();
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
