package abs.khasmer.disastermanagement;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DataImages extends AppCompatActivity {

    private final int IMAGE_REQUEST_CODE = 1;
    private final int CAMERA_PERMISSION_REQUEST_CODE = 2;
    private final int STORAGE_PERMISSION_REQUEST_CODE = 3;

    Button b1, b2;
    private Button mNextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.data);
        b1 = findViewById(R.id.b1);
        mNextButton = findViewById(R.id.next_button);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.CAMERA) !=
                            PackageManager.PERMISSION_GRANTED
                            || checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                                    PackageManager.PERMISSION_GRANTED) {

                        requestPermissions(
                                new String[]{
                                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                        Manifest.permission.CAMERA
                                },
                                CAMERA_PERMISSION_REQUEST_CODE);
                    }
                    else {
                        startCamera();
                    }
                }
            }
        });

        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(
                                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                STORAGE_PERMISSION_REQUEST_CODE);
                    }
                    else {
                        startGallery();
                    }
                }
            }
        });
    }

    private void startGallery() {
        Intent i = new Intent(DataImages.this, ImagesGallery.class);
        startActivity(i);
    }

    private void startCamera() {
        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(i, IMAGE_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Bitmap b = (Bitmap) data.getExtras().get("data");
                saveImage(b);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                    grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                startCamera();
            }
        }
        else if (requestCode == STORAGE_PERMISSION_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startGallery();
            }
        }
    }

    /**
     * Checks whether read + write permission is given or not
     * @return false if storage permission is not given
     *         True otherwise
     */
    boolean isExternalStorageAccessible() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }

    private File getDirectory() {
        String name = "images";
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), name);

        if (!file.exists()) {
            file.mkdirs();
        }
        return file;
    }

    private void saveImage(Bitmap bitmap) {
        if (!isExternalStorageAccessible()) {
            // show dialog
            return;
        }
        File file = new File(getDirectory(), createImageFile());

        try {
            if (file.exists()) {
                file.delete();
                file.createNewFile();
            }
            FileOutputStream outputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            outputStream.flush();
            outputStream.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }

    private String createImageFile() {
        // Create an image file name
        @SuppressLint("SimpleDateFormat")
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        return "disaster-" + timeStamp + ".jpg";
    }
}
