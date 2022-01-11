package fr.epsi.epsiproject_v2

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton

class PasDeCompteActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pas_de_compte)

        val btnScan:ImageButton = findViewById(R.id.qrScan)
        val btnForm:Button = findViewById(R.id.formulaire)

        btnForm.setOnClickListener(View.OnClickListener {
            FormulaireActivity.startThisActivity(application)
        })
    }

    companion object{
        fun startThisActivity(con:Context){
            val i = Intent(con,PasDeCompteActivity::class.java)
            con.startActivity(i)
        }
    }
}