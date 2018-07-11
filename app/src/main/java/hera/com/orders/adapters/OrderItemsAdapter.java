package hera.com.orders.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.swipe.SimpleSwipeListener;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.BaseSwipeAdapter;

import java.util.ArrayList;
import java.util.List;

import hera.com.orders.ArticleAmountActivity;
import hera.com.orders.CombinedActivity;
import hera.com.orders.MainActivity;
import hera.com.orders.OrderDetailsActivity;
import hera.com.orders.R;
import hera.com.orders.sqlite.OrderItems;
import hera.com.orders.sqlite.Orders;

public class OrderItemsAdapter extends BaseSwipeAdapter {
    LayoutInflater inflater;
    Context context;
    ArrayList id, name, code, unit,quantity, packaging, price;
    public static int pos;
    public List<hera.com.orders.model.OrderItems> orderItems;
    Orders order;

    public OrderItemsAdapter(Context context, List<hera.com.orders.model.OrderItems> orderItems)
    {
        this.context=context;
        order=new Orders();
        this.orderItems=orderItems;
        try {
            for (hera.com.orders.model.OrderItems orderItems1 : orderItems) {
                id = new ArrayList();
                name = new ArrayList();
                code = new ArrayList();
                unit = new ArrayList();
                quantity = new ArrayList();
                packaging = new ArrayList();
                price = new ArrayList();
                id.add(orderItems1.articleId);
                name.add(orderItems1.articleName);
                code.add(orderItems1.articleCode);
                quantity.add(orderItems1.quantity);
                unit.add(orderItems1.articleUnits);
                packaging.add(orderItems1.articlePacking);
                price.add(orderItems1.price);
            }
        }catch (Exception e){}

        inflater=LayoutInflater.from(context);
    }
    public void remove(int position) {
        //orderItems.remove(position);
        //notifyDataSetChanged();
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
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe1;
    }

    @Override
    public View generateView(final int position, ViewGroup parent) {
        View v =inflater.inflate(R.layout.order_items_layout,null);
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
                if(context.toString().contains("OrderDetailsActivity")) {
                    Cursor c1 = MainActivity.db.rawQuery("select * from orders2 where orderId=" + MainActivity.orderID + "", null);
                    String sended1 = "";
                    while (c1.moveToNext()) {
                        sended1 = c1.getString(4);
                    }
                    if (sended1.equals("N")) {
                        pos = getItem(position).articleId;
                        Orders.deleteItem(MainActivity.orderID,orderItems.get(position).articleId);
                        Intent intent1 = new Intent(context, OrderDetailsActivity.class);
                        context.startActivity(intent1);
                        ((OrderDetailsActivity) context).finish();
                        Toast.makeText(context, "" + context, Toast.LENGTH_SHORT).show();
                    } else if (sended1.equals("Y")) {
                        Toast.makeText(context, "Nije moguće urediti poslanu narudžbu", Toast.LENGTH_SHORT).show();
                    }
                }
                else if(context.toString().contains("CombinedActivity")){
                    pos = getItem(position).articleId;
                    OrderItems.deleteItem(orderItems.get(position).articleId);
                    Intent intent1 = new Intent(context, CombinedActivity.class);
                    intent1.putExtra("fragToLoad", 2);
                    context.startActivity(intent1);
                    ((CombinedActivity) context).finish();
                }
            }
        });
        return v;
    }

    @Override
    public void fillValues(int position, View convertView) {
        TextView n=(TextView)convertView.findViewById(R.id.textView21);
        TextView pr=(TextView)convertView.findViewById(R.id.textView24);
        TextView co=(TextView)convertView.findViewById(R.id.textView22);
        TextView q=(TextView)convertView.findViewById(R.id.textView23);
        n.setText(getItem(position).articleName);
        co.setText(""+getItem(position).articleCode+"     ");
        q.setText(""+getItem(position).quantity+"  "+getItem(position).articleUnits );
        String s=getItem(position).price;
        //Log.e("price1:",s);
        double d = Double.parseDouble(s);
        String str = String.format("%1.2f", d);
        if(str.contains(","))
            str=str.replace(",",".");
        d = Double.valueOf(str);
        pr.setText(""+d+" KM");
    }
}
