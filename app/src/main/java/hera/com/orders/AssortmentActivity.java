package hera.com.orders;

import android.content.Intent;
import android.content.res.Configuration;
import android.database.sqlite.SQLiteDatabase;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import hera.com.orders.sqlite.Orders;

public class AssortmentActivity extends AppCompatActivity {

    public static String assortment_url="http://192.168.111.15:8081/Euro99NarudzbeBack/resources/protected/asortiman";
    hera.com.orders.service.Assortment service_assortment;
    hera.com.orders.sqlite.Assortment sqlite_assortment;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;
    public static SQLiteDatabase db;
    public AssortmentActivity()
    {
        db=openOrCreateDatabase("order",MODE_PRIVATE, null);
        service_assortment = new hera.com.orders.service.Assortment();
        sqlite_assortment = new hera.com.orders.sqlite.Assortment();
        service_assortment.connect(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assortment);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.mainmenu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        else {
            Orders orders=new Orders();
            switch (item.getItemId()) {
                case R.id.sendAll:
                    boolean flag=orders.sendAllToServer();
                    if(flag==true) {
                        Toast.makeText(getApplicationContext(), "Order Sent!", Toast.LENGTH_SHORT).show();
                        Intent intent2 = new Intent(getApplicationContext(), MainActivity.class);
                        intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent2);
                        finish();
                    }
                    else
                        Toast.makeText(getApplicationContext(),"Enter Items First!",Toast.LENGTH_SHORT).show();
                    break;
                case R.id.deleteAll:
                    orders.deleteAll();
                    Toast.makeText(getApplicationContext(), "Orders Deleted!", Toast.LENGTH_SHORT).show();
                    Intent intent3 = new Intent(getApplicationContext(), MainActivity.class);
                    intent3.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent3);
                    finish();
                    break;
                case R.id.deleteSend:
                    orders.deleteSend();
                    Toast.makeText(getApplicationContext(), "Orders Deleted!", Toast.LENGTH_SHORT).show();
                    Intent intent4 = new Intent(getApplicationContext(), MainActivity.class);
                    intent4.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent4);
                    finish();
                    break;
                case R.id.deleteUnsend:
                    orders.deleteUnsend();
                    Toast.makeText(getApplicationContext(), "Orders Deleted!", Toast.LENGTH_SHORT).show();
                    Intent intent5 = new Intent(getApplicationContext(), MainActivity.class);
                    intent5.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent5);
                    finish();
                    break;
                case R.id.setup:
                    Intent intent = new Intent(this, UpdateURLActivity.class);
                    startActivity(intent);
                    break;
                case R.id.logout:
                    Intent intent1 = new Intent(this, LoginActivity.class);
                    startActivity(intent1);
                    finish();
                    db.execSQL("delete from login");
                    break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPostCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        actionBarDrawerToggle.onConfigurationChanged(newConfig);
    }
}
