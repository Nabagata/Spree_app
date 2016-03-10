package com.spree.spree_app;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
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


public class Login extends ActionBarActivity {
    static int login = 0;
    private ProgressDialog mProgressDialog;

    private void showProgressDialog()
    {
        if(mProgressDialog==null)
        {
            mProgressDialog=new ProgressDialog(this);
            mProgressDialog.setMessage("Loggin you in.");
            mProgressDialog.setIndeterminate(true);
        }
        mProgressDialog.show();
    }

    private void hideProgressDialog()
    {
        if(mProgressDialog!=null && mProgressDialog.isShowing())
            mProgressDialog.hide();
    }
    class LoginAsync extends AsyncTask<String, Void, String> {
        Context context;
        String username = "", password = "";

        public LoginAsync(Context context) {
            this.context = context;
        }

        @Override
        protected String doInBackground(String... arg) {
            try {
                username = (String) arg[0];
                password = (String) arg[1];

                String link = "http://register.springspree.in/accounts/login_mobile";
                String data = URLEncoder.encode("inputEmail", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8");
                data += "&" + URLEncoder.encode("inputPassword", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");

                URL url = new URL(link);
                URLConnection conn = url.openConnection();

                conn.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

                wr.write(data);
                wr.flush();

                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                StringBuilder sb = new StringBuilder();
                String line = null;

                // Read Server Response
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                    break;
                }
                return sb.toString();
            } catch (Exception e) {

                return new String("Exception: " + e.getMessage());
            }
        }

        @Override
        protected void onPostExecute(String s) {
            if (!s.equals("0")) {
                hideProgressDialog();
                SharedPreferences.Editor editor = getSharedPreferences("spree_login", MODE_PRIVATE).edit();
                editor.putString("username", username);
                editor.putString("userid", s);
                editor.commit();
                Intent I = new Intent(getApplicationContext(), Event_9.class);
                I.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(I);

            } else {
                hideProgressDialog();
                TextView error = (TextView) findViewById(R.id.error);
                error.setText("Error logging in. Please check your username/password");
            }
        }
    }


    EditText username, password;
    TextView sign_up;
    android.support.v7.widget.AppCompatButton login_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences prefs = getSharedPreferences("spree_login", MODE_PRIVATE);
        String restoredText = prefs.getString("userid", null);
        if (restoredText != null) {
            Intent I = new Intent(getApplicationContext(), Event_9.class);
            startActivity(I);
        }
        setContentView(R.layout.activity_login);
        username = (EditText) findViewById(R.id.input_username);
        password = (EditText) findViewById(R.id.input_password);
        sign_up = (TextView) findViewById(R.id.link_signup);
        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, Register.class));
            }
        });

        login_button = (AppCompatButton) findViewById(R.id.btn_login);
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNetworkAvailable())
                {
                    showProgressDialog();
                    new LoginAsync(getApplicationContext()).execute(username.getText().toString(), password.getText().toString());
                }
                else
                    Toast.makeText(getApplicationContext(), "Please check your internet connection", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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
      /*  if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);*/
        return  false;
    }
}
