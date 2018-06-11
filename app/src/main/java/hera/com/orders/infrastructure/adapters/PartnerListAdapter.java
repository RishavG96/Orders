package hera.com.orders.infrastructure.adapters;

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
import java.util.List;
import java.util.Locale;

import hera.com.orders.PartnerDetailsActivity;
import hera.com.orders.R;
import hera.com.orders.infrastructure.sqlite.Partner;

public class PartnerListAdapter extends BaseAdapter implements Filterable{

    LayoutInflater inflater;
    Context context;
    ArrayList name, code, amount, address, city;
    private ItemFilter mFilter = new ItemFilter();
    public static int pos;
    public PartnerListAdapter(Context context, ArrayList name, ArrayList code, ArrayList amount, ArrayList address, ArrayList city)
    {
        this.context=context;
        this.name=name;
        this.code=code;
        this.amount=amount;
        this.address=address;
        this.city=city;
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
        convertView=inflater.inflate(R.layout.partner_layout,null);
        TextView n=(TextView)convertView.findViewById(R.id.textView2);
        TextView co=(TextView)convertView.findViewById(R.id.textView5);
        TextView am=(TextView)convertView.findViewById(R.id.textView6);
        TextView ad=(TextView)convertView.findViewById(R.id.textView3);
        TextView ci=(TextView)convertView.findViewById(R.id.textView4);
        ImageButton imageButton=(ImageButton)convertView.findViewById(R.id.imageButton);
        imageButton.setTag(getItem(position));
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch(v.getId())
                {
                    case R.id.imageButton:
                        final PopupMenu popup = new PopupMenu(context, v);
                        popup.getMenuInflater().inflate(R.menu.listoption, popup.getMenu());
                        popup.show();
                        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem item) {
                                switch (item.getItemId()) {
                                    case R.id.show:
                                        pos=position;
                                        Intent intent=new Intent(context, PartnerDetailsActivity.class);
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
        n.setText(name.get(position).toString());
        co.setText("Code: "+code.get(position).toString());
        am.setText("    Amount: "+amount.get(position).toString()+"     ");
        ad.setText("Address: "+address.get(position).toString());
        ci.setText("    City: "+city.get(position).toString());
        return convertView;
    }

    @Override
    public Filter getFilter() {
        return mFilter;
    }

    private class ItemFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            String filterString = constraint.toString().toLowerCase();

            FilterResults results = new FilterResults();

            final List<String> list = Partner.name;

            int count = list.size();
            final ArrayList<String> nlist = new ArrayList<String>(count);

            String filterableString ;

            for (int i = 0; i < count; i++) {
                filterableString = list.get(i);
                if (filterableString.toLowerCase().contains(filterString)) {
                    nlist.add(filterableString);
                }
            }

            results.values = nlist;
            results.count = nlist.size();

            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            name = (ArrayList<String>) results.values;
            notifyDataSetChanged();
        }

    }
}
