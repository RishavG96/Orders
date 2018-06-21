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



//        navigationView=findViewById(R.id.nav_view3);
//        Toolbar toolbar=findViewById(R.id.toolbar_main);
//        setSupportActionBar(toolbar);
//        drawerLayout=findViewById(R.id.drawer_layout3);
//        actionBarDrawerToggle= new ActionBarDrawerToggle(this,drawerLayout, toolbar, R.string.navigation_drawer_open,R.string.navigation_drawer_close);
//        drawerLayout.addDrawerListener(actionBarDrawerToggle);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setHomeButtonEnabled(true);
//        navigationView.setNavigationItemSelectedListener(
//                new NavigationView.OnNavigationItemSelectedListener() {
//                    @Override
//                    public boolean onNavigationItemSelected(MenuItem menuItem) {
//                        switch (menuItem.getItemId()) {
//                            case R.id.orders:
//                                Intent intent2=new Intent(getApplicationContext(), MainActivity.class);
//                                startActivity(intent2);
//                                finish();
//                                break;
//                            case R.id.partner:
//                                Intent intent = new Intent(getApplicationContext(), PartnersActivity.class);
//                                startActivity(intent);
//                                finish();
//                                break;
//                            case R.id.article:
//                                Intent intent1=new Intent(getApplicationContext(), ArticleActivity.class);
//                                startActivity(intent1);
//                                finish();
//                                break;
//                            case R.id.assortment:
//                                Intent intent3=new Intent(getApplicationContext(), AssortmentActivity.class);
//                                startActivity(intent3);
//                                finish();
//                                break;
//                        }
//                        drawerLayout.closeDrawers();  // CLOSE DRAWER
//                        return true;
//                    }
//                });
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
            switch (item.getItemId()) {
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
