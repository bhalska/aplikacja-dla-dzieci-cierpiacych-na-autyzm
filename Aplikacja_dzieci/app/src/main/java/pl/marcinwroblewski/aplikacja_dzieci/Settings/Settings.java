package pl.marcinwroblewski.aplikacja_dzieci.Settings;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import pl.marcinwroblewski.aplikacja_dzieci.R;
import pl.marcinwroblewski.aplikacja_dzieci.Usable.Utilities;


public class Settings extends Activity {

    public static final int READER_MEN = 0;
    public static final int READER_WOMEN = 1;
    private static final int RESULT_LOAD_IMAGE = 1;
    private static final int TAKE_PICTURE = 2;

    private int reward;

    private EditText childName;
    private TextView readerMen, readerWomen;
    private Button saveSettings;
    private int readerChoice;
    private ImageView firstReward, secondReward, thirdReward, childPhoto;
    private SharedPreferences settings;
    private SharedPreferences.Editor settingsEditor;
    private Utilities utils;
    private String imageName;
    private BitmapFactory.Options options;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        overridePendingTransition(R.anim.push_down_in, R.anim.push_down_out);

        getActionBar().hide();

        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN
        );

        utils = new Utilities();
        options = new BitmapFactory.Options();

        settings = getApplicationContext().getSharedPreferences("pl.marcinwroblewski.aplikacja_dzieci", getApplicationContext().MODE_PRIVATE);
        settingsEditor = settings.edit();

        settingsEditor.putString("external_directory",  getApplicationContext().getFilesDir().getAbsolutePath() + "/");
        imageName = "child_photo.png";

        Log.d("Settings", "Avatar directory: " + settings.getString("external_directory", "") + imageName);

        childName = (EditText)findViewById(R.id.child_name_holder);


        childName.setOnKeyListener(new View.OnKeyListener() {

            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                int event = keyEvent.getAction();


                if(event == KeyEvent.ACTION_DOWN &&
                        keyCode == KeyEvent.KEYCODE_ENTER){

                    getWindow().setSoftInputMode(
                    WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN
                    );

                    saveSettings();
                }


                return false;
            }
        });

        readerMen = (TextView)findViewById(R.id.reader_men);
        readerWomen = (TextView)findViewById(R.id.reader_women);
        saveSettings = (Button)findViewById(R.id.button_send);
            saveSettings.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    int event = motionEvent.getAction();

                    switch(event){
                        case MotionEvent.ACTION_DOWN:
                            saveSettings();
                            break;
                    }

                    return false;
                }
            });


        firstReward = (ImageView)findViewById(R.id.reward01);
        secondReward = (ImageView)findViewById(R.id.reward02);
        thirdReward = (ImageView)findViewById(R.id.reward03);
        childPhoto = (ImageView)findViewById(R.id.child_photo);

        getSettings();

        //setReaderColor();
        setRewardColor();
    }



    public void sendBack(View view) {
        super.onBackPressed();
        overridePendingTransition(R.anim.push_down_in, R.anim.push_down_out);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.push_down_in, R.anim.push_down_out);
    }


    private void getSettings(){
        childName.setText(settings.getString("child_name", ""));
        readerChoice = settings.getInt("reader", READER_MEN);
        reward = settings.getInt("reward_id", 2);

        getImageFromExternal();
    }




    @Override
    protected void onPause() {
        super.onPause();

        if(getApplicationContext() != null) {
            if (childName.getText() != null && !childName.getText().toString().contains(" ") && !childName.getText().toString().isEmpty()) {
                settingsEditor.putString("child_name", childName.getText().toString().substring(0, 1).toUpperCase() +
                        childName.getText().toString().substring(1, childName.getText().toString().length()).toLowerCase());
            }
            settingsEditor.putInt("reader", readerChoice);
            settingsEditor.putInt("reward_id", reward);

            settingsEditor.commit();
        }else{
            Toast.makeText(getApplicationContext(), "Błąd zapisu", Toast.LENGTH_SHORT).show();
        }
    }

    public void setReaderChoice(View view) {

        switch (view.getId()){
            case R.id.reader_men:
                readerChoice = READER_MEN;
                break;
            case R.id.reader_women:
                readerChoice = READER_WOMEN;
                break;
            default:
                readerChoice = READER_MEN;
        }

        setReaderColor();
    }

    public void setReaderColor(){
        if(readerChoice == READER_MEN) {
            utils.setPressedColor(getResources().getColor(R.color.white), getResources().getColor(R.color.blue),
                    getResources().getColor(R.color.blue_background), getResources().getColor(R.color.white_background), readerMen, readerWomen);
        }else if(readerChoice == READER_WOMEN){
            utils.setPressedColor(getResources().getColor(R.color.white), getResources().getColor(R.color.blue),
                    getResources().getColor(R.color.blue_background), getResources().getColor(R.color.white_background), readerWomen, readerMen);
        }
    }

    public void setReward(View view) {

        switch (view.getId()){
            case R.id.reward01:
                reward = 1;
                break;
            case R.id.reward02:
                reward = 2;
                break;
            case R.id.reward03:
                reward = 3;
                break;
            default:
                reward = 1;
                break;
        }
        setRewardColor();
    }

    public void setRewardColor(){
        if(reward==1){
            utils.setPressedColor(getResources().getColor(R.color.blue_background), getResources().getColor(R.color.white_background),
                    firstReward, secondReward, thirdReward);
        }else if(reward==2){
            utils.setPressedColor(getResources().getColor(R.color.blue_background), getResources().getColor(R.color.white_background),
                    secondReward, thirdReward, firstReward);
        }else if(reward==3){
            utils.setPressedColor(getResources().getColor(R.color.blue_background), getResources().getColor(R.color.white_background),
                    thirdReward, secondReward, firstReward);
        }else{
            firstReward.setBackgroundColor(Color.parseColor("#CC000"));
            secondReward.setBackgroundColor(Color.parseColor("#CC000"));
            thirdReward.setBackgroundColor(Color.parseColor("#CC000"));
        }
    }


    public void saveSettings() {

        if(getApplicationContext() != null) {
            if (childName.getText() != null && !childName.getText().toString().contains(" ") && !childName.getText().toString().isEmpty()) {
                if(childName.getText().toString().length() <= 15) {
                    settingsEditor.putString("child_name", childName.getText().toString().substring(0, 1).toUpperCase() + childName.getText().toString().substring(1).toLowerCase());
                }else{
                    settingsEditor.putString("child_name", childName.getText().toString().substring(0, 1).toUpperCase() + childName.getText().toString().substring(1, 15).toLowerCase());
                }
            }else {
                settingsEditor.putString("child_name", "");
            }
            settingsEditor.putInt("reader", readerChoice);
            settingsEditor.putInt("reward_id", reward);

            settingsEditor.commit();

            saveSettings.setText("Zapisano");
            saveSettings.setBackgroundColor(getResources().getColor(R.color.white_background));
            saveSettings.setTextColor(getResources().getColor(R.color.blue));
        }else{
            Toast.makeText(getApplicationContext(), "Błąd zapisu", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

            switch (requestCode) {
                case RESULT_LOAD_IMAGE:
                if (resultCode == RESULT_OK && null != data) {
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getContentResolver().query(selectedImage, filePathColumn,
                            null, null, null);
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String picturePath = cursor.getString(columnIndex);
                    cursor.close();

                    setChildImage(BitmapFactory.decodeFile(picturePath));

                    //bitmap save options
                    options.inSampleSize = 5;
                } else {
                    Toast.makeText(getApplicationContext(), "Nie udało się wybrać zdjęcia", Toast.LENGTH_SHORT).show();
                }

                break;


                case TAKE_PICTURE:
                if (resultCode == Activity.RESULT_OK) {

                    Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
                    setChildImage(thumbnail);

                    //bitmap save options
                    options.inSampleSize = 0;
                } else {
                    Toast.makeText(getApplicationContext(), "Nie udało się zrobić zdjęcia", Toast.LENGTH_SHORT).show();
                }
            }
    }



    public void setChildImage(Bitmap photo){

        childPhoto.setImageBitmap(photo);
        saveChildPhoto(photo);
    }

    public void saveChildPhoto(Bitmap photo){
        File dir = new File(settings.getString("external_directory", ""));
        dir.mkdirs();

        File file = new File(settings.getString("external_directory", ""), imageName);



        try {
            FileOutputStream fileOut = new FileOutputStream(file);
            photo.compress(Bitmap.CompressFormat.PNG, 100, fileOut);
            fileOut.flush();
            fileOut.close();
        } catch (FileNotFoundException e) {
            Toast.makeText(getApplicationContext(), "Nie udało się zapisać zdjęcia", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(getApplicationContext(), "Nie udało się zapisać zdjęcia!", Toast.LENGTH_SHORT).show();
        }
    }


    private void getImageFromExternal() {
        if((new File(settings.getString("external_directory", "") + imageName).exists())) {
            Bitmap bitmap = BitmapFactory.decodeFile(settings.getString("external_directory", "") + imageName, options);
            setChildImage(bitmap);
        }else if(settings.getString("external_directory", "").equals("")){
            Toast.makeText(getApplicationContext(), "Do zapisu zdjęcia potrzebna jest pamięć zewnętrzna", Toast.LENGTH_SHORT).show();
        }
    }


    public void goToGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, RESULT_LOAD_IMAGE);
    }

    public void goToCamera(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, TAKE_PICTURE);
    }

    public void imageMenu(View view) {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);

        alertBuilder.setTitle("Wybierz zdjęcie");

        alertBuilder.setMessage("Skąd chcesz pobrać zdjęcie dziecka?");
        alertBuilder.setCancelable(true);

        alertBuilder.setPositiveButton("Aparat", new DialogInterface.OnClickListener(){

            public void onClick(DialogInterface dialog, int id){
                goToCamera();
            }
        });

        alertBuilder.setNegativeButton("Galeria", new DialogInterface.OnClickListener(){

            public void onClick(DialogInterface dialog, int id){
                goToGallery();
            }
        });

        alertBuilder.setNeutralButton("Awatar", new DialogInterface.OnClickListener(){

            public void onClick(DialogInterface dialog, int id){


                final Drawable man = getResources().getDrawable(R.drawable.on);
                setChildImage(((BitmapDrawable)man).getBitmap());

                //Toast.makeText(getApplicationContext(), "Nie gotowe", Toast.LENGTH_SHORT);
            }
        });

        AlertDialog dialog = alertBuilder.create();
        dialog.show();
    }
}
