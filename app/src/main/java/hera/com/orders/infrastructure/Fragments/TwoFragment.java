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
import hera.com.orders.MainActivity;
import hera.com.orders.OrderPartners;
import hera.com.orders.R;
import hera.com.orders.infrastructure.adapters.ArticleListAdapter;
import hera.com.orders.infrastructure.adapters.AssortmentListAdapter;


public class TwoFragment extends Fragment {
    ListView lv;
    AssortmentListAdapter adapter;
    hera.com.orders.infrastructure.sqlite.Assortment sqlite_assort;
    public TwoFragment() {
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
        View view = inflater.inflate(R.layout.fragment_two, container, false);
        lv=view.findViewById(R.id.listview4);
        sqlite_assort=new hera.com.orders.infrastructure.sqlite.Assortment();
        sqlite_assort.showAssortment(getContext(), OrderPartners.partnerID);
        adapter=new AssortmentListAdapter(getContext(), sqlite_assort.name, sqlite_assort.code,
                sqlite_assort.price, sqlite_assort.units);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CombinedActivity.articleId=Integer.parseInt(sqlite_assort.id.get(position));
                CombinedActivity.articleName=sqlite_assort.name.get(position);
                CombinedActivity.articleUnits=sqlite_assort.units.get(position);
                CombinedActivity.articlePacking=sqlite_assort.packing.get(position);
                CombinedActivity.articleWeight=sqlite_assort.weight.get(position);
                Intent intent=new Intent(getContext(), ArticleAmount.class);
                startActivity(intent);
            }
        });
        return view;
    }
}
