package com.imic.admin.contentproviderandroid_use;

import android.app.Activity;
import android.content.CursorLoader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * @author Hà Quang Trung
 * @version 1.0.0
 * @description
 * @desc Technical Director, FPT-Software.
 * @created 7/6/2018
 * @updated 7/6/2018
 * @modified by
 * @updated on 7/6/2018
 * @since 1.0
 */
public class ShowAllContactActivity extends AppCompatActivity {
    Button btnback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all_contact);
        btnback = (Button) findViewById(R.id.btnback);
        btnback.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                finish();
            }
        });
        showAllContacts1();
    }

    /**
     * hàm danh toàn bộ danh bạ
     * dùng CursorLoader
     */
    public void showAllContacts1() {
        Uri uri = Uri.parse("content://contacts/people");
        ArrayList<String> list = new ArrayList<String>();
        CursorLoader loader = new
                CursorLoader(this, uri, null, null, null, null);
        Cursor c1 = loader.loadInBackground();
        c1.moveToFirst();
        while (c1.isAfterLast() == false) {
            String s = "";
            String idColumnName = ContactsContract.Contacts._ID;
            int idIndex = c1.getColumnIndex(idColumnName);
            s = c1.getString(idIndex) + " - ";
            String nameColumnName = ContactsContract.Contacts.DISPLAY_NAME;
            int nameIndex = c1.getColumnIndex(nameColumnName);
            s += c1.getString(nameIndex);
            c1.moveToNext();
            list.add(s);
        }
        c1.close();
        ListView lv = (ListView) findViewById(R.id.listView1);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, list);
        lv.setAdapter(adapter);
    }

    /**
     * hàm danh toàn bộ danh bạ
     * dùng getContentResolver
     */
    public void showAllContacts2() {
        Uri uri = Uri.parse("content://contacts/people");
        ArrayList<String> list = new ArrayList<String>();
        Cursor c1 = getContentResolver()
                .query(uri, null, null, null, null);
        c1.moveToFirst();
        while (c1.isAfterLast() == false) {
            String s = "";
            String idColumnName = ContactsContract.Contacts._ID;
            int idIndex = c1.getColumnIndex(idColumnName);
            s = c1.getString(idIndex) + " - ";
            String nameColumnName = ContactsContract.Contacts.DISPLAY_NAME;
            int nameIndex = c1.getColumnIndex(nameColumnName);
            s += c1.getString(nameIndex);
            c1.moveToNext();
            list.add(s);
        }
        c1.close();
        ListView lv = (ListView) findViewById(R.id.listView1);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, list);
        lv.setAdapter(adapter);
    }

}