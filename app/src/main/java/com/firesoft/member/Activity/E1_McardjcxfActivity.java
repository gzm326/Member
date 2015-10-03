package com.firesoft.member.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
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
import com.firesoft.member.Adapter.ProductxfAdapter;
import com.firesoft.member.Adapter.SpecificationAdapter;
import com.firesoft.member.Model.MemberModel;
import com.firesoft.member.Model.Product;
import com.firesoft.member.Protocol.ApiInterface;
import com.firesoft.member.Protocol.SIMPLE_MEMBER;
import com.firesoft.member.Protocol.memberaddResponse;
import com.firesoft.member.R;

import org.json.JSONException;
import org.json.JSONObject;

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
    HashMap<String,Product> hm=new HashMap<String,Product>();

    private TextView txv_no;
    private TextView txv_name;
    private TextView txv_type;
    private TextView txv_state;
    private TextView txv_money;
    private TextView txv_integrals;
    private TextView txv_phone;
    private Button btn_qd;
    private EditText edt_keyword;
    private MemberModel mDataModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.e0_mcard_jcxf);
        init_qd();

        tv_add_goods=(TextView)findViewById(R.id.add_goods);
        tv_del_goods=(TextView)findViewById(R.id.delete_goods);
        lv_product = (ListView)findViewById(R.id.listview_goods);
        sv_product= (ScrollView)findViewById(R.id.scroll_goods);

        tv_add_goods.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(E1_McardjcxfActivity.this,SpecificationActivity.class);
                //it.putExtra("num", Integer.valueOf(dataModel.goodDetail.goods_number));
                //startActivity(it);
                startActivityForResult(it, REQUESTCODE1 );
                overridePendingTransition(R.anim.my_scale_action, R.anim.my_alpha_action);

            }
        });

        tv_del_goods.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                ProductxfAdapter sf=(ProductxfAdapter)lv_product.getAdapter();
                ProductxfAdapter la;
                ArrayList<Product> al ;
                delHpmap(sf.getCheckedHm());
                al = getAlProduct(hm);
                la = new ProductxfAdapter(E1_McardjcxfActivity.this,al);
                lv_product.setAdapter(la);
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
        btn_qd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String member_no = edt_keyword.getText().toString();
                if ("".equals(member_no)) {
                    ToastShow("请输入会员卡号！");
                    edt_keyword.setText("");
                    edt_keyword.requestFocus();
                }
                mDataModel.getinfo(member_no);


            }
        });
        mDataModel = new MemberModel(this);
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
                member = mDataModel.member;
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
            }
        }

    }

    public  void ToastShow(String atr){
        ToastView toast = new ToastView(E1_McardjcxfActivity.this, atr);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    private void delHpmap(HashMap<String,Product> hbx){
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

    private void  getHbmap(HashMap<String,Product> hbx){
        if(hm.size()==0){
            hm=hbx;
        }else{
            Iterator iter = hbx.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry entry = (Map.Entry) iter.next();
                if(hm.containsKey(entry.getKey())){
                    Product product;
                    product = (Product)entry.getValue();
                    if(hm.get(entry.getKey()).num<hm.get(entry.getKey()).it_maxnum-hm.get(entry.getKey()).it_num){
                        product.num=hm.get(entry.getKey()).num+1;
                    }else{
                        product.num=hm.get(entry.getKey()).num;
                    }


                    //ToastShow(Integer.toString(product.num));
                    hm.remove(entry.getKey());
                    hm.put(product.str_productid,product);
                }else{
                    hm.put((String)entry.getKey(),(Product)entry.getValue());
                }
            }
        }
    }

    private ArrayList<Product> getAlProduct(HashMap<String,Product> map){
        ArrayList<Product> al= new ArrayList<Product>();
        Iterator iter = map.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            al.add((Product)entry.getValue());
        }
        return al;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        ArrayList<Product> productArrayList;
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUESTCODE1){
            if(resultCode == Activity.RESULT_OK){
                //productArrayList=(ArrayList<Product>)data.getSerializableExtra("product");
                HashMap<String,Product> hmTemp;
                hmTemp=(HashMap<String,Product>)data.getSerializableExtra("product");
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
