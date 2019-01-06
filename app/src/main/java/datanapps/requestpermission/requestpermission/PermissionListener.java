package datanapps.requestpermission.requestpermission;

public interface PermissionListener {
    void onPermissionGranted(String permissionName);
    void onPermissionDenied();
}
