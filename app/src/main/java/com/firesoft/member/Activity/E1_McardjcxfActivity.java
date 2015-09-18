package com.firesoft.member.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.BusinessResponse;
import com.BeeFramework.view.ToastView;
import com.external.androidquery.callback.AjaxStatus;
import com.firesoft.member.Adapter.ProductxfAdapter;
import com.firesoft.member.Adapter.SpecificationAdapter;
import com.firesoft.member.Model.Product;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.e0_mcard_jcxf);

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

    @Override
    public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status) throws JSONException {

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
