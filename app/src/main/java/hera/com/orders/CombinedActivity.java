package hera.com.orders;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import hera.com.orders.Fragments.OneFragment;
import hera.com.orders.Fragments.ThreeFragment;
import hera.com.orders.Fragments.TwoFragment;
import hera.com.orders.model.Orders;


public class CombinedActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;
    hera.com.orders.sqlite.Orders orders;
    SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_combined);
        orders = new hera.com.orders.sqlite.Orders();
        if(MainActivity.orderID==0) {
            Orders orders2=new Orders();
            orders2.partnerId=MainActivity.partnerID;
            orders2.partnerName=MainActivity.partnerName;
            orders2.dates=MainActivity.dates;
            orders2.note=MainActivity.notes;
            orders.addOrder(this, orders2);
        }
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //MainActivity.db.execSQL("drop table if exists orderitems");
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        if(getIntent().hasExtra("fragToLoad"))
        {
            int frag=getIntent().getIntExtra("fragToLoad",0);
            if(frag==2)
            {
                viewPager.setCurrentItem(2,true);
            }
        }
        //viewPager.setCurrentItem(0,true);
        navigationView=findViewById(R.id.nav_view5);
        Toolbar toolbar=findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        drawerLayout=findViewById(R.id.drawer_layout5);
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
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new OneFragment(), "Article");
        adapter.addFragment(new TwoFragment(), "Assortment");
        adapter.addFragment(new ThreeFragment(), "Cart");
        viewPager.setAdapter(adapter);
    }
    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
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
        else {
            hera.com.orders.sqlite.Orders orders=new hera.com.orders.sqlite.Orders();
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
    protected void onStop() {
        setResult(1);
        setResult(3);
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        setResult(1);
        setResult(3);
        super.onDestroy();
    }
}
