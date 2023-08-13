package android.example.vendorpage;

import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import de.hdodenhof.circleimageview.CircleImageView;

public class outletList_adapter extends FirebaseRecyclerAdapter<model,outletList_adapter.myviewholder>
{
    private OnItemClickListener listener;

    public outletList_adapter(@NonNull FirebaseRecyclerOptions<model> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull model model) {

        holder.outletName.setText(model.getOutletName());
        Log.v("Database","Database is linked");
        Glide.with(holder.outletPic.getContext()).load(model.getOutlet_purl()).into(holder.outletPic);

    }

    @NonNull
    @Override
    public outletList_adapter.myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerow_outletslist, parent, false);
        return new myviewholder(view);
    }


    class myviewholder extends RecyclerView.ViewHolder
    {

        CircleImageView outletPic;
        TextView outletName;


        public myviewholder(@NonNull View itemView)
        {
            super(itemView);
            outletPic=(CircleImageView) itemView.findViewById(R.id.outlet_picture);
            outletName=(TextView)itemView.findViewById(R.id.outlet_name);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position=getBindingAdapterPosition();
                    if(position!=RecyclerView.NO_POSITION && listener!=null)
                    {
                        listener.onItemClick(position);
                    }
                }
            });
        }
    }
    public interface OnItemClickListener
    {
        void onItemClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener)
    {
        this.listener=listener;
    }

}



