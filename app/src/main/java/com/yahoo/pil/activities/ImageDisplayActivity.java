package com.yahoo.pil.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.yahoo.pil.R;
import com.yahoo.pil.adapters.DetailsImagesListAdapter;
import com.yahoo.pil.models.DetailsScrollImage;
import com.yahoo.pil.models.ImageSearchApiClient;
import com.yahoo.pil.models.Photo;

import org.lucasr.twowayview.TwoWayView;

import java.util.ArrayList;


public class ImageDisplayActivity extends ActionBarActivity {

    Boolean isFavorite;
    ImageView topImageView;
    ArrayList<DetailsScrollImage> images;
    DetailsImagesListAdapter imagesAdapter;
    ImageSearchApiClient detailsClient;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_display);
        isFavorite = true;
        updateFavoriteButtonImage();
        
        //getActionBar().hide();
        Photo imageResult = getIntent().getParcelableExtra("photo");
       // final TouchImageView imageView = (TouchImageView)findViewById(R.id.ivImageResult);
        topImageView = (ImageView) findViewById(R.id.ivProfileBackgroundImage);
        final String fullImageUrl = imageResult.getBigImageURL();
        Picasso.with(this).load(fullImageUrl).into(topImageView);
        TextView tvName = (TextView) findViewById(R.id.tvLocationName);
        tvName.setText(imageResult.getTitle().toString());

        // make a list of ImageToLoad objects
        images = new ArrayList<DetailsScrollImage>();
        //Adapter
        imagesAdapter = new DetailsImagesListAdapter(this, images);
        
        //for (int i=0; i<2; i++) {
            DetailsScrollImage nextImage = new DetailsScrollImage(fullImageUrl);
            images.add(nextImage); // substitute some pretty picture you can stand to see 20 times in a list
            nextImage = new DetailsScrollImage("https://farm9.staticflickr.com/8263/8685477275_3e0e704743_b.jpg");
            images.add(nextImage);
            DetailsScrollImage image3 = new DetailsScrollImage("https://farm3.staticflickr.com/2801/4128710402_8a6d7e39a5_b.jpg");
            images.add(image3);
            nextImage = new DetailsScrollImage("https://farm8.staticflickr.com/7237/7343944286_fa525f1954_b.jpg");
            images.add(nextImage);
            nextImage = new DetailsScrollImage("https://farm3.staticflickr.com/2640/4128708874_cb33bc6dfc_b.jpg");
            images.add(nextImage);
            nextImage = new DetailsScrollImage("https://farm3.staticflickr.com/2801/4128710402_8a6d7e39a5_b.jpg");
            images.add(nextImage);
            nextImage = new DetailsScrollImage("https://farm8.staticflickr.com/7502/15923503632_d5bc50d341_b.jpg");
            images.add(nextImage);
       // }
        
        TwoWayView lvScrollView = (TwoWayView) findViewById(R.id.lvItems);
        lvScrollView.setAdapter(imagesAdapter);
        imagesAdapter.notifyDataSetChanged();

       /* ArrayList<String> items = new ArrayList<String>();
        for (int i=0; i<20; i++) {
            items.add(fullImageUrl);
            //items.add("Item "+i+" ");
        }
        ArrayAdapter<String> aItems = new ArrayAdapter<String>(this, R.layout.item_details_image_small, items);
        TwoWayView lvTest = (TwoWayView) findViewById(R.id.lvItems);
        lvTest.setAdapter(aItems);*/
        lvScrollView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
              // Toast.makeText(ImageDisplayActivity.this, "Image clicked",Toast.LENGTH_SHORT).show();
                DetailsScrollImage selectedImage = images.get(position);
                Picasso.with(ImageDisplayActivity.this).load(selectedImage.urlString).into(topImageView);
            }
        });
    }

    public void favoriteClicked(View v) {
        
        isFavorite = !isFavorite;
        updateFavoriteButtonImage();
    }
    
    private void updateFavoriteButtonImage() {
        ImageView ivFavImageView = (ImageView) findViewById(R.id.ivFavoriteImage);
        if (isFavorite) {
            ivFavImageView.setImageResource(R.drawable.ic_favorite);
        } else {
            ivFavImageView.setImageResource(R.drawable.ic_favorite_notselected);
        }
        
    }
    
    public void callClicked(View v) {

        String phoneNum = "408 594 6671";
        Toast.makeText(this,"Calling "+phoneNum, Toast.LENGTH_SHORT).show();
        String uri = "tel:" + phoneNum.trim() ;
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse(uri));
        startActivity(intent);
    }
    
    public void navigateClicked(View v) {

        Toast.makeText(this,"Navigate", Toast.LENGTH_SHORT).show();
        String destinationLatitude = "46.414382";
        String destinationLongitude = "10.013988";

        String sourceLatitude = "45.414382";
        String sourceLongitude = "10.013988";
        
       // Intent navigation = new Intent(Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps?saddr=42.35892,-71.05781&daddr=40.756054,-73.986951"));
        String uriString = "http://maps.google.com/maps?daddr="+destinationLatitude+"," +destinationLongitude; //40.756054,-73.986951
        if ((sourceLatitude!=null) && (sourceLongitude != null)) {
            uriString = uriString+"&saddr="+sourceLatitude+","+sourceLongitude;
        }
        Intent navigation = new Intent(Intent.ACTION_VIEW, Uri.parse(uriString));
        startActivity(navigation);

    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_image_display, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
