package hera.com.orders.infrastructure.Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import hera.com.orders.MainActivity;
import hera.com.orders.R;
import hera.com.orders.infrastructure.adapters.AssortmentListAdapter;
import hera.com.orders.infrastructure.adapters.OrderItemsAdapter;


public class ThreeFragment extends Fragment {
    hera.com.orders.infrastructure.sqlite.OrderItems orderItems;
    ListView lv;
    TextView total;
    Button submit;
    OrderItemsAdapter adapter;
    public ThreeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_three, container, false);
        orderItems = new hera.com.orders.infrastructure.sqlite.OrderItems();
        lv=view.findViewById(R.id.listview6);
        total=view.findViewById(R.id.totalprice);
        submit=view.findViewById(R.id.placeorder);
        orderItems.showOrders(getContext());
        adapter=new OrderItemsAdapter(getContext(), orderItems.articleId, orderItems.articleName,orderItems.articleCode,
                orderItems.articleUnits,orderItems.units,orderItems.packaging,orderItems.price);
        lv.setAdapter(adapter);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.db.execSQL("delete from orderitems");
                Intent intent=new Intent(getContext(), MainActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }
}
