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

import hera.com.orders.AssortmentDetailsActivity;
import hera.com.orders.R;
import hera.com.orders.sqlite.Assortment;

public class AssortmentListAdapter extends BaseAdapter implements Filterable {
    LayoutInflater inflater;
    Context context;
    ArrayList id, name, code, amount, units, original_id, original_name, original_code, original_amount, original_units;
    public static int pos;
    ArrayList FilteredArrList1;
    ArrayList FilteredArrList2;
    ArrayList FilteredArrList3;
    ArrayList FilteredArrList4;
    public AssortmentListAdapter(Context context, ArrayList id, ArrayList name, ArrayList code, ArrayList amount, ArrayList units)
    {
        this.context=context;
        this.id=id;
        this.original_id=id;
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
                                        Intent intent=new Intent(context, AssortmentDetailsActivity.class);
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
        pr.setText("Price: "+amount.get(position).toString()+"    ");
        un.setText("Units: "+units.get(position).toString());
        return convertView;
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint,FilterResults results) {
                name = (ArrayList)results.values; // has the filtered values
                code=FilteredArrList1;
                amount=FilteredArrList2;
                units=FilteredArrList3;
                id=FilteredArrList4;
                Assortment.name=name;
                Assortment.code=code;
                Assortment.price=amount;
                Assortment.units=units;
                Assortment.id=id;
                notifyDataSetChanged();  // notifies the data with new filtered values
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();        // Holds the results of a filtering operation in values
                ArrayList FilteredArrList = new ArrayList();
                FilteredArrList1 = new ArrayList();
                FilteredArrList2 = new ArrayList();
                FilteredArrList3 = new ArrayList();
                FilteredArrList4 = new ArrayList();

                if (original_id == null) {
                    original_id = new ArrayList(id);
                }
                if (original_name == null) {
                    original_name = new ArrayList(name);
                }
                if (original_code == null) {
                    original_code = new ArrayList(code);
                }
                if (original_amount == null) {
                    original_amount = new ArrayList(amount);
                }
                if (original_units == null) {
                    original_units = new ArrayList(units);
                }

                if (constraint == null || constraint.length() == 0) {

                    // set the Original result to return
                    results.count =original_name.size();
                    results.values = original_name;
                    FilteredArrList1=original_code;
                    FilteredArrList2=original_amount;
                    FilteredArrList3=original_units;
                    FilteredArrList4=original_id;
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
                    for (int i = 0; i < original_name.size(); i++) {
                        String name_data = (String)original_name.get(i);
                        String code_data = (String)original_code.get(i);
                        if(flag==0) {
                            if (name_data.toLowerCase().contains(constraint.toString())) {
                                FilteredArrList.add(original_name.get(i));
                                FilteredArrList1.add(original_code.get(i));
                                FilteredArrList2.add(original_amount.get(i));
                                FilteredArrList3.add(original_units.get(i));
                                FilteredArrList4.add(original_id.get(i));
                            }
                        }
                        else if(temp.length >1)
                        {
                            if (name_data.toLowerCase().contains(temp[0]) && code_data.toLowerCase().contains(temp[1])) {
                                FilteredArrList.add(original_name.get(i));
                                FilteredArrList1.add(original_code.get(i));
                                FilteredArrList2.add(original_amount.get(i));
                                FilteredArrList3.add(original_units.get(i));
                                FilteredArrList4.add(original_id.get(i));
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
