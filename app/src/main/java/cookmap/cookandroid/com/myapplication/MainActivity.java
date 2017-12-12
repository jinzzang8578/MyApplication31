package cookmap.cookandroid.com.myapplication;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    protected Button btn_Call, btTts, button, btn_SMS, btn_lo;
    protected EditText etTts;
    protected TextToSpeech tts;
    protected ArrayList<String> arName, arPhoneNum;
    protected final int nNameSize = 3;
    protected static final int RECOG_CODE = 1234;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
        String speech = result.get(0);
        if (resultCode == RESULT_OK) {
            if (requestCode == RECOG_CODE) {
                ArrayList<String> arStr = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                String sRecog = arStr.get(0);
                Toast.makeText(getApplicationContext(), sRecog, Toast.LENGTH_SHORT).show();
                int nPos = arName.indexOf(sRecog);
                if(nPos == -1) {
                   Toast.makeText(this, speech + "는 없는 이름입니다. \n 다시말씀해주세요", Toast.LENGTH_SHORT).show();
                }
                else{
                    String sPhoneNum = arPhoneNum.get(nPos);
                    Toast.makeText(getApplicationContext(), sRecog, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + sPhoneNum));
                    startActivity(intent);
                }

            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_lo = (Button) findViewById(R.id.btn_lo);

btn_lo.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getApplicationContext(), Map.class);
        startActivity(intent);
    }
});


        btn_Call = (Button) findViewById(R.id.btn_Call);
        btn_Call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.KOREAN);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "음성 인식중.");
                startActivityForResult(intent, RECOG_CODE);
            }
        });
        btn_SMS = (Button) findViewById(R.id.btn_SMS);
        btn_SMS.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View v) {
                                           Intent intent = new Intent(getApplicationContext(), second.class);
                                           startActivity(intent);

                                       }
                                   });


//        btTts = (Button) findViewById(R.id.btTts);
//        btTts.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String str = etTts.getText().toString();
//                Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                    tts.speak(str, TextToSpeech.QUEUE_FLUSH, null, null);
//                }
//
//            }
//        });


//        etTts = (EditText) findViewById(R.id.etTts);
//
//        tts = new TextToSpeech(this, this);

                arName = new ArrayList<String>(nNameSize);
        arPhoneNum = new ArrayList<String>(nNameSize);

        arName.add("테스트");
        arPhoneNum.add("01012345678");

    }


//    @Override
//    public void onInit(int i) {
//        if (i == TextToSpeech.SUCCESS) {
//            tts.setLanguage(Locale.KOREAN);
//            tts.setPitch(0.5f);
//            tts.setSpeechRate(1.0f);
//        }
//
//    }


}