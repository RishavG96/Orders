package hera.com.orders.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import hera.com.orders.R;

public class DetailsAdapter extends BaseAdapter{
    LayoutInflater inflater;
    Context context;
    ArrayList heading, values;
    public DetailsAdapter(Context context, ArrayList heading, ArrayList values)
    {
        this.context=context;
        this.heading=heading;
        this.values=values;
        inflater=LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return heading.size();
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
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView=inflater.inflate(R.layout.article_details_layout,null);
        TextView n=(TextView)convertView.findViewById(R.id.textView33);
        TextView de=(TextView)convertView.findViewById(R.id.textView34);
        n.setText(heading.get(position).toString());
        de.setText(values.get(position).toString());
        return convertView;
    }
}
