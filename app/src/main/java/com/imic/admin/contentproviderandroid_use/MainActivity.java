package com.imic.admin.contentproviderandroid_use;

import android.annotation.SuppressLint;
import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.provider.Browser;
import android.provider.CallLog;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import static android.provider.CallLog.*;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnshowallcontact;
    Button btnaccesscalllog;
    Button btnaccessmediastore;
    Button btnaccessbookmarks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnshowallcontact = findViewById(R.id.btnshowallcontact);
        btnshowallcontact.setOnClickListener(this);
        btnaccesscalllog = findViewById(R.id.btnaccesscalllog);
        btnaccesscalllog.setOnClickListener(this);
        btnaccessmediastore = findViewById(R.id.btnmediastore);
        btnaccessmediastore.setOnClickListener(this);
        btnaccessbookmarks = findViewById(R.id.btnaccessbookmarks);
        btnaccessbookmarks.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        Intent intent = null;
        if (v == btnshowallcontact) {
            intent = new Intent(this, ShowAllContactActivity.class);
            startActivity(intent);
        } else if (v == btnaccesscalllog) {
            accessTheCallLog();
        } else if (v == btnaccessmediastore) {
            accessMediaStore();
        } else if (v == btnaccessbookmarks) {
            // accessBookmarks();
        }
    }

    /**
     * hàm lấy danh sách lịch sử cuộc gọi
     * với thời gian nhỏ hơn 30 giây và sắp xếp theo ngày gọi
     */
    public void accessTheCallLog() {
        String[] projection = new String[]{
                Calls.DATE,
                Calls.NUMBER,
                Calls.DURATION
        };
        @SuppressLint("MissingPermission") Cursor c = getContentResolver().query(
                Calls.CONTENT_URI,
                projection,
                Calls.DURATION + "<?", new String[]{"30"},
                Calls.DATE + " Asc");
        c.moveToFirst();
        String s = "";
        while (c.isAfterLast() == false) {
            for (int i = 0; i < c.getColumnCount(); i++) {
                s += c.getString(i) + " - ";
            }
            s += "\n";
            c.moveToNext();
        }
        c.close();
        Toast.makeText(this, s, Toast.LENGTH_LONG).show();
    }

    /**
     * hàm đọc danh sách các Media trong SD CARD
     */
    public void accessMediaStore() {
        String[] projection = {
                MediaStore.MediaColumns.DISPLAY_NAME,
                MediaStore.MediaColumns.DATE_ADDED,
                MediaStore.MediaColumns.MIME_TYPE
        };
        CursorLoader loader = new CursorLoader
                (this, MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                        projection, null, null, null);
        Cursor c = loader.loadInBackground();
        c.moveToFirst();
        String s = "";
        while (!c.isAfterLast()) {
            for (int i = 0; i < c.getColumnCount(); i++) {
                s += c.getString(i) + " - ";
            }
            s += "\n";
            c.moveToNext();
        }
        Toast.makeText(this, s, Toast.LENGTH_LONG).show();
        c.close();
    }

    /**
     * hàm đọc danh sách Bookmark trong trình duyệt
     */
  /*  public void accessBookmarks() {
        String[] projection = {
                Browser.BookmarkColumns.TITLE,
                Browser.BookmarkColumns.URL,
        };
        Cursor c = getContentResolver()
                .query(Browser.BOOKMARKS_URI, projection,
                        null, null, null);
        c.moveToFirst();
        String s = "";
        int titleIndex = c.getColumnIndex
                (Browser.BookmarkColumns.TITLE);
        int urlIndex = c.getColumnIndex
                (Browser.BookmarkColumns.URL);
        while (!c.isAfterLast()) {
            s += c.getString(titleIndex) + " - " +
                    c.getString(urlIndex);
            c.moveToNext();
        }
        c.close();
        Toast.makeText(this, s, Toast.LENGTH_LONG).show();
    }*/
}
