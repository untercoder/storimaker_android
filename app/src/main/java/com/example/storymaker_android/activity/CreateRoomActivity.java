package com.example.storymaker_android.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.storymaker_android.R;
import com.example.storymaker_android.RequestManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

import static com.example.storymaker_android.NetworkUnits.getResponseFromURL;

public class CreateRoomActivity extends AppCompatActivity {

    EditText InputRoomName;
    String RoomName;
    QueryTaskCreateActivity MyTask;
    String json_string;

    public void Query() throws JSONException, ExecutionException, InterruptedException {
        RoomName = InputRoomName.getText().toString();
        MyTask = new QueryTaskCreateActivity();
        MyTask.execute(new RequestManager().CreateRoom(RoomName));
        json_string = MyTask.get();
        JSONObject reader = new JSONObject(json_string);
        if(reader.getBoolean("error") == false) {
            Toast toast = Toast.makeText(this, "Комната создана.",Toast.LENGTH_LONG);
            toast.show();
            Intent intent = new Intent(this, TableRoomActivity.class);
            startActivity(intent);
        }
        else {
            Toast toast = Toast.makeText(this, "Ошибка соединения.",Toast.LENGTH_LONG);
            toast.show();
        }
    }

    class QueryTaskCreateActivity extends AsyncTask<String,Void,String> {

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
        setContentView(R.layout.activity_create_room);
        InputRoomName = findViewById(R.id.NewRoomName);
    }

    public void CreateRoom(View view) throws InterruptedException, ExecutionException, JSONException {
        Query();
    }
}