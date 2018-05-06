package androidmk.infgallery.listener;

import java.util.List;

import androidmk.infgallery.model.ListItem;

/**
 * Created by Xushnidjon on 5/1/2018.
 */

public interface RequestListener {
    void onListReady(List<ListItem> list);
}
