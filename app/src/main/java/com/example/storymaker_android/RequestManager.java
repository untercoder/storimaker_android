package com.example.storymaker_android;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RequestManager {

    public String GetRooms() throws JSONException {
        Map<String, String> request = new HashMap<String, String>();
        request.put( "entity", "table_rooms" );
        request.put( "action", "get_rooms" );
        JSONObject json = new JSONObject(request);
        return json.toString(2);
    }

    public String CreateRoom (String room_name) throws JSONException {
        Map<String, String> request = new HashMap<String, String>();
        request.put( "entity", "create_room" );
        request.put( "action", "create_room" );
        request.put( "room_name", room_name );
        JSONObject json = new JSONObject(request);
        return json.toString(2);
    }

    public String ReadRoom (String room_name) throws JSONException {
        Map<String, String> request = new HashMap<String, String>();
        request.put( "entity", "work_room" );
        request.put( "action", "read_room" );
        request.put( "room_name", room_name );
        JSONObject json = new JSONObject(request);
        return json.toString(2);
    }

    public String WriteInRoom (String room_name, HashMap<String,String> form) throws JSONException {
        Map<String, Object> request = new HashMap<String, Object>();
        request.put( "entity", "work_room" );
        request.put( "action", "write_in_room" );
        request.put( "room_name", room_name );
        request.put("form", form);
        Gson gson = new Gson();
        String json = gson.toJson(request);
        return json;
    }

}
