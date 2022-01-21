package fr.epsi.epsiproject_v2.fragment

import android.Manifest
import android.annotation.SuppressLint
import android.os.Build
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import com.google.android.gms.maps.*

import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import fr.epsi.epsiproject_v2.DetailMagasinActivity
import fr.epsi.epsiproject_v2.R
import fr.epsi.epsiproject_v2.obj.Marche
import fr.epsi.epsiproject_v2.obj.Produit
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

class MapsFragment : Fragment() {
    lateinit var googleMap :GoogleMap
    @RequiresApi(Build.VERSION_CODES.N)
    @SuppressLint("MissingPermission", "UseRequireInsteadOfGet")
    val locationPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        when {
            permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                // Precise location access granted.
                googleMap.isMyLocationEnabled=true
            }
            permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                googleMap.isMyLocationEnabled=true
                // Only approximate location access granted.
            } else -> {
            // No location access granted.
            }
        }
    }
    private val callback = OnMapReadyCallback { googleMap ->
        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        val tableLoc = ArrayList<Marche>()

        val okHttpClient = OkHttpClient.Builder().build()
        val request = Request.Builder()
            .url("https://djemam.com/epsi/stores.json")
            .get()
            .cacheControl(CacheControl.FORCE_NETWORK)
            .build()
        okHttpClient.newCall(request).enqueue(object: Callback {
            override fun onResponse(call: Call, response: Response) {
                val respons = response.body?.string()
                if (respons != null){
                    val jsobj = JSONObject(respons)
                    val jsArray =jsobj.getJSONArray("stores")
                    for (i in 0 until jsArray.length()){
                        val json = jsArray.getJSONObject(i)
                        val long = json.optDouble("longitude",0.0)
                        val lat = json.optDouble("latitude",0.0)
                        val title = json.optString("name", "Echec")
                        val img = json.optString("pictureStore","Echec")
                        val addr = json.optString("address","Echec")
                        val zip = json.optString("zipcode","Echec")
                        val desc = json.optString("description","Echec")
                        val ville = json.optString("city", "Echec")
                        tableLoc.add(Marche(title, desc,img,addr,zip,ville,long,lat))
                    }
                    activity?.runOnUiThread(Runnable {
                        for (marche in tableLoc){
                            googleMap.addMarker(MarkerOptions().position(LatLng(marche.getLat(),marche.getLong()))
                                .title(marche.getNom()))

                        }

                        googleMap.setOnMarkerClickListener(object :GoogleMap.OnMarkerClickListener{
                            override fun onMarkerClick(p0: Marker): Boolean {
                                val posi:Int = Integer.parseInt(p0.id.split("m")[1])
                                DetailMagasinActivity.startThisActivity(activity!!,tableLoc.get(posi).getNom()
                                ,tableLoc.get(posi).getPic(),tableLoc.get(posi).getAddr()
                                ,tableLoc.get(posi).getZip(),tableLoc.get(posi).getVille()
                                ,tableLoc.get(posi).getDesc())
                                return false
                            }
                        })
                    })
                }
            }
            override fun onFailure(call: Call, e: IOException) {
                TODO("Not yet implemented")
            }
        })
        locationPermissionRequest.launch(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION))
        this.googleMap = googleMap
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }

}