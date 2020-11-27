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

import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import static com.example.storymaker_android.NetworkUnits.getResponseFromURL;

public class PostActivity extends AppCompatActivity {
    final private String INTENT_DATA = "RoomName";
    HashMap <String, String> form;
    Intent intent;
    String RoomName;
    EditText who;
    EditText where;
    EditText doing;
    EditText what_for;
    EditText end;
    QueryTaskPostActivity MyTask;
    String json_string;

    public void GoPost(View view) {
        try {
            Query();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public void GoTableRoomActivity(View view) {
        intent = new Intent(this, TableRoomActivity.class);
        startActivity(intent);
    }

    public void GoReadActivity(View view) {
        intent = new Intent(this, ReadActivity.class);
        intent.putExtra(INTENT_DATA, RoomName);
        startActivity(intent);
    }

    public void Query() throws JSONException, ExecutionException, InterruptedException {

        form = new HashMap<String, String>();
        form.put("who", who.getText().toString());
        form.put("where", where.getText().toString());
        form.put("do", doing.getText().toString());
        form.put("what_for", what_for.getText().toString());
        form.put("end", end.getText().toString());

        MyTask = new QueryTaskPostActivity();
        MyTask.execute(new RequestManager().WriteInRoom(RoomName, form));

        json_string = MyTask.get();
        JSONObject reader = new JSONObject(json_string);

        if(reader.getBoolean("error") == false) {
            Toast toast = Toast.makeText(this, "Запись успешно внесена.",Toast.LENGTH_LONG);
            toast.show();
        }
        else {
            Toast toast = Toast.makeText(this, "Ошибка сервера!.",Toast.LENGTH_LONG);
            toast.show();
        }

    }


    class QueryTaskPostActivity extends AsyncTask<String,Void,String> {

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
        setContentView(R.layout.activity_post);

        Bundle arguments = getIntent().getExtras();
        RoomName = arguments.get(INTENT_DATA).toString();

        who = findViewById(R.id.who_text);
        where = findViewById(R.id.where_text);
        doing = findViewById(R.id.doing_text);
        what_for = findViewById(R.id.what_for_text);
        end = findViewById(R.id.end_text);
    }
}