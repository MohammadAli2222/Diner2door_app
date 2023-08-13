package android.example.vendorpage;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class CartAdapter extends FirebaseRecyclerAdapter<model,CartAdapter.myviewholder>
{

    String Phonenumber;
    public void setPhonenumber(String Phonenumber) {
        this.Phonenumber = Phonenumber;
    }
    final int[] product_cost = {0};

    public CartAdapter(@NonNull FirebaseRecyclerOptions<model> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull CartAdapter.myviewholder holder, int position, @NonNull model model) {

        holder.item_name.setText(model.getCart_item_name());
        holder.item_price.setText(model.getCart_item_price());
        holder.item_quantity.setText(model.getCart_item_quantity());
        Glide.with(holder.img.getContext()).load(model.getCart_item_purl()).into(holder.img);
        holder.addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int quantity=Integer.parseInt(holder.item_quantity.getText().toString());
                quantity++;
                holder.item_quantity.setText(String.valueOf(quantity));
            }
        });
        holder.subtractbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int quantity=Integer.parseInt(holder.item_quantity.getText().toString());
                quantity--;
                if(quantity<0)
                {
                    quantity=0;
                }
                model.cart_item_quantity=String.valueOf(quantity);
                holder.item_quantity.setText(model.getCart_item_quantity());
            }
        });
        holder.deleteimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                FirebaseDatabase.getInstance().getReference().child("Users").child("Customer").child(Phonenumber)
                        .child("cart").child(getRef(position).getKey()).removeValue();

            }
        });










    }
    public int ProductCost()
    {
        return product_cost[0];
    }
    @NonNull
    @Override
    public CartAdapter.myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerow_cart,parent,false);

        return new CartAdapter.myviewholder(view);
    }




    class myviewholder extends RecyclerView.ViewHolder
    {
        CircleImageView img;
        TextView item_name,item_price,item_quantity;
        Button addbtn,subtractbtn;
        ImageView deleteimg;

        public myviewholder(@NonNull View itemView)
        {
            super(itemView);
            img=(CircleImageView)itemView.findViewById(R.id.item_cart_pic);
            item_name=(TextView)itemView.findViewById(R.id.item_cart_name);
            item_price=(TextView)itemView.findViewById(R.id.item_cart_price);
            addbtn=(Button) itemView.findViewById(R.id.add);
            subtractbtn=(Button) itemView.findViewById(R.id.subtract);
            item_quantity=(TextView) itemView.findViewById(R.id.item_cart_quantity);
            deleteimg=(ImageView) itemView.findViewById(R.id.delete_from_cart);
        }
    }
}
