package com.serving.servingpk;

import android.content.Intent;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {

    private static int splash_Time_out=3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
   //     getHashkey();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i=new Intent(SplashActivity.this,Dashboard.class);
                startActivity(i);
                finish();
            }
        },splash_Time_out);
    }
//    public void getHashkey(){
//        try {
//            PackageInfo info = getPackageManager().getPackageInfo(getApplicationContext().getPackageName(), PackageManager.GET_SIGNATURES);
//            for (Signature signature : info.signatures) {
//                MessageDigest md = MessageDigest.getInstance("SHA");
//                md.update(signature.toByteArray());
//
//                Log.i("Base64", Base64.encodeToString(md.digest(),Base64.NO_WRAP));
//            }
//        } catch (PackageManager.NameNotFoundException e) {
//            Log.d("Name not found", e.getMessage(), e);
//
//        } catch (NoSuchAlgorithmException e) {
//            Log.d("Error", e.getMessage(), e);
//        }
//    }
}
