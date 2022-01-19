package fr.epsi.epsiproject_v2

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import fr.epsi.epsiproject_v2.fragment.BarreFragment
import fr.epsi.epsiproject_v2.fragment.MapsFragment
import fr.epsi.epsiproject_v2.fragment.OffresFragment

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setTextHeader("Logo")
        showButtonLogo()

        val bar:TextView = findViewById(R.id.textViewTab1)
        val offre:TextView = findViewById(R.id.textViewTab2)
        val magasin:TextView = findViewById(R.id.textViewTab3)

        showCodeBar()
        bar.setOnClickListener(View.OnClickListener {
            showCodeBar()
        })
        offre.setOnClickListener(View.OnClickListener {
            showOffre()
        })
        magasin.setOnClickListener(View.OnClickListener {
            showMap()
        })
    }
    private fun showOffre() {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.contentLayout, OffresFragment::class.java, null)
        transaction.setReorderingAllowed(true)
        transaction.addToBackStack("fF") // name can be null
        transaction.commit()
    }
    private fun showMap(){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.contentLayout, MapsFragment::class.java, null)
        transaction.setReorderingAllowed(true)
        transaction.addToBackStack("mF") // name can be null
        transaction.commit()
    }
    private fun showCodeBar(){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.contentLayout, BarreFragment::class.java, null)
        transaction.setReorderingAllowed(true)
        transaction.addToBackStack("cF") // name can be null
        transaction.commit()
    }

    companion object{
        fun startThisActivity(con:Context){
            val intent = Intent(con, MainActivity::class.java)
            con.startActivity(intent)
        }
    }
    override fun onBackPressed() {
        if(supportFragmentManager.backStackEntryCount>1)
            super.onBackPressed()
        else
            finish()
    }
}