package com.jobsearchrt.jobsearchapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

public class FullContactActivity extends CustomMenuActivity {
    String url = "https://api.fullcontact.com/v2/company/lookup.json?domain=";
    String api_key = "&apiKey=ba5d7e2d4f109058";
    TextView companyName,companyAddress,contactInfo,companyInfo;
    ImageView companyLogo;
    CompanyInfoDownloader companyInfoDownloader;
    EditText domainName;
    public class CompanyInfoObject {
        String Name, Address, contact, company;
        Bitmap Logo;
    }
    CompanyInfoObject companyInfoObject;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_contact);
        companyName = (TextView) findViewById(R.id.companyName);
        companyAddress = (TextView)findViewById(R.id.companyPlace);
        contactInfo = (TextView) findViewById(R.id.contactInfo);
        companyInfo = (TextView) findViewById(R.id.companyInfo);
        companyLogo = (ImageView)findViewById(R.id.companylogo);
        domainName= (EditText) findViewById(R.id.domainName);
    }
    public void submitDomain(View view) {
        companyInfoObject=new CompanyInfoObject();
        companyInfoDownloader=new CompanyInfoDownloader();
        companyInfoDownloader.execute(domainName.getText().toString());
    }
    public class CompanyInfoDownloader extends AsyncTask<String, Void, CompanyInfoObject> {


        protected CompanyInfoObject doInBackground(String... urls) {
            String urldisplay = urls[0];
            String finalURL = url + urldisplay + api_key;
            System.out.println(finalURL);

            try {
                URL myURL = new URL(finalURL);
                BufferedReader br=new BufferedReader(new InputStreamReader(myURL.openConnection().getInputStream(),"UTF-8"));
                StringBuilder stringBuilder=new StringBuilder();
                String companyline;
                while ((companyline=br.readLine())!=null){
                    stringBuilder.append(companyline);
                }
                JSONObject queryObject=new JSONObject(stringBuilder.toString());
                if(queryObject.getString("logo")!=null) {
                    String imageURL = queryObject.getString("logo");
                    InputStream in = new URL(imageURL).openStream();
                    companyInfoObject.Logo = BitmapFactory.decodeStream(in);
                }
                JSONObject organizationObject=queryObject.getJSONObject("organization");
                if(organizationObject.getString("name")!=null) {
                    companyInfoObject.Name = organizationObject.getString("name");
                }
                JSONObject contactInfo=organizationObject.getJSONObject("contactInfo");
                JSONArray address=contactInfo.getJSONArray("addresses");
                String contacts="";
                for(int i=0;i<address.length();i++){
                    JSONObject contactObject=address.getJSONObject(i);
                    if(contactObject.getString("locality")==null){
                        continue;
                    }else{
                        contacts+=contactObject.getString("locality");
                        JSONObject regionObject=contactObject.getJSONObject("region");
                        contacts+="\n"+regionObject.getString("name");
                        JSONObject countryObject=contactObject.getJSONObject("country");
                        contacts+="\n"+countryObject.getString("name");
                        i=address.length();
                    }

                }
                if(contacts!=""){
                    companyInfoObject.Address=contacts;
                }

                JSONArray socialArray=queryObject.getJSONArray("socialProfiles");
                for(int j=0;j<socialArray.length();j++){
                    JSONObject socialProfile=socialArray.getJSONObject(j);
                    System.out.println("h4"+socialProfile.getString("typeId"));
                        if((socialProfile.getString("typeId").equals("linkedincompany"))){ //|| (socialArray.getJSONObject(j).getString("typeName").equals("CrunchBase"))){
//                        System.out.println("h4");
//                        System.out.println(socialProfile.getString("bio"));
                            companyInfoObject.company=socialProfile.getString("bio");;
                            j=socialArray.length();
                        }
                }
                if(contactInfo.getJSONArray("phoneNumbers")!=null) {
                    JSONArray phoneNumbers = contactInfo.getJSONArray("phoneNumbers");
                    companyInfoObject.contact=phoneNumbers.getJSONObject(0).getString("number");
                }


            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }

            return companyInfoObject;
        }

        protected void onPostExecute(CompanyInfoObject object) {
            companyLogo.setImageBitmap(object.Logo);
            companyName.setText(object.Name);
            companyAddress.setText(object.Address);
            contactInfo.setText(object.contact);
            companyInfo.setText(object.company);
        }
    }
}



