package android.example.vendorpage;

import android.content.Intent;
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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Spinner spinner = (Spinner) findViewById(R.id.login_usertype);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,getResources().getStringArray(R.array.usertype));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        final EditText phone = findViewById(R.id.login_phone);
        final EditText password = findViewById(R.id.login_password);
        final Button login_btn = findViewById(R.id.login_button);
        final TextView registerNowBtn = findViewById(R.id.register_now_link);

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String userTxt = phone.getText().toString();
                final String passwordTxt = password.getText().toString();
                final String user_type=spinner.getSelectedItem().toString();


                if (userTxt.isEmpty() || passwordTxt.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Please enter your Phone Number and password", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    DatabaseReference rootref = FirebaseDatabase.getInstance().getReference();
                    final DatabaseReference orders_Reference = rootref.child("Users").child(user_type);
                   orders_Reference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot)
                        {
                            if(user_type.equals("Customer"))
                            {
                                if (snapshot.hasChild(userTxt)) {
                                    final String getPassword = snapshot.child(userTxt).child("user_data").child("Password").getValue(String.class);
                                    if (getPassword.equals(passwordTxt)) {
                                        Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(LoginActivity.this, CustomerHomePage.class);
                                        intent.putExtra("phonenumber", userTxt);//
                                        startActivity(intent);
                                    } else {
                                        Toast.makeText(LoginActivity.this, "Incorrect Password", Toast.LENGTH_SHORT).show();
                                    }

                                } else {
                                    Toast.makeText(LoginActivity.this, "No such user exists!", Toast.LENGTH_SHORT).show();
                                }
                            }
                            else if(user_type.equals("Vendor"))
                            {
                                if (snapshot.hasChild(userTxt))
                                {
                                    final String getPassword = snapshot.child(userTxt).child("user_data").child("Password").getValue(String.class);
                                    if (getPassword.equals(passwordTxt)) {
                                        Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(LoginActivity.this, VendorHomePage.class);
                                        intent.putExtra("OutletName",userTxt);//
                                        startActivity(intent);
                                    }
                                    else
                                    {
                                        Toast.makeText(LoginActivity.this, "Incorrect Password", Toast.LENGTH_SHORT).show();
                                    }
                                }
                                else
                                {
                                    Toast.makeText(LoginActivity.this, "No such user exists!", Toast.LENGTH_SHORT).show();
                                }
                            }

                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });

        registerNowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
    }
}
