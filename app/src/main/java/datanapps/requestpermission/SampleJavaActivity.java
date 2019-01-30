package datanapps.requestpermission;


import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;

import datanapps.requestpermission.javarequestpermission.PermissionListener;
import datanapps.requestpermission.javarequestpermission.RequestPermissionActivity;

public class SampleJavaActivity extends RequestPermissionActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnTest = findViewById(R.id.open_camera);

        btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermissionAndOpenCamera();
            }
        });
    }

    private void checkPermissionAndOpenCamera(){


        setPermissionGrantedListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(String permissionName) {

                // after got permission
                openCamera();
            }

            @Override
            public void onPermissionDenied() {
                // do your code
            }
        });

        String[] permissions = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (!hasPermissions(this, permissions)) {
            checkRunTimePermissions(permissions);
        } else {

            openCamera();

        }
    }



    private void openCamera(){
        // camera
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, 101);


    }
}
