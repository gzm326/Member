package com.firesoft.member.Protocol;

import com.external.activeandroid.DataBaseModel;
import com.external.activeandroid.annotation.Column;
import com.external.activeandroid.annotation.Table;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 15-10-4.
 */
@Table(name = "chooselistRequest")
public class chooselistRequest extends DataBaseModel {
    @Column(name = "uid")
    public int uid;

    @Column(name = "name")
    public String name;

    public void  fromJson(JSONObject jsonObject)  throws JSONException
    {
        if(null == jsonObject){
            return ;
        }
        this.uid = jsonObject.optInt("uid");
        this.name = jsonObject.optString("name");


        return ;
    }

    public JSONObject  toJson() throws JSONException
    {
        JSONObject localItemObject = new JSONObject();

        localItemObject.put("uid", uid);
        localItemObject.put("name", name);

        return localItemObject;
    }
}
