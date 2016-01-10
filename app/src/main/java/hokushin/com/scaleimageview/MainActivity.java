package hokushin.com.scaleimageview;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.hokushin.scaleimageview.ScaleImageView;

public class MainActivity extends AppCompatActivity {

    private ScaleImageView mScaleImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mScaleImageView = (ScaleImageView) this.findViewById(R.id.image);

        Bitmap bitmap;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 4;
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.test, options);

        mScaleImageView.setImageBitmap(bitmap);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
