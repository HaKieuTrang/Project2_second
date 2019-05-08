package com.example.nhandienkhuonmat0805;

import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    EditText edmail, edpass;
    Button btLogin;
    RadioButton radioButtonGV, radioButtonSV;
    RadioGroup radioGroup;
    String URL_LOGIN = "https://hakieutrang55.000webhostapp.com/Dangnhap/login.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        Connect();

        btLogin.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edmail.getText().toString().trim();
                String pass = edpass.getText().toString().trim();

                if (email.isEmpty()){
                    edmail.setError( "Hãy nhập email" );

                } if (pass.isEmpty()) {
                    edpass.setError( "Hãy nhập mật khẩu" );
                }
                else{
                    Login(email, pass);
                }
            }

        } );
    }

    public void Connect(){
        edmail = (EditText) findViewById( R.id.edit_email );
        edpass = (EditText) findViewById( R.id.edit_pass );
        btLogin = (Button) findViewById( R.id.buttonLogin );
        radioButtonGV = (RadioButton) findViewById( R.id.radiobtGV );
        radioButtonSV = (RadioButton) findViewById( R.id.radiobtSV );
        radioGroup = (RadioGroup) findViewById( R.id.radioGroup );
    }

    private void Login(final String email, final String pass) {
        StringRequest stringRequest = new StringRequest( Request.Method.POST, URL_LOGIN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject( response );
                    if (jsonObject.getInt( "success" ) == 1){
                        Toast.makeText( MainActivity.this, "Đăng nhập thành công", Toast.LENGTH_LONG ).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText( MainActivity.this, "Error " + e.getMessage(), Toast.LENGTH_SHORT ).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        } )
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>(  );
                params.put( "mail", email );
                params.put( "password", pass );
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue( this );
        requestQueue.add( stringRequest );
    }
}
