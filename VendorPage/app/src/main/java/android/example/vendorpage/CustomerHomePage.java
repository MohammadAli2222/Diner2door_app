package android.example.vendorpage;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class CustomerHomePage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    public ActionBarDrawerToggle actionBarDrawerToggle;
    public DrawerLayout drawerLayout;
    String Phonenumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_home_page);
        Toolbar toolbar = findViewById(R.id.toolbar_customer);
        setSupportActionBar(toolbar);
        Bundle bundle=getIntent().getExtras();
        Phonenumber=bundle.getString("phonenumber");
        drawerLayout = findViewById(R.id.my_drawer_layout_customer);
        NavigationView navigationView = findViewById(R.id.nav_view_customer);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_customer,
                    new OutletsList_Customer()).commit();
            navigationView.setCheckedItem(R.id.customernav_customer_outlet);
        }
    }
    public String getPhonenumber()
    {
        return Phonenumber;
    }
    @Override
    public boolean onOptionsItemSelected (@NonNull MenuItem item){

        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.customernav_customer_outlet: {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_customer,
                        new OutletsList_Customer()).commit();
                break;

            }
            case R.id.customernav_customer_profile: {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_customer,
                        new Customer_Profile()).commit();
                break;
            }
            case R.id.customernav_my_cart: {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_customer,
                        new CartFragment()).commit();
                break;
            }
            case R.id.customernav_transaction_history:
            {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_customer,
                        new TransactionHistoryCustomer()).commit();
                break;
            }
            case R.id.customernav_logout:{
                Intent intent=new Intent(CustomerHomePage.this,MainActivity.class);
                startActivity(intent);
                finish();
                break;
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
