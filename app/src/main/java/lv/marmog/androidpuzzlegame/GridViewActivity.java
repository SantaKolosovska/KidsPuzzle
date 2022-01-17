package lv.marmog.androidpuzzlegame;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**This activity has images in GridView
 * home button which redirects to StartActivity
 * camera and gallery buttons to capture image from internal gallery of from camera (front and selfie)
 *
 */
public class GridViewActivity extends AppCompatActivity {

    //Button to go to the StartActivity
    private FloatingActionButton goHome;

     //picture from camera and gallery ----------------------------------------------------
    private static final int REQUEST_PERMISSION_WRITE_EXTERNAL_STORAGE = 2;
    private static final int REQUEST_IMAGE_CAPTURE = 1;

    static final int REQUEST_PERMISSION_READ_EXTERNAL_STORAGE = 3;
    static final int REQUEST_IMAGE_GALLERY = 4;

    private String mCurrentPhotoPath;

    //-------------------------------------------------------------------picture from camera

    // complexity from complexity activity
    private int piecesIntent;
    private int columnsIntent;
    private int rowsIntent;
    private int userId;
    private String username;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_view);
        setName();

        // --- get complexity, username and userid
        Intent getComplexity = getIntent();

        piecesIntent = getComplexity.getIntExtra("numberOfPieces", 0);
        columnsIntent = getComplexity.getIntExtra("numberOfColumns", 0);
        rowsIntent = getComplexity.getIntExtra("numberOfRows", 0);

        userId = getComplexity.getIntExtra("userId", 0);
        username = getComplexity.getStringExtra("username");

        //Button to go to the StartActivity
        goHome = findViewById(R.id.goHome);
        goHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goHome();
            }
        });

        Log.w(GridViewActivity.class.getName(), "User id is " + userId);
        // --- /get complexity

        AssetManager am = getAssets();
        try {
            final String[] files = am.list("img");

            GridView grid = findViewById(R.id.grid);
            grid.setAdapter(new ImageAdapter(this));
            grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                    Intent intent = new Intent(getApplicationContext(), PuzzleActivity.class);
                    intent.putExtra("assetName", files[i % files.length]);

                    // --- put extra for complexity
                    intent.putExtra("numOfPiecesToPuz", piecesIntent);
                    intent.putExtra("numOfColumnsToPuz", columnsIntent);
                    intent.putExtra("numOfRowsToPuz", rowsIntent);

                    intent.putExtra("userId", userId);
                    intent.putExtra("username", username);
                    Log.i(GridViewActivity.class.getName(), "Sent username is " + username);

                    // --- extra for complexity

                    startActivity(intent);
                }
            });
        } catch (IOException e) {
            Toast.makeText(this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    //picture from camera-------------------------------------------------
    /**
     * Gets picture from camera when clicking on camera button
     * @param view View
     */
    public void onImageFromCameraClick(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            }

            if (photoFile != null) {
                Uri photoUri = FileProvider.getUriForFile(this, getApplicationContext().getPackageName() + ".fileprovider", photoFile);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                // will need to exchange the startActivityForResult with ActivityResultLauncher
                startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    /**
     * After we captured the image from the camera or from gallery,
     * we need to send the path to the photo to the PuzzleActivity using extras:
     *
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) { //from camera

            Intent intent = new Intent(this, PuzzleActivity.class);

            // --- put extras for pieces
            intent.putExtra("numOfPiecesToPuz", piecesIntent);
            intent.putExtra("numOfColumnsToPuz", columnsIntent);
            intent.putExtra("numOfRowsToPuz", rowsIntent);
            // --- extra for complexity

            intent.putExtra("mCurrentPhotoPath", mCurrentPhotoPath);

            startActivity(intent);
        }
        if (requestCode == REQUEST_IMAGE_GALLERY && resultCode == RESULT_OK) {
            Uri uri = data.getData();
            Intent intent = new Intent(this, PuzzleActivity.class);
            // --- put extras for pieces
            intent.putExtra("numOfPiecesToPuz", piecesIntent);
            intent.putExtra("numOfColumnsToPuz", columnsIntent);
            intent.putExtra("numOfRowsToPuz", rowsIntent);
            // --- extra for complexity
            intent.putExtra("mCurrentPhotoUri", uri.toString());
            startActivity(intent);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_PERMISSION_WRITE_EXTERNAL_STORAGE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    onImageFromCameraClick(new View(this));
                }

                return;
            }
        }
    }

    /**
     * Creates image file from photo taken on camera and saves in in the gallery in
     * Folder name Pictures/ the name is in format: JPEG_YYYYMMDD_HHMMSS_  .jpg
     * @return image file
     *
     */
    private File createImageFile() throws IOException {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            // permission not granted, initiate request
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERMISSION_WRITE_EXTERNAL_STORAGE);
        } else {
            // Create an image file name
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String imageFileName = "JPEG_" + timeStamp + "_";
            File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            File image = File.createTempFile(
                    imageFileName,  /* prefix */
                    ".jpg",         /* suffix */
                    storageDir      /* directory */
            );
            mCurrentPhotoPath = image.getAbsolutePath(); // save this to use in the intent
            return image;
        }

        return null;
    }





    //-------------------------------------------------------------------picture from camera

    public void onImageFromGalleryClick(View view) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_PERMISSION_READ_EXTERNAL_STORAGE);
        } else {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            startActivityForResult(intent, REQUEST_IMAGE_GALLERY);
        }
    }

    /**
     *Redirects to StartActivity
     */
    public void goHome() {
        Intent intent = new Intent(this, StartActivity.class);
        startActivity(intent);
    }

    public void setName() {
        Intent intent = getIntent();
        String nameString = intent.getStringExtra("username");
        Log.i(GridViewActivity.class.getName(), "Name for textview is " + nameString);
        TextView name = (TextView)findViewById(R.id.username_gridview);
        name.setText(nameString);
    }

}


