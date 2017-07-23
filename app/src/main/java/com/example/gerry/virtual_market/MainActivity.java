package com.example.gerry.virtual_market;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button launchListOrderActivity;
    private Toolbar toolbar;
    private DrawerLayout mDrawer;
    private NavigationView nvDrawer;
    private ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
//        getSupportActionBar().setTitle("Main Page");

        //Drawer Layout
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerToggle = new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.drawer_open, R.string.drawer_close);
        mDrawer.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        nvDrawer = (NavigationView) findViewById(R.id.nvView);
        setupDrawerContent(nvDrawer);

        launchListOrderActivity = (Button) findViewById(R.id.order_list_button);
        launchListOrderActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (PreferencesController.isLoggedIn(getApplicationContext())){
                    launchActivity();
                } else{
                    Intent intentHelp = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intentHelp);
                }

            }
        });
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }

    public void selectDrawerItem(MenuItem menuItem) {
        switch(menuItem.getItemId()) {
            case R.id.nav_home:
                Intent intentHome = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intentHome);
                break;
            case R.id.nav_login:
                Intent intentHelp = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intentHelp);
                break;
            case R.id.nav_logout:
                PreferencesController.removeUser(getApplicationContext()); // remove user id from shared preference
                finish();
                break;
            default:
                Intent intentDefault = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intentDefault);
        }
        menuItem.setChecked(true);
        setTitle(menuItem.getTitle());
        mDrawer.closeDrawers();
    }

    private void launchActivity(){
        Intent intent = new Intent(this, ListOrderActivity.class);
        startActivity(intent);
    }
}
