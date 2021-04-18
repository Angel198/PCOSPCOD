package com.jaylax.pcospcod;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.jaylax.pcospcod.doctoractivities.DoctorDashboardActivity;
import com.jaylax.pcospcod.patientactivities.PatientDashboardActivity;

import java.util.concurrent.TimeUnit;


public class OTPActivity extends AppCompatActivity {

    ImageView login_done;
    EditText code, code1, code2, code3;
    TextView resend_otp;
    String _number;
    String _code , _code1, _code2, _code3, _code4;
    EditText one;
    String _otp;
    private String verificationId;
    private FirebaseAuth mAuth;
    Context context;
    public static String rslt = "";
    PhoneAuthProvider.ForceResendingToken mResendToken;
    String num;
    TextView time;
    int  duration = 60000; //2 minute
    CountDownTimer countDownTimer;
    String user_id, user_name;
    FloatingActionButton login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_o_t_p);

        login = (FloatingActionButton) findViewById(R.id.login);
        code = (EditText) findViewById(R.id.code);
        resend_otp = (TextView) findViewById(R.id.resend_OTP);

        _number = getIntent().getStringExtra("number");
        user_id = getIntent().getStringExtra("human");
        user_name = getIntent().getStringExtra("name");

        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();

        sendVerificationCode(_number);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                _code = code.getText().toString().trim();

                if (_code.isEmpty() || _code.length() <4 )
                {
                    code.setError("Enter Code");
                    code.requestFocus();
                    return;
                }

//                Intent intent = new Intent(OTPActivity.this, PatientDashboardActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                intent.putExtra("num",_number);
//                intent.putExtra("name",user_name);
//                startActivity(intent);

                verifyCode(_code);

            }

        });


        resend_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendVerificationCodeAgain(_number);

            }
        });

    }


    private void sendVerificationCodeAgain(String number)
    {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                number,
                60,
                TimeUnit.SECONDS,
                this,
                mCallBacks
        );

    }


    private void verifyCode(String code)
    {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId,code);
        signInWithCredential(credential);
    }


    private void signInWithCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful())
                        {
                            if (user_id.equals("1"))
                            {
                                Intent intent = new Intent(OTPActivity.this, PatientDashboardActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                intent.putExtra("num",_number);
                                intent.putExtra("name",user_name);
                                startActivity(intent);
                            }
                            else if (user_id.equals("2"))
                            {
                                Intent intent = new Intent(OTPActivity.this, DoctorDashboardActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                intent.putExtra("num",_number);
                                intent.putExtra("name",user_name);
                                startActivity(intent);
                            }

                        }else{
                            Toast.makeText(OTPActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });


    }

    private void sendVerificationCode(String number) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                number, // first parameter is user's mobile number
                60, // second parameter is time limit for OTP
                TimeUnit.SECONDS, // third parameter is for initializing units
                this, // this task will be excuted on Main thread.
                mCallBacks // we are calling callback method when we recieve OTP for
        );

    }


    private PhoneAuthProvider.OnVerificationStateChangedCallbacks
            mCallBacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onCodeSent(String s,PhoneAuthProvider.ForceResendingToken forceResendingToken)
        {
            super.onCodeSent(s,forceResendingToken);
            verificationId = s;
            mResendToken = forceResendingToken;

        }

        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            _otp = phoneAuthCredential.getSmsCode();
            if (_otp!=null)
            {
                code.setText(_otp);
                verifyCode(_otp);
//                countDownTimer.cancel();
            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {

            Toast.makeText(OTPActivity.this,e.getMessage(), Toast.LENGTH_SHORT).show();

        }
    };


}