
package com.firesoft.member.Protocol;

import com.external.activeandroid.Model;
import com.external.activeandroid.annotation.Column;
import com.external.activeandroid.annotation.Table;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@Table(name = "PROPERTY")
public class PROPERTY extends Model
{

     @Column(name = "name")
     public String name;

     @Column(name = "value")
     public String value;

 public void  fromJson(JSONObject jsonObject)  throws JSONException
 {
     if(null == jsonObject){
       return ;
      }


     JSONArray subItemArray;

     this.name = jsonObject.optString("name");

     this.value = jsonObject.optString("value");
     return ;
 }

 public JSONObject  toJson() throws JSONException 
 {
     JSONObject localItemObject = new JSONObject();
     JSONArray itemJSONArray = new JSONArray();
     localItemObject.put("name", name);
     localItemObject.put("value", value);
     return localItemObject;
 }

}
