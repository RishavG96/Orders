package hera.com.orders.infrastructure.service;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import hera.com.orders.LoginActivity;
import hera.com.orders.MainActivity;
import hera.com.orders.PartnersActivity;

import static android.content.Context.MODE_PRIVATE;


public class Partner {
    RequestQueue requestQueue;  // This is our requests queue to process our HTTP requests.
    SQLiteDatabase db;
    String jwtToken;
    hera.com.orders.infrastructure.sqlite.Partner partner;
    Context context;
    public void connect( Context con)
    {
        context=con;
        db=context.openOrCreateDatabase("order",MODE_PRIVATE,null);
        partner=new hera.com.orders.infrastructure.sqlite.Partner();
        requestQueue = Volley.newRequestQueue(context); // This setups up a new request queue which we will need to make HTTP requests
        RequestQueue queue = Volley.newRequestQueue(context);
        JSONObject parameters = new JSONObject();
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
                            partner.deletePartner(context);
                            for(int i=0;i<response.length();i++)
                            {

                                JSONObject ob= (JSONObject) response.opt(i);

                                int Id=Integer.parseInt(ob.optString("id"));
                                String code=ob.optString("code");
                                String name=ob.optString("name");
                                String address=ob.optString("address");
                                String city=ob.optString("city");
                                String amount=ob.optString("amount");
                                String type=ob.optString("type");
                                String discount=ob.optString("discount");
                                String status=ob.optString("status");
                                String businessHours=ob.optString("businessHours");
                                String timeOfReceipt=ob.optString("timeOfReceipt");
                                //String phone=ob.getString("");
                                String responsiblePerson = ob.optString("responsiblePerson");
                                String forMobile=ob.optString("forMobile");

                                partner.addPartner(context, Id, code, name, address, city, amount, type, discount, status,
                                        businessHours, timeOfReceipt, responsiblePerson, forMobile);

                            }
//                            partner.showPartner(context);
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
