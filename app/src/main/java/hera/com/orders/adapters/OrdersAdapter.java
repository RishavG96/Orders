package hera.com.orders.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;

import java.util.ArrayList;

import hera.com.orders.MainActivity;
import hera.com.orders.OrderDetailsActivity;
import hera.com.orders.R;
import hera.com.orders.sqlite.Orders;

public class OrdersAdapter extends BaseAdapter implements Filterable {
    LayoutInflater inflater;
    Context context;
    ArrayList orderId, original_orderId, partnerName, original_partnerName, dates, original_dates;
    ArrayList FilteredArrList1;
    ArrayList FilteredArrList2;
    ArrayList FilteredArrList3;
    public OrdersAdapter(Context context, ArrayList orderId, ArrayList partnerName, ArrayList dates)
    {
        this.context=context;
        this.orderId=orderId;
        this.original_orderId=orderId;
        this.partnerName=partnerName;
        this.original_partnerName=partnerName;
        this.dates=dates;
        this.original_dates=dates;
        inflater=LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return orderId.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        convertView=inflater.inflate(R.layout.orders_layout,null);
        TextView n=(TextView)convertView.findViewById(R.id.textView26);
        TextView pr=(TextView)convertView.findViewById(R.id.textView29);
        TextView un=(TextView)convertView.findViewById(R.id.textView30);
        TextView co=(TextView)convertView.findViewById(R.id.textView27);
        TextView q=(TextView)convertView.findViewById(R.id.textView28);
        ImageButton imageButton=(ImageButton)convertView.findViewById(R.id.imageButton4);
        imageButton.setTag(getItem(position));
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch(v.getId())
                {
                    case R.id.imageButton4:
                        final PopupMenu popup = new PopupMenu(context, v);
                        popup.getMenuInflater().inflate(R.menu.listoption, popup.getMenu());
                        popup.show();
                        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem item) {
                                switch (item.getItemId()) {
                                    case R.id.show:
                                        MainActivity.pos=position;
                                        Intent intent=new Intent(context, OrderDetailsActivity.class);
                                        context.startActivity(intent);
                                        break;
                                    case R.id.delete:
                                        break;
                                }
                                return true;
                            }
                        });
                }
            }
        });
        n.setText("Order ID: "+orderId.get(position).toString());
        co.setText("Partner Name: "+partnerName.get(position).toString());
//        q.setText("Quantity: "+quantity.get(position).toString()+"  "+unit.get(position).toString() );
        q.setText("");
        pr.setText("Date: "+dates.get(position).toString());
        un.setText("");
        return convertView;
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
                Orders.orderId=orderId;
                Orders.partnerName=partnerName;
                Orders.date=dates;
                notifyDataSetChanged();  // notifies the data with new filtered values
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();        // Holds the results of a filtering operation in values
                ArrayList FilteredArrList = new ArrayList();
                FilteredArrList1 = new ArrayList();
                FilteredArrList2 = new ArrayList();

                if (original_orderId == null) {
                    original_orderId = new ArrayList(orderId);
                }
                if (original_partnerName == null) {
                    original_partnerName = new ArrayList(partnerName);
                }
                if (original_dates == null) {
                    original_dates = new ArrayList(dates);
                }

                if (constraint == null || constraint.length() == 0) {

                    // set the Original result to return
                    results.count =original_orderId.size();
                    results.values = original_orderId;
                    FilteredArrList1=original_partnerName;
                    FilteredArrList2=original_dates;
                } else {
                    constraint = constraint.toString().toLowerCase();
                    String[] temp=new String[1000];
                    int flag;
                    String filterString=constraint.toString();
                    if(filterString.contains(" ")) {
                        temp = filterString.split(" ");
                        flag=1;
                    }
                    else
                    {
                        flag=0;
                    }
                    for (int i = 0; i < original_orderId.size(); i++) {
                        String name_data = (String)original_orderId.get(i);
                        String code_data = (String)original_partnerName.get(i);
                        if(flag==0) {
                            if (name_data.toLowerCase().contains(constraint.toString())) {
                                FilteredArrList.add(original_orderId.get(i));
                                FilteredArrList1.add(original_partnerName.get(i));
                                FilteredArrList2.add(original_dates.get(i));
                            }
                        }
                        else if(temp.length >1)
                        {
                            if (name_data.toLowerCase().contains(temp[0]) && code_data.toLowerCase().contains(temp[1])) {
                                FilteredArrList.add(original_orderId.get(i));
                                FilteredArrList1.add(original_partnerName.get(i));
                                FilteredArrList2.add(original_dates.get(i));
                            }
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
