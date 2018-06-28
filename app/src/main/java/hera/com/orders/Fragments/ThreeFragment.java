package hera.com.orders.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import hera.com.orders.MainActivity;
import hera.com.orders.R;
import hera.com.orders.adapters.OrderItemsAdapter;
import hera.com.orders.sqlite.OrderItems;


public class ThreeFragment extends Fragment {
    hera.com.orders.sqlite.OrderItems orderItems;
    hera.com.orders.sqlite.Orders orders;
    ListView lv;
    TextView total,partnerName;
    Button submit;
    OrderItemsAdapter adapter;
    List<hera.com.orders.model.OrderItems> orderItemsList;
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
        orderItems = new hera.com.orders.sqlite.OrderItems();
        orders = new hera.com.orders.sqlite.Orders();
        lv=view.findViewById(R.id.listview6);
        total=view.findViewById(R.id.totalprice);
        partnerName=view.findViewById(R.id.partnername);
        submit=view.findViewById(R.id.placeorder);
        orderItemsList=new ArrayList<>();
        partnerName.setText("Partner Name: "+MainActivity.partnerName);
        try {
            orderItemsList=(List<hera.com.orders.model.OrderItems>) orderItems.showOrders(getContext());
        }catch (Exception e){}
        total.setText("The Total price is: "+orderItems.calculateTotalPrice());
        if(orderItemsList!=null) {
            adapter = new OrderItemsAdapter(getContext(), orderItemsList);
            lv.setAdapter(adapter);
        }
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                    orderItemsList=adapter.orderItems;
                    //orderItems.showOrders(getContext());
                    orders.addToOrderDetails(getContext());
                    try {
                        MainActivity.db.execSQL("delete from orderitems");
                    }catch (Exception e){}
                    Intent intent = new Intent(getContext(), MainActivity.class);
                    startActivity(intent);
                    getActivity().setResult(3);
                    getActivity().setResult(6);
                    getActivity().finish();
            }
        });
        return view;
    }
}
