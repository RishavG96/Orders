package hera.com.orders;

import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.swipe.util.Attributes;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import hera.com.orders.adapters.OrderItemsAdapter;
import hera.com.orders.model.OrderItems;
import hera.com.orders.sqlite.Orders;

public class OrderDetailsActivity extends AppCompatActivity {

    ListView lv;
    TextView tv,tv1,tv2;
    ImageView imageView;
    Button editOrder, submitOrder, addItem;
    TextView partnerName,total;
    OrderItemsAdapter adapter;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;
    hera.com.orders.service.Orders service_orders;
    private SendOrdertask sendOrdertask=null;
    Orders orders;
    List<OrderItems> orderItemsList;
    public static int pos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        lv=findViewById(R.id.listview7);
        tv=findViewById(R.id.textView35);
        tv1=findViewById(R.id.textView39);
        tv2=findViewById(R.id.textView38);
        imageView=findViewById(R.id.imageView1);
        partnerName=findViewById(R.id.textView31);
        partnerName.setMovementMethod(new ScrollingMovementMethod());
        total=findViewById(R.id.textView32);
        editOrder=findViewById(R.id.editorder);
        submitOrder=findViewById(R.id.submitorder);
        addItem=findViewById(R.id.additem);
        service_orders=new hera.com.orders.service.Orders();
        orders=new Orders();
        List<hera.com.orders.model.Orders> ordersList=new ArrayList<>();
        MainActivity.orderID=MainActivity.pos;
        String pn=orders.getPartnerName(this, MainActivity.orderID);
        orderItemsList=(List<OrderItems>) orders.showOrderItems(this, MainActivity.orderID);
        adapter=new OrderItemsAdapter(this, orderItemsList);
        lv.setAdapter(adapter);
        adapter.setMode(Attributes.Mode.Single);
        total.setText(""+Orders.calculateTotalPrice(MainActivity.orderID)+" KM");
        partnerName.setText(pn);
        if(Orders.calculateTotalPrice(MainActivity.orderID)==0)
        {
            tv.setVisibility(View.VISIBLE);
            imageView.setVisibility(View.VISIBLE);
            addItem.setVisibility(View.VISIBLE);
            lv.setVisibility(View.GONE);
            editOrder.setVisibility(View.GONE);
            submitOrder.setVisibility(View.GONE);
            total.setVisibility(View.GONE);
            tv1.setVisibility(View.GONE);
            tv2.setVisibility(View.GONE);
            tv.setText("No Order Items added.");
        }

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                orderItemsList=adapter.orderItems;
                pos=orderItemsList.get(position).articleId;
                Cursor c= MainActivity.db.rawQuery("select * from orders2 where orderId="+MainActivity.orderID+"", null);
                String sended="";
                while(c.moveToNext())
                {
                    sended=c.getString(4);
                }
                if(sended.equals("N")) {
                    Orders.pushOrderItems(getApplicationContext(), MainActivity.orderID);
                    MainActivity.partnerName = Orders.getPartnerName(getApplicationContext(), MainActivity.orderID);
                    Intent intent = new Intent(getApplicationContext(), CombinedActivity.class);
                    intent.putExtra("fragToLoad", 2);
                    startActivity(intent);
                }
                else if(sended.equals("Y"))
                {
                    Toast.makeText(getApplicationContext(),"Cannot edit, order already Sended!",Toast.LENGTH_SHORT).show();
                }
            }
        });

        editOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor c=MainActivity.db.rawQuery("select * from orders2 where orderId="+MainActivity.orderID+"", null);
                String sended="";
                while(c.moveToNext())
                {
                    sended=c.getString(4);
                }
                if(sended.equals("N")) {
                    Orders.pushOrderItems(getApplicationContext(), MainActivity.orderID);
                    MainActivity.partnerName = Orders.getPartnerName(getApplicationContext(), MainActivity.orderID);
                    Intent intent = new Intent(getApplicationContext(), CombinedActivity.class);
                    startActivity(intent);
                    finish();
                }
                else if(sended.equals("Y"))
                {
                    Toast.makeText(getApplicationContext(),"Cannot edit, order already Sended!",Toast.LENGTH_SHORT).show();
                }
            }
        });
        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor c=MainActivity.db.rawQuery("select * from orders2 where orderId="+MainActivity.orderID+"", null);
                String sended="";
                while(c.moveToNext())
                {
                    sended=c.getString(4);
                }
                if(sended.equals("N")) {
                    Orders.pushOrderItems(getApplicationContext(), MainActivity.orderID);
                    MainActivity.partnerName = Orders.getPartnerName(getApplicationContext(), MainActivity.orderID);
                    Intent intent = new Intent(getApplicationContext(), CombinedActivity.class);
                    startActivity(intent);
                    finish();
                }
                else if(sended.equals("Y"))
                {
                    Toast.makeText(getApplicationContext(),"Cannot edit, order already Sended!",Toast.LENGTH_SHORT).show();
                }
            }
        });
        submitOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor c1=MainActivity.db.rawQuery("select * from orderdetails1 where orderId="+MainActivity.orderID+"", null);
                if(c1.moveToNext()) {
                    Cursor c = MainActivity.db.rawQuery("select * from orders2 where orderId=" + MainActivity.orderID + "", null);
                    String sended = "";
                    while (c.moveToNext()) {
                        sended = c.getString(4);
                    }
                    if (sended.equals("N")) {
                        sendOrdertask = new SendOrdertask();
                        sendOrdertask.execute((Void) null);
                        Toast.makeText(getApplicationContext(), "Order Send!", Toast.LENGTH_SHORT).show();
                    } else if (sended.equals("Y")) {
                        Toast.makeText(getApplicationContext(), "Order already Sended!", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(getApplicationContext(),"Enter Articles first!",Toast.LENGTH_SHORT).show();
                }
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
        View headerView = navigationView.getHeaderView(0);
        TextView username=headerView.findViewById(R.id.nav_header_textView1);
        username.setText(MainActivity.user);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.orders:
                                Intent intent2=new Intent(getApplicationContext(), MainActivity.class);
                                intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
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
                                Intent intent4 = new Intent(getApplicationContext(), MainActivity.class);
                                intent4.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent4);
                                finish();
                                break;
                            case R.id.setup:
                                Intent intent5 = new Intent(getApplicationContext(), UpdateURLActivity.class);
                                startActivity(intent5);
                                break;
                            case R.id.logout:
                                Intent intent6 = new Intent(getApplicationContext(), LoginActivity.class);
                                startActivity(intent6);
                                MainActivity.db.execSQL("drop table login");
                                MainActivity.db.execSQL("drop table user1");
                                MainActivity.db.execSQL("drop table url");
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
        inflater.inflate(R.menu.combined_activitymenu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
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

