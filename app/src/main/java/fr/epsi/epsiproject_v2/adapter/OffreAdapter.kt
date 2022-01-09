package fr.epsi.epsiproject_v2.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import fr.epsi.epsiproject_v2.R
import fr.epsi.epsiproject_v2.obj.Produit

class OffreAdapter(private val objProduit:ArrayList<Produit>)
    : RecyclerView.Adapter<OffreAdapter.ViewHolder>() {
    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val img: ImageView = v.findViewById(R.id.imgProduit)
        val nomProduit = v.findViewById<TextView>(R.id.nomProduit)
        val descProduit = v.findViewById<TextView>(R.id.descProduit)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_offers, parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.descProduit.text = objProduit.get(position).desc
        holder.nomProduit.text = objProduit.get(position).nom
        Picasso.get().load(objProduit.get(position).url).into(holder.img)
    }

    override fun getItemCount(): Int = this.objProduit.size
}