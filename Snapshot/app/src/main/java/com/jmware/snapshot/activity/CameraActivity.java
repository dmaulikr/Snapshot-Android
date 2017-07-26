package com.jmware.snapshot.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.jmware.snapshot.R;
import com.jmware.snapshot.manager.ApplicationManager;

public class CameraActivity extends AppCompatActivity {

    private final int PERMISSION_REQUEST_CAMERA = 1;

    private final int REQUEST_IMAGE_CAPTURE = 2;

    private Button photoLibraryBarButton;

    private Button takePhotoBarButton;

    private Button photoLibraryButton;

    private Button takePhotoButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        initializeViews();
        setupActions();
    }

    private void initializeViews() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        photoLibraryBarButton = (Button) findViewById(R.id.photo_library_bar_button);
        takePhotoBarButton = (Button) findViewById(R.id.take_photo_bar_button);
        photoLibraryButton = (Button) findViewById(R.id.photo_library_button);
        takePhotoButton = (Button) findViewById(R.id.take_photo_button);
    }

    private void setupActions() {
        photoLibraryBarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onPhotoLibraryButtonClick((Button) view);
            }
        });
        takePhotoBarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onTakePhotoButtonClick((Button) view);
            }
        });
        photoLibraryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onPhotoLibraryButtonClick((Button) view);
            }
        });
        takePhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onTakePhotoButtonClick((Button) view);
            }
        });
    }

    private void onPhotoLibraryButtonClick(Button button) {
        Intent intent = new Intent(this, PhotoLibraryActivity.class);
        startActivity(intent);
    }

    private void onTakePhotoButtonClick(Button button) {
        if (getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            if (checkCameraPermission()) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
                }
            }
        } else {
            new AlertDialog.Builder(this)
                    .setTitle("Snapshot")
                    .setMessage("This device has no supported camera.")
                    .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {}
                    })
                    .show();
        }
    }

    private boolean checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
                Toast.makeText(this, "Provide permission to access camera.", Toast.LENGTH_SHORT).show();
            } else {
                ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CAMERA}, PERMISSION_REQUEST_CAMERA);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CAMERA) {
            if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if ((requestCode == REQUEST_IMAGE_CAPTURE) && (resultCode == RESULT_OK)) {
            ApplicationManager app = (ApplicationManager) getApplication();
            Bitmap photoData = (Bitmap) data.getExtras().get("data");
            app.setCapturedImage(photoData);
            Intent intent = new Intent(this, SaveOptionsActivity.class);
            startActivity(intent);
        }
    }

}
