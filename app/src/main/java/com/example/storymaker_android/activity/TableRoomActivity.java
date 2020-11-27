package com.example.storymaker_android.activity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;


import com.example.storymaker_android.R;
import com.example.storymaker_android.RequestManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;


import static com.example.storymaker_android.NetworkUnits.getResponseFromURL;


public class TableRoomActivity extends AppCompatActivity {


    final private String INTENT_DATA = "RoomName";
    private ListView RoomList; // Обьект списка
    public List<String> RoomArray; // ArrayList в котором храняся названия комнат
    private ArrayAdapter<String> adapter; // Стандартный адаптер для списка
    public String json_string; // Строка в которой храниться jsonObject
    public Intent intent;
    public JSONObject reader;
    QueryTask MyTask;

    class QueryTask extends AsyncTask<String,Void,String> {

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

    private void CreateTrueIntent () {
        intent = new Intent(this, RoomActivity.class);
    }

    public void Query(boolean flag) throws JSONException, ExecutionException, InterruptedException {
        boolean FlagFromRefresh = flag;
        MyTask = new QueryTask();
        RoomArray = new ArrayList<String>();
        MyTask.execute(new RequestManager().GetRooms());
        json_string = MyTask.get();
        reader = new JSONObject(json_string);
        if(reader.getBoolean("error") == false) {
            if(FlagFromRefresh == true) {
            Toast toast = Toast.makeText(this, "Список комнат обновлен!",Toast.LENGTH_LONG);
            toast.show();
            }
                JSONArray array_result = null;
                array_result = reader.getJSONArray("result");
                    for (int j = 0; j < array_result.length(); j++) {
                        String value = array_result.get(j).toString();
                        RoomArray.add(value);
                    }
         adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, RoomArray);
         RoomList.setAdapter(adapter);
                RoomList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {
                        CreateTrueIntent();
                        intent.putExtra(INTENT_DATA, RoomArray.get(position));
                        startActivity(intent);

                    }
                });
            }
            else {
            Toast toast = Toast.makeText(this, "Ошибка! Не удалось обновить список комнат.",Toast.LENGTH_LONG);
            toast.show();
                adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, Collections.singletonList("Ошибка Сервера"));
                RoomList.setAdapter(adapter);
            }
    }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RoomList = findViewById(R.id.RoomList);
            try {
                Query(false);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater men = getMenuInflater();
        men.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.CreateRoomItem:
                intent = new Intent(this, CreateRoomActivity.class);
                startActivity(intent);
                break;
            case R.id.Refresh:
                try {
                    RoomArray = null;
                    Query(true);
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                break;

        }
        return super.onOptionsItemSelected(item);
    }
}

