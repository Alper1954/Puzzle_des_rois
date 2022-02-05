package aperr.android.puzzledesrois;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void clickimage(View view){
        String nomRoi = (String) view.getTag();

        //Log.i("alain", roi);

        Intent intent = new Intent(MainActivity.this, image.class);
        intent.putExtra("roi", nomRoi);
        startActivity(intent);
    }

    public void clickbutton(View view){
        Intent intent = new Intent(MainActivity.this, puzzle.class);
        startActivity(intent);
    }
}
