package com.firesoft.member.Model;

import android.content.Context;

import com.BeeFramework.model.BaseModel;
import com.BeeFramework.model.BeeCallback;
import com.external.androidquery.callback.AjaxStatus;
import com.firesoft.member.MemberAppConst;
import com.firesoft.member.Protocol.ApiInterface;
import com.firesoft.member.Protocol.ENUM_SEARCH_ORDER;
import com.firesoft.member.Protocol.SIMPLE_CHOOSE;
import com.firesoft.member.Protocol.chooselistRequest;
import com.firesoft.member.Protocol.chooselistResponse;
import com.firesoft.member.SESSION;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 15-10-4.
 */
public class ChooseModel extends BaseModel {
    public ArrayList<SIMPLE_CHOOSE> dataList = new ArrayList<SIMPLE_CHOOSE>();
    public static final int NUMPERPAGE = 10;

    public ChooseModel(Context context)
    {
        super(context);
    }

    public void getList()
    {
        chooselistRequest chooselistrequest = new chooselistRequest();


        chooselistrequest.uid = SESSION.getInstance().uid;
        BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {

            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status) {
                try {
                    ChooseModel.this.callback(this, url, jo, status);
                    if (null != jo)
                    {
                        chooselistResponse response = new chooselistResponse();
                        response.fromJson(jo);

                        if(response.succeed == 1)
                        {
                            dataList.clear();
                            dataList.addAll(response.chooses);
                            ChooseModel.this.OnMessageResponse(url,jo,status);
                        }
                        else
                        {
                            ChooseModel.this.callback(url, response.error_code, response.error_desc);
                        }
                    }else{
                        ChooseModel.this.OnMessageResponse(url,jo,status);
                    }

                } catch (JSONException e) {

                }
            }
        };

        Map<String, Object> params = new HashMap<String, Object>();
        try {
            JSONObject jsonObject = chooselistrequest.toJson();
            params.put("json", chooselistrequest.toJson().toString());

        } catch (JSONException e) {

        }
        if(isSendingMessage(ApiInterface.CHOOSE_LIST)){
            return;
        }
        cb.url(ApiInterface.CHOOSE_LIST).type(JSONObject.class).params(params);
        ajaxProgress(cb);

    }
}
