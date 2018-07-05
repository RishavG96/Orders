package hera.com.orders.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.swipe.SimpleSwipeListener;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.BaseSwipeAdapter;

import java.util.ArrayList;
import java.util.List;

import hera.com.orders.MainActivity;
import hera.com.orders.OrderDetailsActivity;
import hera.com.orders.R;
import hera.com.orders.sqlite.Orders;

public class OrdersAdapter extends BaseSwipeAdapter implements Filterable {
    LayoutInflater inflater;
    Context context;
    ArrayList orderId, original_orderId, partnerName, original_partnerName, dates, original_dates, sended, original_sended;
    ArrayList FilteredArrList1;
    ArrayList FilteredArrList2;
    ArrayList FilteredArrList3;
    Orders orders;
    public List<hera.com.orders.model.Orders> ordersList;
    public OrdersAdapter(Context context, List<hera.com.orders.model.Orders> ordersList)
    {
        this.context=context;
        orders=new Orders();
        this.ordersList=ordersList;

        orderId=new ArrayList();
        original_orderId=new ArrayList();
        partnerName=new ArrayList();
        original_partnerName=new ArrayList();
        dates=new ArrayList();
        original_dates=new ArrayList();
        sended=new ArrayList();
        original_sended=new ArrayList();
        for(hera.com.orders.model.Orders orders: ordersList)
        {
            orderId.add(orders.orderId);
            original_orderId.add(orders.orderId);
            partnerName.add(orders.partner.name);
            original_partnerName.add(orders.partner.name);
            dates.add(orders.dates);
            original_dates.add(orders.dates);
            sended.add(orders.sended);
            original_sended.add(orders.sended);
        }

        inflater=LayoutInflater.from(context);
    }
    public void remove(int position) {
        ordersList.remove(position);
        notifyDataSetChanged();
    }
    @Override
    public int getViewTypeCount() {
        return 1;
    }
    @Override
    public int getItemViewType(int position) {

        return position;
    }
    @Override
    public int getCount() {
        return ordersList.size();
    }

    @Override
    public hera.com.orders.model.Orders getItem(int position) {
        return ordersList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }

    @Override
    public View generateView(final int position, final ViewGroup parent) {
        View v =inflater.inflate(R.layout.orders_layout,null);
        SwipeLayout swipeLayout = (SwipeLayout)v.findViewById(getSwipeLayoutResourceId(position));
        swipeLayout.addSwipeListener(new SimpleSwipeListener() {
            @Override
            public void onOpen(SwipeLayout layout) {
                //YoYo.with(Techniques.Tada).duration(500).delay(100).playOn(layout.findViewById(R.id.trash));
            }
        });
        v.findViewById(R.id.delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.pos=getItem(position).orderId;
                MainActivity.orderID= ordersList.get(position).orderId;
                orders.deleteOrder(MainActivity.orderID);
                Intent intent1=new Intent(context, MainActivity.class);
                context.startActivity(intent1);
                ((Activity)context).finish();
                //Toast.makeText(context, "click delete"+position, Toast.LENGTH_SHORT).show();
            }
        });
        return v;
    }

    @Override
    public void fillValues(final int position, View convertView) {
        TextView n=(TextView)convertView.findViewById(R.id.textView26);
        TextView s=(TextView)convertView.findViewById(R.id.textView29);
        TextView un=(TextView)convertView.findViewById(R.id.textView30);
        TextView co=(TextView)convertView.findViewById(R.id.textView27);
        TextView q=(TextView)convertView.findViewById(R.id.textView28);
        ImageView iv=convertView.findViewById(R.id.sendedicon);
        n.setText(getItem(position).partner.name);
        String sended=getItem(position).sended;
        un.setText(sended);
        if(sended.equals("Y")) {
            iv.setImageResource(R.drawable.icon);
        }
//        q.setText("Quantity: "+quantity.get(position).toString()+"  "+unit.get(position).toString() );
        String d=getItem(position).dates.substring(0,10);
        q.setText(""+d);
        co.setText("Date: ");
    }


    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint,FilterResults results) {
                orderId = (ArrayList)results.values; // has the filtered values
                partnerName=FilteredArrList1;
                dates=FilteredArrList2;
                sended=FilteredArrList3;
                List<hera.com.orders.model.Orders> ordersList1=new ArrayList<>();
                for(int i=0;i<orderId.size();i++)
                {
                    hera.com.orders.model.Orders orders=new hera.com.orders.model.Orders();
                    orders.orderId=Integer.parseInt(orderId.get(i).toString());
                    orders.dates=dates.get(i).toString();
                    orders.sended=sended.get(i).toString();
                    hera.com.orders.model.Partner partner=new hera.com.orders.model.Partner();
                    partner.name=partnerName.get(i).toString();
                    orders.partner=partner;
                    ordersList1.add(orders);
                }
                ordersList=ordersList1;
                notifyDataSetChanged();  // notifies the data with new filtered values
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();        // Holds the results of a filtering operation in values
                ArrayList FilteredArrList = new ArrayList();
                FilteredArrList1 = new ArrayList();
                FilteredArrList2 = new ArrayList();
                FilteredArrList3 = new ArrayList();
                if (original_orderId == null) {
                    original_orderId = new ArrayList(orderId);
                }
                if (original_partnerName == null) {
                    original_partnerName = new ArrayList(partnerName);
                }
                if (original_dates == null) {
                    original_dates = new ArrayList(dates);
                }
                if (original_sended == null) {
                    original_sended = new ArrayList(sended);
                }
                if (constraint == null || constraint.length() == 0) {

                    // set the Original result to return
                    results.count =original_orderId.size();
                    results.values = original_orderId;
                    FilteredArrList1=original_partnerName;
                    FilteredArrList2=original_dates;
                    FilteredArrList3=original_sended;
                } else {
                    constraint = constraint.toString().toLowerCase();
                    for (int i = 0; i < original_orderId.size(); i++) {
                        String name_data = original_partnerName.get(i).toString().toLowerCase();
                            if (name_data.contains(constraint.toString())) {
                                FilteredArrList.add(original_orderId.get(i));
                                FilteredArrList1.add(original_partnerName.get(i));
                                FilteredArrList2.add(original_dates.get(i));
                                FilteredArrList3.add(original_sended.get(i));
                            }
                    }

                    // set the Filtered result to return
                    results.count = FilteredArrList.size();
                    results.values = FilteredArrList;
                }
                return results;
            }
        };
        return filter;
    }
}
