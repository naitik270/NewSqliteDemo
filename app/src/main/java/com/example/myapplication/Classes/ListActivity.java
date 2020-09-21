package com.example.myapplication.Classes;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Database.DataBaseOperations;
import com.example.myapplication.R;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {

    private RecyclerView mRv;
    private ListAdapter mAdapter;
    DataBaseOperations mDataBaseOperations;
    List<ClsRegistrationGetSet> mList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        main();
    }


    private void main() {
        mDataBaseOperations = new DataBaseOperations(getApplicationContext());
        mRv = findViewById(R.id.mRv);
        mRv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        viewData();

    }

    private void viewData() {
        mList = mDataBaseOperations.getAllMembers();
        Log.d("--mList--", "Size: " + mList.size());


        if (mList.size() > 0) {
            mAdapter = new ListAdapter(ListActivity.this);
            mRv.setAdapter(mAdapter);

            mAdapter.AddItems(mList);

            mAdapter.SetOnClickListener(obj -> {
                int getId = obj.getId();
                Intent intent = new Intent(ListActivity.this, RegistrationActivity.class);
                intent.putExtra("regID", getId);
                startActivity(intent);
            });

        }

        Gson gson = new Gson();
        String jsonInString = gson.toJson(mList);
        Log.e("--mList--", "listVal: " + jsonInString);


    }

}
