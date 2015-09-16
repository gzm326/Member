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


public class E1_McardjcxfActivity extends BaseActivity implements BusinessResponse {
    public static final int REQUESTCODE1 = 1;
    TextView tv_add_goods,tv_nodata;
    ListView lv_product;
    ScrollView sv_product;
    ProductxfAdapter listAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.e0_mcard_jcxf);

        tv_add_goods=(TextView)findViewById(R.id.add_goods);

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

    }

    @Override
    public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status) throws JSONException {

    }

    public  void ToastShow(String atr){
        ToastView toast = new ToastView(E1_McardjcxfActivity.this, atr);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        ArrayList<Product> productArrayList;
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUESTCODE1){
            if(resultCode == Activity.RESULT_OK){
                productArrayList=(ArrayList<Product>)data.getSerializableExtra("product");
                lv_product = (ListView)findViewById(R.id.listview_goods);
                sv_product= (ScrollView)findViewById(R.id.scroll_goods);
                tv_nodata = (TextView)findViewById(R.id.no_data);
                if(productArrayList.size()>0){
                    sv_product.setVisibility(View.VISIBLE);
                    tv_nodata.setVisibility(View.GONE);
                }
                listAdapter = new ProductxfAdapter(this,productArrayList);
                lv_product.setAdapter(listAdapter);
                ToastShow("返回成功");
            }
        }
    }

}
