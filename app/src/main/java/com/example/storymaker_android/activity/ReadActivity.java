package com.example.storymaker_android.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.storymaker_android.R;
import com.example.storymaker_android.RequestManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

import static com.example.storymaker_android.NetworkUnits.getResponseFromURL;

public class ReadActivity extends AppCompatActivity {

    final private String INTENT_DATA = "RoomName";
    Intent intent;
    TextView WhoText;
    TextView WhereText;
    TextView DoText;
    TextView WhatForText;
    TextView EndText;
    String RoomName;
    QueryTaskReadActivity MyTask;
    String json_string;
    JSONObject array_result;

    public void GoToPostActivity(View view) {
        intent = new Intent(this, PostActivity.class);
        intent.putExtra(INTENT_DATA, RoomName);
        startActivity(intent);
    }

    public void Query() {
        MyTask = new QueryTaskReadActivity();

        try {
            MyTask.execute(new RequestManager().ReadRoom(RoomName));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            json_string = MyTask.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        JSONObject reader = null;
        try {
            reader = new JSONObject(json_string);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            array_result = reader.getJSONObject("result");
        } catch (JSONException e) {
            e.printStackTrace();
        }



        try {
            if(array_result != null) {
                WhoText.setText(array_result.getString("who"));
                WhereText.setText(array_result.getString("where"));
                DoText.setText(array_result.getString("do"));
                WhatForText.setText(array_result.getString("what_for"));
                EndText.setText(array_result.getString("end"));
            }

            else {
                WhoText = findViewById(R.id.who);
                WhoText.setText("Таблица пуста");
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void GoTableRoomActivity(View view) {
        intent = new Intent(this, TableRoomActivity.class);
        startActivity(intent);
    }

    public void ReadMore(View view) {
        Query();
    }


    class QueryTaskReadActivity extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... strings) {
            /*

             */
            String responce = null;
            responce = getResponseFromURL(strings[0]);
            return responce;
        }

        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(response);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);
        WhoText = findViewById(R.id.who);
        WhereText = findViewById(R.id.where);
        DoText = findViewById(R.id.doing);
        WhatForText = findViewById(R.id.what_for);
        EndText = findViewById(R.id.end);

        Bundle arguments = getIntent().getExtras();
        RoomName = arguments.get(INTENT_DATA).toString();
        Query();


    }
}