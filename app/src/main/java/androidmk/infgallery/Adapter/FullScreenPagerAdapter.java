package androidmk.infgallery.Adapter;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;

import java.io.File;
import java.util.List;

import androidmk.infgallery.R;
import androidmk.infgallery.listener.FullScreenInterface;
import androidmk.infgallery.model.ListItem;

/**
 * Created by Xushnidjon on 5/5/2018.
 * Pager for Full screen
 */

public class FullScreenPagerAdapter extends PagerAdapter {
    private Context mContext;
    private List<ListItem> listItems;
    private FullScreenInterface fullScreenInterface;
    public FullScreenPagerAdapter(Context context, List<ListItem> list, FullScreenInterface fullScreenInterface){
        listItems = list;
        mContext = context;
        this.fullScreenInterface = fullScreenInterface;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.pager_slides, container, false);

        PhotoView photoView = view.findViewById(R.id.full_screen);
        ImageButton shareButton = view.findViewById(R.id.share_photo);
        ImageButton imageButton = view.findViewById(R.id.arrow_back);
        ImageButton downloadButton = view.findViewById(R.id.download_photo);
        final String Url = listItems.get(position).getImageURL();
        final String title = listItems.get(position).getDescription();
        Glide.with(mContext).load(Url).into(photoView);

        //sharing the content
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = listItems.get(position).getImageURL();
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                mContext.startActivity(Intent.createChooser(sharingIntent, "Share via:"));

            }
        });

        //back button finishes the Activity
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fullScreenInterface.onFinish();
            }
        });

        //downloading image
        downloadButton.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                String filename = title;
                String downloadUrlOfImage = Url;
                File direct = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath() + "/" + "InfGallery" + "/");
                if (!direct.exists()) {
                    direct.mkdir();
                }
                //Using DownloadManager for saving image
                DownloadManager dm = (DownloadManager) mContext.getSystemService(Context.DOWNLOAD_SERVICE);
                Uri downloadUri = Uri.parse(downloadUrlOfImage);
                DownloadManager.Request request = new DownloadManager.Request(downloadUri);
                request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE)
                        .setAllowedOverRoaming(false)
                        .setTitle(filename)
                        .setMimeType("image/jpeg")
                        .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                        .setDestinationInExternalPublicDir(Environment.DIRECTORY_PICTURES,
                                File.separator + "Gallery" + File.separator + filename);

                dm.enqueue(request);
            }
        });

        container.addView(view, 0);
        return view;
    }


    @Override
    public int getCount() {
        return listItems.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }
}
