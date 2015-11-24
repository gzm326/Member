package com.firesoft.member.Protocol;

import com.external.activeandroid.DataBaseModel;
import com.external.activeandroid.annotation.Column;
import com.external.activeandroid.annotation.Table;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2015/10/30.
 */
@Table(name = "SIMPLE_PRODUCTTYPE")
public class SIMPLE_PRODUCTTYPE extends DataBaseModel {
    @Column(name = "uid")
    public int id;

    @Column(name = "name")
    public String name;

    @Column(name = "shopid")
    public String shopid;

    @Column(name = "shopname")
    public String shopname;

    @Column(name = "bz")
    public String bz;

    public void  fromJson(JSONObject jsonObject)  throws JSONException
    {
        if(null == jsonObject){
            return ;
        }

        this.id = jsonObject.optInt("id");

        this.name = jsonObject.optString("name");

        this.shopid = jsonObject.optString("shopid");
        this.shopname = jsonObject.optString("shopname");

        this.bz = jsonObject.optString("bz");

        return ;
    }
    public JSONObject  toJson() throws JSONException
    {
        JSONObject localItemObject = new JSONObject();

        localItemObject.put("id", id);
        localItemObject.put("name", name);
        localItemObject.put("shopid", shopid);
        localItemObject.put("shopname", shopname);
        localItemObject.put("bz", bz);
        return localItemObject;
    }
}
