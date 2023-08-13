package android.example.vendorpage;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Spinner spinner = (Spinner) findViewById(R.id.user_type);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,getResources().getStringArray(R.array.usertype));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        final EditText email = findViewById(R.id.email);
        final EditText fullname = findViewById(R.id.name);
        final EditText phone = findViewById(R.id.phone_number);
        final EditText outlet_purl=findViewById(R.id.outlet_purl);
        final EditText password = findViewById(R.id.password);
        final EditText confirm_password = findViewById(R.id.confirm_password);
        final Button register_btn = findViewById(R.id.register_button);
        final TextView signInBtn = findViewById(R.id.sign_in_now_link);

        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

                final String Username = fullname.getText().toString();
                final String Email = email.getText().toString();
                final String PhoneNumber = phone.getText().toString();
                final String UserType=spinner.getSelectedItem().toString();
                final String Password = password.getText().toString();
                final String confirmTxt = confirm_password.getText().toString();
                final String OutletPURL=outlet_purl.getText().toString();

                if(Email.isEmpty()||Password.isEmpty()||Username.isEmpty()||PhoneNumber.isEmpty()||confirmTxt.isEmpty()){
                    Toast.makeText(RegisterActivity.this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
                }

                else if(!Password.equals(confirmTxt))
                {
                    Toast.makeText(RegisterActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                }

                else
                {
                    DatabaseReference databaseReference;
                    databaseReference=FirebaseDatabase.getInstance().getReference();
                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener()
                    {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot)
                        {
                            if (UserType.contains("Customer"))
                            {

                                    HashMap<String, Object> userdataMap = new HashMap<>();
                                    userdataMap.put("Password", Password);
                                    userdataMap.put("Username", Username);
                                    userdataMap.put("Email", Email);
                                    userdataMap.put("UID", "UID_3");
                                    FirebaseDatabase.getInstance().getReference().child("Users").child("Customer").child(PhoneNumber).
                                            child("user_data").updateChildren(userdataMap)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {
                                                    Toast.makeText(RegisterActivity.this, "Congratulations, your account has been created.", Toast.LENGTH_SHORT).show();
                                                   /* Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                    startActivity(intent);*/
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Toast.makeText(RegisterActivity.this, "Network Error: Please try again after some time...", Toast.LENGTH_SHORT).show();
                                                }
                                            });



                            }
                            else if (UserType.contains("Vendor"))
                            {

                                    HashMap<String, Object> userdataMap = new HashMap<>();
                                    HashMap<String, Object> map = new HashMap<>();
                                    map.put("OutletName",Username);
                                    map.put("outlet_purl",OutletPURL);
                                    userdataMap.put("Password", Password);
                                    userdataMap.put("PhoneNumber", PhoneNumber);
                                    userdataMap.put("Email", Email);
                                    userdataMap.put("UID", "UID_3");


                                    FirebaseDatabase.getInstance().getReference().child("Vendors").child("Outlet_List")
                                                    .push().setValue(map);

                                FirebaseDatabase.getInstance().getReference().child("Users").child("Vendor").child(Username).child("user_data")
                                        .updateChildren(userdataMap)
                                           .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {
                                                    Toast.makeText(RegisterActivity.this, "Congratulations, your account has been created.", Toast.LENGTH_SHORT).show();

                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Toast.makeText(RegisterActivity.this, "Network Error: Please try again after some time...", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                }
                            databaseReference.removeEventListener(this);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });


                }

            }
        });

        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}