package com.firesoft.member.Protocol;

import com.external.activeandroid.DataBaseModel;
import com.external.activeandroid.annotation.Column;
import com.external.activeandroid.annotation.Table;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2015/11/9.
 */
@Table(name = "shopaddRequest")
public class shopaddRequest extends DataBaseModel {
    @Column(name = "uid")
    public int id;

    @Column(name = "userid")
    public int userid;

    @Column(name = "name")
    public String name;

    @Column(name = "lxr")
    public String lxr;

    @Column(name = "lxdh")
    public String lxdh;

    @Column(name = "adress_province")
    public String adress_province;

    @Column(name = "adress_province_name")
    public String adress_province_name;

    @Column(name = "adress_city")
    public String adress_city;

    @Column(name = "adress_city_name")
    public String adress_city_name;

    @Column(name = "adress_county")
    public String adress_county;

    @Column(name = "adress_county_name")
    public String adress_county_name;

    @Column(name = "adress_detail")
    public String adress_detail;

    @Column(name = "grade_credit")
    public String grade_credit;

    @Column(name = "grade_discount")
    public String grade_discount;

    @Column(name = "sale_credit")
    public String sale_credit;

    @Column(name = "sale_discount")
    public String sale_discount;

    @Column(name = "birth_credit")
    public String birth_credit;

    @Column(name = "birth_discount")
    public String birth_discount;

    @Column(name = "shop_credit")
    public String shop_credit;

    @Column(name = "shop_discount")
    public String shop_discount;

    @Column(name = "rem")
    public String rem;



    public void  fromJson(JSONObject jsonObject)  throws JSONException
    {
        if(null == jsonObject){
            return ;
        }

        this.id = jsonObject.optInt("id");
        this.userid = jsonObject.optInt("userid");
        this.lxr = jsonObject.optString("lxr");
        this.name = jsonObject.optString("name");
        this.lxdh = jsonObject.optString("lxdh");
        this.adress_province = jsonObject.optString("adress_province");
        this.adress_province_name = jsonObject.optString("adress_province_name");
        this.adress_city = jsonObject.optString("adress_city");
        this.adress_city_name = jsonObject.optString("adress_city_name");
        this.adress_county = jsonObject.optString("adress_county");
        this.adress_county_name = jsonObject.optString("adress_county_name");
        this.adress_detail = jsonObject.optString("adress_detail");
        this.grade_credit = jsonObject.optString("grade_credit");
        this.grade_discount = jsonObject.optString("grade_discount");
        this.sale_credit = jsonObject.optString("sale_credit");
        this.sale_discount = jsonObject.optString("sale_discount");
        this.birth_credit = jsonObject.optString("birth_credit");
        this.birth_discount = jsonObject.optString("birth_discount");
        this.shop_credit = jsonObject.optString("shop_credit");
        this.shop_discount = jsonObject.optString("shop_discount");
        this.rem = jsonObject.optString("rem");
        return ;
    }
    public JSONObject  toJson() throws JSONException
    {
        JSONObject localItemObject = new JSONObject();

        localItemObject.put("id", id);
        localItemObject.put("userid", userid);
        localItemObject.put("name", name);
        localItemObject.put("lxr", lxr);
        localItemObject.put("lxdh", lxdh);
        localItemObject.put("adress_province", adress_province);
        localItemObject.put("adress_province_name", adress_province_name);
        localItemObject.put("adress_city", adress_city);
        localItemObject.put("adress_city_name", adress_city_name);
        localItemObject.put("adress_county", adress_county);
        localItemObject.put("adress_county_name", adress_county_name);
        localItemObject.put("adress_detail", adress_detail);
        localItemObject.put("grade_credit", grade_credit);
        localItemObject.put("grade_discount", grade_discount);
        localItemObject.put("sale_credit", sale_credit);
        localItemObject.put("sale_discount", sale_discount);
        localItemObject.put("birth_credit", birth_credit);
        localItemObject.put("birth_discount", birth_discount);
        localItemObject.put("shop_credit", shop_credit);
        localItemObject.put("shop_discount", shop_discount);
        localItemObject.put("rem", rem);
        return localItemObject;
    }
}
