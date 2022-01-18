package fr.epsi.epsiproject_v2.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.ColorInt
import androidx.core.app.NotificationCompat.getColor
import com.google.zxing.BarcodeFormat
import com.google.zxing.oned.Code128Writer
import com.google.zxing.qrcode.QRCodeWriter
import fr.epsi.epsiproject_v2.R
import org.json.JSONArray
import org.json.JSONObject
import java.lang.StringBuilder

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [BarreFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BarreFragment : Fragment() {
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_barre, container, false)

        return view
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bareImg = view.findViewById<ImageView>(R.id.imageBarre)
        val textValue = view.findViewById<TextView>(R.id.textBitmap)
        val textNomPrenom = view.findViewById<TextView>(R.id.textNomPrenom)

        val sharedPref = readSharedPreferences("info",view.context)
        val jsonTable = JSONObject(sharedPref)

        textNomPrenom.setText(jsonTable.getString("firstName")+" "+jsonTable.getString("lastName"))

        val textBitmap = jsonTable.getString("firstName")+" "+jsonTable.getString("lastName")+
                " "+ jsonTable.getString("email")+" "+jsonTable.getString("address")+
                " "+jsonTable.getString("zipcode")+" "+jsonTable.getString("city")

        //Toast.makeText(view.context,textBitmap, Toast.LENGTH_LONG).show()
        displayBitmap(textBitmap,bareImg,textValue)
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment BarreFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            BarreFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    @SuppressLint("ResourceAsColor")
    private fun displayBitmap(value: String, image_barcode:ImageView, text_barcode_number:TextView) {
        val widthPixels = resources.getDimensionPixelSize(R.dimen.width_barcode)
        val heightPixels = resources.getDimensionPixelSize(R.dimen.height_barcode)

        val bitmapData = createBarcodeBitmap(
            barcodeValue = value,
            barcodeColor = R.color.black,
            backgroundColor = android.R.color.white,
            widthPixels = widthPixels,
            heightPixels = heightPixels
        )
        image_barcode.setImageBitmap(bitmapData)
        text_barcode_number.text = bitmapData.byteCount.toString()
    }

    private fun createBarcodeBitmap(barcodeValue: String, @ColorInt barcodeColor: Int,
        @ColorInt backgroundColor: Int, widthPixels: Int, heightPixels: Int): Bitmap {
        val bitMatrix = Code128Writer().encode(
            barcodeValue,
            BarcodeFormat.CODE_128,
            widthPixels,
            heightPixels
        )

        val pixels = IntArray(bitMatrix.width * bitMatrix.height)
        for (y in 0 until bitMatrix.height) {
            val offset = y * bitMatrix.width
            for (x in 0 until bitMatrix.width) {
                pixels[offset + x] =
                    (if (bitMatrix.get(x, y)) barcodeColor else backgroundColor)!!
            }
        }

        val bitmap = Bitmap.createBitmap(
            bitMatrix.width,
            bitMatrix.height,
            Bitmap.Config.ARGB_8888
        )
        bitmap.setPixels(
            pixels,
            0,
            bitMatrix.width,
            0,
            0,
            bitMatrix.width,
            bitMatrix.height
        )
        return bitmap
    }
    private fun readSharedPreferences(key : String, con: Context) : String{
        val sharedPreferences = con.getSharedPreferences("epsi", Context.MODE_PRIVATE)
        val txt=sharedPreferences.getString(key,"Not found")
        return txt.toString()
    }
}