package hera.com.orders.service;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.StrictMode;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonArray;

import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import hera.com.orders.MainActivity;

import static android.content.Context.MODE_PRIVATE;

public class Orders  {
    String jwtToken="eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJKT1NJUCIsImlzcyI6IkVVUk85OSIsImlhdCI6MTUyODE5NjM5NX0.bm_IzXl0-hLyHPwYYCInwTDBGD-NMz2PVfAQjDEqj5E";
    RequestQueue requestQueue;  // This is our requests queue to process our HTTP requests.
    SQLiteDatabase db;
    hera.com.orders.sqlite.Orders sqlite_orders;
    hera.com.orders.model.Orders model_orders;
    JSONObject parameters;
    Gson _gson;
    String toJson;
    URL url;
    //String url="http://192.168.111.15:8081/Euro99NarudzbeBack/resources/protected/narudzba";
    //String urlString="http://192.168.111.15:8081/Euro99NarudzeBack/resources/protected/artikli";
    String urlString="http://192.168.111.15:8081/Euro99NarudzeBack/resources/protected/narudzba";
    public void sendToServer(final Context context,final int orderId){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


//        requestQueue = Volley.newRequestQueue(context); // This setups up a new request queue which we will need to make HTTP requests
//        RequestQueue queue = Volley.newRequestQueue(context);
//        sqlite_orders=new hera.com.orders.sqlite.Orders();
//        model_orders=sqlite_orders.showOrders(context,orderId);
//
//        Gson gson = new Gson();
//        String toJson=gson.toJson(model_orders);
//        db=context.openOrCreateDatabase("order",MODE_PRIVATE, null);
//        //Toast.makeText(context,"here="+jwtToken,Toast.LENGTH_SHORT).show();
//
//        try {
//            parameters=new JSONObject(toJson);
////            parameters.put("Content-Type","application/json");
//////                headers.put("Accept","application/json");
////            parameters.put("jwtToken", jwtToken);
//            //parameters.put("jwtToken", "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJKT1NJUCIsImlzcyI6IkVVUk85OSIsImlhdCI6MTUyODE5NjM5NX0.bm_IzXl0-hLyHPwYYCInwTDBGD-NMz2PVfAQjDEqj5E");
//            //Toast.makeText(context,"here="+toJson,Toast.LENGTH_SHORT).show();
//            Log.d("json",parameters+"");
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//
//
//
//        JsonObjectRequest strRequest = new JsonObjectRequest(Request.Method.POST, ""+url,toJson,
//                new Response.Listener<JSONObject>()
//                {
//                    @Override
//                    public void onResponse(JSONObject response)
//                    {
//                        //ContentValues cv=new ContentValues();
//                        //cv.put("sended", "Y");
//                        //db.update("order",cv,"orderId="+orderId,null);
//                        Toast.makeText(context,"Order Sent!",Toast.LENGTH_SHORT).show();
//                    }
//                },
//                new Response.ErrorListener()
//                {
//                    @Override
//                    public void onErrorResponse(VolleyError error)
//                    {
//                        Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
//                    }
//                }
//                )
//        {
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                HashMap<String, String> headers = new HashMap<String, String>();
//                //headers.put("Content-Type", "application/json");
//                headers.put("Content-Type","application/json");
//                headers.put("Accept","application/json");
//                headers.put("jwtToken", jwtToken);
//                return headers;
//            }
//        }
//        ;
//        queue.add(strRequest);

        sqlite_orders=new hera.com.orders.sqlite.Orders();
        model_orders=sqlite_orders.showOrders(context,orderId);
        _gson = new Gson();


        toJson = _gson.toJson(model_orders);


        try {
        url=new URL(urlString);
        HttpURLConnection connection=(HttpURLConnection)url.openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        connection.setRequestProperty("Accept","application/json");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("jwtToken",jwtToken);
        connection.setRequestProperty ("User-agent", "mozilla");
        OutputStreamWriter osw = new OutputStreamWriter(connection.getOutputStream());
        osw.write(toJson);
        Toast.makeText(context,toJson+"",Toast.LENGTH_SHORT).show();
        osw.flush();
        osw.close();
        Log.d("response code",""+connection.getResponseCode());
//                InputStreamReader x = new InputStreamReader(connection.getInputStream());
//                String s= x.toString();
        connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context,e+"",Toast.LENGTH_SHORT).show();
        }
    }
}
