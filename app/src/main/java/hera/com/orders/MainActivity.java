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

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    EditText id; // This will be a reference to our GitHub username input.
    EditText pass;
    Button submit;  // This is a reference to the "Get Repos" button.

    RequestQueue requestQueue;  // This is our requests queue to process our HTTP requests.
    String url="http://192.168.111.15:8081/Euro99NarudzbeBack/resources/login"; // This will hold the full URL which will include the username entered in the id.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.id = (EditText) findViewById(R.id.ID);  // Link our github user text box.
        this.pass = (EditText) findViewById(R.id.pass);  // Link our github user text box.
        this.submit = (Button) findViewById(R.id.send);  // Link our clicky button.
        requestQueue = Volley.newRequestQueue(this); // This setups up a new request queue which we will need to make HTTP requests
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login(id.getText().toString(),pass.getText().toString());
            }
        });
    }
    public void login(String username, String password)
    {
        RequestQueue queue = Volley.newRequestQueue(this);
        Map<String, String> params = new HashMap();
        params.put("username", username);
        params.put("password", password);

        JSONObject parameters = new JSONObject(params);
        JsonObjectRequest strRequest = new JsonObjectRequest(Request.Method.POST, url,parameters,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response)
                    {
                        try {
                            String jwt = response.getString("jwt");
                            Toast.makeText(getApplicationContext(), jwt, Toast.LENGTH_SHORT).show();
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
