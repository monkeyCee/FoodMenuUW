package ca.uwaterloo.uwfoodservices;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

public class ImageLoadingTask extends AsyncTask<String, Void, Bitmap> {
	  
	  ImageView mapView;
	  
	  public ImageLoadingTask(ImageView mapView){
		  this.mapView = mapView;
	  }
      @Override
      protected Bitmap doInBackground(String... stringURL) {
          Bitmap bmp = null;
          try {
              URL url = new URL(stringURL[0]);

              HttpURLConnection conn = (HttpURLConnection) url.openConnection();
              conn.setDoInput(true);
              conn.connect();
              InputStream is = conn.getInputStream();
              BitmapFactory.Options options = new BitmapFactory.Options();

              bmp = BitmapFactory.decodeStream(is, null, options);
          } catch (Exception e) {
              e.printStackTrace();
          }

          return bmp;
      }

      @Override
      protected void onPostExecute(Bitmap result) {
          mapView.setImageBitmap(result);
          super.onPostExecute(result);
      }

  }