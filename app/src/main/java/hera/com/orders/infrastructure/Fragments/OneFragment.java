package hera.com.orders.infrastructure.Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import hera.com.orders.ArticleAmount;
import hera.com.orders.CombinedActivity;
import hera.com.orders.R;
import hera.com.orders.infrastructure.adapters.ArticleListAdapter;


public class OneFragment extends Fragment {
    ListView lv;
    hera.com.orders.infrastructure.sqlite.Article sqlite_article;
    ArticleListAdapter adapter;
    public OneFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_one, container, false);
        lv=view.findViewById(R.id.listview3);
        sqlite_article = new hera.com.orders.infrastructure.sqlite.Article();
        sqlite_article.showArticle(getContext());
        adapter=new ArticleListAdapter(getContext(),sqlite_article.id, sqlite_article.name, sqlite_article.code,
                sqlite_article.price, sqlite_article.units);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CombinedActivity.articleId=Integer.parseInt(sqlite_article.id.get(position));
                CombinedActivity.articleName=sqlite_article.name.get(position);
                CombinedActivity.articleUnits=sqlite_article.units.get(position);
                Toast.makeText(getContext(),"here="+sqlite_article.weight.get(position),Toast.LENGTH_SHORT).show();
                Toast.makeText(getContext(),"here="+sqlite_article.name.get(position),Toast.LENGTH_SHORT).show();
                CombinedActivity.articlePacking=sqlite_article.packing.get(position);
                CombinedActivity.articleWeight=sqlite_article.weight.get(position);
                Intent intent=new Intent(getContext(), ArticleAmount.class);
                startActivity(intent);
            }
        });
        return view;
    }
}
