package fr.epsi.epsiproject_v2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper

class SplashActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        Handler(Looper.getMainLooper()).postDelayed(Runnable {
            if (readSharedPreferences("info").equals("Not found")){
                PasDeCompteActivity.startThisActivity(application)
            }else{
                MainActivity.startThisActivity(application)
            }
        },2000)
    }
}