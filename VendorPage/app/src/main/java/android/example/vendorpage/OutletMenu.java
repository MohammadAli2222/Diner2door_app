package android.example.vendorpage;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.FirebaseDatabase;


public class OutletMenu extends Fragment
{

    myadapter adapter;
    FloatingActionButton fb;
    RecyclerView recview;
    String OutletName;
    String Phonenumber;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment

        View view=inflater.inflate(R.layout.fragment_outlet_menu, container, false);
        VendorHomePage activity = (VendorHomePage) getActivity();
        OutletName=activity.getOutletName();
        Log.v("OutletName",OutletName);
        recview=(RecyclerView)view.findViewById(R.id.recview);
        FirebaseRecyclerOptions<model> options = new FirebaseRecyclerOptions.Builder<model>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("Vendors").child(OutletName)
                        .child("Menu"), model.class)
                .build();
        recview.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter=new myadapter(options);
        adapter.setOutletName(OutletName);
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