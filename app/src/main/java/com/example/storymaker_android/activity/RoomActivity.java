package com.example.storymaker_android.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.storymaker_android.R;

public class RoomActivity extends AppCompatActivity {

    final private String INTENT_DATA = "RoomName";
    TextView RoomNameText;
    Intent intent;
    String RoomNameIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);

        Bundle arguments = getIntent().getExtras();
        RoomNameIntent = arguments.get(INTENT_DATA).toString();



        RoomNameText = findViewById(R.id.RoomName);
        RoomNameText.setText(RoomNameIntent);
    }

    public void GoTableRoomActivity(View view) {
        intent = new Intent(this, TableRoomActivity.class);
        startActivity(intent);
    }

    public void GoReadActivity(View view) {
        intent = new Intent(this, ReadActivity.class);
        intent.putExtra(INTENT_DATA, RoomNameIntent);
        startActivity(intent);
    }

    public void GoToPostActivity(View view) {
        intent = new Intent(this, PostActivity.class);
        intent.putExtra(INTENT_DATA, RoomNameIntent);
        startActivity(intent);
    }
}