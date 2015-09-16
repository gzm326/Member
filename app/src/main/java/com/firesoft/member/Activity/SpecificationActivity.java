package com.firesoft.member.Activity;
//
//                       __
//                      /\ \   _
//    ____    ____   ___\ \ \_/ \           _____    ___     ___
//   / _  \  / __ \ / __ \ \    <     __   /\__  \  / __ \  / __ \
//  /\ \_\ \/\  __//\  __/\ \ \\ \   /\_\  \/_/  / /\ \_\ \/\ \_\ \
//  \ \____ \ \____\ \____\\ \_\\_\  \/_/   /\____\\ \____/\ \____/
//   \/____\ \/____/\/____/ \/_//_/         \/____/ \/___/  \/___/
//     /\____/
//     \/___/
//
//  Powered by BeeFramework
//

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.external.androidquery.callback.AjaxStatus;
import com.external.eventbus.EventBus;
import com.BeeFramework.model.BusinessResponse;
import com.BeeFramework.view.ToastView;
import com.firesoft.member.R;
import com.firesoft.member.Adapter.SpecificationAdapter;
import com.firesoft.member.Model.Product;
import com.firesoft.member.Protocol.SPECIFICATION_VALUE;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SpecificationActivity extends Activity implements BusinessResponse
{

    private ListView specificationListView;
    private SpecificationAdapter listAdapter;
    private ImageView minusImageView;
    private ImageView addImageView;
    private TextView ok;
    private EditText quantityEditText;
    private TextView goodTotalPriceTextView;
    private View addItemComponent;
    private int num;
    private boolean creat_cart;
    private ArrayList<Product> productA2;
    private Product product1,product2,product3;

	@Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.specification_activity);
        
        /*Intent intent = getIntent();
        num = intent.getIntExtra("num", 0);
        creat_cart = intent.getBooleanExtra("creat_cart", false);*/

        specificationListView = (ListView)findViewById(R.id.specification_list);
        productA2=initProduct();
        listAdapter = new SpecificationAdapter(this,productA2 );
        specificationListView.setAdapter(listAdapter);
        



        
        ok = (TextView) findViewById(R.id.shop_car_item_ok);
        ok.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                ArrayList<Product> productA3;
                productA3= initProduct();
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putSerializable("product",productA3);
                intent.putExtras(bundle);
                setResult(Activity.RESULT_OK, intent);
                finish();
                overridePendingTransition(R.anim.my_alpha_action, R.anim.my_scale_finish);

            }
        });

        EventBus.getDefault().register(this);


    }

    private ArrayList<Product> initProduct(){
        ArrayList<Product> productAl = new ArrayList<Product>();
        product1= new Product();
        product2= new Product();
        product3= new Product();

        product1.str_productid="0101";
        product1.str_productname="面部护理";
        product1.it_maxnum=10;
        product1.it_num=1;

        product2.str_productid="0102";
        product2.str_productname="头部护理";
        product2.it_maxnum=10;
        product2.it_num=2;

        product3.str_productid="0103";
        product3.str_productname="背部护理";
        product3.it_maxnum=10;
        product3.it_num=3;

        productAl.add(product1);
        productAl.add(product2);
        productAl.add(product3);

        return productAl;
    }

    private ArrayList<Product> getProduct(){

        ArrayList<Product> productAl = new ArrayList<Product>();

        return productAl;

    }

    @Override
    public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status) throws JSONException {

    }

    public void onEvent(Object event)
    {
       /* if (event.getClass() == SPECIFICATION_VALUE.class)
        {
            refreshTotalPrice();
        }*/
    }
    
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {		
		if(keyCode == KeyEvent.KEYCODE_BACK){
			finish();
			overridePendingTransition(R.anim.my_alpha_action,R.anim.my_scale_finish);
		}
		return true;
	}
}
