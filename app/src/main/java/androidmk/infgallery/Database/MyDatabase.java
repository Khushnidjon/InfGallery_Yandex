package androidmk.infgallery.Database;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;
import androidmk.infgallery.model.ListItem;

/**
 * Created by Xushnidjon on 4/29/2018.
 * MyDataBase is used for saving data and getting data
 * GalleryDatabase has table "images", and also it had 4 columns
 * addSaveDetails - storing data
 * getAllSaveDetails - getting data
 * deleteDataBase - for deleting database
 */

public class MyDatabase extends SQLiteOpenHelper {
    // Database Details
    private static final String DATABASE_NAME = "GalleryDatabase";
    private static final int DATABASE_VERSION = 1;

    //table
    private static final String TABLE_LIST = "images";

    // Table columns
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_SIZE = "date";
    private static final String KEY_URL_IMAGE = "urlImage";

    Context context;
    private static MyDatabase sInstance;

    public static synchronized MyDatabase getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new MyDatabase(context.getApplicationContext());
        }
        return sInstance;
    }

    private MyDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    //creating database
    @Override
    public void onCreate(SQLiteDatabase db) {
        String TABLE_ALL = "CREATE TABLE " + TABLE_LIST +
                "(" +
                KEY_ID + " INTEGER PRIMARY KEY," +
                KEY_NAME + " TEXT ," +
                KEY_SIZE + " TEXT ," +
                KEY_URL_IMAGE + " TEXT" +
                ")";

        db.execSQL(TABLE_ALL);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            db.execSQL("DROP TABLE IF EXISTS  " + TABLE_LIST);
            onCreate(db);
        }
    }

    //saving details to the list
    public void addSaveDetails(List<ListItem> list) {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try {
            for (int i = 0; i < list.size(); i++) {
                ContentValues values = new ContentValues();
                values.put(KEY_NAME, list.get(i).getDescription());
                values.put(KEY_SIZE, list.get(i).getSize());
                values.put(KEY_URL_IMAGE, list.get(i).getImageURL());
                db.insert(TABLE_LIST, null, values);
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
    }

    //getting details from db
    public List<ListItem> getAllSaveDetails() {
        List<ListItem> list = new ArrayList<>();

        String POSTS_SELECT_QUERY =
                String.format("SELECT * FROM %s ",
                        TABLE_LIST);
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(POSTS_SELECT_QUERY, null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    ListItem lists = new ListItem();
                    lists.setSize(cursor.getString(cursor.getColumnIndex(KEY_SIZE)));
                    lists.setDescription(cursor.getString(cursor.getColumnIndex(KEY_NAME)));
                    lists.setImageURL(cursor.getString(cursor.getColumnIndex(KEY_URL_IMAGE)));
                    list.add(lists);
                } while (cursor.moveToNext());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return list;
    }

    //deleting db
    public void deleteDataBase() {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try {
            db.delete(TABLE_LIST, null, null);
            db.setTransactionSuccessful();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
    }
}
