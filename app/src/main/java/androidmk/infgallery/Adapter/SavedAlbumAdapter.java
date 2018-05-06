package androidmk.infgallery.Adapter;

import android.content.Context;
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

import androidmk.infgallery.R;
import androidmk.infgallery.model.ListItem;

/**
 * Created by Xushnidjon on 4/28/2018.
 * Adapter for the second fragment
 */

public class SavedAlbumAdapter extends RecyclerView.Adapter<SavedAlbumAdapter.ViewHolder> {

    private List<ListItem> savedListItems;
    private Context savedContext;

    public SavedAlbumAdapter(List<ListItem> savedListItems, Context savedContext) {
        this.savedListItems = savedListItems;
        this.savedContext = savedContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_saved, parent, false);
        return new SavedAlbumAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final ListItem listItem = savedListItems.get(position);
        holder.ViewDescription.setText(listItem.getDescription());
        holder.SizeDescription.setText(listItem.getSize());

        //opening image using glide
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.yandex_album);
        Glide.with(savedContext).setDefaultRequestOptions(requestOptions).load(listItem.getImageURL()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return savedListItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView ViewDescription;
        public TextView SizeDescription;
        public ImageView imageView;
        public ImageView collapsingImageView;
        public RelativeLayout relativeLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            ViewDescription = itemView.findViewById(R.id.text_view_name);
            SizeDescription = itemView.findViewById(R.id.text_view_size);
            imageView = itemView.findViewById(R.id.item_image);
            collapsingImageView = itemView.findViewById(R.id.iv_detail);
            relativeLayout = itemView.findViewById(R.id.layout_item_saved);
        }
    }
}
