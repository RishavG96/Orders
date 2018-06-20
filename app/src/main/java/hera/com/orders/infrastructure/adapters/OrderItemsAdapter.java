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

public class OrderItemsAdapter extends BaseAdapter{
    LayoutInflater inflater;
    Context context;
    ArrayList id, name, code, unit,quantity, packaging, price;
    public static int pos;
    public OrderItemsAdapter(Context context, ArrayList id, ArrayList name, ArrayList code, ArrayList unit,ArrayList quantity, ArrayList packaging, ArrayList price)
    {
        this.context=context;
        this.id=id;
        this.name=name;
        this.code=code;
        this.quantity=quantity;
        this.unit=unit;
        this.packaging=packaging;
        this.price=price;
        inflater=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return id.size();
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
        convertView=inflater.inflate(R.layout.order_items_layout,null);
        TextView n=(TextView)convertView.findViewById(R.id.textView21);
        TextView pr=(TextView)convertView.findViewById(R.id.textView24);
        TextView un=(TextView)convertView.findViewById(R.id.textView25);
        TextView co=(TextView)convertView.findViewById(R.id.textView22);
        TextView q=(TextView)convertView.findViewById(R.id.textView23);
        ImageButton imageButton=(ImageButton)convertView.findViewById(R.id.imageButton3);
        imageButton.setTag(getItem(position));
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch(v.getId())
                {
                    case R.id.imageButton3:
                        final PopupMenu popup = new PopupMenu(context, v);
                        popup.getMenuInflater().inflate(R.menu.listoption, popup.getMenu());
                        popup.show();
                        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem item) {
                                switch (item.getItemId()) {
                                    case R.id.show:
                                        pos=position;
                                        //Intent intent=new Intent(context, OrderItemsDetailsActivity.class);
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
        n.setText(name.get(position).toString());
        co.setText("Code: "+code.get(position).toString()+"     ");
        q.setText("Quantity: "+quantity.get(position).toString()+"  "+unit.get(position).toString() );
        pr.setText("Price: "+price.get(position).toString()+"    ");
        un.setText("");
        return convertView;
    }
}
