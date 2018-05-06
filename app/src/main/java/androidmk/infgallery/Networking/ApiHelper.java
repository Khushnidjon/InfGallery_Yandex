package androidmk.infgallery.Networking;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import androidmk.infgallery.Database.MyDatabase;
import androidmk.infgallery.listener.RequestListener;
import androidmk.infgallery.model.ListItem;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Xushnidjon on 5/1/2018.
 */

public class ApiHelper {

    //this Url is gotten from google drive api, and here after files directory my folder
    private static final String URL_DATA =
            "https://www.googleapis.com/drive/v2/files?q=%271I_rScRUbaFwZrQa_Oj6UnpRFgrZVGc0q%27+in+parents&key=AIzaSyCzijTtoFw-5L9RbSIFZ3LjFbxfP-0IAzQ";

    List<ListItem> listItems;
    Context mContext;
    MyDatabase detailsDatabaseHelper;
    RequestListener requestListener;

    public ApiHelper(Context context, RequestListener requestListener) {
        listItems = new ArrayList<>();
        detailsDatabaseHelper = MyDatabase.getInstance(context);
        this.requestListener = requestListener;
        mContext = context;
    }

    //requesting the responce to the db
    public void loadRecyclerViewData() {
        final ProgressDialog progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                URL_DATA,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        listItems.clear();
                        progressDialog.dismiss();
                        try {

                            //from JsonObj i got item JsonArray
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("items");

                            //getting all items in JsonArray
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject o = jsonArray.getJSONObject(i);
                                //here i am getting 3 type of data: title, url, size from Json Format
                                String title = o.getString("title");
                                String url = "https://drive.google.com/uc?export=download&id=" + o.getString("id");
                                String size = o.getString("fileSize");

                                ListItem item = new ListItem(title, size, url);
                                listItems.add(item);
                            }
                            detailsDatabaseHelper.addSaveDetails(listItems);
                            requestListener.onListReady(detailsDatabaseHelper.getAllSaveDetails());
                            SharedPreferences preferences = mContext.getSharedPreferences("my_preferences", MODE_PRIVATE);
                            preferences.edit().putBoolean("onboarding_complete", true).apply();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(mContext, "Check your Connection", Toast.LENGTH_LONG).show();
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        requestQueue.add(stringRequest);
    }
}
