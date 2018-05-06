package androidmk.infgallery.Activity;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import androidmk.infgallery.Fragment.AlbumFragment;
import androidmk.infgallery.Fragment.SavedFragment;
import androidmk.infgallery.R;

//In this activity its implemented BottomNavigation
public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        final FragmentManager fragmentManager = getSupportFragmentManager();
        final Fragment fragment1 = new AlbumFragment();
        final Fragment fragment2 = new SavedFragment();

        //calling Album Fragment
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame, fragment1);
        fragmentTransaction.commit();

        //when items in bottom navigation clicked it opens given Fragments
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            //album fragment
                            case R.id.action_album:
                                fragmentTransaction = fragmentManager.beginTransaction();
                                fragmentTransaction.replace(R.id.frame, fragment1);
                                fragmentTransaction.commit();
                                return true;
                            //download fragment
                            case R.id.action_download:
                                fragmentTransaction = fragmentManager.beginTransaction();
                                fragmentTransaction.replace(R.id.frame, fragment2);
                                fragmentTransaction.commit();
                                return true;
                        }
                        return true;
                    }
                });
    }
}