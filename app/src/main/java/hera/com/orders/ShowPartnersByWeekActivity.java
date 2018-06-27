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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import hera.com.orders.adapters.PartnerWeekListAdapter;
import hera.com.orders.model.Partner;
import hera.com.orders.model.PartnerByWeek;
import hera.com.orders.sqlite.Orders;

public class ShowPartnersByWeekActivity extends AppCompatActivity {

    PartnerWeekListAdapter adapter;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;
    ListView lv;
    List<Partner> partnerList;
    public static String partner_week="http://192.168.111.15:8081/Euro99NarudzbeBack/resources/protected/planovi";
    hera.com.orders.service.PartnerByWeek service_partner_week;
    hera.com.orders.sqlite.PartnerByWeek sqlite_partner_week;
    SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_partners_by_week);
        lv=findViewById(R.id.listview12);
        service_partner_week=new hera.com.orders.service.PartnerByWeek();
        sqlite_partner_week=new hera.com.orders.sqlite.PartnerByWeek();
        service_partner_week.connect(this);
        List<PartnerByWeek> partnerByWeekList=new ArrayList<>();
        partnerList=new ArrayList<>();
        partnerByWeekList=(List<PartnerByWeek>) sqlite_partner_week.showPartner(this);
        partnerList=(List<Partner>) sqlite_partner_week.showPartner(this, WeekDaysActivity.pos);
        adapter=new PartnerWeekListAdapter(this, partnerList);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                partnerList=adapter.partners;
                MainActivity.partnerID=Integer.parseInt(partnerList.get(position).id.toString());
                MainActivity.partnerName=partnerList.get(position).name;
                int exit=2;
                Intent intent=new Intent(getApplicationContext(), OrderEntryActivity.class);
                startActivityForResult(intent,exit);
            }
        });

        navigationView=findViewById(R.id.nav_view12);
        Toolbar toolbar=findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        drawerLayout=findViewById(R.id.drawer_layout12);
        actionBarDrawerToggle= new ActionBarDrawerToggle(this,drawerLayout, toolbar, R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
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
                                Intent intent4 = new Intent(getApplicationContext(), MainActivity.class);
                                intent4.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent4);
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
    @Override
    protected void onResume() {
        super.onResume();
        try {
            MainActivity.db.execSQL("delete from orderitems");
        }catch (Exception e){}
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        try {
            MainActivity.db.execSQL("delete from orderitems");
        }catch (Exception e){}
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==2)
            if(resultCode==3)
                finish();
    }
    @Override
    protected void onStop() {
        setResult(6);
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        setResult(6);
        super.onDestroy();
    }
}
