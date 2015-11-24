package com.firesoft.member.Protocol;


import com.external.activeandroid.DataBaseModel;
import com.external.activeandroid.annotation.Column;
import com.external.activeandroid.annotation.Table;
import org.json.JSONException;
import org.json.JSONObject;
/**
 * Created by Administrator on 2015/9/30.
 */

@Table(name = "SIMPLE_MEMBER")
public class SIMPLE_MEMBER extends DataBaseModel{

    @Column(name = "SIMPLE_MEMBER_id")
    public int id;

    @Column(name="member_no")
    public String member_no;

    @Column(name="member_name")
    public String member_name;

    @Column(name="mobile_no")
    public String mobile_no;

    @Column(name = "shopid")
    public String shopid;

    @Column(name = "shopname")
    public String shopname;


    public void  fromJson(JSONObject jsonObject)  throws JSONException
    {
        if(null == jsonObject){
            return ;
        }

        this.id = jsonObject.optInt("id");

        this.member_no = jsonObject.optString("member_no");

        this.member_name = jsonObject.optString("member_name");

        this.mobile_no = jsonObject.optString("mobile_no");

        this.shopid = jsonObject.optString("shopid");
        this.shopname = jsonObject.optString("shopname");

        return ;
    }
    public JSONObject  toJson() throws JSONException
    {
        JSONObject localItemObject = new JSONObject();

        localItemObject.put("id", id);
        localItemObject.put("member_no", member_no);
        localItemObject.put("member_name", member_name);
        localItemObject.put("mobile_no", mobile_no);

        localItemObject.put("shopid", shopid);
        localItemObject.put("shopname", shopname);
        return localItemObject;
    }
}
