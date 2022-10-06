package com.ussoft.glassywallpaper;

import android.app.Dialog;
import android.app.WallpaperManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.ussoft.glassywallpaper.adapter.PagerSliderAdapter;
import com.ussoft.glassywallpaper.database.AppDatabase;
import com.ussoft.glassywallpaper.database.FavImages;
import com.ussoft.glassywallpaper.database.UserDao;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import jp.wasabeef.glide.transformations.BlurTransformation;

public class PageSliderActivity extends AppCompatActivity {

    PagerSliderAdapter pagerSliderAdapter;
    ViewPager2 viewPager2;
    RelativeLayout mainLayout;
    ArrayList<String> wallpapers, checkingList;
    ImageView iconShare, iconFav, iconFullScreen, iconMoreOption, iconDownload;
    ImageView backButton;
    int itemPosition;
    String selectedImage;
    Boolean check;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_slider);

        //remove duplicate from the list
        wallpapers = getIntent().getStringArrayListExtra("WallpaperSrc");
        checkingList = new ArrayList<>();
        for(int i = 0 ; i <wallpapers.size() ; i++){
            String value = wallpapers.get(i);
            if (!checkingList.contains(value)){
                checkingList.add(wallpapers.get(i));
            }
        }
        wallpapers.clear();
        wallpapers.addAll(checkingList);

        itemPosition = getIntent().getIntExtra("page_position", 0);
        viewPager2 = findViewById(R.id.sliderviewpager);
        mainLayout = findViewById(R.id.mainlayout);
        setBackgroundWallpaper(wallpapers.get(itemPosition));

        backButton = findViewById(R.id.pager_backbutton);
        backButton.setOnClickListener(view -> finish());


        pagerSliderAdapter = new PagerSliderAdapter(this, wallpapers);
        viewPager2.setAdapter(pagerSliderAdapter);
        viewPager2.setCurrentItem(wallpapers.indexOf(wallpapers.get(itemPosition)));
        //viewPager2.setCurrentItem(itemPosition);
        viewPager2.setOffscreenPageLimit(3);
        viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);
        CompositePageTransformer compositePageTransformer =
                new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(30));
        compositePageTransformer.addTransformer((page, position) -> {
            float r = 1 - Math.abs(position);
            page.setScaleY(0.85f + r * 0.25f);
        });
        viewPager2.setPageTransformer(compositePageTransformer);

        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "GlassyWallpaper").allowMainThreadQueries().build();
        UserDao userDao = db.dao();
        //Set BackgroundWallpaper...
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                selectedImage = wallpapers.get(position);
                setBackgroundWallpaper(selectedImage);
                itemPosition = viewPager2.getCurrentItem();
                //int imageID = Integer.parseInt(wallpapers.get(position).substring(33, 40));
                check = userDao.is_Exist(selectedImage);
                iconFav.setOnClickListener(view -> {
                    if (check){
                        userDao.deleteWallpaper(selectedImage);
                        iconFav.setImageResource(R.drawable.icon_heart);
                        check = false;
                    }else{
                        userDao.insert(new FavImages(selectedImage));
                        iconFav.setImageResource(R.drawable.icon_heart_filled);
                        check = true;
                    }
                });
                if (check){
                    iconFav.setImageResource(R.drawable.icon_heart_filled);
                }else{
                    iconFav.setImageResource(R.drawable.icon_heart);
                }

            }
        });
        iconFullScreen = findViewById(R.id.slider_fullscreen_wallpaper);
        iconShare = findViewById(R.id.slider_share_wallpaper);
        iconDownload = findViewById(R.id.slider_download_wallpaper);
        iconMoreOption = findViewById(R.id.slider_moreoption_wallpaper);
        iconFav = findViewById(R.id.slider_fav_wallpaper);

        //FullScreen Button
        iconFullScreen.setOnClickListener(view -> {
            Intent intent = new Intent(PageSliderActivity.this, FullScreenActivity.class);
            intent.putExtra("CurrentPosition", viewPager2.getCurrentItem());
            intent.putStringArrayListExtra("WallpaperList", wallpapers);
            startActivity(intent);
        });


        //Share Button
        iconShare.setOnClickListener(view -> {
            String imageLink = wallpapers.get(viewPager2.getCurrentItem());
            shareImage(imageLink);
        });

        //Download Button
        iconDownload.setOnClickListener(view -> {
            String imageLink = wallpapers.get(viewPager2.getCurrentItem());
            saveWallpaper(imageLink);
        });

        //More Options Button...
        iconMoreOption.setOnClickListener(view -> showBottomDialog());


    }

    private void setBackgroundWallpaper(String selectedImage) {
        Glide.with(getApplicationContext())
                .asBitmap().load(selectedImage)
                .transform(new BlurTransformation(25, 8))
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        Drawable drawable = new BitmapDrawable(getResources(), resource);
                        mainLayout.setBackground(drawable);
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {

                    }
                });
    }

    private void showBottomDialog() {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottomsheetlayout);
        LinearLayout setWallpaper = dialog.findViewById(R.id.setwallpaperbtn);
        LinearLayout shareBtn = dialog.findViewById(R.id.sharebtn);
        LinearLayout saveBtn = dialog.findViewById(R.id.savebtn);

        setWallpaper.setOnClickListener(view -> {
            String imageLink = wallpapers.get(viewPager2.getCurrentItem());
            WallpaperManager manager = WallpaperManager.getInstance(getApplicationContext());
            Glide.with(getApplicationContext())
                    .asBitmap().load(imageLink)
                    .into(new CustomTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                            try {
                                manager.setBitmap(resource);
                                dialog.dismiss();
                                Toast.makeText(getApplicationContext(), "Set Wallpaper", Toast.LENGTH_SHORT).show();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onLoadCleared(@Nullable Drawable placeholder) {

                        }
                    });
        });

        shareBtn.setOnClickListener(view -> {
            String imageLink = wallpapers.get(viewPager2.getCurrentItem());
            shareImage(imageLink);
            dialog.dismiss();
        });

        saveBtn.setOnClickListener(view -> {
            String imageLink = wallpapers.get(viewPager2.getCurrentItem());
            saveWallpaper(imageLink);
            dialog.dismiss();
        });
        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }

    //Share Image Function...
    private void shareImage(String imageLink) {
        Glide.with(getApplicationContext()).asBitmap().load(imageLink)
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        String mediaPath = MediaStore.Images.Media.insertImage(getContentResolver(), resource , "Wallpaper", null);
                        Uri uri = Uri.parse(mediaPath);
                        Intent intent = new Intent(Intent.ACTION_SEND);
                        intent.setType("image/*");
                        intent.putExtra(Intent.EXTRA_STREAM, uri);
                        startActivity(Intent.createChooser(intent, "Share Image:"));
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {

                    }
                });
    }

    //Save Image Function...
    private void saveWallpaper(String imageLink) {
        Glide.with(getApplicationContext())
                .asBitmap().load(imageLink)
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        try {
                            File storage = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
                            File dir = new File(storage.getAbsolutePath() + "/Glassy Wallpapers");
                            if (!dir.exists()){
                                dir.mkdirs();
                            }
                            String filename = String.format("%d.jpg", System.currentTimeMillis());
                            File out = new File(dir, filename);
                            FileOutputStream stream = new FileOutputStream(out);
                            resource.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                            Toast.makeText(PageSliderActivity.this, "Image Saved", Toast.LENGTH_SHORT).show();
                            stream.flush();
                            stream.close();
                        }catch (Exception e){
                            Toast.makeText(PageSliderActivity.this, "Image Saved: " + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {

                    }
                });
    }
}