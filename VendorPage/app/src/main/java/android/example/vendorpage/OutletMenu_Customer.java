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
import com.google.firebase.database.Query;


public class OutletMenu_Customer extends Fragment {
    RecyclerView recview;
    OutletMenu_Customer_Adapter adapter;
    String Phonenumber;
    public String getPhonenumberfromOutletMenu()
    {
        return Phonenumber;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view=inflater.inflate(R.layout.fragment_outlet_menu__customer, container, false);
        recview=(RecyclerView)view.findViewById(R.id.recview_menu_customer);
        CustomerHomePage activity = (CustomerHomePage) getActivity();
        Phonenumber = activity.getPhonenumber();
        String value=getArguments().getString("YourKey");
        int i=Integer.parseInt(value);
        Query query = null;


        if(i==0)
        {
            query=FirebaseDatabase.getInstance().getReference().child("Vendors").child("ABC")
                    .child("Menu");
        }
        else if(i==1)
        {
            query=FirebaseDatabase.getInstance().getReference().child("Vendors").child("Yummpys")
                    .child("Menu");

        }
        else if(i==2)
        {
            query=FirebaseDatabase.getInstance().getReference().child("Vendors").child("Hotspot")
                    .child("Menu");

        }




        FirebaseRecyclerOptions<model> options =
                new FirebaseRecyclerOptions.Builder<model>()
                        .setQuery(query, model.class)
                        .build();

        recview.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter=new OutletMenu_Customer_Adapter(options);
        adapter.setPhonenumber(Phonenumber);
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