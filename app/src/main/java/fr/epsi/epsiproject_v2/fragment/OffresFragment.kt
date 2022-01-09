package fr.epsi.epsiproject_v2.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fr.epsi.epsiproject_v2.R
import fr.epsi.epsiproject_v2.adapter.OffreAdapter
import fr.epsi.epsiproject_v2.obj.Produit
import okhttp3.*
import org.json.JSONObject
import java.io.IOException
import java.util.ArrayList

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [OffresFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class OffresFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_offres, container, false)

        val list:RecyclerView = view.findViewById(R.id.listOffre)
        list.layoutManager = LinearLayoutManager(view.context)

        val listProd = ArrayList<Produit>()
        val adapterProd = OffreAdapter(listProd)
        list.adapter = adapterProd

        val okHttpClient = OkHttpClient.Builder().build()
        val request = Request.Builder()
            .url("https://djemam.com/epsi/offers.json")
            .get()
            .cacheControl(CacheControl.FORCE_NETWORK)
            .build()
        okHttpClient.newCall(request).enqueue(object: Callback {
            override fun onResponse(call: Call, response: Response) {
                val respons = response.body?.string()
                if (respons != null){
                    val jsobj = JSONObject(respons)
                    val jsArray =jsobj.getJSONArray("items")

                    for (i in 0 until jsArray.length()){
                        val json = jsArray.getJSONObject(i)
                        val nom = json.optString("name","Echec")
                        val desc = json.optString("description", "Echec")
                        val url = json.optString("picture_url","echec")

                        listProd.add(Produit(nom, desc, url))
                    }
                    activity?.runOnUiThread(Runnable {
                        adapterProd.notifyDataSetChanged()
                    })
                }
            }
            override fun onFailure(call: Call, e: IOException) {
                TODO("Not yet implemented")
            }
        })
        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment OffresFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            OffresFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}