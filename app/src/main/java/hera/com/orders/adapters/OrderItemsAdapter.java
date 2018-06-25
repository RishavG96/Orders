package hera.com.orders.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import hera.com.orders.ArticleAmountActivity;
import hera.com.orders.CombinedActivity;
import hera.com.orders.R;
import hera.com.orders.sqlite.OrderItems;

public class OrderItemsAdapter extends BaseAdapter{
    LayoutInflater inflater;
    Context context;
    ArrayList id, name, code, unit,quantity, packaging, price;
    public static int pos;
    public List<hera.com.orders.model.OrderItems> orderItems;

    public OrderItemsAdapter(Context context, List<hera.com.orders.model.OrderItems> orderItems)
    {
        this.context=context;

        this.orderItems=orderItems;
        for(hera.com.orders.model.OrderItems orderItems1: orderItems)
        {
            id=new ArrayList();
            name=new ArrayList();
            code=new ArrayList();
            unit=new ArrayList();
            quantity=new ArrayList();
            packaging=new ArrayList();
            price=new ArrayList();
            id.add(orderItems1.articleId);
            name.add(orderItems1.articleName);
            code.add(orderItems1.articleCode);
            quantity.add(orderItems1.quantity);
            unit.add(orderItems1.articleUnits);
            packaging.add(orderItems1.articlePacking);
            price.add(orderItems1.price);
        }

        inflater=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return orderItems.size();
    }

    @Override
    public hera.com.orders.model.OrderItems getItem(int position) {
        return orderItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
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
                        popup.getMenuInflater().inflate(R.menu.orderedit, popup.getMenu());
                        popup.show();
                        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem item) {
                                switch (item.getItemId()) {
                                    case R.id.edit:
                                        pos=getItem(position).articleId;
                                        Intent intent=new Intent(context, ArticleAmountActivity.class);
                                        intent.putExtra("articleId",pos);
                                        context.startActivity(intent);
                                        break;
                                    case R.id.remove:
                                        pos=getItem(position).articleId;
                                        OrderItems.deleteItem(orderItems.get(position).articleId);
                                        Intent intent1=new Intent(context,CombinedActivity.class);
                                        intent1.putExtra("fragToLoad", 2);
                                        context.startActivity(intent1);
                                        ((CombinedActivity)context).finish();
                                        break;
                                }
                                return true;
                            }
                        });
                }
            }
        });
        n.setText(getItem(position).articleName);
        co.setText("Code: "+getItem(position).articleCode+"     ");
        q.setText("Quantity: "+getItem(position).quantity+"  "+getItem(position).articleUnits );
        pr.setText("Price: "+getItem(position).price+"    ");
        un.setText("");
        return convertView;
    }
}
