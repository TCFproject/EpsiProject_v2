package fr.epsi.epsiproject_v2

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import org.json.JSONObject

class FormulaireActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_formulaire)
        val nom:EditText = findViewById(R.id.nom)
        val prenom:EditText = findViewById(R.id.prenom)
        val email:EditText = findViewById(R.id.email)
        val adresse:EditText = findViewById(R.id.adresse)
        val codePostal:EditText = findViewById(R.id.postal)
        val ville:EditText = findViewById(R.id.ville)
        val codeFidelite:EditText = findViewById(R.id.fidelite)

        nom.setText(listText().get("nom"))
        prenom.setText(listText().get("prenom"))
        email.setText(listText().get("mail"))
        adresse.setText(listText().get("addresse"))
        codePostal.setText(listText().get("code postal"))
        ville.setText(listText().get("ville"))
        codeFidelite.setText(listText().get("abonnement"))
        val versMain:Button = findViewById(R.id.creer)
        versMain.setOnClickListener(View.OnClickListener {
            if (nom.text.isNotBlank() && prenom.text.isNotBlank() &&
                    email.text.isNotBlank() && adresse.text.isNotBlank() &&
                    codePostal.text.isNotBlank() && ville.text.isNotBlank() &&
                    codeFidelite.text.isNotBlank()){
                        val jSonParse = JSONObject()
                        jSonParse.put("firstName",nom.text.toString())
                        jSonParse.put("lastName",prenom.text.toString())
                        jSonParse.put("email",email.text.toString())
                        jSonParse.put("address",adresse.text.toString())
                        jSonParse.put("zipcode",codePostal.text.toString())
                        jSonParse.put("city",ville.text.toString())
                        jSonParse.put("cardRef",codeFidelite.text.toString())

                        writeSharedPreferences("info",jSonParse.toString())
                        MainActivity.startThisActivity(application)
            }
        })
    }

    private fun listText(): HashMap<String,String>{
        val getIntent = intent.getStringExtra("jsonConn")
        val jsonIntent = JSONObject(getIntent)
        val info = HashMap<String,String>()

        if (jsonIntent.has("firstName") && jsonIntent.has("lastName")
            && jsonIntent.has("email") && jsonIntent.has("address")
            && jsonIntent.has("zipcode") && jsonIntent.has("city")
            && jsonIntent.has("cardRef")){

            info.put("nom",jsonIntent.getString("firstName"))
            info.put("prenom",jsonIntent.getString("lastName"))
            info.put("mail",jsonIntent.getString("email"))
            info.put("addresse",jsonIntent.getString("address"))
            info.put("code postal",jsonIntent.getString("zipcode"))
            info.put("ville",jsonIntent.getString("city"))
            info.put("abonnement",jsonIntent.getString("cardRef"))
        }else{
            Toast.makeText(this,"Json not compliant", Toast.LENGTH_SHORT).show()
            ScanActivity.startThisActivity(application)
        }
        return info
    }

    private fun writeSharedPreferences(key : String , value : String){
        val sharedPreferences= getSharedPreferences("epsi",Context.MODE_PRIVATE)
        val edit=sharedPreferences.edit()
        edit.putString(key,value)
        edit.apply()
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