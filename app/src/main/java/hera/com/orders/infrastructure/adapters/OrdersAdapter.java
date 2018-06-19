package hera.com.orders.infrastructure.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;

import java.util.ArrayList;

import hera.com.orders.R;

public class OrdersAdapter extends BaseAdapter {
    LayoutInflater inflater;
    Context context;
    public static int pos;
    ArrayList orderId, partnerName, dates;
    public OrdersAdapter(Context context, ArrayList orderId, ArrayList partnerName, ArrayList dates)
    {
        this.context=context;
        this.orderId=orderId;
        this.partnerName=partnerName;
        this.dates=dates;
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
                                        pos=position;
                                        //Intent intent=new Intent(context, PartnerDetailsActivity.class);
                                        //context.startActivity(intent);
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
}
