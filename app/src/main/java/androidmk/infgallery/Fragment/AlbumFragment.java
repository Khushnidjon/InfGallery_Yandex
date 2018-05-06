package androidmk.infgallery.Fragment;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;
import java.util.Random;

import androidmk.infgallery.Adapter.AlbumAdapter;
import androidmk.infgallery.Connection;
import androidmk.infgallery.Database.MyDatabase;
import androidmk.infgallery.Networking.ApiHelper;
import androidmk.infgallery.listener.RequestListener;
import androidmk.infgallery.R;
import androidmk.infgallery.model.ListItem;

public class AlbumFragment extends Fragment implements RequestListener {

    public RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    ApiHelper apiHelper;
    Connection connection;
    FloatingActionButton actionButton;
    List<ListItem> listItem;
    MyDatabase myDB;
    Random ran;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public AlbumFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_album, container, false);


        recyclerView = view.findViewById(R.id.recyclerView);
        actionButton = view.findViewById(R.id.fab);
        final ImageView preview = view.findViewById(R.id.iv_detail);

        connection = new Connection(getContext());
        myDB = MyDatabase.getInstance(getActivity());
        listItem = myDB.getAllSaveDetails();
        adapter = new AlbumAdapter(listItem, getActivity());
        apiHelper = new ApiHelper(getActivity(), this);

        //Used grid layout which has 3 spancount
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(view.getContext(), 3);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);


        //used shared preference, to load data for the first time
        final SharedPreferences preferences = getActivity().getSharedPreferences("my_preferences", Context.MODE_PRIVATE);
        if (!preferences.getBoolean("onboarding_complete", false)) {
            apiHelper.loadRecyclerViewData();
        } else {
            ran = new Random();
            int random = ran.nextInt(listItem.size());
            Glide.with(getContext()).load(listItem.get(random).getImageURL()).into(preview);
        }

        actionButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                //checking whether connected to the internet and refresh
                if (connection.isConnected()) {
                    myDB.deleteDataBase();
                    apiHelper.loadRecyclerViewData();
                    listItem.clear();
                } else {
                    Snackbar.make(container, "You're out of Internet!", Snackbar.LENGTH_LONG)
                            .setDuration(2000).show();
                }
            }
        });

        return view;
    }

    @Override
    public void onListReady(List<ListItem> list) {
        listItem.clear();
        listItem.addAll(list);
        adapter.notifyDataSetChanged();
    }
    
}