package datanapps.requestpermission.kotlinrequestpermission

import android.annotation.TargetApi
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity

import datanapps.requestpermission.R


/*
*
* This class is base class of activities for project. All other activities extend this class
* **/

open class RequestPermissionActivity : AppCompatActivity() {

    protected lateinit var permissionListener: PermissionListener
    /**
     * Create all class objects.
     */
    private var permissionAlert: AlertDialog? = null
    private var permissions: Array<String>? = null

    /**
     * This method is used to get user all readable permission name
     */
    private val permissionListName: String
        get() {
            val permissionName = StringBuilder()
            for (permission in permissions!!) {
                permissionName.append(getPermissionLabel(permission) + ",")
            }
            return permissionName.toString()
        }

    /**
     * This method is used to check that this app has permission or not.
     */
    fun hasPermissions(context: Context?, vararg permissions: String): Boolean {
        if (context != null && permissions != null) {
            for (permission in permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false
                }
            }
        }
        return true
    }

    fun checkRunTimePermissions(permissions: Array<String>?) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M || permissions == null) {
            return
        }
        this.permissions = permissions
        if (!hasPermissions(this, *permissions)) {
            ActivityCompat.requestPermissions(this, permissions, PERMISSIONS_REQUEST)
        }
    }

    fun setPermissionGrantedListener(listener: PermissionListener) {
        this.permissionListener = listener
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSIONS_REQUEST && permissions.size > 0) {
            var isGranted = true
            for (i in grantResults) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    isGranted = i == 0 || isGranted
                } else {
                    isGranted = false
                }
            }
            if (isGranted) {
                permissionListener.onPermissionGranted("")
            } else {
                denyPermission(permissions.size - 1)
            }
        }
    }

    /**
     * This method is user show popup if permission denied by user.
     */
    @TargetApi(Build.VERSION_CODES.M)
    private fun denyPermission(i: Int) {
        if (permissions != null && permissions!!.size > 0) {
            val showRationale = shouldShowRequestPermissionRationale(permissions!![i])
            if (showRationale)
                showAlertForPermission()
            else
                showAlertForPermissionAndShowSettings(permissionListName)
        }
    }

    /**
     * This method is user to show popup to and move to set app permission
     */
    private fun showAlertForPermission() {
        if (permissionAlert == null) {
            permissionAlert = showAlertWithListener(this, getString(R
                    .string.msg_permission), getString(R.string.set_permission), getString(android.R.string.no), DialogInterface.OnClickListener { dialog, which -> checkRunTimePermissions(permissions) }, DialogInterface.OnClickListener { dialog, which -> permissionListener.onPermissionDenied() })
            permissionAlert!!.setOnDismissListener { permissionAlert = null }
            permissionAlert!!.setOnCancelListener { permissionAlert = null }
            permissionAlert!!.show()
        }
    }

    /**
     * This method is user to show popup to and move to app settings
     */
    private fun showAlertForPermissionAndShowSettings(permissionName: String) {
        if (permissionAlert == null) {
            permissionAlert = showAlertWithListener(this, permissionName + "\n" + getString(R.string.msg_permission_with_settings), getString(R.string.set_permission), getString(android.R.string.no), DialogInterface.OnClickListener { dialog, which -> openAppSettingPage() }, DialogInterface.OnClickListener { dialog, which -> permissionListener.onPermissionDenied() })
            permissionAlert!!.setOnCancelListener { permissionAlert = null }
            permissionAlert!!.setOnDismissListener { permissionAlert = null }
            permissionAlert!!.show()
        }
    }

    /**
     * This method is used to get user readable name of given permission
     */
    private fun getPermissionLabel(permission: String): String {
        try {
            val packageManager = packageManager
            val permissionInfo = packageManager.getPermissionInfo(permission, 0)
            return permissionInfo.loadLabel(packageManager).toString()
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }

        return ""
    }

    /**
     * This method is used to move app setting page to set app permission
     */
    private fun openAppSettingPage() {
        val i = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        i.data = Uri.parse("package:$packageName")
        startActivityForResult(i, PERMISSIONS_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PERMISSIONS_REQUEST) {
            checkRunTimePermissions(permissions)
        }


    }


    private fun showAlertWithListener(context: Context, message: String, positiveBtnText: String?, negativeBtnText: String?, positiveListener: DialogInterface.OnClickListener?, negativeListener: DialogInterface.OnClickListener?): AlertDialog {

        val alertDialog: AlertDialog
        val alert = AlertDialog.Builder(context)
        alert.setIcon(R.mipmap.ic_launcher)
                .setTitle(context.getString(R.string.app_name))
                .setMessage(message)
        if (positiveBtnText != null && positiveListener != null) {
            alert.setPositiveButton(positiveBtnText, positiveListener)
        }
        if (negativeBtnText != null && negativeListener != null) {
            alert.setNegativeButton(negativeBtnText, negativeListener)
        }
        if (positiveListener == null && negativeListener == null) {
            alert.setNegativeButton(android.R.string.ok, null)
        }
        alertDialog = alert.create()
        alertDialog.setCanceledOnTouchOutside(false)
        alertDialog.setCancelable(false)
        return alertDialog
    }

    companion object {
        val PERMISSIONS_REQUEST = 11
    }


}