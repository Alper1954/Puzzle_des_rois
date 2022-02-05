package aperr.android.puzzledesrois;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class image extends Activity {
    private String nomRoi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        Intent intent = getIntent();
        nomRoi = intent.getStringExtra("roi");

        TextView tv =(TextView) findViewById(R.id.titre_roi);
        int roiStringId = getResources().getIdentifier(nomRoi, "string", getPackageName());
        tv.setText(getString(roiStringId));

        //Log.i("alain", nomRoi);

        ImageView img = (ImageView)findViewById(R.id.roi);
        int imgId = getResources().getIdentifier(nomRoi, "drawable", getPackageName());
        img.setImageResource(imgId);

    }

    public void clickimage(View view){
        Intent intent = new Intent(image.this, puzzle.class);
        intent.putExtra("roi", nomRoi);
        startActivity(intent);

        overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
        finish();
    }
}
