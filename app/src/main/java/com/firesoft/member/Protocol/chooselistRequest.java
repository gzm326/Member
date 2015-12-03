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

    @Column(name = "shopid")
    public String shopid;

    @Column(name = "type_id")
    public String type_id;

    @Column(name = "name")
    public String name;

    public void  fromJson(JSONObject jsonObject)  throws JSONException
    {
        if(null == jsonObject){
            return ;
        }
        this.uid = jsonObject.optInt("uid");
        this.name = jsonObject.optString("name");
        this.shopid = jsonObject.optString("shopid");
        this.type_id = jsonObject.optString("type_id");


        return ;
    }

    public JSONObject  toJson() throws JSONException
    {
        JSONObject localItemObject = new JSONObject();

        localItemObject.put("uid", uid);
        localItemObject.put("name", name);
        localItemObject.put("shopid", shopid);
        localItemObject.put("type_id", type_id);

        return localItemObject;
    }
}
