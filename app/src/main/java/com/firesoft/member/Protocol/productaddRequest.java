package com.firesoft.member.Protocol;

import com.external.activeandroid.DataBaseModel;
import com.external.activeandroid.annotation.Column;
import com.external.activeandroid.annotation.Table;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2015/10/30.
 */
@Table(name = "productaddRequest")
public class productaddRequest extends DataBaseModel {
    @Column(name = "uid")
    public int uid;

    @Column(name = "name")
    public String name;

    @Column(name = "type_id")
    public int type_id;

    @Column(name = "price")
    public String price;

    @Column(name = "special")
    public String special;

    @Column(name = "specia_price")
    public String specia_price;

    @Column(name = "unit")
    public String unit;

    @Column(name = "special_discount")
    public String special_discount;

    @Column(name = "special_credit")
    public String special_credit;

    @Column(name = "shopid")
    public String shopid;

    @Column(name = "shopname")
    public String shopname;



    public void  fromJson(JSONObject jsonObject)  throws JSONException
    {
        if(null == jsonObject){
            return ;
        }

        this.uid = jsonObject.optInt("uid");
        this.type_id = jsonObject.optInt("type_id");
        this.name = jsonObject.optString("name");
        this.price = jsonObject.optString("price");
        this.special = jsonObject.optString("special");
        this.specia_price = jsonObject.optString("specia_price");
        this.unit = jsonObject.optString("unit");
        this.special_discount = jsonObject.optString("special_discount");
        this.special_credit = jsonObject.optString("special_credit");
        this.shopid = jsonObject.optString("shopid");
        this.shopname = jsonObject.optString("shopname");

        return ;
    }
    public JSONObject  toJson() throws JSONException
    {
        JSONObject localItemObject = new JSONObject();

        localItemObject.put("uid", uid);
        localItemObject.put("name", name);
        localItemObject.put("type_id", type_id);
        localItemObject.put("price", price);
        localItemObject.put("special", special);
        localItemObject.put("specia_price", specia_price);
        localItemObject.put("unit", unit);
        localItemObject.put("special_discount", special_discount);
        localItemObject.put("special_credit", special_credit);
        localItemObject.put("shopid", shopid);
        localItemObject.put("shopname", shopname);
        return localItemObject;
    }
}
