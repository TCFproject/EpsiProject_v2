package fr.epsi.epsiproject_v2

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.budiyev.android.codescanner.AutoFocusMode
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.CodeScannerView
import com.budiyev.android.codescanner.DecodeCallback
import com.budiyev.android.codescanner.ErrorCallback
import com.budiyev.android.codescanner.ScanMode
import com.google.zxing.integration.android.IntentIntegrator
import fr.epsi.epsiproject_v2.databinding.ActivityMainBinding
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.util.jar.Manifest

class ScanActivity : BaseActivity() {
    private lateinit var codeScanner: CodeScanner
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan)

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)==
                PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this,
                arrayOf(android.Manifest.permission.CAMERA),123)
        }else{
            startScanning()
        }
    }

    private fun isJson(json:String): Boolean{
        try {
            JSONObject(json)
        }catch (ex: JSONException){
            try {
                JSONArray(json)
            } catch (ex1:JSONException ) {
                return false
            }
        }
        return true
    }

    private fun startScanning(){
        val scannerView:CodeScannerView = findViewById(R.id.scanner_view)
        codeScanner = CodeScanner(this,scannerView)
        codeScanner.camera = CodeScanner.CAMERA_BACK
        codeScanner.formats = CodeScanner.ALL_FORMATS

        codeScanner.autoFocusMode = AutoFocusMode.SAFE
        codeScanner.scanMode = ScanMode.SINGLE
        codeScanner.isAutoFocusEnabled = true
        codeScanner.decodeCallback = DecodeCallback {
            runOnUiThread {
                if (isJson(it.text)){
                    FormulaireActivity.startThisActivity(application,it.text)
                }else{
                    Toast.makeText(this,"Bad form string : not JSon", Toast.LENGTH_SHORT).show()
                    startThisActivity(application)
                }
            }
        }
        codeScanner.errorCallback = ErrorCallback {
            runOnUiThread {
                Toast.makeText(this,"Scan initialize ${it.message}", Toast.LENGTH_SHORT).show()
            }
        }
        scannerView.setOnClickListener {
            codeScanner.startPreview()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 123){
            if (grantResults[0]==PackageManager.PERMISSION_DENIED){
                Toast.makeText(this,"Camera persission granted", Toast.LENGTH_SHORT).show()
                startScanning()
            }else{
                Toast.makeText(this,"Camera persission denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (::codeScanner.isInitialized){
            codeScanner?.startPreview()
        }
    }

    override fun onPause() {
        if (::codeScanner.isInitialized){
            codeScanner?.releaseResources()
        }
        super.onPause()
    }
    companion object{
        fun startThisActivity(con:Context){
            val i = Intent(con,ScanActivity::class.java)
            con.startActivity(i)
        }
    }
}