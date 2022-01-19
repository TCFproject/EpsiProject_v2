package fr.epsi.epsiproject_v2

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

open class BaseActivity : AppCompatActivity() {
    fun readSharedPreferences(key : String) : String{
        val sharedPreferences= getSharedPreferences("epsi", Context.MODE_PRIVATE)
        val txt=sharedPreferences.getString(key,"Not found")
        return txt.toString()
    }

    fun showButtonLogo(){
        val imgLogo: ImageView = findViewById(R.id.logoImg)
        imgLogo.visibility = View.VISIBLE
        imgLogo.setOnClickListener(View.OnClickListener {
            CompteActivity.startThisActivity(application, readSharedPreferences("info"))
        })
    }

    fun showButtonBack(){
        val imgLogo: ImageView = findViewById(R.id.backArrow)
        imgLogo.visibility = View.VISIBLE
        imgLogo.setOnClickListener(View.OnClickListener {
            finish()
        })
    }

    fun setTextHeader(text:String){
        val title:TextView = findViewById(R.id.header_titre)
        title.setText(text)
    }
}