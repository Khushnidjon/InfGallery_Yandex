package androidmk.infgallery.Activity;


import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.util.List;
import androidmk.infgallery.Adapter.FullScreenPagerAdapter;
import androidmk.infgallery.Database.MyDatabase;
import androidmk.infgallery.R;
import androidmk.infgallery.listener.FullScreenInterface;
import androidmk.infgallery.model.ListItem;

//this class used for opening image in full screen format
public class FullScreen extends AppCompatActivity implements FullScreenInterface{

    private static ViewPager mPager;
    private int position;
    MyDatabase dbMain;
    List<ListItem> listReference;
    FullScreenPagerAdapter viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen);
        mPager = findViewById(R.id.pager_slide_view_pager);

        //used for is there enough space or not
        isStoragePermissionGranted();

        //getting info from database
        dbMain = MyDatabase.getInstance(this);
        position = getIntent().getIntExtra("image", 0);
        listReference = dbMain.getAllSaveDetails();
        viewPagerAdapter = new FullScreenPagerAdapter(this, listReference, this);
        mPager.setAdapter(viewPagerAdapter);
        mPager.setCurrentItem(position);
    }

    public boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        } else {
            return true;
        }
    }

    @Override
    public void onFinish() {
        finish();
    }
}

