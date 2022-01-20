package fr.epsi.epsiproject_v2

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class CompteActivity : FormulaireActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_compte)

        setTextHeader("Compte")
        showButtonBack()
        val button:Button = findViewById(R.id.creer)
        button.setText("J'enregistre les modifications")

        setAndParseData()
    }

    companion object{
        fun startThisActivity(con: Context, jSon:String){
            val i = Intent(con,CompteActivity::class.java)
            i.putExtra("jsonConn",jSon)
            con.startActivity(i)
        }
    }
}