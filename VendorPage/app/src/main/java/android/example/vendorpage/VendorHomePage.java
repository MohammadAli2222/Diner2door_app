package android.example.vendorpage;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

public class VendorHomePage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
{
    public ActionBarDrawerToggle actionBarDrawerToggle;
    public DrawerLayout drawerLayout;
    String OutletName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_home_page);
        Toolbar toolbar= (Toolbar) findViewById(R.id.toolbar_vendor);
        setSupportActionBar(toolbar);


        Bundle bundle=getIntent().getExtras();
        OutletName=bundle.getString("OutletName");
        Log.v("outletname",OutletName);


        drawerLayout = findViewById(R.id.my_drawer_layout_vendor);
        NavigationView navigationView = findViewById(R.id.nav_view_vendor);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        if (savedInstanceState == null) {


            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_vendor,
                    new OutletMenu()).commit();
            navigationView.setCheckedItem(R.id.menu_vendor);
        }
        FloatingActionButton fButton=(FloatingActionButton) findViewById(R.id.fadd);
        fButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(VendorHomePage.this,AddItem.class);
                intent.putExtra("additem",OutletName);
                startActivity(intent);
            }
        });
    }
    public String getOutletName()
    {
        return OutletName;
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
            case R.id.menu_vendor: {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_vendor,
                        new OutletMenu()).commit();
                break;

            }
            case R.id.transaction_history_vendor: {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_vendor,
                        new TransactionHistory_Vendor()).commit();
                break;
            }
            case R.id.logout_vendor: {
                Intent intent=new Intent(VendorHomePage.this,MainActivity.class);
                finish();
                break;
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
