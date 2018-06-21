package hera.com.orders;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import hera.com.orders.infrastructure.adapters.OrderItemsAdapter;
import hera.com.orders.infrastructure.adapters.OrdersAdapter;
import hera.com.orders.infrastructure.sqlite.Orders;

public class OrderDetailsActivity extends AppCompatActivity {

    ListView lv;
    TextView tv;
    Button editOrder, submitOrder;
    TextView partnerName,total;
    OrderItemsAdapter adapter;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        lv=findViewById(R.id.listview7);
        tv=findViewById(R.id.textView35);
        partnerName=findViewById(R.id.textView31);
        partnerName.setMovementMethod(new ScrollingMovementMethod());
        total=findViewById(R.id.textView32);
        editOrder=findViewById(R.id.editorder);
        submitOrder=findViewById(R.id.submitorder);
        MainActivity.orderID=Integer.parseInt(Orders.orderId.get(MainActivity.pos).toString());
        String pn=Orders.partnerName.get(MainActivity.pos).toString();
        total.setText("Total Order Price: "+Orders.calculateTotalPrice(MainActivity.orderID));
        partnerName.setText("Partner Name: "+ pn);
        Orders.showOrderItems(this, MainActivity.orderID);
        adapter=new OrderItemsAdapter(this, Orders.articleId, Orders.articleName,Orders.articleCode,
                Orders.articleUnits,Orders.quantity,Orders.packaging,Orders.price);
        lv.setAdapter(adapter);
        if(Orders.calculateTotalPrice(MainActivity.orderID)==0)
        {
            tv.setText("No Order Items added.");
        }

        editOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Orders.pushOrderItems(getApplicationContext(), MainActivity.orderID);
                MainActivity.partnerName=Orders.getPartnerName(getApplicationContext(), MainActivity.orderID);
                Intent intent=new Intent(getApplicationContext(), CombinedActivity.class);
                startActivity(intent);
                finish();
            }
        });

        navigationView=findViewById(R.id.nav_view10);
        Toolbar toolbar=findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        drawerLayout=findViewById(R.id.drawer_layout10);
        actionBarDrawerToggle= new ActionBarDrawerToggle(this,drawerLayout, toolbar, R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.orders:
                                Intent intent2=new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent2);
                                finish();
                                break;
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
                        }
                        drawerLayout.closeDrawers();  // CLOSE DRAWER
                        return true;
                    }
                });
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
                    MainActivity.db.execSQL("delete from login");
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
