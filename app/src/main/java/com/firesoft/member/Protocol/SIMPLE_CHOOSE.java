package com.firesoft.member.Protocol;

import com.external.activeandroid.DataBaseModel;
import com.external.activeandroid.annotation.Column;
import com.external.activeandroid.annotation.Table;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 15-10-4.
 */
@Table(name = "SIMPLE_CHOOSE")
public class SIMPLE_CHOOSE extends DataBaseModel{
    @Column(name = "uid")
    public int id;

    @Column(name = "name")
    public String name;

    public void  fromJson(JSONObject jsonObject)  throws JSONException
    {
        if(null == jsonObject){
            return ;
        }

        this.id = jsonObject.optInt("id");

        this.name = jsonObject.optString("name");

        return ;
    }
    public JSONObject  toJson() throws JSONException
    {
        JSONObject localItemObject = new JSONObject();

        localItemObject.put("id", id);
        localItemObject.put("name", name);
        return localItemObject;
    }
}
