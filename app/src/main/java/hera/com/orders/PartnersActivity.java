package hera.com.orders;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class PartnersActivity extends AppCompatActivity {

    public static hera.com.orders.infrastructure.service.Partner service_partner;
    public static String partner_url="http://192.168.111.15:8081/Euro99NarudzbeBack/resources/protected/partneri";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partners);

        service_partner=new hera.com.orders.infrastructure.service.Partner();
        service_partner.connect(this);
    }
}
