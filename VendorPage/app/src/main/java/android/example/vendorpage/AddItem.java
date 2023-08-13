package android.example.vendorpage;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class AddItem extends AppCompatActivity {
    EditText item_name, price, purl;
    Button back, submit;

    String OutletName;
    String data;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        Bundle bundle = getIntent().getExtras();
        OutletName = bundle.getString("additem");
        item_name = (EditText) findViewById(R.id.item_name);
        price = (EditText) findViewById(R.id.price);
        purl = (EditText) findViewById(R.id.purl);

        back = (Button) findViewById(R.id.add_back);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), VendorHomePage.class));
                finish();
            }
        });
        submit = (Button) findViewById(R.id.add_submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                processinsert();
            }
        });
    }


    private void processinsert() {
        Map<String, Object> map = new HashMap<>();
        map.put("item_name", item_name.getText().toString());
        map.put("price", price.getText().toString());
        map.put("purl", purl.getText().toString());
        Log.i("Receivedphone",OutletName);


        FirebaseDatabase.getInstance().getReference().child("Vendors").child(OutletName).child("Menu")
                .push().setValue(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        item_name.setText("");
                        price.setText("");
                        purl.setText("");

                        Toast.makeText(getApplicationContext(), "Inserted Successfully", Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Could not insert", Toast.LENGTH_LONG).show();
                    }
                });




    }


}