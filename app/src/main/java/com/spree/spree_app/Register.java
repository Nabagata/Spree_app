package com.spree.spree_app;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;

import retrofit.Call;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;


public class Register extends ActionBarActivity {
    private static SpreeApiEndpointInterface apiService;
    TextView login;
    android.support.v7.widget.AppCompatButton register_button;
    EditText username,email,password,phone,college,sex,city,state;
    class SignupAsync extends AsyncTask<String,String,String>{

        String inputName;
        String inputEmail;
        String inputPhone;
        String inputCollege;
        String inputGender;
        String inputCollegeId;
        String inputCity;
        String inputState;
        String inputPassword;
        Context context;
        SignupAsync(Context context,String Name,String Email,String Phone, String College, String Gender, String CollegeId, String City, String State, String Password)
        {
            this.context=context;
            this.inputName=Name;
            this.inputEmail=Email;
            this.inputPhone=Phone;
            this.inputCollege=College;
            this.inputGender=Gender;
            this.inputCollegeId=CollegeId;
            this.inputCity=City;
            this.inputState=State;
            this.inputPassword=Password;


        }
        @Override
        protected String doInBackground(String... arg) {
            ArrayList<String> arr=new ArrayList<>();
            arr.add("123");
            arr.add("345");
            Call<String> call=Register.apiService.signUpMobile(new SignupRetrofitClass(arr,this.inputName,this.inputEmail,this.inputPhone,this.inputCollege,this.inputGender,this.inputCollegeId,
                    this.inputCity,this.inputState,this.inputPassword));
            String response="";
            try{
                response=call.execute().body();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            Log.d("retrofitsignup", response);
            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            Log.d("retrofitsignup", s);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        login= (TextView) findViewById(R.id.link_login);
        username= (EditText) findViewById(R.id.input_username);
        email= (EditText) findViewById(R.id.input_email);
        password= (EditText) findViewById(R.id.input_password);
        phone= (EditText) findViewById(R.id.input_phone);
        college= (EditText) findViewById(R.id.input_college);
        sex= (EditText) findViewById(R.id.input_sex);
        city= (EditText) findViewById(R.id.input_city);
        state= (EditText) findViewById(R.id.input_state);
        Retrofit retrofi=new Retrofit.Builder().baseUrl(Constants.base_url_reg).addConverterFactory(GsonConverterFactory.create()).build();
        Register.apiService=retrofi.create(SpreeApiEndpointInterface.class);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Register.this,Login.class));
            }
        });
        register_button= (AppCompatButton) findViewById(R.id.btn_signup);
        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"was clicked",Toast.LENGTH_LONG).show();
//                new SignupAsync(getApplicationContext(),username.getText().toString(),email.getText().toString(),phone.getText().toString(),
//                        college.getText().toString(),sex.getText().toString(),"1234",city.getText().toString(),state.getText().toString(),
//                        password.getText().toString()).execute();
//                //startActivity(new Intent(Register.this,SpreeMain.class));
                new SignupAsync(getApplicationContext(),"dfdsf","dfd@gg.com","7777777777","uushuf","male","4556","waramgal","tel","1234").execute();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_register, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
