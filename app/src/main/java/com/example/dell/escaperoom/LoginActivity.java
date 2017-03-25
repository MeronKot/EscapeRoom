package com.example.dell.escaperoom;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;




public class LoginActivity extends AppCompatActivity {


    private FirebaseAuth mAuth;

    private String userName;
    private String uid;
    private static final int RC_SIGN_IN = 0;
    public static final String ID = "id";
    public static final String NAME = "name";

    public static final String TAG = LoginActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_login);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        mAuth = FirebaseAuth.getInstance();

         if (mAuth.getCurrentUser() != null) {
             // already signed in
             uid = mAuth.getCurrentUser().getUid();
             userName = mAuth.getCurrentUser().getDisplayName();
             Log.d("Auth", "id: " + uid + " name: " + userName);
             Intent intent = new Intent(LoginActivity.this, MainActivity.class);
             intent.putExtra(ID , uid);
             intent.putExtra(NAME , userName);
             startActivity(intent);
             finish();
         } else {
            // not signed in
             startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder()
                     .setProviders(AuthUI.FACEBOOK_PROVIDER,
                             AuthUI.GOOGLE_PROVIDER).build(), RC_SIGN_IN);
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RC_SIGN_IN){
            if(resultCode == RESULT_OK){
                //mAuth.getCurrentUser().getEmail();
                uid = mAuth.getCurrentUser().getUid();
                userName = mAuth.getCurrentUser().getDisplayName();
                Log.d("Auth", "id: " + uid +" name: "+ userName);
                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
            else{

            }
        }
    }
}

