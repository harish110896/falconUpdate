package com.example.apple.imagecapture;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.github.florent37.androidslidr.Slidr;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Button btnCapture,btnRemoveTime,btnScreenCapture,btnRlLoader,btnCustomer;
    public static final  int CAMERA_REQUEST = 1;

    public static final  int TAKENPHOTO = 2;
    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 5;
    private static final int MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE = 10;
    ImageView ivCaptureImage;
    ScrollView svTestList;
    Bitmap photo;
    File photofile;
    List<Long> appTimeKiller = new ArrayList<>();
    TextView tvLocalTimeMilli;
    private static final String TAG = "MyFirebaseMsgService";

    private static final String KEY_BUTTON = "button";
    private FirebaseRemoteConfig mFirebaseRemoteConfig;
    private static final String LOADING_PHRASE_CONFIG_KEY = "loading_phrase";
    private static final String WELCOME_MESSAGE_KEY = "btnRlLoader";
    private static final String WELCOME_MESSAGE_CAPS_KEY = "welcome_message_caps";

    private RoundCornerProgressBar progressStatus;

    private RelativeLayout llProgressText;
    private TextView tvProgressText;

    ProgressBar pbGraph;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnCapture = (Button) findViewById(R.id.btnCapture);
        btnRemoveTime = (Button) findViewById(R.id.btnRemoveTime);
        ivCaptureImage = (ImageView)findViewById(R.id.ivCaptureImage);
        btnScreenCapture = (Button) findViewById(R.id.btnScreenCapture);
        svTestList = (ScrollView)findViewById(R.id.svTestList);
        tvLocalTimeMilli = (TextView)findViewById(R.id.tvLocalTimeMilli);
        btnRlLoader = (Button)findViewById(R.id.btnRlLoader);
        btnCustomer = (Button)findViewById(R.id.btnCustomer);
//
//        progressStatus = (RoundCornerProgressBar) findViewById(R.id.progressStatus);
//        progressStatus.setProgress(56);
        pbGraph = (ProgressBar) findViewById(R.id.pbGraph);
        double seek = 50.0;
        ObjectAnimator animation = ObjectAnimator.ofInt(pbGraph, "progress", 0, (int)seek);
        animation.setDuration(1000); // 0.5 second
        animation.setInterpolator(new DecelerateInterpolator());
        animation.start();

        tvProgressText = (TextView)findViewById(R.id.tvProgressText);
        tvProgressText.setText("56/100");

        llProgressText = (RelativeLayout)findViewById(R.id.llProgressText);



        btnCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,CustomerInfo.class);
                startActivity(intent);
            }
        });
        btnRlLoader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this,RlLoader.class);
//                startActivity(intent);
                  CommonFunctions.enableRlLoader(MainActivity.this,true);
                Handler ha=new Handler();
                ha.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //call function
                        CommonFunctions.enableRlLoader(MainActivity.this,false);
                    }
                }, 5000);
            }
        });

        btnScreenCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tkn = FirebaseInstanceId.getInstance().getToken();
                Log.d(TAG, "Message data payload: " + tkn);
                Log.d("App", "Token ["+tkn+"]");
                if(tkn.equals("cHiEMgVWGXw:APA91bFzN9enwgFaDNNt3yCXD4k5FuW7LTts8n7g3jdIIAumh7fx8o05nKs3cV_XWIluWYvVLzFQUNC12Rur3zRg96_ZGeyYffRRcV1eMMA0dK41gf7RW6jD4zYJn3RSbhwfWmlDdj4nIsoEquIJKy1QT0047hb9Sg"))
                {
                    Toast.makeText(getApplicationContext(),"Matches Successfully",Toast.LENGTH_LONG).show();
                }
                FirebaseMessaging.getInstance().subscribeToTopic("fcm")
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                String msg = "Subscribed";
                                if (!task.isSuccessful()) {
                                    msg = "Subsciption Failed";
                                }
                                Log.d(TAG, msg);
