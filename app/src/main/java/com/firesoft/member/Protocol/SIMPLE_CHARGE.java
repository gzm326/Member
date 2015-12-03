package com.firesoft.member.Protocol;

import com.external.activeandroid.DataBaseModel;
import com.external.activeandroid.annotation.Column;
import com.external.activeandroid.annotation.Table;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2015/11/26.
 */
@Table(name = "SIMPLE_CHARGE")
public class SIMPLE_CHARGE extends DataBaseModel {
    @Column(name = "uid")
    public int id;

    @Column(name = "member_no")
    public String member_no;

    @Column(name = "charge_money")
    public String charge_money;

    @Column(name = "give_money")
    public String give_money;

    @Column(name = "real_money")
    public String real_money;

    @Column(name = "begin_balance")
    public String begin_balance;

    @Column(name = "end_balance")
    public String end_balance;

    @Column(name = "bonus")
    public String bonus;

    @Column(name = "pay_ali")
    public String pay_ali;

    @Column(name = "pay_wx")
    public String pay_wx;

    @Column(name = "pay_cash")
    public String pay_cash;

    @Column(name = "pay_card")
    public String pay_card;

    @Column(name = "oper")
    public String oper;

    @Column(name = "opername")
    public String opername;

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
        this.charge_money = jsonObject.optString("charge_money");
        this.give_money = jsonObject.optString("give_money");
        this.real_money = jsonObject.optString("real_money");
        this.begin_balance = jsonObject.optString("begin_balance");
        this.end_balance = jsonObject.optString("end_balance");
        this.bonus = jsonObject.optString("bonus");
        this.pay_ali = jsonObject.optString("pay_ali");
        this.pay_wx = jsonObject.optString("pay_wx");
        this.pay_cash = jsonObject.optString("pay_cash");
        this.pay_card = jsonObject.optString("pay_card");
        this.oper = jsonObject.optString("oper");
        this.opername = jsonObject.optString("opername");
        this.shopid = jsonObject.optString("shopid");
        this.shopname = jsonObject.optString("shopname");


        return ;
    }
    public JSONObject  toJson() throws JSONException
    {
        JSONObject localItemObject = new JSONObject();

        localItemObject.put("id", id);
        localItemObject.put("member_no", member_no);
        localItemObject.put("charge_money", charge_money);
        localItemObject.put("give_money", give_money);
        localItemObject.put("real_money", real_money);
        localItemObject.put("begin_balance", begin_balance);
        localItemObject.put("end_balance", end_balance);
        localItemObject.put("bonus", bonus);
        localItemObject.put("pay_ali", pay_ali);
        localItemObject.put("pay_wx", pay_wx);
        localItemObject.put("pay_cash", pay_cash);
        localItemObject.put("pay_card", pay_card);
        localItemObject.put("oper", oper);
        localItemObject.put("opername", opername);
        localItemObject.put("shopid", shopid);
        localItemObject.put("shopname", shopname);
        return localItemObject;
    }
}
