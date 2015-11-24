package com.firesoft.member.Protocol;

import com.external.activeandroid.DataBaseModel;
import com.external.activeandroid.annotation.Column;
import com.external.activeandroid.annotation.Table;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2015/11/4.
 */
@Table(name = "gradeaddRequest")
public class gradeaddRequest extends DataBaseModel {
    @Column(name = "uid")
    public int id;

    @Column(name = "name")
    public String name;

    @Column(name = "discount_percent")
    public String discount_percent;

    @Column(name = "credit_percent")
    public String credit_percent;

    @Column(name = "validity")
    public String validity;

    @Column(name = "validity_unit")
    public String validity_unit;

    @Column(name = "beg_money")
    public String beg_money;

    @Column(name = "beg_credit")
    public String beg_credit;

    @Column(name = "rec_credit")
    public String rec_credit;

    @Column(name = "rem")
    public String rem;



    public void  fromJson(JSONObject jsonObject)  throws JSONException
    {
        if(null == jsonObject){
            return ;
        }

        this.id = jsonObject.optInt("id");
        this.discount_percent = jsonObject.optString("discount_percent");
        this.name = jsonObject.optString("name");
        this.credit_percent = jsonObject.optString("credit_percent");
        this.validity = jsonObject.optString("validity");
        this.validity_unit = jsonObject.optString("validity_unit");
        this.beg_money = jsonObject.optString("beg_money");
        this.beg_credit = jsonObject.optString("beg_credit");
        this.rec_credit = jsonObject.optString("rec_credit");
        this.rem = jsonObject.optString("rem");

        return ;
    }
    public JSONObject  toJson() throws JSONException
    {
        JSONObject localItemObject = new JSONObject();

        localItemObject.put("id", id);
        localItemObject.put("name", name);
        localItemObject.put("discount_percent", discount_percent);
        localItemObject.put("credit_percent", credit_percent);
        localItemObject.put("validity", validity);
        localItemObject.put("validity_unit", validity_unit);
        localItemObject.put("beg_money", beg_money);
        localItemObject.put("beg_credit", beg_credit);
        localItemObject.put("rec_credit", rec_credit);
        localItemObject.put("rem", rem);
        return localItemObject;
    }
}
