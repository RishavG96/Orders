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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;

import hera.com.orders.adapters.DetailsAdapter;
import hera.com.orders.adapters.PartnerListAdapter;
import hera.com.orders.sqlite.Partner;

public class PartnerDetailsActivity extends AppCompatActivity {

    ListView lv;
    DetailsAdapter adapter;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partner_details);

        lv=findViewById(R.id.listview9);

        String id = Partner.id.get(PartnerListAdapter.pos).toString();
        String code = Partner.code.get(PartnerListAdapter.pos).toString();
        String name = Partner.name.get(PartnerListAdapter.pos).toString();
        String address = Partner.address.get(PartnerListAdapter.pos).toString();
        String city = Partner.city.get(PartnerListAdapter.pos).toString();
        String amount = Partner.amount.get(PartnerListAdapter.pos).toString();
        String type = Partner.type.get(PartnerListAdapter.pos).toString();
        String discount = Partner.discount.get(PartnerListAdapter.pos).toString();
        String status = Partner.status.get(PartnerListAdapter.pos).toString();
        String businessHOurs = Partner.businessHours.get(PartnerListAdapter.pos).toString();
        String timeOfReceipt = Partner.timeOfReceipt.get(PartnerListAdapter.pos).toString();
        String responsiblePerson = Partner.responsiblePerson.get(PartnerListAdapter.pos).toString();
        String forMobile = Partner.forMobile.get(PartnerListAdapter.pos).toString();
        ArrayList values=new ArrayList();
        ArrayList heading=new ArrayList();
        heading.add("Partner ID");
        heading.add("Partner Code");
        heading.add("Partner Name");
        heading.add("Partner Address");
        heading.add("Partner City");
        heading.add("Partner Amount");
        heading.add("Partner Type");
        heading.add("Discount");
        heading.add("Partner Status");
        heading.add("Business Hours");
        heading.add("Time Of Recepit");
        heading.add("Responsible Person");
        heading.add("For Mobile");
        values.add(id);
        values.add(code);
        values.add(name);
        values.add(address);
        values.add(city);
        values.add(amount);
        values.add(type);
        values.add(discount);
        values.add(status);
        values.add(businessHOurs);
        values.add(timeOfReceipt);
        values.add(responsiblePerson);
        values.add(forMobile);

        adapter=new DetailsAdapter(this, heading, values);
        lv.setAdapter(adapter);

        navigationView=findViewById(R.id.nav_view8);
        Toolbar toolbar=findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        drawerLayout=findViewById(R.id.drawer_layout8);
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
