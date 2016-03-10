package com.spree.spree_app;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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

import java.util.ArrayList;

import retrofit.Call;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;


public class Register extends ActionBarActivity {
    private static SpreeApiEndpointInterface apiService;
    TextView login;
    android.support.v7.widget.AppCompatButton register_button;
    EditText username, email, password, phone, college, sex, city, state, collegeId;
    private ProgressDialog mProgressDialog;

    private void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage("Signing you up.");
            mProgressDialog.setIndeterminate(true);
        }
        mProgressDialog.show();
    }

    private void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing())
            mProgressDialog.hide();
    }

    class SignupAsync extends AsyncTask<String, String, String> {

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

        SignupAsync(Context context, String Name, String Email, String Phone, String College, String Gender, String CollegeId, String City, String State, String Password) {
            this.context = context;
            this.inputName = Name;
            this.inputEmail = Email;
            this.inputPhone = Phone;
            this.inputCollege = College;
            this.inputGender = Gender;
            this.inputCollegeId = CollegeId;
            this.inputCity = City;
            this.inputState = State;
            this.inputPassword = Password;


        }

        @Override
        protected String doInBackground(String... arg) {
            ArrayList<String> arr = new ArrayList<>();
            arr.add("123");
            arr.add("345");
            Call<String> call = Register.apiService.signUpMobile(new SignupRetrofitClass(arr, this.inputName, this.inputEmail, this.inputPhone, this.inputCollege, this.inputGender, this.inputCollegeId,
                    this.inputCity, this.inputState, this.inputPassword));
            String response = "";
            try {
                response = call.execute().body();
            } catch (Exception e) {
                e.printStackTrace();
            }
            Log.d("retrofitsignup", response);
            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            Log.d("retrofitsignup", s);
            if (!s.equals("-1")) {
                hideProgressDialog();
                SharedPreferences.Editor editor = getSharedPreferences("spree_login", MODE_PRIVATE).edit();
                editor.putString("username", this.inputName);
                editor.putString("userid", s);
                editor.commit();

                Intent I = new Intent(getApplicationContext(), Event_9.class);
                I.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(I);

            } else {
                hideProgressDialog();
                TextView error = (TextView) findViewById(R.id.error);
                error.setText("Error signing up. Please check your details");
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        SharedPreferences prefs = getSharedPreferences("spree_login", MODE_PRIVATE);
        String restoredText = prefs.getString("userid", null);
        if (restoredText != null) {
            Intent I = new Intent(getApplicationContext(), SpreeMain.class);
            startActivity(I);
        }
        login = (TextView) findViewById(R.id.link_login);
        username = (EditText) findViewById(R.id.input_name);
        email = (EditText) findViewById(R.id.input_email);
        password = (EditText) findViewById(R.id.input_register_password);
        phone = (EditText) findViewById(R.id.input_phone);
        college = (EditText) findViewById(R.id.input_college);
        collegeId = (EditText) findViewById(R.id.input_college_id);
        sex = (EditText) findViewById(R.id.input_sex);
        city = (EditText) findViewById(R.id.input_city);
        state = (EditText) findViewById(R.id.input_state);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.base_url_reg)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Register.apiService = retrofit.create(SpreeApiEndpointInterface.class);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Register.this, Login.class));
            }
        });
        register_button = (AppCompatButton) findViewById(R.id.btn_signup);
        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isNetworkAvailable()) {
                    showProgressDialog();
                    new SignupAsync(getApplicationContext(), username.getText().toString(), email.getText().toString(), phone.getText().toString(),
                            college.getText().toString(), sex.getText().toString(), collegeId.getText().toString(), city.getText().toString(), state.getText().toString(),
                            password.getText().toString()).execute();
                }
                else
                    Toast.makeText(getApplicationContext(), "Please check your internet connection", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_register, menu);
        return true;
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
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
