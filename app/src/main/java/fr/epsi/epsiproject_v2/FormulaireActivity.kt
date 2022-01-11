package fr.epsi.epsiproject_v2

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import org.json.JSONObject

class FormulaireActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_formulaire)

        val versMain:Button = findViewById(R.id.creer)
        versMain.setOnClickListener(View.OnClickListener {
            MainActivity.startThisActivity(application)
        })
    }

    companion object{
        fun startThisActivity(con:Context){
            val i = Intent(con,FormulaireActivity::class.java)
            con.startActivity(i)
        }
        fun startThisActivity(con:Context, jSon:String){
            val i = Intent(con,FormulaireActivity::class.java)
            i.putExtra("jsonConn",jSon)
            con.startActivity(i)
        }
    }
}