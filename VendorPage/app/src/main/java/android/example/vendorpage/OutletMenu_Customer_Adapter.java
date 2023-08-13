package android.example.vendorpage;

import android.telephony.PhoneNumberFormattingTextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class OutletMenu_Customer_Adapter extends FirebaseRecyclerAdapter<model,OutletMenu_Customer_Adapter.myviewholder>
{
    String Phonenumber;
    public void setPhonenumber(String Phonenumber) {
        this.Phonenumber = Phonenumber;
    }





    public OutletMenu_Customer_Adapter(@NonNull FirebaseRecyclerOptions<model> options) {
        super(options);

    }

    @Override
    protected void onBindViewHolder(@NonNull OutletMenu_Customer_Adapter.myviewholder holder, int position, @NonNull model model)
    {

        holder.item_name.setText(model.getItem_name());
        holder.item_price.setText(model.getprice());
        Glide.with(holder.img.getContext()).load(model.getPurl()).into(holder.img);

        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.processinsert(model);
            }
        });

    }



    @NonNull
    @Override
    public OutletMenu_Customer_Adapter.myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerow_menu_customer, parent, false);
        return new OutletMenu_Customer_Adapter.myviewholder(view);
    }



    class myviewholder extends RecyclerView.ViewHolder
    {

        CircleImageView img;
        TextView item_name,item_price;
        ImageView add;


        public myviewholder(@NonNull View itemView)
        {
            super(itemView);
            img=(CircleImageView) itemView.findViewById(R.id.food_pic);
            item_name=(TextView)itemView.findViewById(R.id.food_name);
            item_price=(TextView)itemView.findViewById(R.id.food_price);
            add=(ImageView)itemView.findViewById(R.id.add_to_cart_icon);

        }
        private void processinsert(model model)
        {
            Map<String,Object> map=new HashMap<>();
            map.put("cart_item_name",item_name.getText().toString());
            map.put("cart_item_price",item_price.getText().toString());
            map.put("cart_item_purl",model.getPurl());
            map.put("cart_item_quantity",String.valueOf(1));


            FirebaseDatabase.getInstance().getReference().child("Users").child("Customer").child(Phonenumber).child("cart")
                    .push().setValue(map)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(item_name.getContext(), "Inserted Successfully",Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e)
                        {
                            Toast.makeText(item_name.getContext(),"Could not insert",Toast.LENGTH_LONG).show();
                        }
                    });

        }


    }


}





