package com.firesoft.member.Activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Message;
import android.view.Gravity;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.BusinessResponse;
import com.BeeFramework.view.ToastView;
import com.external.androidquery.callback.AjaxStatus;
import com.external.eventbus.EventBus;
import com.firesoft.member.APIErrorCode;
import com.firesoft.member.Adapter.ProductxfAdapter;
import com.firesoft.member.Adapter.SpecificationAdapter;
import com.firesoft.member.MemberAppConst;
import com.firesoft.member.MessageConstant;
import com.firesoft.member.Model.MemberModel;
import com.firesoft.member.Model.SalesModel;
import com.firesoft.member.Protocol.ApiInterface;
import com.firesoft.member.Protocol.SIMPLE_MEMBER;
import com.firesoft.member.Protocol.SIMPLE_NUMBER;
import com.firesoft.member.Protocol.SIMPLE_SALES;
import com.firesoft.member.Protocol.memberaddResponse;
import com.firesoft.member.Protocol.salesaddResponse;
import com.firesoft.member.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class E1_McardjcxfActivity extends BaseActivity implements BusinessResponse {
    public static final int REQUESTCODE1 = 1;
    TextView tv_add_goods,tv_nodata,tv_del_goods;
    ListView lv_product;
    ScrollView sv_product;
    ProductxfAdapter listAdapter;
    HashMap<String,SIMPLE_NUMBER> hm=new HashMap<String,SIMPLE_NUMBER>();

    private TextView txv_no;
    private TextView txv_name;
    private TextView txv_type;
    private TextView txv_state;
    private TextView txv_money;
    private TextView txv_integrals;
    private TextView txv_phone;
    private Button btn_qd;
    private EditText edt_keyword;
    private MemberModel mMemberModel;
    private SharedPreferences mShared;
    private String nShopid;
    private String nShopname;
    private TextView   mTitleTextView;
    private SalesModel mDataModel;
    private DecimalFormat df;

    private TextView mAddComplete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.e0_mcard_jcxf);
        mShared =getSharedPreferences(MemberAppConst.USERINFO, 0);
        init_qd();

        tv_add_goods=(TextView)findViewById(R.id.add_goods);
        tv_del_goods=(TextView)findViewById(R.id.delete_goods);
        lv_product = (ListView)findViewById(R.id.listview_goods);
        sv_product= (ScrollView)findViewById(R.id.scroll_goods);
        mTitleTextView = (TextView)findViewById(R.id.top_view_title);
        mAddComplete = (TextView) findViewById(R.id.c0_publish_button1);
        df = new DecimalFormat("#.00");
        mTitleTextView.setText("会员消费");

        tv_add_goods.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                String member_no = txv_no.getText().toString();
                if ("--".equals(member_no)) {
                    ToastShow("请输入会员卡号！");
                    edt_keyword.setText("");
                    edt_keyword.requestFocus();
                }
                Intent it = new Intent(E1_McardjcxfActivity.this, SpecificationActivity.class);
                it.putExtra("member_no", member_no);
                it.putExtra("shopid", nShopid);
                //startActivity(it);
                startActivityForResult(it, REQUESTCODE1);
                overridePendingTransition(R.anim.my_scale_action, R.anim.my_alpha_action);

            }
        });

        tv_del_goods.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                ProductxfAdapter sf=(ProductxfAdapter)lv_product.getAdapter();
                ProductxfAdapter la;
                ArrayList<SIMPLE_NUMBER> al ;
                delHpmap(sf.getCheckedHm());
                al = getAlProduct(hm);
                la = new ProductxfAdapter(E1_McardjcxfActivity.this,al);
                lv_product.setAdapter(la);
            }
        });

          mAddComplete.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {


                String member_no = txv_no.getText().toString();

                int userid = mShared.getInt("uid", 0);


                if ("--".equals(member_no)) {
                    ToastShow("请选择会员信息！");
                    edt_keyword.setText("");
                    edt_keyword.requestFocus();
                } else if (hm == null) {
                    ToastShow("请选择消费项目");

                } else {
                    ToastShow(Integer.toString(hm.size()));
                    /*Iterator iter = hm.entrySet().iterator();
                    while (iter.hasNext()) {
                        Map.Entry entry = (Map.Entry) iter.next();
                        SIMPLE_NUMBER number= (SIMPLE_NUMBER)entry.getValue();
                        SIMPLE_SALES sale =  new SIMPLE_SALES();
                        sale.member_no = member_no;
                        sale.product_id = number.product_id;
                        sale.product_name = number.product_name;
                        sale.type_id = number.type_id;
                        sale.type_name = number.type_name;
                        sale.num = Integer.toString(number.lnum);
                        sale.sale_price = number.sale_price;
                        sale.sum =df.format(Double.toString(number.lnum*Double.parseDouble(number.sale_price))) ;
                        sale.oper = Integer.toString(userid);
                        sale.opername = Integer.toString(userid);
                        sale.shopid = nShopid;
                        sale.shopname = nShopname;
                        ToastShow(Integer.toString(number.lnum));
                    }*/
                    //mNumberModel.add(obj);

                }

            }
        });

    }

    private void init_qd(){
        txv_no=(TextView)findViewById(R.id.txv_kh);
        txv_name=(TextView)findViewById(R.id.txv_name);
        txv_type=(TextView)findViewById(R.id.txv_type);
        txv_state=(TextView)findViewById(R.id.txv_state);
        txv_money=(TextView)findViewById(R.id.txv_money);
        txv_integrals=(TextView)findViewById(R.id.txv_integrals);
        txv_phone=(TextView)findViewById(R.id.txv_phone);
        btn_qd=(Button)findViewById(R.id.btn_qd);
        edt_keyword=(EditText)findViewById(R.id.edt_keyword);

        nShopid=mShared.getString("shopid", "0");
        nShopname = mShared.getString("shopname", "");

        btn_qd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tmp = edt_keyword.getText().toString();
                String str = "00000000" + tmp;
                String member_no = str.substring(str.length() - 8, str.length());


                if ("".equals(member_no)) {
                    ToastShow("请输入会员卡号！");
                    edt_keyword.setText("");
                    edt_keyword.requestFocus();
                }
                mMemberModel.getinfo(member_no, nShopid);


            }
        });
        mMemberModel = new MemberModel(this);
        mMemberModel.addResponseListener(this);

        mDataModel = new SalesModel(this);
        mDataModel.addResponseListener(this);


    }
    private  void init_null(){
        txv_no.setText("--");
        txv_name.setText("--");
        txv_type.setText("--");
        txv_state.setText("--");
        txv_money.setText("--");
        txv_integrals.setText("--");
        txv_phone.setText("--");
    }
    private  void init_member(SIMPLE_MEMBER member){
        txv_no.setText(member.member_no);
        txv_name.setText(member.member_name);
        txv_type.setText("普通会员");
        txv_state.setText("正常");
        txv_money.setText("00");
        txv_integrals.setText("00");
        txv_phone.setText(member.mobile_no);
    }


    @Override
    public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status) throws JSONException {
        if(url.endsWith(ApiInterface.MEMBER_MINFO))
        {
            if (null != jo)
            {
                memberaddResponse response = new memberaddResponse();
                response.fromJson(jo);
                //ToastShow(response.member.member_name);
                SIMPLE_MEMBER member;
                member = mMemberModel.member;
                if (null != member) {
                    //init_member(member);
                    if(null !=member.member_name) {
                        init_member(member);
                    }else {
                        ToastShow("会员卡号输入有误，无此会员信息！");
                        init_null();
                        edt_keyword.setText("");
                        edt_keyword.requestFocus();
                    }
                } else {
                    ToastShow("会员卡号输入有误，无此会员信息！");
                    init_null();
                    edt_keyword.setText("");
                    edt_keyword.requestFocus();
                }
            }else if (url.endsWith(ApiInterface.SALES_ADD)) {
                salesaddResponse response = new salesaddResponse();
                response.fromJson(jo);
                if (response.succeed == 1) {
                    init_null();
                    Message msg = new Message();
                    msg.what = MessageConstant.REFRESH_LIST;
                    EventBus.getDefault().post(msg);
                }else {
                    if (response.error_code == APIErrorCode.NICKNAME_EXIST) {

                    }
                }
            }
        }

    }

    public  void ToastShow(String atr){
        ToastView toast = new ToastView(E1_McardjcxfActivity.this, atr);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    private void delHpmap(HashMap<String,SIMPLE_NUMBER> hbx){
        if(hm.size()>0){
            Iterator iter = hbx.entrySet().iterator();
            while (iter.hasNext()){
                Map.Entry entry = (Map.Entry) iter.next();
                if(hm.containsKey(entry.getKey())){
                    hm.remove(entry.getKey());
                }
            }
        }
    }

    private void  getHbmap(HashMap<String,SIMPLE_NUMBER> hbx){
        if(hm.size()==0){
            hm=hbx;
        }else{
            Iterator iter = hbx.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry entry = (Map.Entry) iter.next();
                if(hm.containsKey(entry.getKey())){
                    SIMPLE_NUMBER number;
                    number = (SIMPLE_NUMBER)entry.getValue();
                    if(Integer.parseInt(hm.get(entry.getKey()).num)<Integer.parseInt(hm.get(entry.getKey()).num) - Integer.parseInt(hm.get(entry.getKey()).donum)){
                        number.lnum=hm.get(entry.getKey()).lnum+1;
                    }else{
                        number.lnum=hm.get(entry.getKey()).lnum;
                    }


                    //ToastShow(Integer.toString(product.num));
                    hm.remove(entry.getKey());
                    hm.put(number.product_id,number);
                }else{
                    hm.put((String)entry.getKey(),(SIMPLE_NUMBER)entry.getValue());
                }
            }
        }
    }

    private ArrayList<SIMPLE_NUMBER> getAlProduct(HashMap<String,SIMPLE_NUMBER> map){
        ArrayList<SIMPLE_NUMBER> al= new ArrayList<SIMPLE_NUMBER>();
        Iterator iter = map.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            al.add((SIMPLE_NUMBER)entry.getValue());
        }
        return al;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        ArrayList<SIMPLE_NUMBER> productArrayList;
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUESTCODE1){
            if(resultCode == Activity.RESULT_OK){
                //productArrayList=(ArrayList<Product>)data.getSerializableExtra("product");
                HashMap<String,SIMPLE_NUMBER> hmTemp;
                hmTemp=(HashMap<String,SIMPLE_NUMBER>)data.getSerializableExtra("numbers");
                getHbmap(hmTemp);
                productArrayList= getAlProduct(hm);

                tv_nodata = (TextView)findViewById(R.id.no_data);
                if(productArrayList.size()>0){
                    sv_product.setVisibility(View.VISIBLE);
                    tv_nodata.setVisibility(View.GONE);
                }
                listAdapter = new ProductxfAdapter(this,productArrayList);
                lv_product.setAdapter(listAdapter);
                //ToastShow(Integer.toString(hm.size()));
            }
        }
    }

}
