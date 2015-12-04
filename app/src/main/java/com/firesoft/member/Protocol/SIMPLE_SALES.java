package com.firesoft.member.Protocol;

import com.external.activeandroid.DataBaseModel;
import com.external.activeandroid.annotation.Column;
import com.external.activeandroid.annotation.Table;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2015/12/4.
 */
@Table(name = "SIMPLE_SALES")
public class SIMPLE_SALES extends DataBaseModel {
    @Column(name = "uid")
    public int id;

    @Column(name = "member_no")
    public String member_no;

    @Column(name = "product_id")
    public String product_id;

    @Column(name = "product_name")
    public String product_name;

    @Column(name = "type_id")
    public String type_id;

    @Column(name = "type_name")
    public String type_name;

    @Column(name = "num")
    public String num;

    @Column(name = "sum")
    public String sum;

    @Column(name = "sale_price")
    public String sale_price;

    @Column(name = "oper")
    public String oper;

    @Column(name = "opername")
    public String opername;

    @Column(name = "shopid")
    public String shopid;

    @Column(name = "shopname")
    public String shopname;


    public void fromJson(JSONObject jsonObject) throws JSONException {
        if (null == jsonObject) {
            return;
        }

        this.id = jsonObject.optInt("id");
        this.member_no = jsonObject.optString("member_no");
        this.product_id = jsonObject.optString("product_id");
        this.product_name = jsonObject.optString("product_name");
        this.type_id = jsonObject.optString("type_id");
        this.type_name = jsonObject.optString("type_name");
        this.num = jsonObject.optString("num");
        this.sum = jsonObject.optString("sum");
        this.sale_price = jsonObject.optString("sale_price");


        this.oper = jsonObject.optString("oper");
        this.opername = jsonObject.optString("opername");
        this.shopid = jsonObject.optString("shopid");
        this.shopname = jsonObject.optString("shopname");


        return;
    }

    public JSONObject toJson() throws JSONException {
        JSONObject localItemObject = new JSONObject();

        localItemObject.put("id", id);
        localItemObject.put("member_no", member_no);
        localItemObject.put("product_id", product_id);
        localItemObject.put("product_name", product_name);
        localItemObject.put("type_id", type_id);
        localItemObject.put("type_name", type_name);
        localItemObject.put("num", num);
        localItemObject.put("sum", sum);
        localItemObject.put("sale_price", sale_price);


        localItemObject.put("oper", oper);
        localItemObject.put("opername", opername);
        localItemObject.put("shopid", shopid);
        localItemObject.put("shopname", shopname);
        return localItemObject;
    }
}
