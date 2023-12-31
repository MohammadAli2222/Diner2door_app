package android.example.vendorpage;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.FirebaseDatabase;

public class TransactionHistory_Vendor extends Fragment {


    TransactionHistory_adapter adapter;
    RecyclerView recview;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view=inflater.inflate(R.layout.fragment_transaction_history__vendor, container, false);
        recview=(RecyclerView)view.findViewById(R.id.recview_transaction_history);
        FirebaseRecyclerOptions<model> options =
                new FirebaseRecyclerOptions.Builder<model>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Vendors").child("Yummpys")
                                .child("TransactionHistory"), model.class)
                        .build();

        recview.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter=new TransactionHistory_adapter(options);
        recview.setAdapter(adapter);


        recview.setLayoutManager(new LinearLayoutManager(getContext()));
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

}
