package com.firesoft.member.Model;

import android.content.Context;

import com.BeeFramework.model.BaseModel;
import com.BeeFramework.model.BeeCallback;
import com.external.androidquery.callback.AjaxStatus;
import com.firesoft.member.APIErrorCode;
import com.firesoft.member.Protocol.ApiInterface;
import com.firesoft.member.Protocol.SIMPLE_PRODUCT;
import com.firesoft.member.Protocol.productaddRequest;
import com.firesoft.member.Protocol.productaddResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2015/10/30.
 */
public class ProductModel extends BaseModel {
    private Context context;
    public SIMPLE_PRODUCT product;
    public ProductModel(Context context) {
        super(context);
        this.context = context;

    }
    public void add(SIMPLE_PRODUCT product) {
        productaddRequest request = new productaddRequest();

        request.type_id = product.type_id;
        request.name =product.name ;
        request.price =product.price;
        request.special = product.special;
        request.specia_price = product.specia_price;
        request.unit = product.unit;
        request.special_discount = product.special_discount;
        request.special_credit = product.special_credit;

        BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {
            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status) {
                try {
                    ProductModel.this.callback(this, url, jo, status);

                    if (null != jo) {
                        productaddResponse response = new productaddResponse();
                        response.fromJson(jo);
                        if (response.succeed == 1) {
                            ProductModel.this.OnMessageResponse(url, jo, status);
                        } else {
                            if(response.error_code== APIErrorCode.NICKNAME_EXIST){

                                ProductModel.this.OnMessageResponse(url, jo, status);
                            }
                            ProductModel.this.callback(url, response.error_code, response.error_desc);
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

        cb.url(ApiInterface.PRODUCT_ADD).type(JSONObject.class).params(params);
        //aq.ajax(cb);
        ajaxProgress(cb);
    }

    public void update( SIMPLE_PRODUCT product) {
        productaddRequest request = new productaddRequest();

        request.type_id = product.type_id;
        request.name =product.name ;
        request.price =product.price;
        request.special = product.special;
        request.specia_price = product.specia_price;
        request.unit = product.unit;
        request.special_discount = product.special_discount;
        request.special_credit = product.special_credit;
        request.uid=product.id;
        BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {
            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status) {
                try {
                    ProductModel.this.callback(this, url, jo, status);

                    if (null != jo) {
                        productaddResponse response = new productaddResponse();
                        response.fromJson(jo);
                        if (response.succeed == 1) {
                            ProductModel.this.OnMessageResponse(url, jo, status);
                        } else {
                            if(response.error_code== APIErrorCode.NICKNAME_EXIST){

                                ProductModel.this.OnMessageResponse(url, jo, status);
                            }
                            ProductModel.this.callback(url, response.error_code, response.error_desc);
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

        cb.url(ApiInterface.PRODUCT_UPDATE).type(JSONObject.class).params(params);
        //aq.ajax(cb);
        ajaxProgress(cb);
    }

    public void del(int uid) {
        productaddRequest request = new productaddRequest();

        request.uid=uid;
        BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {
            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status) {
                try {
                    ProductModel.this.callback(this, url, jo, status);

                    if (null != jo) {
                        productaddResponse response = new productaddResponse();
                        response.fromJson(jo);
                        if (response.succeed == 1) {
                            ProductModel.this.OnMessageResponse(url, jo, status);
                        } else {
                            if(response.error_code== APIErrorCode.NICKNAME_EXIST){

                                ProductModel.this.OnMessageResponse(url, jo, status);
                            }
                            ProductModel.this.callback(url, response.error_code, response.error_desc);
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

        cb.url(ApiInterface.PRODUCT_DEL).type(JSONObject.class).params(params);
        //aq.ajax(cb);
        ajaxProgress(cb);
    }

    public void getinfo(int id)
    {
        productaddRequest memberrequest = new productaddRequest();

        memberrequest.uid = id;



        BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {

            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status) {
                try {
                    ProductModel.this.callback(this, url, jo, status);
                    if (null != jo)
                    {
                        productaddResponse response = new productaddResponse();
                        response.fromJson(jo);

                        if(response.succeed == 1)
                        {
                            product= response.product;
                            ProductModel.this.OnMessageResponse(url,jo,status);
                        }
                        else
                        {
                            ProductModel.this.callback(url, response.error_code, response.error_desc);
                        }
                    }else{
                        ProductModel.this.OnMessageResponse(url, jo, status);
                    }

                } catch (JSONException e) {

                }
            }
        };

        Map<String, Object> params = new HashMap<String, Object>();
        try {
            JSONObject jsonObject = memberrequest.toJson();
            params.put("json", memberrequest.toJson().toString());

        } catch (JSONException e) {

        }
        if(isSendingMessage(ApiInterface.PRODUCT_INFO)){
            return;
        }
        cb.url(ApiInterface.PRODUCT_INFO).type(JSONObject.class).params(params);
        ajaxProgress(cb);

    }
}
