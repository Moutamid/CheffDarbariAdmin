package com.moutamid.cheffdarbariadminn.activities;

import android.os.Bundle;
import android.view.Menu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.messaging.FirebaseMessaging;
import com.moutamid.cheffdarbariadminn.R;
import com.moutamid.cheffdarbariadminn.databinding.ActivityNavigationDrawerBinding;
import com.moutamid.cheffdarbariadminn.utils.Constants;

import java.util.ArrayList;

public class NavigationDrawerActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityNavigationDrawerBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityNavigationDrawerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.appBarNavigationDrawer.toolbar);
        /*int i;
        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        i = list.get(10);*/
        FirebaseMessaging.getInstance().subscribeToTopic(Constants.ADMIN_NOTIFICATIONS);

        DrawerLayout drawer = binding.drawerLayout;
        binding.appBarNavigationDrawer.menuBtn.setOnClickListener(view -> {
            if (drawer.isOpen())
                drawer.close();

            else drawer.open();
        });
        NavigationView navigationView = binding.navView;
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_add_job,

                R.id.nav_jobs_posted,
                R.id.nav_new_party_bookings,
                R.id.nav_accepted_jobs,
                R.id.nav_completed_jobs,
                R.id.nav_affiliate_partners,
                R.id.nav_chef_list
        )
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this,
                R.id.nav_host_fragment_content_navigation_drawer);
        NavigationUI.setupActionBarWithNavController(this, navController,
                mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation_drawer, menu);
        return true;
    }


    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this,
                R.id.nav_host_fragment_content_navigation_drawer);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}