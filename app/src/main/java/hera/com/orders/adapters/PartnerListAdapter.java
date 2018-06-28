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
import java.util.List;

import hera.com.orders.PartnerDetailsActivity;
import hera.com.orders.R;
import hera.com.orders.model.Partner;

public class PartnerListAdapter extends BaseAdapter implements Filterable{

    LayoutInflater inflater;
    Context context;
    ArrayList id, original_id, name, code, amount, address, city, original_name, original_code, original_amount, original_address, original_city;
    ArrayList FilteredArrList1;
    ArrayList FilteredArrList2;
    ArrayList FilteredArrList3;
    ArrayList FilteredArrList4;
    ArrayList FilteredArrList5;
    public List<Partner> partners;

    public PartnerListAdapter(Context context, List<Partner> partnerIterable)
    {
        this.context=context;
        partners=partnerIterable;

        name=new ArrayList();
        code=new ArrayList();
        amount=new ArrayList();
        address=new ArrayList();
        city=new ArrayList();
        id=new ArrayList();
        original_name=new ArrayList();
        original_code=new ArrayList();
        original_amount=new ArrayList();
        original_address=new ArrayList();
        original_city=new ArrayList();
        original_id=new ArrayList();
        for(Partner partner:partnerIterable)
        {
            id.add(partner.id);
            original_id.add(partner.id);
            name.add(partner.name);
            original_name.add(partner.name);
            code.add(partner.code);
            original_code.add(partner.code);
            amount.add(partner.amount);
            original_amount.add(partner.amount);
            address.add(partner.address);
            original_address.add(partner.address);
            city.add(partner.city);
            original_city.add(partner.city);
        }

        inflater=LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return partners.size();
    }

    @Override
    public Partner getItem(int position) {
        return partners.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        convertView=inflater.inflate(R.layout.partner_layout,null);
        TextView n=(TextView)convertView.findViewById(R.id.textView2);
        TextView co=(TextView)convertView.findViewById(R.id.textView5);
        TextView am=(TextView)convertView.findViewById(R.id.textView6);
        TextView ad=(TextView)convertView.findViewById(R.id.textView3);
        TextView ci=(TextView)convertView.findViewById(R.id.textView4);
//        ImageButton imageButton=(ImageButton)convertView.findViewById(R.id.imageButton);
//        imageButton.setTag(getItem(position));
//        imageButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                switch(v.getId())
//                {
//                    case R.id.imageButton:
//                        final PopupMenu popup = new PopupMenu(context, v);
//                        popup.getMenuInflater().inflate(R.menu.listoption, popup.getMenu());
//                        popup.show();
//                        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//                            @Override
//                            public boolean onMenuItemClick(MenuItem item) {
//                                switch (item.getItemId()) {
//                                    case R.id.show:
//                                        pos=getItem(position).id;
//                                        Intent intent=new Intent(context, PartnerDetailsActivity.class);
//                                        context.startActivity(intent);
//                                        break;
//                                    case R.id.delete:
//                                        break;
//                                }
//                                return true;
//                            }
//                        });
//                }
//            }
//        });
        n.setText((getItem(position)).name);
        co.setText("Code: "+getItem(position).code);
        am.setText("    Amount: "+getItem(position).amount+"     ");
        ad.setText("Address: "+getItem(position).address);
        ci.setText("    City: "+getItem(position).city);
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
                address=FilteredArrList3;
                city=FilteredArrList4;
                id=FilteredArrList5;
                List<Partner> partnerList=new ArrayList<Partner>();
                for(int i=0;i<name.size();i++)
                {
                    Partner partner=new Partner();
                    partner.id=Integer.parseInt(id.get(i).toString());
                    partner.name=name.get(i).toString();
                    partner.code=code.get(i).toString();
                    partner.amount=amount.get(i).toString();
                    partner.address=address.get(i).toString();
                    partner.city=city.get(i).toString();
                    partnerList.add(partner);
                }
                partners=partnerList;
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
                FilteredArrList5 = new ArrayList();


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
                if (original_address == null) {
                    original_address = new ArrayList(address);
                }
                if (original_city == null) {
                    original_city = new ArrayList(city);
                }

                if (constraint == null || constraint.length() == 0) {

                    // set the Original result to return
                    results.count =original_name.size();
                    results.values = original_name;
                    FilteredArrList1=original_code;
                    FilteredArrList2=original_amount;
                    FilteredArrList3=original_address;
                    FilteredArrList4=original_city;
                    FilteredArrList5=original_id;
                } else {
//                    constraint = constraint.toString().toLowerCase();
//                    String[] temp=new String[1000];
//                    int flag;
//                    String filterString=constraint.toString();
//                    if(filterString.contains(" ")) {
//                        temp = filterString.split(" ");
//                        flag=1;
//                    }
//                    else
//                    {
//                        flag=0;
//                    }
//                    for (int i = 0; i < original_name.size(); i++) {
//                        String name_data = (String)original_name.get(i);
//                        String address_data = (String)original_address.get(i);
//                        if(flag==0) {
//                            if (name_data.toLowerCase().contains(constraint.toString())) {
//                                FilteredArrList.add(original_name.get(i));
//                                FilteredArrList1.add(original_code.get(i));
//                                FilteredArrList2.add(original_amount.get(i));
//                                FilteredArrList3.add(original_address.get(i));
//                                FilteredArrList4.add(original_city.get(i));
//                                FilteredArrList5.add(original_id.get(i));
//                            }
//                        }
//                        else if(temp.length >1)
//                        {
//                            if (name_data.toLowerCase().contains(temp[0]) && address_data.toLowerCase().contains(temp[1])) {
//                                FilteredArrList.add(original_name.get(i));
//                                FilteredArrList1.add(original_code.get(i));
//                                FilteredArrList2.add(original_amount.get(i));
//                                FilteredArrList3.add(original_address.get(i));
//                                FilteredArrList4.add(original_city.get(i));
//                                FilteredArrList5.add(original_id.get(i));
//                            }
//                        }
//                    }
//                    // set the Filtered result to return
//                    results.count = FilteredArrList.size();
//                    results.values = FilteredArrList;

                    constraint = constraint.toString().toLowerCase();
                    String temp[];
                    temp=constraint.toString().split(" ");
                    for(int i=0;i<name.size();i++)
                    {
                        String t[];
                        String n=name.get(i).toString();
                        String c=address.get(i).toString();
                        String concat=n+" "+c;
                        t=concat.split(" ");
                        int flag[]=new int[temp.length+1];
                        for(int j=0;j<temp.length;j++)
                            flag[j]=0;
                        int count=0;
                        for(String str:temp)
                        {
                            for(String s:t)
                            {
                                if(s.toLowerCase().contains(str.toLowerCase()))
                                {
                                    flag[count]=1;
                                }
                            }
                            count++;
                        }
                        int f=0;
                        for(int j=0;j<temp.length;j++) {
                            if (flag[j] == 0) {
                                f=1;
                                break;
                            }
                        }
                        if(f==0)
                        {
                            FilteredArrList.add(name.get(i));
                            FilteredArrList1.add(code.get(i));
                            FilteredArrList2.add(amount.get(i));
                            FilteredArrList3.add(address.get(i));
                            FilteredArrList4.add(city.get(i));
                            FilteredArrList5.add(id.get(i));
                        }
                    }
                    results.count = FilteredArrList.size();
                    results.values = FilteredArrList;

                }
                return results;
            }
        };
        return filter;
    }
}
