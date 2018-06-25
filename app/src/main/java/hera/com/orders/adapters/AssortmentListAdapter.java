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

import hera.com.orders.AssortmentDetailsActivity;
import hera.com.orders.R;
import hera.com.orders.module.Article;
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
    public List<hera.com.orders.module.Article> articles;

    public AssortmentListAdapter(Context context, List<Article> articles)
    {
        this.context=context;
        this.articles=articles;

        id=new ArrayList();
        name=new ArrayList();
        code=new ArrayList();
        amount=new ArrayList();
        units=new ArrayList();
        original_id=new ArrayList();
        original_name=new ArrayList();
        original_code=new ArrayList();
        original_amount=new ArrayList();
        original_units=new ArrayList();

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
                                        pos=getItem(position).id;
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
        n.setText(getItem(position).name);
        co.setText("Code: "+getItem(position).code);
        pr.setText("Price: "+getItem(position).price+"    ");
        un.setText("Units: "+getItem(position).units);
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
                List<Article> articleList=new ArrayList<>();
                for(int i=0;i<name.size();i++)
                {
                    Article article=new Article();
                    article.id=Integer.parseInt(id.get(i).toString());
                    article.name=name.get(i).toString();
                    article.code=code.get(i).toString();
                    article.price=amount.get(i).toString();
                    article.units=units.get(i).toString();
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
