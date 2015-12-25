package com.firesoft.member.Protocol;

import com.external.activeandroid.DataBaseModel;
import com.external.activeandroid.annotation.Column;
import com.external.activeandroid.annotation.Table;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2015/12/12.
 */
@Table(name = "SIMPLE_GUIDE")
public class SIMPLE_GUIDE extends DataBaseModel {
    @Column(name = "uid")
    public int id;

    @Column(name = "name")
    public String name;

    @Column(name = "phone")
    public String phone;

    @Column(name = "user_flag")
    public String user_flag;

    @Column(name = "shopid")
    public String shopid;

    @Column(name = "shopname")
    public String shopname;

    @Column(name = "password")
    public String password;



    public void  fromJson(JSONObject jsonObject)  throws JSONException
    {
        if(null == jsonObject){
            return ;
        }
        this.id = jsonObject.optInt("id");
        this.name = jsonObject.optString("name");
        this.phone = jsonObject.optString("phone");
        this.shopid = jsonObject.optString("shopid");
        this.shopname = jsonObject.optString("shopname");
        this.user_flag = jsonObject.optString("user_flag");

        return ;
    }

    public JSONObject  toJson() throws JSONException
    {
        JSONObject localItemObject = new JSONObject();

        localItemObject.put("id", id);
        localItemObject.put("name", name);
        localItemObject.put("phone", phone);
        localItemObject.put("shopid", shopid);
        localItemObject.put("shopname", shopname);
        localItemObject.put("user_flag", user_flag);

        return localItemObject;
    }
}
