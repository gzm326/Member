package com.firesoft.member.Protocol;

import com.external.activeandroid.DataBaseModel;
import com.external.activeandroid.annotation.Column;
import com.external.activeandroid.annotation.Table;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2015/10/30.
 */
@Table(name = "productaddResponse")
public class productaddResponse extends DataBaseModel {
    @Column(name = "uid")
    public int uid;

    @Column(name = "product")
    public SIMPLE_PRODUCT product;

    @Column(name = "succeed")
    public int succeed;

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

        this.uid = jsonObject.optInt("uid");

        SIMPLE_PRODUCT product = new SIMPLE_PRODUCT();
        product.fromJson(jsonObject.optJSONObject("member"));
        this.product=product;

        this.succeed = jsonObject.optInt("succeed");

        this.error_code = jsonObject.optInt("error_code");

        this.error_desc = jsonObject.optString("error_desc");
        return ;
    }

    public JSONObject  toJson() throws JSONException
    {
        JSONObject localItemObject = new JSONObject();
        JSONArray itemJSONArray = new JSONArray();
        localItemObject.put("uid", uid);

        if(null != product)
        {
            localItemObject.put("product", product.toJson());
        }

        localItemObject.put("succeed", succeed);

        localItemObject.put("error_code", error_code);

        localItemObject.put("error_desc", error_desc);
        return localItemObject;
    }
}
