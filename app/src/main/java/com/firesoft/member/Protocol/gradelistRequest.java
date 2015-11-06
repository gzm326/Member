package com.firesoft.member.Protocol;

import com.external.activeandroid.DataBaseModel;
import com.external.activeandroid.annotation.Column;
import com.external.activeandroid.annotation.Table;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2015/11/4.
 */
@Table(name = "gradelistRequest")
public class gradelistRequest extends DataBaseModel {
    @Column(name = "uid")
    public int uid;

    @Column(name = "sid")
    public String   sid;

    @Column(name = "by_no")
    public int by_no;

    @Column(name = "count")
    public int count;

    @Column(name = "sort_by")
    public int sort_by;

    @Column(name = "ver")
    public int ver;

    public void  fromJson(JSONObject jsonObject)  throws JSONException
    {
        if(null == jsonObject){
            return ;
        }
        this.uid = jsonObject.optInt("uid");
        this.sid = jsonObject.optString("sid");
        this.by_no = jsonObject.optInt("by_no");
        this.count = jsonObject.optInt("count");
        this.sort_by = jsonObject.optInt("sort_by");
        this.ver = jsonObject.optInt("ver");

        return ;
    }

    public JSONObject  toJson() throws JSONException
    {
        JSONObject localItemObject = new JSONObject();

        localItemObject.put("uid", uid);
        localItemObject.put("sid", sid);
        localItemObject.put("count", count);
        localItemObject.put("sort_by", sort_by);
        localItemObject.put("ver", ver);
        localItemObject.put("by_no", by_no);
        return localItemObject;
    }
}
