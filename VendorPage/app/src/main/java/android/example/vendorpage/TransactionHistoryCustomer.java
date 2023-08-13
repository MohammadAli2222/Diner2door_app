package android.example.vendorpage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;


public class TransactionHistoryCustomer extends Fragment {
    RecyclerView recview;
    TransactionHistory_Customer_Adapter adapter;
    String Phonenumber;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_transaction_history_customer, container, false);
        recview=(RecyclerView)view.findViewById(R.id.recview_transaction_history);
        CustomerHomePage activity = (CustomerHomePage) getActivity();
        Phonenumber = activity.getPhonenumber();
        FirebaseRecyclerOptions<model> options =
                new FirebaseRecyclerOptions.Builder<model>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Users").child("Customer")
                                .child(Phonenumber).child("transaction_history_customer"), model.class)
                        .build();

        recview.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter=new TransactionHistory_Customer_Adapter(options);
        recview.setAdapter(adapter);


        recview.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }
}