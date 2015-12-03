package com.firesoft.member.Protocol;

import com.external.activeandroid.DataBaseModel;
import com.external.activeandroid.annotation.Column;
import com.external.activeandroid.annotation.Table;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2015/12/3.
 */
@Table(name = "SIMPLE_NUMBERHISTORY")
public class SIMPLE_NUMBERHISTORY extends DataBaseModel {
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

    @Column(name = "sale_price")
    public String sale_price;

    @Column(name = "sum")
    public String sum;

    @Column(name = "product_price")
    public String product_price;

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

    @Column(name = "pay_ali")
    public String pay_ali;

    @Column(name = "pay_wx")
    public String pay_wx;

    @Column(name = "pay_cash")
    public String pay_cash;

    @Column(name = "pay_card")
    public String pay_card;

    @Column(name = "pay_balance")
    public String pay_balance;

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
        this.sale_price = jsonObject.optString("sale_price");
        this.sum = jsonObject.optString("sum");
        this.product_price = jsonObject.optString("product_price");
        this.special = jsonObject.optString("special");
        this.specia_price = jsonObject.optString("specia_price");
        this.unit = jsonObject.optString("unit");
        this.special_discount = jsonObject.optString("special_discount");
        this.special_credit = jsonObject.optString("special_credit");
        this.pay_ali = jsonObject.optString("pay_ali");
        this.pay_wx = jsonObject.optString("pay_wx");
        this.pay_cash = jsonObject.optString("pay_cash");
        this.pay_card = jsonObject.optString("pay_card");
        this.pay_balance = jsonObject.optString("pay_balance");
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
        localItemObject.put("sale_price", sale_price);
        localItemObject.put("sum", sum);
        localItemObject.put("product_price", product_price);
        localItemObject.put("special", special);
        localItemObject.put("specia_price", specia_price);
        localItemObject.put("unit", unit);
        localItemObject.put("special_discount", special_discount);
        localItemObject.put("special_credit", special_credit);
        localItemObject.put("pay_ali", pay_ali);
        localItemObject.put("pay_wx", pay_wx);
        localItemObject.put("pay_cash", pay_cash);
        localItemObject.put("pay_card", pay_card);
        localItemObject.put("pay_balance", pay_balance);
        localItemObject.put("oper", oper);
        localItemObject.put("opername", opername);
        localItemObject.put("shopid", shopid);
        localItemObject.put("shopname", shopname);
        return localItemObject;
    }
}
