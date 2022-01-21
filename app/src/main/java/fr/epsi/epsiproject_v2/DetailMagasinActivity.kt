package fr.epsi.epsiproject_v2

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso

class DetailMagasinActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_magasin)
        val addr:TextView = findViewById(R.id.textAddress)
        val zipVille:TextView = findViewById(R.id.text_zip_ville)
        val descMaga:TextView = findViewById(R.id.descMagasin)
        val imgMaga:ImageView = findViewById(R.id.imageMagasin)

        intent.getStringExtra("nom")?.let { setTextHeader(it) }
        showButtonBack()

        Picasso.get().load(intent.getStringExtra("img")).into(imgMaga)
        addr.setText(intent.getStringExtra("address"))
        descMaga.setText(intent.getStringExtra("description"))
        zipVille.setText(intent.getStringExtra("codePostal")+" - "+intent.getStringExtra("ville"))
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