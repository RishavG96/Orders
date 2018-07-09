package hera.com.orders.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import hera.com.orders.ArticleAmountActivity;
import hera.com.orders.CombinedActivity;
import hera.com.orders.R;
import hera.com.orders.adapters.ArticleListAdapter;
import hera.com.orders.model.Article;


public class OneFragment extends Fragment {
    ListView lv;
    hera.com.orders.sqlite.Article sqlite_article;
    ArticleListAdapter adapter;
    SearchView searchView;
    List<Article> articleList;
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
        //getActivity().supportInvalidateOptionsMenu();
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_one, container, false);
        lv=view.findViewById(R.id.listview3);
        sqlite_article = new hera.com.orders.sqlite.Article();
        articleList=new ArrayList<>();
        articleList=(List<Article>)sqlite_article.showArticle(getContext());
        adapter=new ArticleListAdapter(getContext(),articleList);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                articleList=adapter.articles;
                int exit=0;
                Intent intent=new Intent(getContext(), ArticleAmountActivity.class);
                intent.putExtra("articleId", articleList.get(position).id);
                intent.putExtra("page", "fragone");
                startActivityForResult(intent, exit);
            }
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==0)
            if(resultCode==1)
                getActivity().finish();
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        //MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.mainmenu,menu);
        //SearchManager searchManager = (SearchManager)getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.app_bar_search).getActionView();
        //searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.performClick();
        searchView.requestFocus();
        searchView.setIconifiedByDefault(false);
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.getFilter().filter(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return true;
            }
        });
    }
}
