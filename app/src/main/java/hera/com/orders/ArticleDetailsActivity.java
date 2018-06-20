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

import hera.com.orders.infrastructure.adapters.DetailsAdapter;
import hera.com.orders.infrastructure.adapters.ArticleListAdapter;
import hera.com.orders.infrastructure.sqlite.Article;

public class ArticleDetailsActivity extends AppCompatActivity {

    ListView lv;
    DetailsAdapter adapter;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_details);

        lv=findViewById(R.id.listview8);

        String id= Article.id.get(ArticleListAdapter.pos);
        String code=Article.code.get(ArticleListAdapter.pos);
        String name=Article.name.get(ArticleListAdapter.pos);
        String shortName=Article.shortName.get(ArticleListAdapter.pos);
        String units=Article.units.get(ArticleListAdapter.pos);
        String packing=Article.packing.get(ArticleListAdapter.pos);
        String brutto=Article.brutto.get(ArticleListAdapter.pos);
        String netto=Article.netto.get(ArticleListAdapter.pos);
        String weight=Article.weight.get(ArticleListAdapter.pos);
        String price=Article.price.get(ArticleListAdapter.pos);
        ArrayList values=new ArrayList();
        ArrayList heading=new ArrayList();
        heading.add("Article ID");
        heading.add("Article Code");
        heading.add("Article Name");
        heading.add("Article Short Name");
        heading.add("Article Units");
        heading.add("Article Packing");
        heading.add("Article Brutto");
        heading.add("Article Netto");
        heading.add("Article Weight");
        heading.add("Article Price");
        values.add(id);
        values.add(code);
        values.add(name);
        values.add(shortName);
        values.add(units);
        values.add(packing);
        values.add(brutto);
        values.add(netto);
        values.add(weight);
        values.add(price);

        adapter=new DetailsAdapter(this, heading, values);
        lv.setAdapter(adapter);

        navigationView=findViewById(R.id.nav_view7);
        Toolbar toolbar=findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        drawerLayout=findViewById(R.id.drawer_layout7);
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
