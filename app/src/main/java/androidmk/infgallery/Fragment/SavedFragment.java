package androidmk.infgallery.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.stone.vega.library.VegaLayoutManager;
import java.util.ArrayList;
import java.util.List;
import androidmk.infgallery.Adapter.SavedAlbumAdapter;
import androidmk.infgallery.Connection;
import androidmk.infgallery.Database.MyDatabase;
import androidmk.infgallery.Networking.ApiHelper;
import androidmk.infgallery.R;
import androidmk.infgallery.listener.RequestListener;
import androidmk.infgallery.model.ListItem;

public class SavedFragment extends Fragment implements RequestListener {

    public RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    List<ListItem> listItems;
    ApiHelper apiHelper;
    List<ListItem> listItem;
    Connection connection;
    MyDatabase myDB;

    public SavedFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_saved, container, false);

        connection = new Connection(getContext());

        recyclerView = view.findViewById(R.id.recyclerViewSaved);
        myDB = MyDatabase.getInstance(getActivity());

        listItem = myDB.getAllSaveDetails();
        adapter = new SavedAlbumAdapter(listItem, getActivity());

        recyclerView.setLayoutManager(new VegaLayoutManager());
        recyclerView.setAdapter(adapter);

        apiHelper = new ApiHelper(getActivity(), this);
        listItems = new ArrayList<>();

        return view;
    }

    @Override
    public void onListReady(List<ListItem> list) {
        listItem.clear();
        listItem.addAll(list);
        adapter.notifyDataSetChanged();
    }
}
