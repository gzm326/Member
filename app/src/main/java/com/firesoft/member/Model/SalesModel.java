package com.firesoft.member.Model;

import android.content.Context;

import com.BeeFramework.model.BaseModel;
import com.BeeFramework.model.BeeCallback;
import com.external.androidquery.callback.AjaxStatus;
import com.firesoft.member.APIErrorCode;
import com.firesoft.member.Protocol.ApiInterface;
import com.firesoft.member.Protocol.SIMPLE_SALES;
import com.firesoft.member.Protocol.salesaddRequest;
import com.firesoft.member.Protocol.salesaddResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2015/12/4.
 */
public class SalesModel extends BaseModel {
    private Context context;
    public SIMPLE_SALES object;
    public SalesModel(Context context) {
        super(context);
        this.context = context;

    }
    public void add(ArrayList<SIMPLE_SALES> number) {

        salesaddRequest request = new salesaddRequest();
        request.sales=number;
        /*request.member_no =number.member_no;
        request.product_id = number.product_id;
        request.product_name = number.product_name;
        request.type_id = number.type_id;
        request.type_name = number.type_name;
        request.num = number.num;
        request.sale_price = number.sale_price;
        request.sum = number.sum;
        request.oper = number.oper;
        request.opername = number.opername;
        request.shopid=number.shopid;
        request.shopname=number.shopname;*/
        BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {
            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status) {
                try {
                    SalesModel.this.callback(this, url, jo, status);

                    if (null != jo) {
                        salesaddResponse response = new salesaddResponse();
                        response.fromJson(jo);
                        if (response.succeed == 1) {
                            SalesModel.this.OnMessageResponse(url, jo, status);
                        } else {
                            if(response.error_code== APIErrorCode.NICKNAME_EXIST){

                                SalesModel.this.OnMessageResponse(url, jo, status);
                            }
                            SalesModel.this.callback(url, response.error_code, response.error_desc);
                        }
                    }

                } catch (JSONException e) {

                }
                ;
            }
        };
        Map<String, String> params = new HashMap<String, String>();
        try {
            params.put("json", request.toAddJson().toString());
        } catch (JSONException e) {

        }

        cb.url(ApiInterface.SALES_ADD).type(JSONObject.class).params(params);
        //aq.ajax(cb);
        ajaxProgress(cb);
    }

    public void getinfo(String member_no,String shopid)
    {
        salesaddRequest request = new salesaddRequest();

        request.member_no = member_no;
        request.shopid=shopid;



        BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {

            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status) {
                try {
                    SalesModel.this.callback(this, url, jo, status);
                    if (null != jo)
                    {
                        salesaddResponse response = new salesaddResponse();
                        response.fromJson(jo);

                        if(response.succeed == 1)
                        {
                            object= response.sale;
                            SalesModel.this.OnMessageResponse(url,jo,status);
                        }
                        else
                        {
                            SalesModel.this.callback(url, response.error_code, response.error_desc);
                        }
                    }else{
                        SalesModel.this.OnMessageResponse(url, jo, status);
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
        if(isSendingMessage(ApiInterface.SALES_INFO)){
            return;
        }
        cb.url(ApiInterface.SALES_INFO).type(JSONObject.class).params(params);
        ajaxProgress(cb);

    }
}
