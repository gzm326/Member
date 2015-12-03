package com.firesoft.member.Model;

import android.content.Context;

import com.BeeFramework.model.BaseModel;
import com.BeeFramework.model.BeeCallback;
import com.external.androidquery.callback.AjaxStatus;
import com.firesoft.member.APIErrorCode;
import com.firesoft.member.Protocol.ApiInterface;
import com.firesoft.member.Protocol.SIMPLE_CHARGE;
import com.firesoft.member.Protocol.chargeaddRequest;
import com.firesoft.member.Protocol.chargeaddResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2015/11/26.
 */
public class ChargeModel extends BaseModel {
    private Context context;
    public SIMPLE_CHARGE charge;
    public ChargeModel(Context context) {
        super(context);
        this.context = context;

    }
    public void add(SIMPLE_CHARGE scharge) {
        chargeaddRequest request = new chargeaddRequest();
        request.member_no =scharge.member_no;
        request.charge_money = scharge.charge_money;
        request.give_money = scharge.give_money;
        request.real_money = scharge.real_money;
        request.begin_balance = scharge.begin_balance;
        request.end_balance = scharge.end_balance;
        request.bonus = scharge.bonus;
        request.pay_ali = scharge.pay_ali;
        request.pay_wx = scharge.pay_wx;
        request.pay_cash = scharge.pay_cash;
        request.pay_card = scharge.pay_card;
        request.oper = scharge.oper;
        request.opername = scharge.opername;
        request.shopid=scharge.shopid;
        request.shopname=scharge.shopname;
        BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {
            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status) {
                try {
                    ChargeModel.this.callback(this, url, jo, status);

                    if (null != jo) {
                        chargeaddResponse response = new chargeaddResponse();
                        response.fromJson(jo);
                        if (response.succeed == 1) {
                            ChargeModel.this.OnMessageResponse(url, jo, status);
                        } else {
                            if(response.error_code== APIErrorCode.NICKNAME_EXIST){

                                ChargeModel.this.OnMessageResponse(url, jo, status);
                            }
                            ChargeModel.this.callback(url, response.error_code, response.error_desc);
                        }
                    }

                } catch (JSONException e) {

                }
                ;
            }
        };
        Map<String, String> params = new HashMap<String, String>();
        try {
            params.put("json", request.toJson().toString());
        } catch (JSONException e) {

        }

        cb.url(ApiInterface.CHARGE_ADD).type(JSONObject.class).params(params);
        //aq.ajax(cb);
        ajaxProgress(cb);
    }

    /*public void update(SIMPLE_CHARGE scharge) {
        chargeaddRequest request = new chargeaddRequest();
        request.member_no =scharge.member_no;
        request.charge_money = scharge.charge_money;
        request.give_money = scharge.give_money;
        request.real_money = scharge.real_money;
        request.begin_balance = scharge.begin_balance;
        request.end_balance = scharge.end_balance;
        request.bonus = scharge.bonus;
        request.oper = scharge.oper;
        request.opername = scharge.opername;
        request.shopid=scharge.shopid;
        request.shopname=scharge.shopname;
        request.id=scharge.id;
        BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {
            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status) {
                try {
                    ChargeModel.this.callback(this, url, jo, status);

                    if (null != jo) {
                        chargeaddResponse response = new chargeaddResponse();
                        response.fromJson(jo);
                        if (response.succeed == 1) {
                            ChargeModel.this.OnMessageResponse(url, jo, status);
                        } else {
                            if(response.error_code== APIErrorCode.NICKNAME_EXIST){

                                ChargeModel.this.OnMessageResponse(url, jo, status);
                            }
                            ChargeModel.this.callback(url, response.error_code, response.error_desc);
                        }
                    }

                } catch (JSONException e) {

                }
                ;
            }
        };
        Map<String, String> params = new HashMap<String, String>();
        try {
            params.put("json", request.toJson().toString());
        } catch (JSONException e) {

        }

        cb.url(ApiInterface.MEMBER_UPDATE).type(JSONObject.class).params(params);
        //aq.ajax(cb);
        ajaxProgress(cb);
    }

    public void del(int uid) {
        chargeaddRequest request = new chargeaddRequest();

        request.id=uid;
        BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {
            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status) {
                try {
                    ChargeModel.this.callback(this, url, jo, status);

                    if (null != jo) {
                        chargeaddResponse response = new chargeaddResponse();
                        response.fromJson(jo);
                        if (response.succeed == 1) {
                            ChargeModel.this.OnMessageResponse(url, jo, status);
                        } else {
                            if(response.error_code== APIErrorCode.NICKNAME_EXIST){

                                ChargeModel.this.OnMessageResponse(url, jo, status);
                            }
                            ChargeModel.this.callback(url, response.error_code, response.error_desc);
                        }
                    }

                } catch (JSONException e) {

                }
                ;
            }
        };
        Map<String, String> params = new HashMap<String, String>();
        try {
            params.put("json", request.toJson().toString());
        } catch (JSONException e) {

        }

        cb.url(ApiInterface.MEMBER_DEL).type(JSONObject.class).params(params);
        //aq.ajax(cb);
        ajaxProgress(cb);
    }*/

    public void getinfo(String member_no,String shopid)
    {
        chargeaddRequest request = new chargeaddRequest();

        request.member_no = member_no;
        request.shopid=shopid;



        BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {

            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status) {
                try {
                    ChargeModel.this.callback(this, url, jo, status);
                    if (null != jo)
                    {
                        chargeaddResponse response = new chargeaddResponse();
                        response.fromJson(jo);

                        if(response.succeed == 1)
                        {
                            charge= response.charge;
                            ChargeModel.this.OnMessageResponse(url,jo,status);
                        }
                        else
                        {
                            ChargeModel.this.callback(url, response.error_code, response.error_desc);
                        }
                    }else{
                        ChargeModel.this.OnMessageResponse(url, jo, status);
                    }

                } catch (JSONException e) {

                }
            }
        };

        Map<String, Object> params = new HashMap<String, Object>();
        try {
            JSONObject jsonObject = request.toJson();
            params.put("json", request.toJson().toString());

        } catch (JSONException e) {

        }
        if(isSendingMessage(ApiInterface.CHARGE_INFO)){
            return;
        }
        cb.url(ApiInterface.CHARGE_INFO).type(JSONObject.class).params(params);
        ajaxProgress(cb);

    }
}
