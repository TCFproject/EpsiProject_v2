package fr.epsi.epsiproject_v2

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class DetailMagasinActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_magasin)
    }

    companion object{
        fun startThisActivity(con: Context, nom:String, img:String, address:String, codePostal:String, ville:String, desc:String){
            val i = Intent(con,DetailMagasinActivity::class.java)
            i.putExtra("nom",nom)
            i.putExtra("img",img)
            i.putExtra("address",address)
            i.putExtra("codePostal",codePostal)
            i.putExtra("ville",ville)
            i.putExtra("description",desc)
            con.startActivity(i)
        }
    }
}