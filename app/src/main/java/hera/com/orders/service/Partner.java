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

import hera.com.orders.LoginActivity;
import hera.com.orders.MainActivity;
import hera.com.orders.PartnersActivity;

import static android.content.Context.MODE_PRIVATE;


public class Partner {
    RequestQueue requestQueue;  // This is our requests queue to process our HTTP requests.

    String jwtToken;
    hera.com.orders.sqlite.Partner partner;
    Context context;
    public static SQLiteDatabase db;
    public void connect(Context con)
    {
        context=con;
        partner=new hera.com.orders.sqlite.Partner();
        requestQueue = Volley.newRequestQueue(context); // This setups up a new request queue which we will need to make HTTP requests
        RequestQueue queue = Volley.newRequestQueue(context);
        JSONObject parameters = new JSONObject();
        db=context.openOrCreateDatabase("order",MODE_PRIVATE,null);
        Cursor c=db.rawQuery("select * from user1",null);
        while(c.moveToNext())
        {
            if(c.getInt(0)==MainActivity.Id)
            {
                jwtToken=c.getString(3);
            }
        }
        JsonArrayRequest strRequest = new JsonArrayRequest(Request.Method.GET, PartnersActivity.partner_url, (String) null,
                new Response.Listener<JSONArray>()
                {
                    @Override
                    public void onResponse(JSONArray response)
                    {
                        try {
                            if(LoginActivity.part==0) {
                                db=context.openOrCreateDatabase("order", MODE_PRIVATE,null);
                                partner.deletePartner(context);
                                //Toast.makeText(context, "part"+LoginActivity.part,Toast.LENGTH_SHORT).show();
                                db.beginTransaction();
                                for (int i = 0; i < response.length(); i++) {

                                    JSONObject ob = (JSONObject) response.opt(i);
                                    hera.com.orders.module.Partner partner_mod=new hera.com.orders.module.Partner();
                                    partner_mod.id = Integer.parseInt(ob.optString("id"));
                                    partner_mod.code = ob.optString("code");
                                    partner_mod.name = ob.optString("name");
                                    partner_mod.address = ob.optString("address");
                                    partner_mod.city = ob.optString("city");
                                    partner_mod.amount = ob.optString("amount");
                                    partner_mod.type = ob.optString("type");
                                    partner_mod.discount = ob.optString("discount");
                                    partner_mod.status = ob.optString("status");
                                    partner_mod.businessHours = ob.optString("businessHours");
                                    partner_mod.timeOfReceipt = ob.optString("timeOfReceipt");
                                    partner_mod.responsiblePerson = ob.optString("responsiblePerson");
                                    partner_mod.forMobile = ob.optString("forMobile");
                                    partner.addPartner(context, partner_mod);
                                }
                                db.setTransactionSuccessful();
                                db.endTransaction();
                                db.close();
                                LoginActivity.part=1;
                            }
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

