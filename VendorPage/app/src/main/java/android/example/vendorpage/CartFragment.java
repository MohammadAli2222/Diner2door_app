package android.example.vendorpage;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;


public class CartFragment extends Fragment {
    RecyclerView recview;
    CartAdapter adapter;
    String Phonenumber;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_cart, container, false);
        recview=(RecyclerView)view.findViewById(R.id.recview_my_cart);
        CustomerHomePage activity = (CustomerHomePage) getActivity();
        Phonenumber = activity.getPhonenumber();
        FirebaseRecyclerOptions<model> options =
                new FirebaseRecyclerOptions.Builder<model>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Users").child("Customer")
                                .child(Phonenumber).child("cart"), model.class)
                        .build();

        recview.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter=new CartAdapter(options);
        recview.setAdapter(adapter);
        adapter.setPhonenumber(Phonenumber);
        recview.setLayoutManager(new LinearLayoutManager(getContext()));
        TextView total=(TextView)view.findViewById(R.id.total_cart);

        final int[] product_cost = {0};

        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("Users").child("Customer")
                .child(Phonenumber).child("cart");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    model Model = snapshot.getValue(model.class);
                    int price=Integer.parseInt(Model.getCart_item_price());
                    int quantity=Integer.parseInt(Model.getCart_item_quantity());
                    int total=price*quantity;
                    product_cost[0] = product_cost[0]+total;


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
        total.setText(String.valueOf(product_cost[0]));

        Button placeOrder=(Button)view.findViewById(R.id.place_order);
        placeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fieldTransactionId;
                fieldTransactionId=String.valueOf(System.currentTimeMillis());
                Map<String, Object> map = new HashMap<>();
                map.put("Amount", "100");
                map.put("TransactionID", fieldTransactionId);

                FirebaseDatabase.getInstance().getReference().child("Current_Orders").push().setValue(map);
                Intent intent= new Intent(getActivity(),PaymentActivity.class);
                intent.putExtra("Phonenumber", Phonenumber);
                startActivity(intent);

            }
        });



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