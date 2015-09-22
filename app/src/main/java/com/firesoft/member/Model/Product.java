package com.firesoft.member.Model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Administrator on 2015/9/15.
 */
public class Product implements Serializable{
    private static final long serialVersionUID = 1L;
    public String str_productid,str_productname;
    public int it_maxnum,it_num,num=1;
    public ArrayList<ProductDetail> productDetails;


    public Product(){};

}
