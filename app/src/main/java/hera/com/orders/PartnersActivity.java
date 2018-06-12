package hera.com.orders;

import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ViewStub;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;

import hera.com.orders.infrastructure.adapters.PartnerListAdapter;
import hera.com.orders.infrastructure.sqlite.Partner;

public class PartnersActivity extends AppCompatActivity {

    public static hera.com.orders.infrastructure.service.Partner service_partner;
    hera.com.orders.infrastructure.sqlite.Partner sqlite_partner;
    public static String partner_url="http://192.168.111.15:8081/Euro99NarudzbeBack/resources/protected/partneri";
    ListView lv;
    EditText searchEditText;
    PartnerListAdapter adapter;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partners);

        lv=findViewById(R.id.listview);
        searchEditText=findViewById(R.id.editText5);
        service_partner=new hera.com.orders.infrastructure.service.Partner();
        sqlite_partner=new hera.com.orders.infrastructure.sqlite.Partner();
        service_partner.connect(this);
        sqlite_partner.showPartner(this);
        adapter=new PartnerListAdapter(this, sqlite_partner.name, sqlite_partner.code,
                sqlite_partner.amount, sqlite_partner.address, sqlite_partner.city);
        lv.setAdapter(adapter);
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String text=searchEditText.getText().toString();
                adapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}
