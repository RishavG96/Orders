package hera.com.orders.service;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import hera.com.orders.LoginActivity;
import hera.com.orders.MainActivity;

import static android.content.Context.MODE_PRIVATE;


public class User {
    RequestQueue requestQueue;  // This is our requests queue to process our HTTP requests.
    SQLiteDatabase db;
    hera.com.orders.sqlite.User sqlite_user;
    hera.com.orders.model.User user1;
    String url;
    public void login(final Context context, hera.com.orders.model.User user)
    {
        user1=user;
        requestQueue = Volley.newRequestQueue(context); // This setups up a new request queue which we will need to make HTTP requests
        RequestQueue queue = Volley.newRequestQueue(context);
        JSONObject parameters = new JSONObject();
        sqlite_user=new hera.com.orders.sqlite.User();
        db=context.openOrCreateDatabase("order",MODE_PRIVATE, null);
        try {
            parameters.put("username",user1.Username);
            parameters.put("password", user1.Password);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Cursor cursor=db.rawQuery("select * from url",null);
        if(cursor.moveToNext())
        {
            url=cursor.getString(0);
        }
        Toast.makeText(context, url, Toast.LENGTH_SHORT).show();
        JsonObjectRequest strRequest = new JsonObjectRequest(Request.Method.POST,url+"login",parameters,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response)
                    {
                        try {
                            db.execSQL("insert into login values(1)");
                            String jwt = response.getString("jwt");
                            String id = response.getJSONObject("korisnik").getString("id");
                            MainActivity.Id=Integer.parseInt(id);
                            user1.Id=Integer.parseInt(id);
                            user1.Token=jwt;
                            user1.Url=url;
                            sqlite_user.addUser(context,user1);
                            Intent intent=new Intent(context,MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(intent);
                            ((Activity)context).finish();

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
                        Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
        queue.add(strRequest);
    }
}
