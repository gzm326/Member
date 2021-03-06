package com.firesoft.member.Protocol;

import com.external.activeandroid.DataBaseModel;
import com.external.activeandroid.annotation.Column;
import com.external.activeandroid.annotation.Table;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 15-10-1.
 */
@Table(name = "memberaddRequest")
public class memberaddRequest extends DataBaseModel
{
    @Column(name = "uid")
    public int uid;

    @Column(name = "member_no")
    public String member_no;

    @Column(name = "member_name")
    public String member_name;

    @Column(name = "mobile_no")
    public String mobile_no;

    @Column(name = "shopid")
    public String shopid;

    @Column(name = "shopname")
    public String shopname;

    @Column(name = "flag")
    public String flag;

    public void  fromJson(JSONObject jsonObject)  throws JSONException
    {
        if(null == jsonObject){
            return ;
        }

        JSONArray subItemArray;

        this.uid=jsonObject.optInt("uid");
        this.member_no = jsonObject.optString("member_no");
        this.member_name = jsonObject.optString("member_name");
        this.mobile_no = jsonObject.optString("mobile_no");
        this.shopid = jsonObject.optString("shopid");
        this.shopname = jsonObject.optString("shopname");
        this.flag = jsonObject.optString("flag");

        return ;
    }

    public JSONObject  toJson() throws JSONException
    {
        JSONObject localItemObject = new JSONObject();
        JSONArray itemJSONArray = new JSONArray();
        localItemObject.put("uid", uid);
        localItemObject.put("member_no", member_no);
        localItemObject.put("member_name", member_name);
        localItemObject.put("mobile_no", mobile_no);
        localItemObject.put("shopid", shopid);
        localItemObject.put("shopname", shopname);
        localItemObject.put("flag", flag);
        return localItemObject;
    }
}
