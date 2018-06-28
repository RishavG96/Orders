package hera.com.orders.service;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.StrictMode;
import android.provider.SyncStateContract;
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
import hera.com.orders.SendOrdertask;

import static android.content.ContentValues.TAG;
import static android.content.Context.MODE_PRIVATE;

public class Orders  {
    String jwtToken;
    hera.com.orders.sqlite.Orders sqlite_orders;
    hera.com.orders.model.Orders model_orders;
    Gson _gson;
    String toJson;
    URL url;
    String urlString="http://192.168.111.15:8081/Euro99NarudzbeBack/resources/protected/narudzba";
    public void sendToServer(final int orderId){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        sqlite_orders=new hera.com.orders.sqlite.Orders();
        model_orders=sqlite_orders.showOrders(orderId);
        Cursor c=MainActivity.db.rawQuery("select * from user1",null);
        while(c.moveToNext())
        {
            if(c.getInt(0)== MainActivity.Id)
            {
                jwtToken=c.getString(3);
            }
        }
        _gson = new Gson();

        try {
            toJson = _gson.toJson(model_orders);
            url=new URL(urlString);
            HttpURLConnection connection=(HttpURLConnection)url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setRequestProperty("Accept","application/json");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("jwtToken",jwtToken);
            OutputStreamWriter osw = new OutputStreamWriter(connection.getOutputStream());
            osw.write(toJson);
            osw.flush();
            osw.close();
            int code=connection.getResponseCode();
            if(code==200)
            {
                ContentValues cv=new ContentValues();
                cv.put("sended", "Y");
                MainActivity.db.update("orders2",cv,"orderId="+orderId,null);
            }
            Log.d("response code",""+code);
            Log.d("JSON:",""+toJson);
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
