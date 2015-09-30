package com.firesoft.member.Protocol;

import com.external.activeandroid.DataBaseModel;
import com.external.activeandroid.annotation.Column;
import com.external.activeandroid.annotation.Table;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/9/28.
 */
@Table(name = "memberlistResponse")
public class memberlistResponse extends DataBaseModel {

    public ArrayList<SIMPLE_MEMBER> members = new ArrayList<SIMPLE_MEMBER>();
    @Column(name = "id")
    public int id;

    @Column(name="member_no")
    public String member_no;

    @Column(name="member_name")
    public String member_name;

    @Column(name="mobile_no")
    public String mobile_no;

    @Column(name = "total")
    public int total;
    @Column(name = "more")
    public int more;

    @Column(name = "succeed")
    public int succeed;

    @Column(name = "count")
    public int count;

    @Column(name = "error_code")
    public int error_code;

    @Column(name = "error_desc")
    public String   error_desc;

    public void  fromJson(JSONObject jsonObject)  throws JSONException
    {
        if(null == jsonObject){
            return ;
        }

        JSONArray subItemArray;

        subItemArray = jsonObject.optJSONArray("members");

        if(null != subItemArray)
        {
            for(int i = 0;i < subItemArray.length();i++)
            {
                JSONObject subItemObject = subItemArray.getJSONObject(i);
                SIMPLE_MEMBER subItem = new SIMPLE_MEMBER();
                subItem.fromJson(subItemObject);
                this.members.add(subItem);
            }
        }

        this.total = jsonObject.optInt("total");

        this.more = jsonObject.optInt("more");

        this.succeed = jsonObject.optInt("succeed");

        this.count = jsonObject.optInt("count");

        this.error_code = jsonObject.optInt("error_code");

        this.error_desc = jsonObject.optString("error_desc");

        return ;
    }

    public JSONObject  toJson() throws JSONException
    {
        JSONObject localItemObject = new JSONObject();
        JSONArray itemJSONArray = new JSONArray();

        for(int i =0; i< members.size(); i++)
        {
            SIMPLE_MEMBER itemData =members.get(i);
            JSONObject itemJSONObject = itemData.toJson();
            itemJSONArray.put(itemJSONObject);
        }

        localItemObject.put("total", total);
        localItemObject.put("users", itemJSONArray);
        localItemObject.put("more", more);
        localItemObject.put("succeed", succeed);
        localItemObject.put("count", count);
        localItemObject.put("error_code", error_code);
        localItemObject.put("error_desc", error_desc);
        return localItemObject;
    }
}
