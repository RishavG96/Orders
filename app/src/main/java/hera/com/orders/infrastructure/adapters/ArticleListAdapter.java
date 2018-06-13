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
import java.util.List;

import hera.com.orders.R;
import hera.com.orders.infrastructure.sqlite.Article;
import hera.com.orders.infrastructure.sqlite.Partner;

public class ArticleListAdapter extends BaseAdapter implements Filterable {

    LayoutInflater inflater;
    Context context;
    ArrayList name=new ArrayList(), code, amount, units;
    private ItemFilter mFilter = new ItemFilter();
    public static int pos;
    public ArticleListAdapter(Context context, ArrayList name, ArrayList code, ArrayList amount, ArrayList units)
    {
        this.context=context;
        this.name=name;
        this.code=code;
        this.amount=amount;
        this.units=units;
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
        convertView=inflater.inflate(R.layout.article_layout,null);
        TextView n=(TextView)convertView.findViewById(R.id.textView7);
        TextView pr=(TextView)convertView.findViewById(R.id.textView10);
        TextView un=(TextView)convertView.findViewById(R.id.textView11);
        TextView co=(TextView)convertView.findViewById(R.id.textView8);
        TextView no=(TextView)convertView.findViewById(R.id.textView9);
        ImageButton imageButton=(ImageButton)convertView.findViewById(R.id.imageButton1);
        imageButton.setTag(getItem(position));
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch(v.getId())
                {
                    case R.id.imageButton1:
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
        return mFilter;
    }
    private class ItemFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            String filterString = constraint.toString().toLowerCase();

            FilterResults results = new FilterResults();

            final List<String> list = Article.name;

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
