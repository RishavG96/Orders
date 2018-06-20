package hera.com.orders;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import hera.com.orders.infrastructure.sqlite.Orders;

public class OrderDetailsActivity extends AppCompatActivity {

    ListView lv;
    TextView partnerName,total;
    OrderItemsAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        lv=findViewById(R.id.listview7);
        partnerName=findViewById(R.id.textView31);
        total=findViewById(R.id.textView32);
        int orderId=Integer.parseInt(Orders.orderId.get(MainActivity.pos).toString());
        String pn=Orders.partnerName.get(MainActivity.pos).toString();
        total.setText("Total Order Price: "+Orders.calculateTotalPrice(orderId));
        partnerName.setText("Partner Name: "+ pn);
        Orders.showOrderItems(this, orderId);
        adapter=new OrderItemsAdapter(this, Orders.articleId, Orders.articleName,Orders.articleCode,
                Orders.articleUnits,Orders.quantity,Orders.packaging,Orders.price);
        lv.setAdapter(adapter);
    }
}
