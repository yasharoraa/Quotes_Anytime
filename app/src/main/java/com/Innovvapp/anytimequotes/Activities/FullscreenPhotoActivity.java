package com.Innovvapp.anytimequotes.Activities;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.Innovvapp.anytimequotes.Models.Quote;
import com.Innovvapp.anytimequotes.Utils.RealmController;
import com.android.android.quotes.R;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.io.ByteArrayOutputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Yash Arora on 23-01-2018.
 */

public class FullscreenPhotoActivity extends AppCompatActivity {
    public int REQUEST_ID_SET_AS_WALLPAPER = 1001;

    public int REQUEST_CODE = 300;

    @BindView(R.id.activity_full_screen_photo_photo)
    ImageView fullscreenPhoto;

    @BindView(R.id.item_fullscreen_quote_quote)
    TextView fullScreenQuote;

    @BindView(R.id.fullscreen_author_name)
    TextView author;

    @BindView(R.id.activity_fullscreen_photo_fab_menu)
    FloatingActionMenu fabMenu;

    @BindView(R.id.activity_fullscreen_photo_fab_copy)
    FloatingActionButton fabCopy;

    @BindView(R.id.activity_fullscreen_photo_fab_save)
    FloatingActionButton fabSave;

    @BindView(R.id.activity_fullscreen_photo_fab_set_share)
    FloatingActionButton fabShare;

    @BindView(R.id.activity_fullscreen_photo_fab_delete)
    FloatingActionButton fabDelete;

    @BindView(R.id.activity_fullscreen_photo_fab_set_wallpaper)
    FloatingActionButton fabWallpaper;

    private Unbinder unbinder;

    String quoteText;
    String authorText;

    int imageBackgroundId;

    int quoteId;

    private RealmController realmController;





    Realm realm = Realm.getDefaultInstance();

    int i;

