package com.firesoft.member.Model;

import android.content.Context;

import com.BeeFramework.model.BaseModel;
import com.BeeFramework.model.BeeCallback;
import com.external.androidquery.callback.AjaxStatus;
import com.firesoft.member.APIErrorCode;
import com.firesoft.member.Protocol.ApiInterface;
import com.firesoft.member.Protocol.SIMPLE_GRADE;
import com.firesoft.member.Protocol.gradeaddRequest;
import com.firesoft.member.Protocol.gradeaddResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2015/11/4.
 */
public class GradeModel extends BaseModel {
    private Context context;
    public SIMPLE_GRADE grade;
    public GradeModel(Context context) {
        super(context);
        this.context = context;

    }
    public void add(SIMPLE_GRADE grade) {
        gradeaddRequest request = new gradeaddRequest();

        request.discount_percent = grade.discount_percent;
        request.name =grade.name ;
        request.shopid=grade.shopid;
        request.shopname=grade.shopname;
        request.credit_percent =grade.credit_percent;
        request.validity = grade.validity;
        request.validity_unit = grade.validity_unit;
        request.beg_money = grade.beg_money;
        request.beg_credit = grade.beg_credit;
        request.rec_credit = grade.rec_credit;
        request.rem = grade.rem;

        BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {
            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status) {
                try {
                    GradeModel.this.callback(this, url, jo, status);

                    if (null != jo) {
                        gradeaddResponse response = new gradeaddResponse();
                        response.fromJson(jo);
                        if (response.succeed == 1) {
                            GradeModel.this.OnMessageResponse(url, jo, status);
                        } else {
                            if(response.error_code== APIErrorCode.NICKNAME_EXIST){

                                GradeModel.this.OnMessageResponse(url, jo, status);
                            }
                            GradeModel.this.callback(url, response.error_code, response.error_desc);
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

        cb.url(ApiInterface.GRADE_ADD).type(JSONObject.class).params(params);
        //aq.ajax(cb);
        ajaxProgress(cb);
    }

    public void update( SIMPLE_GRADE grade) {
        gradeaddRequest request = new gradeaddRequest();

        request.discount_percent = grade.discount_percent;
        request.name =grade.name ;
        request.shopid=grade.shopid;
        request.shopname=grade.shopname;
        request.credit_percent =grade.credit_percent;
        request.validity = grade.validity;
        request.validity_unit = grade.validity_unit;
        request.beg_money = grade.beg_money;
        request.beg_credit = grade.beg_credit;
        request.rec_credit = grade.rec_credit;
        request.rem = grade.rem;
        request.uid=grade.id;
        BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {
            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status) {
                try {
                    GradeModel.this.callback(this, url, jo, status);

                    if (null != jo) {
                        gradeaddResponse response = new gradeaddResponse();
                        response.fromJson(jo);
                        if (response.succeed == 1) {
                            GradeModel.this.OnMessageResponse(url, jo, status);
                        } else {
                            if(response.error_code== APIErrorCode.NICKNAME_EXIST){

                                GradeModel.this.OnMessageResponse(url, jo, status);
                            }
                            GradeModel.this.callback(url, response.error_code, response.error_desc);
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

        cb.url(ApiInterface.GRADE_UPDATE).type(JSONObject.class).params(params);
        //aq.ajax(cb);
        ajaxProgress(cb);
    }

    public void del(int uid) {
        gradeaddRequest request = new gradeaddRequest();

        request.uid=uid;
        BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {
            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status) {
                try {
                    GradeModel.this.callback(this, url, jo, status);

                    if (null != jo) {
                        gradeaddResponse response = new gradeaddResponse();
                        response.fromJson(jo);
                        if (response.succeed == 1) {
                            GradeModel.this.OnMessageResponse(url, jo, status);
                        } else {
                            if(response.error_code== APIErrorCode.NICKNAME_EXIST){

                                GradeModel.this.OnMessageResponse(url, jo, status);
                            }
                            GradeModel.this.callback(url, response.error_code, response.error_desc);
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

        cb.url(ApiInterface.GRADE_DEL).type(JSONObject.class).params(params);
        //aq.ajax(cb);
        ajaxProgress(cb);
    }

    public void getinfo(int id)
    {
        gradeaddRequest memberrequest = new gradeaddRequest();

        memberrequest.uid = id;



        BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {

            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status) {
                try {
                    GradeModel.this.callback(this, url, jo, status);
                    if (null != jo)
                    {
                        gradeaddResponse response = new gradeaddResponse();
                        response.fromJson(jo);

                        if(response.succeed == 1)
                        {
                            grade= response.grade;
                            GradeModel.this.OnMessageResponse(url,jo,status);
                        }
                        else
                        {
                            GradeModel.this.callback(url, response.error_code, response.error_desc);
                        }
                    }else{
                        GradeModel.this.OnMessageResponse(url, jo, status);
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
        if(isSendingMessage(ApiInterface.GRADE_INFO)){
            return;
        }
        cb.url(ApiInterface.GRADE_INFO).type(JSONObject.class).params(params);
        ajaxProgress(cb);

    }
}
