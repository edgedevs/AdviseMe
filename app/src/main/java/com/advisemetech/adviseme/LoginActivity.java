package com.advisemetech.adviseme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.util.Patterns;
import java.util.regex.Pattern;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.google.android.gms.auth.api.signin.GoogleSignIn;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import es.dmoral.toasty.Toasty;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {


    /*private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    //"(?=.*[0-9])" +         //at least 1 digit
                    //"(?=.*[a-z])" +         //at least 1 lower case letter
                    //"(?=.*[A-Z])" +         //at least 1 upper case letter
                    "(?=.*[a-zA-Z])" +      //any letter
                    "(?=.*[@#$%^&+=])" +    //at least 1 special character
                    "(?=\\S+$)" +           //no white spaces
                    ".{4,}" +               //at least 4 characters
                    "$");*/


    String regexPassword = "(?=.*[a-z])(?=.*[A-Z])(?=.*[\\d])(?=.*[~`!@#\\$%\\^&\\*\\(\\)\\-_\\+=\\{\\}\\[\\]\\|\\;:\"<>,./\\?]).{8,}";


    EditText emailEt, passEt;
    Button  login_btn, login_button, sign_up_button;
    CheckBox checkBox;
    ProgressBar progressBar;
    FirebaseAuth mAuth;
    SignInButton signInButton2;
    TextView fgt_tv, register_btn;

    private GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth firebaseAuthGoogleButton;
    AwesomeValidation awesomeValidation;
    private final static int RC_SIGN_IN = 123;
    Button verify;
    FirebaseUser user;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

         firebaseAuthGoogleButton = FirebaseAuth.getInstance();
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);




        emailEt = findViewById(R.id.login_email_et200);
        passEt = findViewById(R.id.login_password_et200);
        register_btn = findViewById(R.id.sign_up_link);
        fgt_tv= findViewById(R.id.forget_vp);
        login_btn = findViewById(R.id.button_login200);
        checkBox = findViewById(R.id.login_checkbox200);
        progressBar = findViewById(R.id.progressbar_login200);
        mAuth = FirebaseAuth.getInstance();
        signInButton2 = (SignInButton)findViewById(R.id.google_login_btn200);
        // login_btn.setOnClickListener(this);
        // register_btn.setOnClickListener(this);
        signInButton2.setOnClickListener(this);
        fgt_tv.setOnClickListener(this);


        //awesomeValidation.addValidation(this, R.id.editTextName, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.nameerror);


       // awesomeValidation.addValidation(LoginActivity.this, R.id.login_password_et200, regexPassword, R.string.err_password);
// to validate a confirmation field (don't validate any rule other than confirmation on confirmation field)
       // mAwesomeValidation.addValidation(activity, R.id.edt_password_confirmation, R.id.edt_password, R.string.err_password_confirmation);
        awesomeValidation.addValidation(LoginActivity.this, R.id.login_email_et200, Patterns.EMAIL_ADDRESS, R.string.emailerror);



        creatRequest();
        findViewById(R.id.google_login_btn200).setOnClickListener(v12 -> signIn());

        fgt_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ResetPasswordActivity.class);
                startActivity(intent);
            }
        });

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if (b){
                    passEt.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    //    confirm_pass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }else {

                    passEt.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    //  confirm_pass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });


        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                String email = emailEt.getText().toString();

                awesomeValidation.validate();


                if (!TextUtils.isEmpty(email)){
                    String pass = passEt.getText().toString();

                    if(!TextUtils.isEmpty(pass)){
                         awesomeValidation.validate();


                        progressBar.setVisibility(View.VISIBLE);
                        mAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if (task.isSuccessful()){
                                    sendtoMain();
                                }
                            }
                        });
                        /*if(awesomeValidation.validate()){
                            progressBar.setVisibility(View.VISIBLE);
                            mAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    if (task.isSuccessful()){
                                        sendtoMain();
                                    }
                                }
                            });

                        }*/
                    }
                }else {

                   // Toasty.error(LoginActivity.this, "This is an error toast.", Toast.LENGTH_SHORT, true).show();
                    }






            }
        });


    }

  /*  private boolean validateEmail() {
        String emailInput = emailEt.getText().toString().trim();
        if (emailInput.isEmpty()) {
            emailEt.setError("Field can't be empty");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
            emailEt.setError("Please enter a valid email address");
            return false;
        } else {
            emailEt.setError(null);
            return true;
        }
    }*/




    @Override
    protected void onStart() {
        super.onStart();
         FirebaseUser user= firebaseAuthGoogleButton.getCurrentUser();
       //FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user!= null){
            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void sendtoMain() {
        Intent intent = new Intent(LoginActivity.this,Splashscreen.class);
        startActivity(intent);
        finish();
    }



    private void creatRequest() {

        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);


                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately

                // ...
            }
        }
    }


    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        firebaseAuthGoogleButton.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information


                        //user = FirebaseAuth.getInstance().getCurrentUser();
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                    } else {
                        // If sign in fails, display a message to the user.

                        Context context;
                        CharSequence text;


                    }

                    // ...
                });
    }

    @Override
    public void onClick(View v) {


    }




}




