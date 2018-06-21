package hera.com.orders.infrastructure.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import hera.com.orders.ArticleAmountActivity;
import hera.com.orders.CombinedActivity;
import hera.com.orders.MainActivity;
import hera.com.orders.OrderPartnersActivity;
import hera.com.orders.R;
import hera.com.orders.infrastructure.adapters.AssortmentListAdapter;


public class TwoFragment extends Fragment {
    ListView lv;
    AssortmentListAdapter adapter;
    TextView empty;
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
        empty=view.findViewById(R.id.empty);
        sqlite_assort=new hera.com.orders.infrastructure.sqlite.Assortment();
        sqlite_assort.showAssortment(getContext(), MainActivity.partnerID);
        adapter=new AssortmentListAdapter(getContext(), sqlite_assort.name, sqlite_assort.code,
                sqlite_assort.price, sqlite_assort.units);
        if(sqlite_assort.name.isEmpty())
            empty.setText("No Assortments for the Partner");
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CombinedActivity.articleId=Integer.parseInt(sqlite_assort.articleId.get(position));
                CombinedActivity.articleName=sqlite_assort.name.get(position);
                CombinedActivity.articleUnits=sqlite_assort.units.get(position);
                CombinedActivity.articlePacking=sqlite_assort.packing.get(position);
                CombinedActivity.articleWeight=sqlite_assort.weight.get(position);
                CombinedActivity.articlePrice=sqlite_assort.price.get(position);
                CombinedActivity.articleCode=sqlite_assort.code.get(position);
                int exit=0;
                Intent intent=new Intent(getContext(), ArticleAmountActivity.class);
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
}
