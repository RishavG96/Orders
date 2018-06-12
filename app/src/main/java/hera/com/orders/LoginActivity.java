package hera.com.orders;


import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class LoginActivity extends AppCompatActivity {

    EditText id;
    EditText pass;
    Button submit;
    EditText newurl;
    public static hera.com.orders.infrastructure.module.User classes_user;
    public static hera.com.orders.infrastructure.sqlite.User sqlite_user;
    public static hera.com.orders.infrastructure.service.User service_user;
    SQLiteDatabase db;
    public static String url="http://192.168.111.15:8081/Euro99NarudzbeBack/resources/login"; // This will hold the full URL which will include the username entered in the id.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        classes_user=new hera.com.orders.infrastructure.module.User();
        sqlite_user=new hera.com.orders.infrastructure.sqlite.User();
        service_user=new hera.com.orders.infrastructure.service.User();


        this.id = (EditText) findViewById(R.id.ID);
        this.pass = (EditText) findViewById(R.id.pass);
        this.submit = (Button) findViewById(R.id.send);
        newurl=findViewById(R.id.editText2);
        newurl.setText(url);


        db=openOrCreateDatabase("order",MODE_PRIVATE, null);
        db.execSQL("create table if not exists url(url varchar(1000))");
        db.execSQL("create table if not exists login(flag integer)");
        Cursor c=db.rawQuery("select * from url",null);
        int flag=0;
        while(c.moveToNext()) {
            flag=1;
        }
        if(flag==0)
            db.execSQL("insert into url values('"+url+"')");
        Cursor c1=db.rawQuery("select * from login",null);
        int flag1=0;
        while(c1.moveToNext()) {
            flag1=1;
        }
        if(flag1==0) {
//            db.execSQL("insert into login values(1)");
        }
        else
        {
            Intent intent=new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                classes_user.Username=id.getText().toString();
                classes_user.Password=pass.getText().toString();
                service_user.login(getApplicationContext(),classes_user.Username,classes_user.Password);

                if(!url.equals(newurl.getText().toString()))
                {
                    url=newurl.getText().toString();
                    Toast.makeText(getApplicationContext(),"URL Changed",Toast.LENGTH_SHORT).show();
                }
                classes_user.Url=url;
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        Cursor c1=db.rawQuery("select * from login",null);
        int flag1=0;
        while(c1.moveToNext()) {
            flag1=1;
        }
        if(flag1==1)
        {
            Intent intent=new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
