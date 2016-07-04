package ua.stepiukyevhen.yevhenstepiuktestapp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static final String PREFS = "SHARED_PREFS";
    public static final String NAME = "NAME";
    public static final String DAY_OF_MONTH = "DAY_OF_MONTH";
    public static final String MONTH = "MONTH";
    public static final String YEAR = "YEAR";
    public static final String LIST = "LIST";

    AboutMeFragment aboutMeFragment;
    MainScreenFragment mainScreenFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        aboutMeFragment = new AboutMeFragment();
        mainScreenFragment = new MainScreenFragment();

        FragmentManager fragmentManager = getSupportFragmentManager();

        if (savedInstanceState == null) {
            navigationView.setCheckedItem(R.id.nav_main);
            fragmentManager.beginTransaction()
                    .replace(R.id.container, mainScreenFragment)
                    .commit();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.nav_main:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, mainScreenFragment)
                        .commit();
                break;
            case R.id.nav_about:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, aboutMeFragment)
                        .commit();
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
