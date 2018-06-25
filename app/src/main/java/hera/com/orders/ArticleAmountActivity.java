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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import hera.com.orders.model.OrderItems;

public class ArticleAmountActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;
    TextView tv1, tv2;
    EditText et1,et2;
    Button submit;
    hera.com.orders.sqlite.OrderItems orderItems;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_amount);

        tv1=findViewById(R.id.textView18);
        tv2=findViewById(R.id.textView19);
        et1=findViewById(R.id.editText5);
        et2=findViewById(R.id.editText6);
        submit=findViewById(R.id.button4);
        tv1.setText(CombinedActivity.articleName);
        tv2.setText(CombinedActivity.articleUnits);
        orderItems = new hera.com.orders.sqlite.OrderItems();
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
                    if(CombinedActivity.articlePacking.length()!=0)
                        pack = Double.parseDouble(CombinedActivity.articlePacking);
                    else
                        pack=1;
                    double wei;
                    if(CombinedActivity.articleWeight.length()!=0)
                        wei = Integer.parseInt(CombinedActivity.articleWeight);
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
                    if(CombinedActivity.articlePacking.length()!=0)
                        pack = Double.parseDouble(CombinedActivity.articlePacking);
                    else
                        pack=1;
                    double wei;
                    if(CombinedActivity.articleWeight.length()!=0)
                        wei = Integer.parseInt(CombinedActivity.articleWeight);
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
                String amount1=et1.getText().toString();
                String amount2=et2.getText().toString();
                double price=(Double.parseDouble(amount2)/Double.parseDouble(CombinedActivity.articlePacking))*Double.parseDouble(CombinedActivity.articlePrice);
                String p=price+"";
                if(amount1.equals("") || amount1.equals("0.0") || amount1.equals("0") ||
                        amount2.equals("") || amount2.equals("0.0") || amount2.equals("0"))
                {
                    Toast.makeText(getApplicationContext(), "Enter some value please", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    OrderItems orderItems2=new OrderItems();
                    orderItems2.articleId=CombinedActivity.articleId;
                    orderItems2.articleName= CombinedActivity.articleName;
                    orderItems2.articleCode=CombinedActivity.articleCode;
                    orderItems2.articleUnits=CombinedActivity.articleUnits;
                    orderItems2.articlePacking= CombinedActivity.articlePacking;
                    orderItems2.articleWeight= CombinedActivity.articleWeight;
                    orderItems2.quantity=amount1;
                    orderItems2.packaging=amount2;
                    orderItems2.price=p;
                    orderItems.addOrders(getApplicationContext(),orderItems2);
                    Intent intent = new Intent(getApplicationContext(), CombinedActivity.class);
                    intent.putExtra("fragToLoad", 2);
                    startActivity(intent);
                    setResult(1);
                    finish();
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
