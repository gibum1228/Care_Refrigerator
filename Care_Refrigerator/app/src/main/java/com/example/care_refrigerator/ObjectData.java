package com.example.care_refrigerator;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class ObjectData {
    public String id = "";
    public String name = "";
    public String category = "";
    public String dateEnd = "";
    public String cnt = "0";

    public ObjectData(){}
    public ObjectData(String id, String c, String n, String d, String cnt){
        this.id = id;
        this.category = c;
        this.name = n;
        this.dateEnd = d;
        this.cnt = cnt;
    }

    @Exclude
    public Map<String, Object> toMap(){
        HashMap<String, Object> objectBox = new HashMap<>();
        objectBox.put("id", id);
        objectBox.put("category", category);
        objectBox.put("name", name);
        objectBox.put("dateEnd", dateEnd);
        objectBox.put("count", cnt);

        return objectBox;
    }
}
