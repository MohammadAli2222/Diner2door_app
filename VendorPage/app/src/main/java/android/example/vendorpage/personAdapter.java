package android.example.vendorpage;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;
import java.util.Map;

public class personAdapter extends FirebaseRecyclerAdapter<model,personAdapter.myviewholder>
{
    String Phonenumber;
    public void setPhonenumber(String Phonenumber) {
        this.Phonenumber = Phonenumber;
    }



    public personAdapter(@NonNull FirebaseRecyclerOptions<model> options) {
        super(options);
    }

    // Function to bind the view in Card view(here
    // "person.xml") iwth data in
    // model class(here "person.class")
    @Override
    protected void
    onBindViewHolder(@NonNull myviewholder holder,int position, @NonNull model model) {

        holder.fullname.setText(model.getCustomer_fullname());
        holder.email.setText(model.getCustomer_email());
        holder.phone.setText(model.getCustomer_phonenumber());

        Button editButton = (Button) holder.edit;
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DialogPlus dialogPlus = DialogPlus.newDialog(view.getContext())
                        .setContentHolder(new ViewHolder(R.layout.dialogcontent_editcustomerprofile))
                        .setExpanded(true, 1100)
                        .create();
                View myview = dialogPlus.getHolderView();
                final EditText edit_customer_fullname = myview.findViewById(R.id.edit_name);
                final EditText edit_customer_email = myview.findViewById(R.id.edit_email);
                final EditText edit_customer_phonenumber = myview.findViewById(R.id.edit_phone_number);
                Button submit = myview.findViewById(R.id.edit_submit);
                edit_customer_fullname.setText(model.getCustomer_fullname());
                edit_customer_email.setText(model.getCustomer_email());
                edit_customer_phonenumber.setText(model.getCustomer_phonenumber());
                dialogPlus.show();

                submit.setOnClickListener(new View.OnClickListener() {

                    public void onClick(View view) {
                        Map<String, Object> map = new HashMap<>();
                        map.put("Email", edit_customer_email.getText().toString());
                        map.put("Username", edit_customer_fullname.getText().toString());
                        map.put("PhoneNumber", edit_customer_phonenumber.getText().toString());


                        FirebaseDatabase.getInstance().getReference().child("Users").child("Customer").child(Phonenumber)
                                .child("user_data").updateChildren(map)
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
    }



    @NonNull
    @Override
    public myviewholder
    onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.singlerow_person, parent, false);
        return new personAdapter.myviewholder(view);
    }

    // Sub Class to create references of the views in Crad
    // view (here "person.xml")
    class myviewholder extends RecyclerView.ViewHolder {
        TextView fullname, email, phone;
        Button edit;

        public myviewholder(@NonNull View itemView) {
            super(itemView);

            fullname = itemView.findViewById(R.id.fullname);
            email = itemView.findViewById(R.id.email);
            phone = itemView.findViewById(R.id.phone);
            edit=(Button)itemView.findViewById(R.id.edit_button);

        }
    }
}