//                                Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                            }
                        });
                //ag phone fPZBmY1oRyw:APA91bHMMfLpSTGjdHWU95d1Qx7geN0B8KIWWMdDiMVqB55cFDNXpaQWdtORkdRP85FP8h0-81ymKfsvNFqO_JDlFJsfgsNZijJDAkm_lIrq5ipLH-9ybe22JQrkdGYybqu84pbXeQrDmeVtvvxGnUuKnijy-1fbfw

                //test phone cHiEMgVWGXw:APA91bFzN9enwgFaDNNt3yCXD4k5FuW7LTts8n7g3jdIIAumh7fx8o05nKs3cV_XWIluWYvVLzFQUNC12Rur3zRg96_ZGeyYffRRcV1eMMA0dK41gf7RW6jD4zYJn3RSbhwfWmlDdj4nIsoEquIJKy1QT0047hb9Sg
            }
        });
        btnRemoveTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setapptime(appTimeKiller);
            }
        });
        btnCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePhoto();
            }
        });
        checkPhotoPermission();
        checkWriteStoragePermission();
        //
        long timeLarger =  1529574000;
        appTimeKiller.add(timeLarger);
        long timeSmaller  = 1529574600;
        appTimeKiller.add(timeSmaller);
        tvLocalTimeMilli.setText(""+System.currentTimeMillis());

        FirebaseMessaging.getInstance().setAutoInitEnabled(true);

        mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
                .setDeveloperModeEnabled(BuildConfig.DEBUG)
                .build();
        mFirebaseRemoteConfig.setConfigSettings(configSettings);
        mFirebaseRemoteConfig.setDefaults(R.xml.remote_config_defaults);

        fetchWelcome();
    }

    public void storeCameraPhotoInSDCard(Bitmap bitmap, String directoryName,String imageName) {
        createDirectory(directoryName);
        File outputFile = new File(Environment.getExternalStorageDirectory() + directoryName, imageName + ".jpg");
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(outputFile);
//            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
//            fileOutputStream.flush();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] byteArray = stream.toByteArray();
            fileOutputStream.write(byteArray);
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createDirectory(String directoryPath) {
        File myDirectory = new File(Environment.getExternalStorageDirectory() + directoryPath);
        if (!myDirectory.isDirectory()) {
            myDirectory.mkdirs();

        }

    }

    private void takePhoto() {
//        Intent photoCaptureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        startActivityForResult(photoCaptureIntent, CAMERA_REQUEST);

        File photostorage = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        photofile = new File(photostorage, "testingimage" + ".jpg");

        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); //intent to start camera
//        i.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photofile));
        startActivityForResult(i, TAKENPHOTO);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                Bitmap bitmap = (Bitmap)data.getExtras().get("data");
                String directoryName = "/testing2/images/";
                String imageName = "testImage";
                storeCameraPhotoInSDCard(bitmap,directoryName,imageName);
                ivCaptureImage.setImageBitmap(bitmap);
            }
        }
        else if (requestCode == TAKENPHOTO){

            try{

               photo = (Bitmap) data.getExtras().get("data");
            }
            catch(NullPointerException ex){
                photo = BitmapFactory.decodeFile(photofile.getAbsolutePath());
            }

            if(photo != null){
                ivCaptureImage.setImageBitmap(photo);


            }
            else{

                Toast.makeText(this, "Oops,can't get the photo from your gallery", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void checkPhotoPermission() {
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.CAMERA)) {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA},
                        MY_PERMISSIONS_REQUEST_CAMERA);
            } else {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA},
                        MY_PERMISSIONS_REQUEST_CAMERA);
            }
        }
    }

    private void checkWriteStoragePermission() {
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE);
            } else {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CAMERA: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {
                    checkPhotoPermission();
                }
                return;
            }
        }
    }

    public void setapptime(List<Long> apptime)
    {
        long minimumMilli = System.currentTimeMillis();
        int index = 0;
        for(int i = 0;i<apptime.size();i++)
        {
            long minimumMilliseconds = apptime.get(0)*1000-System.currentTimeMillis();
            if(minimumMilliseconds > 0 && minimumMilliseconds < minimumMilli)
            {
                minimumMilli = minimumMilliseconds;
                index = i;
            }
        }

        Toast.makeText(getApplicationContext(),"The minimum time index "+index,Toast.LENGTH_LONG).show();
    }

    private void fetchWelcome() {
//        btnRlLoader.setText(mFirebaseRemoteConfig.getString(LOADING_PHRASE_CONFIG_KEY));

        long cacheExpiration = 3600; // 1 hour in seconds.
        if (mFirebaseRemoteConfig.getInfo().getConfigSettings().isDeveloperModeEnabled()) {
            cacheExpiration = 0;
        }
        mFirebaseRemoteConfig.fetch(cacheExpiration)
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(MainActivity.this, "Fetch Succeeded",
                                    Toast.LENGTH_SHORT).show();

                            mFirebaseRemoteConfig.activateFetched();
                        } else {
                            Toast.makeText(MainActivity.this, "Fetch Failed",
                                    Toast.LENGTH_SHORT).show();
                        }
                        displayWelcomeMessage();
                    }
                });
    }
    private void displayWelcomeMessage() {
        String welcomeMessage = mFirebaseRemoteConfig.getString(WELCOME_MESSAGE_KEY);
        if (mFirebaseRemoteConfig.getBoolean(WELCOME_MESSAGE_CAPS_KEY)) {
            btnRlLoader.setAllCaps(true);
        } else {
            btnRlLoader.setAllCaps(false);
        }
        btnRlLoader.setText(welcomeMessage);
    }

}