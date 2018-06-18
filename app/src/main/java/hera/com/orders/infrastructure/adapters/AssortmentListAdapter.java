package hera.com.orders.infrastructure.adapters;

import android.content.Context;
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

import hera.com.orders.R;

public class AssortmentListAdapter extends BaseAdapter implements Filterable {
    LayoutInflater inflater;
    Context context;
    ArrayList name, code, amount, units, original_name, original_code, original_amount, original_units;
    public static int pos;
    ArrayList FilteredArrList1;
    ArrayList FilteredArrList2;
    ArrayList FilteredArrList3;
    public AssortmentListAdapter(Context context, ArrayList name, ArrayList code, ArrayList amount, ArrayList units)
    {
        this.context=context;
        this.name=name;
        this.original_name=name;
        this.code=code;
        this.original_code=code;
        this.amount=amount;
        this.original_amount=amount;
        this.units=units;
        this.original_units=units;
        inflater=LayoutInflater.from(context);

    }
    @Override
    public int getCount() {
        return name.size();
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
        convertView=inflater.inflate(R.layout.assortment_layout,null);
        TextView n=(TextView)convertView.findViewById(R.id.textView13);
        TextView pr=(TextView)convertView.findViewById(R.id.textView16);
        TextView un=(TextView)convertView.findViewById(R.id.textView17);
        TextView co=(TextView)convertView.findViewById(R.id.textView14);
        TextView no=(TextView)convertView.findViewById(R.id.textView15);
        ImageButton imageButton=(ImageButton)convertView.findViewById(R.id.imageButton2);
        imageButton.setTag(getItem(position));
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch(v.getId())
                {
                    case R.id.imageButton2:
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
        n.setText(name.get(position).toString());
        co.setText("Code: "+code.get(position).toString());
        pr.setText("Price: "+amount.get(position).toString()+"    ");
        un.setText("Units: "+units.get(position).toString());
        return convertView;
    }

    @Override
    public Filter getFilter() {
        return null;
    }
}
