package aperr.android.puzzledesrois;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

public class puzzle extends Activity {
    int[] numImages = new int[30];
    private String nomRoi;
    private LinearLayout puzzle;
    private MediaPlayer mediaPlayer;
    //private Boolean mediaPlayerStarted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puzzle);
        //mediaPlayer = MediaPlayer.create(this, R.raw.song);
        //mediaPlayer.setLooping(true);
        //mediaPlayerStarted = false;
        randomList();

        Intent intent = getIntent();
        nomRoi = intent.getStringExtra("roi");

        TextView tv =(TextView) findViewById(R.id.titre_roi);
        int roiStringId = getResources().getIdentifier(nomRoi, "string", getPackageName());
        tv.setText(getString(roiStringId));

        ImageView img1 = (ImageView)findViewById(R.id.roi);
        int imgId1 = getResources().getIdentifier(nomRoi, "drawable", getPackageName());
        img1.setImageResource(imgId1);

        int numImg = 0;
        puzzle = (LinearLayout)findViewById(R.id.puzzle);
        for(int i=0; i<6; i++){
            LinearLayout puzzle2 = (LinearLayout)puzzle.getChildAt(i);
            for(int j=0; j<5; j++){
                ImageView img = (ImageView)puzzle2.getChildAt(j);
                //Log.i("alain", "numImg: " + Integer.toString(numImg)+"\n");
                String imgName = nomRoi + "_" + Integer.toString(numImages[numImg]);
                int imgId = getResources().getIdentifier(imgName, "drawable", getPackageName());
                img.setImageResource(imgId);
                img.setTag(imgName);
                img.setOnTouchListener(new ImgTouchListener());
                img.setOnDragListener(new ImgDragListener());
                numImg++;
            }
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_puzzle, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.aide:
                LinearLayout img =(LinearLayout) findViewById(R.id.layer_roi);
                img.setVisibility(View.VISIBLE);
                LinearLayout layout =(LinearLayout)findViewById(R.id.puzzle);
                layout.setVisibility(View.GONE);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public void clickimage(View view){
        LinearLayout img =(LinearLayout) findViewById(R.id.layer_roi);
        img.setVisibility(View.GONE);
        LinearLayout layout =(LinearLayout)findViewById(R.id.puzzle);
        layout.setVisibility(View.VISIBLE);
    }

    private final class ImgTouchListener implements View.OnTouchListener{

        public boolean onTouch(View view, MotionEvent motionEvent){

            if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                ClipData data = ClipData.newPlainText("", "");
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                view.startDrag(data, shadowBuilder, view, 0);

                return true;
            }else{
                return false;
            }
        }
    }

    private class ImgDragListener implements View.OnDragListener {

        public boolean onDrag(View v, DragEvent event){
            ImageView dragimg =(ImageView)event.getLocalState();
            ImageView dropimg = (ImageView) v;
            switch (event.getAction()){
                case DragEvent.ACTION_DRAG_STARTED:
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:
                    dragimg =(ImageView)event.getLocalState();
                    dragimg.setVisibility(View.INVISIBLE);
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    break;
                case DragEvent.ACTION_DROP:
                    dragimg =(ImageView)event.getLocalState();
                    String dragimgtag = (String) dragimg.getTag();
                    int dragimgres = getResources().getIdentifier(dragimgtag, "drawable", getPackageName());

                    dropimg = (ImageView) v;
                    String dropimgtag = (String) dropimg.getTag();
                    int dropimgres = getResources().getIdentifier(dropimgtag, "drawable", getPackageName());

                    dragimg.setImageResource(dropimgres);
                    dragimg.setTag(dropimgtag);
                    dragimg.setVisibility(View.VISIBLE);
                    dropimg.setImageResource(dragimgres);
                    dropimg.setTag(dragimgtag);

                    Boolean success = true;
                    int index = 1;
                    for(int i=0; i<6; i++){
                        LinearLayout puzzle2 = (LinearLayout)puzzle.getChildAt(i);
                        for(int j=0; j<5; j++){
                            ImageView img = (ImageView)puzzle2.getChildAt(j);
                            String imgtag = (String) img.getTag();
                            String[] imgtagFields = imgtag.split("_");
                            int numimg = Integer.parseInt(imgtagFields[1]);
                            if(index != numimg){success = false;}
                            index++;
                        }
                    }
                    LinearLayout root_puzzle = (LinearLayout) findViewById(R.id.root_puzzle);
                    TextView tvfelicitations =(TextView)findViewById(R.id.felicitations);
                    if(success){
                        root_puzzle.setBackgroundColor(Color.BLACK);
                        tvfelicitations.setVisibility(View.VISIBLE);
                        //mediaPlayer.start();
                        //mediaPlayerStarted = true;
                    }else{
                        root_puzzle.setBackgroundColor(Color.parseColor("#048b9a"));
                        tvfelicitations.setVisibility(View.INVISIBLE);
                        //if(mediaPlayerStarted){
                           // mediaPlayer.pause();
                       // }
                    }

                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    dragimg =(ImageView)event.getLocalState();
                    dragimg.setVisibility(View.VISIBLE);
                    break;
                default:
                    break;
            }
            return true;
        }
    }




    private void randomList(){
        ArrayList<Integer> intList = new ArrayList<Integer>();
        for (int i=1; i <= 30; i++){
            intList.add(i);
        }

        Collections.shuffle(intList);

        for (int i=0; i < 30; i++){
            numImages[i]= intList.get(i);
        }

        //for (int i=0; i < 30; i++){
        //Log.i("alain", Integer.toString(i)+": " + Integer.toString(numImages[i])+"\n");
        //}
    }

    @Override
    public void onBackPressed(){
        AlertDialog.Builder alertDlg = new AlertDialog.Builder(this);
        alertDlg.setMessage(R.string.fermeture);
        alertDlg.setNegativeButton(R.string.non, null);
        alertDlg.setPositiveButton(R.string.oui,
                new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which){
                        //mediaPlayer.release();
                        //mediaPlayer = null;
                        finish();
                    }
                });
        alertDlg.create().show();

    }

}
