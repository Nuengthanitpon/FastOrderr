package com.example.fastorderr;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.fastorderr.Model.FoodStoreItem;

import com.example.fastorderr.adapter.FastFoodAdapter;
import com.example.fastorderr.db.PhoneDb;

import java.util.ArrayList;
import java.util.Locale;

public class FastFoodActivity extends AppCompatActivity {

    private ListView mListView5;
    private PhoneDb mHelper;
    private SQLiteDatabase mDb;
    public static ArrayList<FoodStoreItem> aList2 = new ArrayList<>();
    private FastFoodAdapter mAdapter5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fast_food);
        mHelper = new PhoneDb(this);
        mDb = mHelper.getReadableDatabase();

        loadDataFromDb();

        mAdapter5 = new FastFoodAdapter(
                this,
                R.layout.item5,
                aList2
        );
        mListView5 = (ListView) findViewById(R.id.listView5);
        mListView5.setAdapter(mAdapter5);



        getSupportActionBar().setTitle(
                String.format(
                        Locale.getDefault(),
                        "%s",
                        "ร้านอาหารฟาสฟู๊ด"
                )
        );
        mListView5.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                FoodStoreItem item = aList2.get(position);
                String phoneNumber = item.number;

                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + phoneNumber));
                startActivity(intent);

            }
        });

    }

    private void loadDataFromDb() {
        Cursor cursor = mDb.query(
                PhoneDb.TABLE_NAME_FAST,
                null,
                null,
                null,
                null,
                null,
                null
        );

        aList2.clear();

        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(PhoneDb.COL_ID));
            String title = cursor.getString(cursor.getColumnIndex(PhoneDb.COL_TITLE));
            String number = cursor.getString(cursor.getColumnIndex(PhoneDb.COL_NUMBER));
            String picture = cursor.getString(cursor.getColumnIndex(PhoneDb.COL_PICTURE));

            FoodStoreItem item = new FoodStoreItem(id, title, number, picture);
            aList2.add(item);
        }
    }
}