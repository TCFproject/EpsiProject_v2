package fr.epsi.epsiproject_v2

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import org.json.JSONObject

class FormulaireActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_formulaire)

        val getIntent = intent.getStringExtra("jsonConn")

        val nom:EditText = findViewById(R.id.nom)
        val prenom:EditText = findViewById(R.id.prenom)
        val email:EditText = findViewById(R.id.email)
        val adresse:EditText = findViewById(R.id.adresse)
        val codePostal:EditText = findViewById(R.id.postal)
        val ville:EditText = findViewById(R.id.email)
        val codeFidelite:EditText = findViewById(R.id.fidelite)

        val versMain:Button = findViewById(R.id.creer)
        versMain.setOnClickListener(View.OnClickListener {
            if (nom.text.isNotBlank() && prenom.text.isNotBlank() &&
                    email.text.isNotBlank() && adresse.text.isNotBlank() &&
                    codePostal.text.isNotBlank() && ville.text.isNotBlank() &&
                    codeFidelite.text.isNotBlank()){

                writeSharedPreferences("firstName",nom.text.toString())
                writeSharedPreferences("lastName",prenom.text.toString())
                writeSharedPreferences("email",email.text.toString())
                writeSharedPreferences("address",adresse.text.toString())
                writeSharedPreferences("zipcode",codePostal.text.toString())
                writeSharedPreferences("city",ville.text.toString())
                writeSharedPreferences("cardRef",codeFidelite.text.toString())

                MainActivity.startThisActivity(application)
            }
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
    fun writeSharedPreferences(key : String , value : String){
        val sharedPreferences= getSharedPreferences("epsi",Context.MODE_PRIVATE)
        val edit=sharedPreferences.edit()
        edit.putString(key,value)
        edit.apply()
    }
}