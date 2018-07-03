package hera.com.orders.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
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

import hera.com.orders.AssortmentDetailsActivity;
import hera.com.orders.R;
import hera.com.orders.model.Article;

public class AssortmentListAdapter extends BaseAdapter implements Filterable {
    LayoutInflater inflater;
    Context context;
    ArrayList id, name, code, amount, units, original_id, original_name, original_code, original_amount, original_units, packing,
                original_packing, weight, original_weight;
    public static int pos;
    ArrayList FilteredArrList1;
    ArrayList FilteredArrList2;
    ArrayList FilteredArrList3;
    ArrayList FilteredArrList4;
    ArrayList FilteredArrList5;
    ArrayList FilteredArrList6;
    public List<hera.com.orders.model.Article> articles;

    public AssortmentListAdapter(Context context, List<Article> articles)
    {
        this.context=context;
        this.articles=articles;

        id=new ArrayList();
        name=new ArrayList();
        code=new ArrayList();
        amount=new ArrayList();
        units=new ArrayList();
        packing=new ArrayList();
        weight=new ArrayList();
        original_id=new ArrayList();
        original_name=new ArrayList();
        original_code=new ArrayList();
        original_amount=new ArrayList();
        original_units=new ArrayList();
        original_packing=new ArrayList();
        original_weight=new ArrayList();

        for(Article article: articles) {
            id.add(article.id);
            original_id.add(article.id);
            name.add(article.name);
            original_name.add(article.name);
            code.add(article.code);
            original_code.add(article.code);
            amount.add(article.price);
            original_amount.add(article.price);
            units.add(article.units);
            original_units.add(article.units);
            packing.add(article.packing);
            original_packing.add(article.packing);
            weight.add(article.weight);
            original_weight.add(article.weight);
        }
        inflater=LayoutInflater.from(context);

    }
    @Override
    public int getCount() {
        return articles.size();
    }

    @Override
    public Article getItem(int position) {
        return articles.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        convertView=inflater.inflate(R.layout.assortment_layout,null);
        TextView n=(TextView)convertView.findViewById(R.id.textView13);
        TextView pr=(TextView)convertView.findViewById(R.id.textView16);
        TextView un=(TextView)convertView.findViewById(R.id.textView17);
        TextView co=(TextView)convertView.findViewById(R.id.textView14);
        n.setText(getItem(position).name);
        co.setText(""+getItem(position).code);
        pr.setText(""+getItem(position).price+"    ");
        un.setText(""+getItem(position).units);
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
                packing=FilteredArrList5;
                weight=FilteredArrList6;
                List<Article> articleList=new ArrayList<>();
                for(int i=0;i<name.size();i++)
                {
                    Article article=new Article();
                    article.id=Integer.parseInt(id.get(i).toString());
                    article.name=name.get(i).toString();
                    article.code=code.get(i).toString();
                    article.price=amount.get(i).toString();
                    article.units=units.get(i).toString();
                    article.packing=packing.get(i).toString();
                    article.weight=weight.get(i).toString();
                    articleList.add(article);
                }
                articles=articleList;
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
                FilteredArrList6 = new ArrayList();

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
                if (original_packing == null) {
                    original_packing = new ArrayList(packing);
                }
                if (original_weight == null) {
                    original_weight = new ArrayList(weight);
                }

                if (constraint == null || constraint.length() == 0) {

                    // set the Original result to return
                    results.count =original_name.size();
                    results.values = original_name;
                    FilteredArrList1=original_code;
                    FilteredArrList2=original_amount;
                    FilteredArrList3=original_units;
                    FilteredArrList4=original_id;
                    FilteredArrList5=original_packing;
                    FilteredArrList6=original_weight;
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
//                        String code_data = (String)original_code.get(i);
//                        if(flag==0) {
//                            if (name_data.toLowerCase().contains(constraint.toString())) {
//                                FilteredArrList.add(original_name.get(i));
//                                FilteredArrList1.add(original_code.get(i));
//                                FilteredArrList2.add(original_amount.get(i));
//                                FilteredArrList3.add(original_units.get(i));
//                                FilteredArrList4.add(original_id.get(i));
//                                FilteredArrList5.add(original_packing.get(i));
//                                FilteredArrList6.add(original_weight.get(i));
//                            }
//                        }
//                        else if(temp.length >1)
//                        {
//                            if (name_data.toLowerCase().contains(temp[0]) && code_data.toLowerCase().contains(temp[1])) {
//                                FilteredArrList.add(original_name.get(i));
//                                FilteredArrList1.add(original_code.get(i));
//                                FilteredArrList2.add(original_amount.get(i));
//                                FilteredArrList3.add(original_units.get(i));
//                                FilteredArrList4.add(original_id.get(i));
//                                FilteredArrList5.add(original_packing.get(i));
//                                FilteredArrList6.add(original_weight.get(i));
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
                        String c=code.get(i).toString();
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
                            FilteredArrList3.add(units.get(i));
                            FilteredArrList4.add(id.get(i));
                            FilteredArrList5.add(packing.get(i));
                            FilteredArrList6.add(weight.get(i));
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
