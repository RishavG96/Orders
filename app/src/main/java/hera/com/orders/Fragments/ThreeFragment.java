package hera.com.orders.Fragments;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import hera.com.orders.ArticleAmountActivity;
import hera.com.orders.CombinedActivity;
import hera.com.orders.MainActivity;
import hera.com.orders.R;
import hera.com.orders.adapters.OrderItemsAdapter;
import hera.com.orders.sqlite.OrderItems;
import hera.com.orders.sqlite.Orders;


public class ThreeFragment extends Fragment {
    public  static hera.com.orders.sqlite.OrderItems orderItems;
    hera.com.orders.sqlite.Orders orders;
    ListView lv;
    TextView total,partnerName, tv, tv1, tv2;
    ImageView iv;
    Button submit, redirect;
    OrderItemsAdapter adapter;
    List<hera.com.orders.model.OrderItems> orderItemsList;
    public static int pos;
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
        orders = new hera.com.orders.sqlite.Orders();
        lv=view.findViewById(R.id.listview6);
        tv=view.findViewById(R.id.textView40);
        tv1=view.findViewById(R.id.textView41);
        tv2=view.findViewById(R.id.textView42);
        iv=view.findViewById(R.id.imageView2);
        total=view.findViewById(R.id.totalprice);
        partnerName=view.findViewById(R.id.partnername);
        submit=view.findViewById(R.id.placeorder);
        redirect=view.findViewById(R.id.redirect);
        orderItemsList=new ArrayList<>();
        partnerName.setText(""+MainActivity.partnerName);
        try {
            orderItemsList=(List<hera.com.orders.model.OrderItems>) orderItems.showOrders(getContext());
        }catch (Exception e){}
        double d= orderItems.calculateTotalPrice();
        String str = String.format("%1.2f", d);
        if(str.contains(","))
            str=str.replace(",",".");
        d = Double.valueOf(str);
        total.setText(d+" KM");
        if(orderItemsList!=null) {
            adapter = new OrderItemsAdapter(getContext(), orderItemsList);
            lv.setAdapter(adapter);
        }
        if(orderItems.calculateTotalPrice()==0.0)
        {
            tv.setVisibility(View.GONE);
            iv.setVisibility(View.VISIBLE);
            tv1.setVisibility(View.VISIBLE);
            tv2.setVisibility(View.GONE);
            total.setVisibility(View.GONE);
            lv.setVisibility(View.GONE);
            submit.setVisibility(View.GONE);
            redirect.setVisibility(View.VISIBLE);
        }
        else{
            submit.setVisibility(View.VISIBLE);
            redirect.setVisibility(View.GONE);
        }
        redirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), CombinedActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                orderItemsList=adapter.orderItems;
                pos=orderItemsList.get(position).articleId;
                Cursor c= MainActivity.db.rawQuery("select * from orders2 where orderId="+MainActivity.orderID+"", null);
                String sended="";
                while(c.moveToNext())
                {
                    sended=c.getString(4);
                }
                if(sended.equals("N")) {
                    Intent intent = new Intent(getContext(), ArticleAmountActivity.class);
                    intent.putExtra("articleId", pos);
                    intent.putExtra("page", "fragthree");
                    startActivity(intent);
                }
                else if(sended.equals("Y"))
                {
                    Toast.makeText(getContext(),"Cannot edit, order already Sended!",Toast.LENGTH_SHORT).show();
                }
            }
        });
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
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    getActivity().setResult(3);
                    getActivity().setResult(6);
                    getActivity().finish();
            }
        });
        return view;
    }
}
