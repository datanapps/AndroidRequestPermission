package datanapps.requestpermission.javarequestpermission;

public interface PermissionListener {
    void onPermissionGranted(String permissionName);
    void onPermissionDenied();
}
