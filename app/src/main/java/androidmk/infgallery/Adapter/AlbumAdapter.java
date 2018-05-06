package androidmk.infgallery.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import androidmk.infgallery.Activity.FullScreen;
import androidmk.infgallery.R;
import androidmk.infgallery.model.ListItem;

/**
 * Created by Xushnidjon on 4/20/2018.
 * Adapter for the first fragment
 */

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.ViewHolder> {
    private List<ListItem> listItems;
    private Context context;

    public AlbumAdapter(List<ListItem> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final ListItem listItem = listItems.get(position);
        holder.textViewDescription.setText(listItem.getDescription());

        //opening image using glide
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.yandex_album);
        Glide.with(context).setDefaultRequestOptions(requestOptions).load(listItem.getImageURL()).into(holder.imageView);


        //when view clicked, it will be opened in full Screen
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent fullScreen = new Intent(context, FullScreen.class);
                fullScreen.putExtra("image", position);
                context.startActivity(fullScreen);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textViewDescription;
        public ImageView imageView;
        public ImageView topImage;
        public RelativeLayout relativeLayout;


        public ViewHolder(View itemView) {
            super(itemView);
            textViewDescription = itemView.findViewById(R.id.textViewDesc);
            imageView = itemView.findViewById(R.id.item_image_view);
            topImage = itemView.findViewById(R.id.iv_detail);
            relativeLayout = itemView.findViewById(R.id.layout_item);
        }
    }
}
