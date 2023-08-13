package android.example.vendorpage;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class TransactionHistory_Customer_Adapter extends FirebaseRecyclerAdapter<model,TransactionHistory_Customer_Adapter.myviewholder>
{
    public TransactionHistory_Customer_Adapter(@NonNull FirebaseRecyclerOptions<model> options) {
        super(options);
    }
    @Override
    protected void onBindViewHolder(@NonNull TransactionHistory_Customer_Adapter.myviewholder holder, int position, @NonNull model model)
    {


        holder.totalAmount.setText(model.getTotal_amount());
        holder.transactionID.setText(model.getTransactionID());

    }

    @NonNull
    @Override
    public TransactionHistory_Customer_Adapter.myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.singlerow_transactionhistory_customer,parent,false);
        return new TransactionHistory_Customer_Adapter.myviewholder(view);
    }

    class myviewholder extends RecyclerView.ViewHolder
    {

        TextView totalAmount,transactionID;


        public myviewholder(@NonNull View itemView)
        {
            super(itemView);

            totalAmount=(TextView)itemView.findViewById(R.id.total_amount);
            transactionID=(TextView)itemView.findViewById(R.id.transactionID);


        }
    }

}



