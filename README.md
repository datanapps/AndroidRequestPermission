# AndroidRequestPermission (java and kotlin) (Implement runtime permission utility)
An android library that simplifies the process of requesting android run time permissions.


![alt text](https://github.com/datanapps/AndroidRequestPermission/blob/master/screens/android_permission.gif)

Usage :

1.

A. Add "javarequestpermission" directory for java project.

B. Add "kotlinrequestpermission" directory for kotlin project.

2. Add string value in your string.xml file.

  <!-- Permission  -->
    <string name="set_permission">Set Permisssion</string>
    <string name="msg_permission">We can not continue without this permission.</string>
    <string name="msg_permission_with_settings">We can not continue without this permission so goto settings screen and grant permission.</string>


3. Extend RequestPermissionActivity for those activities which needs runtime permission. 

public class MainActivity extends RequestPermissionActivity 


4. You have to add only below method at your action (befor method)
===================================================================
private void checkPermissionAndOpenCamera(){

        setPermissionGrantedListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(String permissionName) {

                // after got permission
                //openCamera();
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

            //openCamera();

        }
    }
    =============================================================================
    
 5.  Full Code:
    
    =============================================================================
    public class MainActivity extends RequestPermissionActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        findViewById(R.id.open_camera).setOnClickListener(new View.OnClickListener() {
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



[![See](https://datanapps.com/public/dnarestapi/buy/buy_coffee4.png)](https://www.paypal.me/datanappspaynow)

  ### License

Copyright [2021] [datanapps]

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at
   http://www.apache.org/licenses/LICENSE-2.0

