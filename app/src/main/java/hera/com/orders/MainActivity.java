package hera.com.orders;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.Message;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import hera.com.orders.adapters.OrdersAdapter;
import hera.com.orders.sqlite.Orders;


public class MainActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    public static int Id;
    NavigationView navigationView;
    ProgressDialog progressDialog;
    public static SQLiteDatabase db;
    hera.com.orders.service.Partner service_partner;
    hera.com.orders.sqlite.Partner sqlite_partner;
    hera.com.orders.service.Article service_article;
    hera.com.orders.service.Assortment service_assortment;
    hera.com.orders.sqlite.Orders orders;
    hera.com.orders.service.PartnerByWeek service_partner_week;
    Handler handle;
    SearchView searchView;
    ListView lv;
    Button newOrder;
    OrdersAdapter adapter;
    public static int pos;
    public static String notes="",dates="";
    public static int partnerID;
    public static String partnerName;
    public static int orderID=0;
    List<hera.com.orders.model.Orders> ordersList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv=findViewById(R.id.listview2);
        newOrder=findViewById(R.id.button3);
        service_partner = new hera.com.orders.service.Partner();
        sqlite_partner = new hera.com.orders.sqlite.Partner();
        service_article = new hera.com.orders.service.Article();
        service_assortment = new hera.com.orders.service.Assortment();
        orders = new hera.com.orders.sqlite.Orders();
        service_partner_week = new hera.com.orders.service.PartnerByWeek();
        orderID=0;
        //Toast.makeText(getApplicationContext(),"here",Toast.LENGTH_SHORT).show();
        db=openOrCreateDatabase("order",MODE_PRIVATE, null);
        try {
            db.execSQL("delete from orderitems");
        }
        catch (Exception e)
        {

        }
        if(LoginActivity.part==0 || LoginActivity.art==0 || LoginActivity.assort==0 || LoginActivity.part_week==0)
        {
            service_partner.connect(getApplicationContext());
            service_article.connect(getApplicationContext());
            //Toast.makeText(this, "art"+LoginActivity.art,Toast.LENGTH_SHORT).show();
            service_assortment.connect(getApplicationContext());
            service_partner_week.connect(getApplicationContext());
            //Toast.makeText(this, "ass"+LoginActivity.assort,Toast.LENGTH_SHORT).show();
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setMax(100);
            progressDialog.setMessage("Loading....");
            progressDialog.setTitle("Refreshing database");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.show();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        while (progressDialog.getProgress() <= progressDialog
                                .getMax()) {
                            Thread.sleep(60);
                            handle.sendMessage(handle.obtainMessage());
                            if (progressDialog.getProgress() == progressDialog
                                    .getMax()) {
                                progressDialog.dismiss();
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }

        Cursor c=db.rawQuery("select * from user1",null);
        while(c.moveToNext())
        {
            Id=c.getInt(0);
        }
        ordersList=new ArrayList<>();
        try {
            ordersList=(List<hera.com.orders.model.Orders>) orders.showOrders(this);
        }
        catch (Exception e){}
        adapter=new OrdersAdapter(this,ordersList);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ordersList=adapter.ordersList;
                pos=ordersList.get(position).orderId;
                MainActivity.orderID= ordersList.get(position).orderId;
                Intent intent=new Intent(getApplicationContext(), OrderDetailsActivity.class);
                startActivity(intent);
            }
        });
        navigationView=findViewById(R.id.nav_view);
        Toolbar toolbar=findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        drawerLayout=findViewById(R.id.drawer_layout);
        actionBarDrawerToggle= new ActionBarDrawerToggle(this,drawerLayout, toolbar, R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        handle = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                progressDialog.incrementProgressBy(1);
            }
        };
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.partner:
                                Intent intent = new Intent(getApplicationContext(), PartnersActivity.class);
                                startActivity(intent);
                                finish();
                                break;
                            case R.id.article:
                                Intent intent1=new Intent(getApplicationContext(), ArticleActivity.class);
                                startActivity(intent1);
                                finish();
                                break;
                            case R.id.orders:
                                Intent intent2=new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent2);
                                finish();
                                break;
                            case R.id.partnerweek:
                                Intent intent3=new Intent(getApplicationContext(), WeekDaysActivity.class);
                                startActivity(intent3);
                                finish();
                                break;
                            case R.id.refresh:
                                LoginActivity.assort=0;
                                LoginActivity.art=0;
                                LoginActivity.part=0;
                                LoginActivity.part_week=0;
                                service_partner.connect(getApplicationContext());
                                service_article.connect(getApplicationContext());
                                service_assortment.connect(getApplicationContext());
                                service_partner_week.connect(getApplicationContext());
                                progressDialog = new ProgressDialog(MainActivity.this);
                                progressDialog.setMax(100);
                                progressDialog.setMessage("Loading....");
                                progressDialog.setTitle("Refreshing database");
                                progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                                progressDialog.show();
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            while (progressDialog.getProgress() <= progressDialog
                                                    .getMax()) {
                                                Thread.sleep(60);
                                                handle.sendMessage(handle.obtainMessage());
                                                if (progressDialog.getProgress() == progressDialog
                                                        .getMax()) {
                                                    progressDialog.dismiss();
                                                }
                                            }
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }).start();
                                break;
                        }
                        drawerLayout.closeDrawers();  // CLOSE DRAWER
                        return true;
                    }
                });
        newOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int exit=5;
                Intent intent=new Intent(getApplicationContext(), OrderPartnersActivity.class);
                startActivityForResult(intent,exit);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.mainmenu,menu);
        SearchManager searchManager = (SearchManager)getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.app_bar_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.performClick();
        searchView.requestFocus();
        searchView.setIconifiedByDefault(false);
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.getFilter().filter(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return true;
            }
        });
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

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }

    @Override
    protected void onResume() {
        super.onResume();
        orderID=0;
        try {
            db.execSQL("delete from orderitems");
        }
        catch (Exception e){}
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        orderID=0;
        try {
            db.execSQL("delete from orderitems");
        }
        catch (Exception e){}
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==5)
            if(resultCode==6)
                finish();
    }
}
