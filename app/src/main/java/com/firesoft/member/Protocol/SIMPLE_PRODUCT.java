package com.firesoft.member.Protocol;

import com.external.activeandroid.DataBaseModel;
import com.external.activeandroid.annotation.Column;
import com.external.activeandroid.annotation.Table;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2015/10/30.
 */
@Table(name = "SIMPLE_PRODUCT")
public class SIMPLE_PRODUCT extends DataBaseModel {
    @Column(name = "uid")
    public int id;

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



    public void  fromJson(JSONObject jsonObject)  throws JSONException
    {
        if(null == jsonObject){
            return ;
        }

        this.id = jsonObject.optInt("id");
        this.type_id = jsonObject.optInt("type_id");
        this.name = jsonObject.optString("name");
        this.price = jsonObject.optString("price");
        this.special = jsonObject.optString("special");
        this.specia_price = jsonObject.optString("specia_price");
        this.unit = jsonObject.optString("unit");
        this.special_discount = jsonObject.optString("special_discount");
        this.special_credit = jsonObject.optString("special_credit");

        return ;
    }
    public JSONObject  toJson() throws JSONException
    {
        JSONObject localItemObject = new JSONObject();

        localItemObject.put("id", id);
        localItemObject.put("name", name);
        localItemObject.put("type_id", type_id);
        localItemObject.put("price", price);
        localItemObject.put("special", special);
        localItemObject.put("specia_price", specia_price);
        localItemObject.put("unit", unit);
        localItemObject.put("special_discount", special_discount);
        localItemObject.put("special_credit", special_credit);
        return localItemObject;
    }
}
