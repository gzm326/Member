package com.firesoft.member.Model;

import android.content.Context;

import com.BeeFramework.model.BaseModel;
import com.BeeFramework.model.BeeCallback;
import com.external.androidquery.callback.AjaxStatus;
import com.firesoft.member.APIErrorCode;
import com.firesoft.member.Protocol.ApiInterface;
import com.firesoft.member.Protocol.SIMPLE_NUMBERHISTORY;
import com.firesoft.member.Protocol.numberhistoryaddRequest;
import com.firesoft.member.Protocol.numberhistoryaddResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2015/12/1.
 */
public class NumberHistoryModel extends BaseModel {
    private Context context;
    public SIMPLE_NUMBERHISTORY object;
    public NumberHistoryModel(Context context) {
        super(context);
        this.context = context;

    }
    public void add(SIMPLE_NUMBERHISTORY number) {
        numberhistoryaddRequest request = new numberhistoryaddRequest();
        request.member_no =number.member_no;
        request.product_id = number.product_id;
        request.product_name = number.product_name;
        request.type_id = number.type_id;
        request.type_name = number.type_name;
        request.num = number.num;
        request.sale_price = number.sale_price;
        request.sum = number.sum;
        request.product_price = number.product_price;
        request.special = number.special;
        request.specia_price = number.specia_price;
        request.unit = number.unit;
        request.special_discount = number.special_discount;
        request.special_credit = number.special_credit;
        request.pay_ali = number.pay_ali;
        request.pay_wx = number.pay_wx;
        request.pay_cash = number.pay_cash;
        request.pay_card = number.pay_card;
        request.pay_balance = number.pay_balance;
        request.oper = number.oper;
        request.opername = number.opername;
        request.shopid=number.shopid;
        request.shopname=number.shopname;
        BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {
            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status) {
                try {
                    NumberHistoryModel.this.callback(this, url, jo, status);

                    if (null != jo) {
                        numberhistoryaddResponse response = new numberhistoryaddResponse();
                        response.fromJson(jo);
                        if (response.succeed == 1) {
                            NumberHistoryModel.this.OnMessageResponse(url, jo, status);
                        } else {
                            if(response.error_code== APIErrorCode.NICKNAME_EXIST){

                                NumberHistoryModel.this.OnMessageResponse(url, jo, status);
                            }
                            NumberHistoryModel.this.callback(url, response.error_code, response.error_desc);
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

        cb.url(ApiInterface.NUMBERHISTORY_ADD).type(JSONObject.class).params(params);
        //aq.ajax(cb);
        ajaxProgress(cb);
    }

    public void getinfo(String member_no,String shopid)
    {
        numberhistoryaddRequest request = new numberhistoryaddRequest();

        request.member_no = member_no;
        request.shopid=shopid;



        BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {

            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status) {
                try {
                    NumberHistoryModel.this.callback(this, url, jo, status);
                    if (null != jo)
                    {
                        numberhistoryaddResponse response = new numberhistoryaddResponse();
                        response.fromJson(jo);

                        if(response.succeed == 1)
                        {
                            object= response.number;
                            NumberHistoryModel.this.OnMessageResponse(url,jo,status);
                        }
                        else
                        {
                            NumberHistoryModel.this.callback(url, response.error_code, response.error_desc);
                        }
                    }else{
                        NumberHistoryModel.this.OnMessageResponse(url, jo, status);
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
        if(isSendingMessage(ApiInterface.NUMBERHISTORY_INFO)){
            return;
        }
        cb.url(ApiInterface.NUMBERHISTORY_INFO).type(JSONObject.class).params(params);
        ajaxProgress(cb);

    }
}
