package android.example.vendorpage;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class TransactionHistory_adapter extends FirebaseRecyclerAdapter<model,TransactionHistory_adapter.myviewholder>
{
    public TransactionHistory_adapter(@NonNull FirebaseRecyclerOptions<model> options) {
        super(options);
    }
    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull model model)
    {


        holder.totalAmount.setText(model.getTotal_amount());
        holder.transactionID.setText(model.getTransactionID());

    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.singlerow_transaction_history,parent,false);
        return new myviewholder(view);
    }

    class myviewholder extends RecyclerView.ViewHolder
    {

        TextView customerPhoneNumber,totalAmount,transactionID;


        public myviewholder(@NonNull View itemView)
        {
            super(itemView);
            customerPhoneNumber=itemView.findViewById(R.id.customer_phone_number);
            totalAmount=(TextView)itemView.findViewById(R.id.total_amount);
            transactionID=(TextView)itemView.findViewById(R.id.transactionID);


        }
    }

}


