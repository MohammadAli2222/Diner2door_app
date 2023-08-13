package android.example.vendorpage;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;


public class OutletsList_Customer extends Fragment implements outletList_adapter.OnItemClickListener {
    outletList_adapter adapter;
    RecyclerView recview;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view=inflater.inflate(R.layout.fragment_outlets_list__customer, container, false);
        recview=(RecyclerView)view.findViewById(R.id.recview_outletslist);
        FirebaseRecyclerOptions<model> options =
                new FirebaseRecyclerOptions.Builder<model>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Vendors").child("Outlet_List")
                                , model.class)
                        .build();


        recview.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter=new outletList_adapter(options);
        recview.setAdapter(adapter);
        recview.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter.setOnItemClickListener(this);


        return view;
    }
    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }


    @Override
    public void onItemClick(int position) {

        OutletMenu_Customer ldf = new OutletMenu_Customer ();
        Bundle args = new Bundle();
        String Position=String.valueOf(position);
        args.putString("YourKey", Position);
        ldf.setArguments(args);

        getParentFragmentManager().beginTransaction().replace(R.id.fragment_container_customer,
                ldf).addToBackStack(null).commit();
    }
}
