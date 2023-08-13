package android.example.vendorpage;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;
import java.util.Map;


public class Customer_Profile extends Fragment {
    RecyclerView recview;
    personAdapter adapter;
    String Phonenumber;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_customer__profile, container, false);
        recview = view.findViewById(R.id.recview_customer_profile);
        CustomerHomePage activity = (CustomerHomePage) getActivity();
        Phonenumber = activity.getPhonenumber();

        // It is a class provide by the FirebaseUI to make a
        // query in the database to fetch appropriate data
        FirebaseRecyclerOptions<model> options =
        new FirebaseRecyclerOptions.Builder<model>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("Users").child("Customer")
                                .child(Phonenumber).child("user_data")
                            , model.class)
                .build();
        // Connecting object of required Adapter class to
        // the Adapter class itself
        recview.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new personAdapter(options);
        // Connecting Adapter class with the Recycler view*/
        recview.setAdapter(adapter);
        adapter.setPhonenumber(Phonenumber);
        recview.setLayoutManager(new LinearLayoutManager(getContext()));
        return view;

    }
    @Override public void onStart()
    {
        super.onStart();
        adapter.startListening();
    }

    // Function to tell the app to stop getting
    // data from database on stopping of the activity
    @Override public void onStop()
    {
        super.onStop();
        adapter.stopListening();
    }
}