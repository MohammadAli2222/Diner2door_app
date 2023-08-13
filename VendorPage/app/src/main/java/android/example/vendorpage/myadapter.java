package android.example.vendorpage;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class myadapter extends FirebaseRecyclerAdapter<model,myadapter.myviewholder>
{
    String OutletName;
    public void setOutletName(String OutletName) {
        this.OutletName = OutletName;
    }

    public myadapter(@NonNull FirebaseRecyclerOptions<model> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull model model)
    {

        holder.item_name.setText(model.getItem_name());

        holder.price.setText(model.getprice());
        Glide.with(holder.img.getContext()).load(model.getPurl()).into(holder.img);

        holder.edit.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View view) {
                                               final DialogPlus dialogPlus = DialogPlus.newDialog(holder.img.getContext())
                                                       .setContentHolder(new ViewHolder(R.layout.dialogcontent))
                                                       .setExpanded(true, 1100)
                                                       .create();
                                               View myview = dialogPlus.getHolderView();
                                               final EditText purl = myview.findViewById(R.id.uimgurl);
                                               final EditText item_name = myview.findViewById(R.id.itemname);
                                               final EditText price = myview.findViewById(R.id.price_update);
                                               Button submit = myview.findViewById(R.id.usubmit);
                                               purl.setText(model.getPurl());
                                               item_name.setText(model.getItem_name());
                                               price.setText(model.getprice());
                                               dialogPlus.show();
                                               submit.setOnClickListener(new View.OnClickListener() {
                                                   @Override
                                                   public void onClick(View view) {
                                                       Map<String, Object> map = new HashMap<>();
                                                       map.put("purl", purl.getText().toString());
                                                       map.put("item_name", item_name.getText().toString());
                                                       map.put("price", price.getText().toString());

                                                       FirebaseDatabase.getInstance().getReference().child("Vendors").child(OutletName).child("Menu")
                                                               .child(getRef(position).getKey()).updateChildren(map)
                                                               .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                   @Override
                                                                   public void onSuccess(Void aVoid) {
                                                                       dialogPlus.dismiss();
                                                                   }
                                                               })
                                                               .addOnFailureListener(new OnFailureListener() {
                                                                   @Override
                                                                   public void onFailure(@NonNull Exception e) {
                                                                       dialogPlus.dismiss();
                                                                   }
                                                               });

                                                   }


                                               });

                                           }
                                       });

        holder.delete.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                AlertDialog.Builder builder=new AlertDialog.Builder(holder.img.getContext());
                builder.setTitle("Delete Panel");
                builder.setMessage("Do you wish to delete this item ?");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                FirebaseDatabase.getInstance().getReference().child("Vendors").child(OutletName).child("Menu")
                                        .child(getRef(position).getKey()).removeValue();


                            }
                        });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener()
                    {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                        {
                        }


                });

                builder.show();
            }
        });
    }





    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerow,parent,false);

        return new myviewholder(view);
    }

    class myviewholder extends RecyclerView.ViewHolder
    {
        CircleImageView img;
        TextView outlet_name,item_name,price;
        ImageView edit,delete;
        Button deletebtn;

        public myviewholder(@NonNull View itemView)
        {
            super(itemView);
            img=(CircleImageView)itemView.findViewById(R.id.img1);
            item_name=(TextView)itemView.findViewById(R.id.coursetext);
            price=(TextView)itemView.findViewById(R.id.emailtext);
            edit=(ImageView)itemView.findViewById(R.id.editicon);
            delete=(ImageView)itemView.findViewById(R.id.deleteicon);




        }
    }

}
