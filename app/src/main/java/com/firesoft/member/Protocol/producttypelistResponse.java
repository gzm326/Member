package com.firesoft.member.Protocol;

import com.external.activeandroid.DataBaseModel;
import com.external.activeandroid.annotation.Column;
import com.external.activeandroid.annotation.Table;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/10/30.
 */
@Table(name = "producttypelistResponse")
public class producttypelistResponse extends DataBaseModel {
    public ArrayList<SIMPLE_PRODUCTTYPE> producttypes = new ArrayList<SIMPLE_PRODUCTTYPE>();

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

        subItemArray = jsonObject.optJSONArray("producttypes");

        if(null != subItemArray)
        {
            for(int i = 0;i < subItemArray.length();i++)
            {
                JSONObject subItemObject = subItemArray.getJSONObject(i);
                SIMPLE_PRODUCTTYPE subItem = new SIMPLE_PRODUCTTYPE();
                subItem.fromJson(subItemObject);
                this.producttypes.add(subItem);
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

        for(int i =0; i< producttypes.size(); i++)
        {
            SIMPLE_PRODUCTTYPE itemData =producttypes.get(i);
            JSONObject itemJSONObject = itemData.toJson();
            itemJSONArray.put(itemJSONObject);
        }

        localItemObject.put("total", total);
        localItemObject.put("producttypes", itemJSONArray);
        localItemObject.put("more", more);
        localItemObject.put("succeed", succeed);
        localItemObject.put("count", count);
        localItemObject.put("error_code", error_code);
        localItemObject.put("error_desc", error_desc);
        return localItemObject;
    }
}
