package com.example.whitboardocrapp;

import static com.example.whitboardocrapp.display.colorList;
import static com.example.whitboardocrapp.display.current_brush;
import static com.example.whitboardocrapp.display.pathList;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

public class MainActivity extends AppCompatActivity {
    public static Path path = new Path();
    public static Paint paint_brush = new Paint();
    Bitmap bitmap;
    //private String stringResult = null;
    private ConstraintLayout constraintLayout;
    String S;

    //private TextView textView;
    //private static final int RC_HANDLE_CAMERA_PERM = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         constraintLayout = findViewById(R.id.constraintLayout2);
        //    private TextToSpeech textToSpeech;
        //    private CameraSource cameraSource;
        //    private TextRecognizer textRecognizer;
        //    private SurfaceView surfaceView;
        Button buttonstart = findViewById(R.id.btn_save);
        buttonstart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bitmap = Bitmap.createBitmap(constraintLayout.getWidth(), constraintLayout.getHeight(),
                        Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bitmap);
                constraintLayout.draw(canvas);

                TextRecognizer textRecognizer = new TextRecognizer.Builder(getApplicationContext()).build();
                if(!textRecognizer.isOperational()){
                    Toast.makeText(getApplicationContext(), "Could not get text", Toast.LENGTH_SHORT).show();
                }
                else {
                    Frame frame = new Frame.Builder().setBitmap(bitmap).build();
                    SparseArray<TextBlock> items = textRecognizer.detect(frame);
                    StringBuilder sb = new StringBuilder();
                    for (int i=0;i<items.size();i++){
                        TextBlock myitem = items.valueAt(i);
                        sb.append(myitem.getValue());
                    }

                   S=(sb.toString());
                }

                final AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.custom_dialog, null);
                final TextView text = mView.findViewById(R.id.ourText);
                Button btn_ok = mView.findViewById(R.id.buttonOk);
                alert.setView(mView);
                text.setText(S);
                final AlertDialog alertDialog = alert.create();
                btn_ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                    }
                });
                alertDialog.show();

            }
        });

    }




//        ActivityCompat.requestPermissions(this, new String[]{CAMERA}, PackageManager.PERMISSION_GRANTED);
//        textToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
//            @Override
//            public void onInit(int i) {
//
//            }
//        });
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//    }
//
//    private void textRecognizer() {
//        textRecognizer = new TextRecognizer.Builder(getApplicationContext()).build();
//        cameraSource = new CameraSource.Builder(getApplicationContext(), textRecognizer)
//                .setFacing(CameraSource.CAMERA_FACING_BACK)
//                .setRequestedPreviewSize(1280, 1024)
//                .setRequestedFps(15.0f)
//                .setAutoFocusEnabled(true)
//                .build();
//        surfaceView = findViewById(R.id.surfaceView);
//        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
//            @Override
//            public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
//
//                    if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
//
//                        try {
//                            cameraSource.start(surfaceView.getHolder());
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                    else{
//                        askCameraPermission();
//                    }
//            }
//
//            @Override
//            public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {
//                cameraSource.stop();
//
//            }
//        });
//        textRecognizer.setProcessor(new Detector.Processor<TextBlock>() {
//            @Override
//            public void release() {
//
//            }
//
//            @Override
//            public void receiveDetections(@NonNull Detector.Detections<TextBlock> detections) {
//                SparseArray<TextBlock> sparseArray=detections.getDetectedItems();
//                StringBuilder stringBuilder=new StringBuilder();
//                for(int i=0;i<sparseArray.size();i++){
//                    TextBlock textBlock=sparseArray.valueAt(i);
//                    if(textBlock!=null && textBlock.getValue()!=null){
//                        stringBuilder.append(textBlock.getValue()+" ");
//                    }
//                }
//                String stringText=stringBuilder.toString();
//                Handler handler=new Handler(Looper.getMainLooper());
//                handler.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        stringResult=stringText;
//                        resultObtained();
//                    }
//                });
//            }
//
//        });
//    }
//    private void resultObtained(){
//        setContentView(R.layout.activity_main);
//        textView=findViewById(R.id.textView);
//        textView.setText(stringResult);
//        textToSpeech.speak(stringResult,TextToSpeech.QUEUE_FLUSH,null,null);
//
//    }
//    public void buttonStart(View view){
//        setContentView(R.layout.surfaceview);
//    }
//    private void askCameraPermission() {
//
//        final String[] permissions = new String[]{Manifest.permission.CAMERA};
//
//        if (!ActivityCompat.shouldShowRequestPermissionRationale(this,
//                Manifest.permission.CAMERA)) {
//            ActivityCompat.requestPermissions(this, permissions, RC_HANDLE_CAMERA_PERM);
//            return;
//        }
//    }

    public void pencil(View view) {
        paint_brush.setColor(Color.BLACK);
        currentColor(paint_brush.getColor());
    }

    public void eraser(View view) {
        pathList.clear();
        colorList.clear();
        path.reset();
    }

    public void redColor(View view) {
        paint_brush.setColor(Color.RED);
        currentColor(paint_brush.getColor());
    }
    public void yellowColor(View view) {
        paint_brush.setColor(Color.YELLOW);
        currentColor(paint_brush.getColor());
    }

    public void magentaColor(View view) {
        paint_brush.setColor(Color.MAGENTA);
        currentColor(paint_brush.getColor());
    }

    public void greenColor(View view) {
        paint_brush.setColor(Color.GREEN);
        currentColor(paint_brush.getColor());
    }
    public void currentColor(int c){
        current_brush=c;
        path=new Path();
    }

}