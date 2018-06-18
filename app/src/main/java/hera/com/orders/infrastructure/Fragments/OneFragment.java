package hera.com.orders.infrastructure.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

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
        adapter=new ArticleListAdapter(getContext(), sqlite_article.name, sqlite_article.code,
                sqlite_article.price, sqlite_article.units);
        lv.setAdapter(adapter);
        return view;
    }
}
