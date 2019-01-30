package datanapps.requestpermission


import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import datanapps.requestpermission.kotlinrequestpermission.PermissionListener
import datanapps.requestpermission.kotlinrequestpermission.RequestPermissionActivity

class SampleKotlinActivity : RequestPermissionActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnTest = findViewById<Button>(R.id.open_camera)

        btnTest.setOnClickListener { checkPermissionAndOpenCamera() }
    }

    private fun checkPermissionAndOpenCamera() {

        setPermissionGrantedListener(object : PermissionListener {
            override fun onPermissionGranted(permissionName: String) {

                // after got permission
                openCamera()
            }

            override fun onPermissionDenied() {
                // do your code
            }
        })

        val permissions = arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        if (!hasPermissions(this, *permissions)) {
            checkRunTimePermissions(permissions)
        } else {

            openCamera()

        }
    }


    private fun openCamera() {
        // camera
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(cameraIntent, 101)


    }
}
