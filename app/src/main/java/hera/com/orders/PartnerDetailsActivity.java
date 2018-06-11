package hera.com.orders;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import hera.com.orders.infrastructure.adapters.PartnerListAdapter;
import hera.com.orders.infrastructure.sqlite.Partner;

public class PartnerDetailsActivity extends AppCompatActivity {

    TextView id, code, name, address, city, amount, type, discount, status, businessHours, timeOfReceipt, responsiblePerson, forMobile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partner_details);
        id=findViewById(R.id.id);
        code=findViewById(R.id.code);
        name=findViewById(R.id.name);
        address=findViewById(R.id.address);
        city=findViewById(R.id.city);
        amount=findViewById(R.id.amount);
        type=findViewById(R.id.type);
        discount=findViewById(R.id.discount);
        status=findViewById(R.id.status);
        businessHours=findViewById(R.id.busniessHours);
        timeOfReceipt=findViewById(R.id.timeOfReceipt);
        responsiblePerson=findViewById(R.id.responsiblePerson);
        forMobile=findViewById(R.id.forMobile);

        id.setText(Partner.id.get(PartnerListAdapter.pos).toString());
        code.setText(Partner.code.get(PartnerListAdapter.pos).toString());
        name.setText(Partner.name.get(PartnerListAdapter.pos).toString());
        address.setText(Partner.address.get(PartnerListAdapter.pos).toString());
        city.setText(Partner.city.get(PartnerListAdapter.pos).toString());
        amount.setText(Partner.amount.get(PartnerListAdapter.pos).toString());
        type.setText(Partner.type.get(PartnerListAdapter.pos).toString());
        discount.setText(Partner.discount.get(PartnerListAdapter.pos).toString());
        status.setText(Partner.status.get(PartnerListAdapter.pos).toString());
        businessHours.setText(Partner.businessHours.get(PartnerListAdapter.pos).toString());
        timeOfReceipt.setText(Partner.timeOfReceipt.get(PartnerListAdapter.pos).toString());
        responsiblePerson.setText(Partner.responsiblePerson.get(PartnerListAdapter.pos).toString());
        forMobile.setText(Partner.forMobile.get(PartnerListAdapter.pos).toString());
    }
}
