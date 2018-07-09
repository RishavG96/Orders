package hera.com.orders;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import hera.com.orders.adapters.OrderItemsAdapter;
import hera.com.orders.model.OrderItems;
import hera.com.orders.sqlite.Article;
import hera.com.orders.sqlite.Orders;

public class ArticleAmountActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;
    TextView tv1, tv2;
    EditText et1,et2;
    Button submit;
    ImageButton del;
    String page;
    hera.com.orders.sqlite.Article articleItems;
    hera.com.orders.sqlite.OrderItems orderItems;
    List<hera.com.orders.model.Article> articles;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_amount);

        tv1=findViewById(R.id.textView18);
        //tv2=findViewById(R.id.textView19);
        et1=findViewById(R.id.editText5);
        et2=findViewById(R.id.editText6);
        submit=findViewById(R.id.button4);
        del=findViewById(R.id.button5);
        articleItems = new hera.com.orders.sqlite.Article();
        orderItems=new hera.com.orders.sqlite.OrderItems();
        articles=new ArrayList<>();
        int id=getIntent().getIntExtra("articleId",0);
        page=getIntent().getStringExtra("page");
        articles=(List<hera.com.orders.model.Article>)articleItems.showArticle(this, id);
        tv1.setText(articles.get(0).name);
        et1.setHint(articles.get(0).units);
        et2.requestFocus();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        et1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!et2.isFocused()) {
                    double temp;
                    if(s.toString().isEmpty())
                        temp=0;
                    else
                        temp = Double.parseDouble(s.toString());
                    double pack;
                    if(articles.get(0).packing.length()!=0)
                        pack = Double.parseDouble(articles.get(0).packing);
                    else
                        pack=1;
                    double wei;
                    if(articles.get(0).weight.length()!=0)
                        wei = Integer.parseInt(articles.get(0).weight);
                    else
                        wei=1;
                    double res = temp / (pack * wei);
                    et2.setText(res + "");
                }
            }
        });
        et2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!et1.isFocused()) {
                    double temp;
                    if(s.toString().isEmpty())
                        temp=0;
                    else
                        temp = Double.parseDouble(s.toString());
                    double pack;
                    if(articles.get(0).packing.length()!=0)
                        pack = Double.parseDouble(articles.get(0).packing);
                    else
                        pack=1;
                    double wei;
                    if(articles.get(0).weight.length()!=0)
                        wei = Integer.parseInt(articles.get(0).weight);
                    else
                        wei=1;
                    double res = temp * (pack * wei);
                    et1.setText(res + "");
                }
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!et1.getText().toString().isEmpty()) {
                    String amount1 = et1.getText().toString();
                    String amount2 = et2.getText().toString();
                    double price = (Double.parseDouble(amount2) / Double.parseDouble(articles.get(0).packing)) * Double.parseDouble(articles.get(0).price);
                    String p = price + "";
                    if (amount1.equals("") || amount1.equals("0.0") || amount1.equals("0") ||
                            amount2.equals("") || amount2.equals("0.0") || amount2.equals("0")) {
                        Toast.makeText(getApplicationContext(), "Enter some value please", Toast.LENGTH_SHORT).show();
                    } else {
                        OrderItems orderItems2 = new OrderItems();
                        orderItems2.articleId = articles.get(0).id;
                        orderItems2.articleName = articles.get(0).name;
                        orderItems2.articleCode = articles.get(0).code;
                        orderItems2.articleUnits = articles.get(0).units;
                        orderItems2.articlePacking = articles.get(0).packing;
                        orderItems2.articleWeight = articles.get(0).weight;
                        orderItems2.quantity = amount1;
                        orderItems2.packaging = amount2;
                        orderItems2.price = p;
                        orderItems.addOrders(getApplicationContext(), orderItems2);
                        if(page.equals("OrderDetails")){
                            Intent intent = new Intent(getApplicationContext(), OrderDetailsActivity.class);
                            startActivity(intent);
                            setResult(1);
                            finish();
                        }
                        else {
                            Intent intent = new Intent(getApplicationContext(), CombinedActivity.class);
                            intent.putExtra("fragToLoad", 2);
                            startActivity(intent);
                            setResult(1);
                            finish();
                        }
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(), "Enter some value please", Toast.LENGTH_SHORT).show();
                }
            }
        });

        navigationView=findViewById(R.id.nav_view3);
        Toolbar toolbar=findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        drawerLayout=findViewById(R.id.drawer_layout3);
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
                                finish();
                                MainActivity.db.execSQL("delete from login");
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