    Quote quote = new Quote();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_full_screen);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        unbinder = ButterKnife.bind(this);

        Intent intent =getIntent();

        Bundle bun = intent.getExtras();


        quoteText = bun.getString("quote");

        authorText = bun.getString("author");
        imageBackgroundId = bun.getInt("image");

        quoteId = bun.getInt("id");



        quote.setQuote(quoteText);
        quote.setAuthor(authorText);
        quote.setPhotoId(imageBackgroundId);
        if (quoteId!=0){
            fabSave.setVisibility(View.GONE);
            fabDelete.setVisibility(View.VISIBLE);
        }




        updateUi();
    }

    public void updateUi(){

        fullScreenQuote.setText(quoteText);
        author.setText(authorText);
        fullscreenPhoto.setImageResource(updateImage());


    }
    public int updateImage(){

        try {

            i = 0;

            switch (imageBackgroundId){

                case 102:
                    //fullscreenPhoto.setImageResource(R.drawable.imagetwo);
                    i = R.drawable.imagetwo;
                    break;

                case 104:
                    //fullscreenPhoto.setImageResource(R.drawable.imagefour);
                    i = R.drawable.imagefour;
                    break;

                case 106:
                    //fullscreenPhoto.setImageResource(R.drawable.imageone);
                    i = R.drawable.imagesix;
                    break;
                case 107:
                    //fullscreenPhoto.setImageResource(R.drawable.imagetwo);
                    i = R.drawable.imageseven;
                    break;
                case 108:
                    //fullscreenPhoto.setImageResource(R.drawable.imagethree);
                    i = R.drawable.imageeight;
                    break;
                case 109:
                    //fullscreenPhoto.setImageResource(R.drawable.imagefour);
                    i = R.drawable.imagenine;
                    break;

                default:
                    fullscreenPhoto.setImageResource(R.drawable.imageseven);
            }


        }catch (Exception e){
            e.printStackTrace();
        }
        return i;
    }
    public View getView() {
        View view  = getLayoutInflater().inflate(R.layout.quote_background,null);
        ImageView imageView;
        TextView textView;
        TextView authorView;
        imageView = view.findViewById(R.id.myImageView);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        imageView.setLayoutParams(new RelativeLayout.LayoutParams(width, width));
        textView = view.findViewById(R.id.my_text_view);
        authorView = view.findViewById(R.id.my_author_view);
        imageView.setImageResource(i);
        textView.setText(quoteText);
        authorView.setText("- " +authorText);
        return view;
    }
    public static Bitmap loadBitmapFromView(View v) {
        if (v.getMeasuredHeight() <= 0) {
            v.measure(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
            Bitmap b = Bitmap.createBitmap(v.getMeasuredWidth(), v.getMeasuredWidth(), Bitmap.Config.ARGB_8888);
            Canvas c = new Canvas(b);
            v.layout(0, 0, v.getMeasuredWidth(), v.getMeasuredWidth());
            v.draw(c);
            return b;
        }else {
            Bitmap b = Bitmap.createBitmap(v.getLayoutParams().width, v.getLayoutParams().height, Bitmap.Config.ARGB_8888);
            Canvas c = new Canvas(b);
            v.layout(v.getLeft(), v.getTop(), v.getRight(), v.getBottom());
            v.draw(c);
            return b;
        }
    }

    @OnClick(R.id.activity_fullscreen_photo_fab_set_share)
    public void shareFabImage(){
        Context context = getView().getContext();

        try{
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.putExtra(Intent.EXTRA_STREAM, getImageUri(context,loadBitmapFromView(getView())));
            shareIntent.setType("image/png");
            shareIntent.putExtra(Intent.EXTRA_TEXT,"Shared Via Quotes Anytime App\nhttps://play.google.com/store/apps/details?id=com.Innovvapp.anytimequotes&ah=AoRy8unC9WzUFvlIHf1VscQDF1c");
            startActivity(Intent.createChooser(shareIntent, getResources().getText(R.string.sent_to)));
        }catch (Exception e){

            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE);

            Toast.makeText(this,R.string.share_again,Toast.LENGTH_SHORT).show();



        }

    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    @OnClick(R.id.activity_fullscreen_photo_fab_save)
    public void setFabSave(){


        saveQuote(quote);

        Toast.makeText(this,"Saved",Toast.LENGTH_SHORT).show();


    }
    public void saveQuote(final Quote quote){




                Number currentIdNum = realm.where(Quote.class).max("id");
                int nextId;
                if(currentIdNum == null) {
                    nextId = 1;
                } else {
                    nextId = currentIdNum.intValue() + 1;
                }

                quote.setId(nextId);

                realm.beginTransaction();
                realm.copyToRealm(quote);
                realm.commitTransaction();

            }

    @OnClick(R.id.activity_fullscreen_photo_fab_delete)
    public void deleteImage(){

        AlertDialog diaBox = AskOption();
        diaBox.show();

        diaBox.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);



    }

    private AlertDialog AskOption() {

        AlertDialog myQuittingDialogBox =new AlertDialog.Builder(this)
                //set message, title, and icon
                .setTitle(R.string.delete)
                .setMessage(R.string.delete_message)
                .setIcon(R.drawable.ic_delete)


                .setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {

                        try {


                            RealmResults<Quote> results = realm.where(Quote.class).equalTo("quote", quoteText).findAll();
                            if (!realm.isInTransaction()) {
                                realm.beginTransaction();
                            }

                            results.deleteAllFromRealm();
                            realm.commitTransaction();
                            Toast.makeText(FullscreenPhotoActivity.this, "Deleted",Toast.LENGTH_SHORT).show();
                        }catch (Exception e){
                            Toast.makeText(FullscreenPhotoActivity.this,"Delete Failed",Toast.LENGTH_SHORT).show();
                        }
                        dialog.dismiss();
                        FullscreenPhotoActivity.this.finish();



                    }




                })

                .setNegativeButton(R.string.cancel_title, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();

                    }
                })
                .create();
        return myQuittingDialogBox;
    }





    @OnClick(R.id.activity_fullscreen_photo_fab_set_wallpaper)
    public void setAs(){
        Context context = getView().getContext();

        try {


            Intent intent = new Intent(Intent.ACTION_ATTACH_DATA);
            intent.setDataAndType( getImageUri(context,loadBitmapFromView(getView())), "image/*");
            intent.putExtra("jpg", "image/*");
            startActivityForResult(Intent.createChooser(intent,
                    getString(R.string.wall_chooser_title)), REQUEST_ID_SET_AS_WALLPAPER);


        }catch (Exception e){

            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE);

            Toast.makeText(this,"Please grant permissions and try again",Toast.LENGTH_SHORT).show();


        }


    }
    @OnClick(R.id.activity_fullscreen_photo_fab_copy)
    public void copyQuote(){
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText(null, quoteText);
        clipboard.setPrimaryClip(clip);

        Toast.makeText(FullscreenPhotoActivity.this,R.string.quote_copied,Toast.LENGTH_SHORT).show();
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
