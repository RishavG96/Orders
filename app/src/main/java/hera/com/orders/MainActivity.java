package hera.com.orders;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.Response;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;




public class MainActivity extends AppCompatActivity {

    EditText id;
    EditText pass;
    Button submit;
    hera.com.orders.infrastructure.classes.User classes_user;
    hera.com.orders.infrastructure.sqlite.User sqlite_user;
    RequestQueue requestQueue;  // This is our requests queue to process our HTTP requests.
    String url="http://192.168.111.15:8081/Euro99NarudzbeBack/resources/login"; // This will hold the full URL which will include the username entered in the id.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        classes_user=new hera.com.orders.infrastructure.classes.User();
        sqlite_user=new hera.com.orders.infrastructure.sqlite.User();
        this.id = (EditText) findViewById(R.id.ID);
        this.pass = (EditText) findViewById(R.id.pass);
        this.submit = (Button) findViewById(R.id.send);
        requestQueue = Volley.newRequestQueue(this); // This setups up a new request queue which we will need to make HTTP requests
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                classes_user.Username=id.getText().toString();
                classes_user.Password=pass.getText().toString();
                classes_user.Url=url;
                login(classes_user.Username,classes_user.Password);
            }
        });

    }
    public void login(final String username, final String password)
    {
        RequestQueue queue = Volley.newRequestQueue(this);
        JSONObject parameters = new JSONObject();
        try {
            parameters.put("username",username);
            parameters.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest strRequest = new JsonObjectRequest(Request.Method.POST, url,parameters,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response)
                    {
                        try {
                            String jwt = response.getString("jwt");
                            String id = response.getJSONObject("korisnik").getString("id");
                            classes_user.Id=Integer.parseInt(id);
                            classes_user.Token=jwt;
                            sqlite_user.addUser(getApplicationContext(), id, username, password, url, jwt);
                            Toast.makeText(getApplicationContext(), id, Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
        queue.add(strRequest);
    }
}
