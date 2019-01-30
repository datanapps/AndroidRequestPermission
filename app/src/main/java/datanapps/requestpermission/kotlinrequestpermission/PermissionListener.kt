package datanapps.requestpermission.kotlinrequestpermission

interface PermissionListener {
    fun onPermissionGranted(permissionName: String)
    fun onPermissionDenied()
}
