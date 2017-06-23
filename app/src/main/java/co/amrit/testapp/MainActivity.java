package co.amrit.testapp;

import android.annotation.TargetApi;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Matrix;
import android.os.Build;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.Locale;

import co.amrit.testapp.db.DBHelper;
import co.amrit.testapp.db.Question;

public class MainActivity extends AppCompatActivity implements RecognitionListener {
    TextToSpeech tts;
    private Intent recognizerIntent;
    private SQLiteDatabase db;
    ArrayList<Question> questions;
    DBHelper dbHelper;
    private TextView returnedText;
    private SpeechRecognizer speech = null;
    FloatingActionButton btnMicrophone;
    private String LOG_TAG = "VoiceRecogActivity";
    Animation scanAnimation;
    private final int SPEECH_RECOGNITION_CODE = 1;
    int questionCounter;
    //String answer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        returnedText = (TextView) findViewById(R.id.txt_output);
        dbHelper = new DBHelper(this);
        questions = dbHelper.getQuestions();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        /*RotateAnimation rotate = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                rotate.setDuration(500);
                rotate.setInterpolator(new LinearInterpolator());
                rotate.setRepeatCount(Animation.INFINITE);*/

        //ImageView image= (ImageView) findViewById(R.id.imageView);

        //image.startAnimation(rotate);
                /*RotateAnimation ra = new RotateAnimation(0, 6*360);
                ra.setFillAfter(true);
                ra.setDuration(1000);*/
        //btnMicrophone.startAnimation(rotate);

        scanAnimation = new ScaleAnimation(
                1f, 1.4f, // Start and end values for the X axis scaling
                1f, 1.4f, // Start and end values for the Y axis scaling
                Animation.RELATIVE_TO_SELF, 0.5f, // Pivot point of X scaling
                Animation.RELATIVE_TO_SELF, 0.5f); // Pivot point of Y scaling
        scanAnimation.setFillAfter(true); // Needed to keep the result of the animation
        scanAnimation.setDuration(15);
        scanAnimation.setRepeatCount(Animation.INFINITE);
        //anim.setFillAfter(true);
        tts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    tts.setLanguage(Locale.UK);
                }
            }
        });
        /*tts.setOnUtteranceProgressListener(new UtteranceProgressListener() {
            @Override
            public void onStart(String utteranceId) {
                while(tts.isSpeaking()){
                }
                tts.speak("Please scan your thumb and answer", TextToSpeech.QUEUE_FLUSH, null);
            }

            @Override
            public void onDone(String utteranceId) {
                //startSpeechToText();
                while(tts.isSpeaking()){
                }
                tts.speak("Please scan your thumb and answer", TextToSpeech.QUEUE_FLUSH, null);
            }

            @Override
            public void onError(String utteranceId) {

            }
        });*/
        startSpeechToText();
        /*btnMicrophone = (ImageButton) findViewById(R.id.btn_mic);
        btnMicrophone.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                speech.startListening(recognizerIntent);
                return true;
            }
        });*/

        btnMicrophone = (FloatingActionButton) findViewById(R.id.btn_mic);
        btnMicrophone.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                speech.startListening(recognizerIntent);

                btnMicrophone.startAnimation(scanAnimation);

                return true;
            }
        });

        /*Matrix matrix = new Matrix();
        matrix.postRotate((float) 45, 45, 45);
        fab1.setImageMatrix(matrix);*/
        //fab1.setScaleType(ImageView.ScaleType.FIT_CENTER);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startNarcoTest();
                /*TextView tView = (TextView)findViewById(R.id.textView4);
                String question1 = questions.get(0).getQuestion();
                tView.setText(question1);
                //String toSpeak = tView.getText().toString();
                //Toast.makeText(getApplicationContext(), toSpeak,Toast.LENGTH_SHORT).show();
                tts.speak(question1, TextToSpeech.QUEUE_FLUSH, null);*/

                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/


            }
        });

    }
    private void startSpeechToText() {

        speech = SpeechRecognizer.createSpeechRecognizer(this);
        speech.setRecognitionListener(this);

        //Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        recognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE,
                "en");
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,
                "co.amrit.testapp");
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 3);
        /*try {
            speech.startListening(recognizerIntent);
            Thread.sleep(1000L);
            speech.stopListening();
            //startActivityForResult(recognizerIntent, SPEECH_RECOGNITION_CODE);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    "Sorry! Speech recognition is not supported in this device.",
                    Toast.LENGTH_SHORT).show();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
    }

    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case SPEECH_RECOGNITION_CODE: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    String text = result.get(0);
                    returnedText.setText(text);
                }
                break;
            }
        }
    }*/
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

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (speech != null) {
            speech.destroy();
            Log.i(LOG_TAG, "destroy");
        }

    }
boolean speechStarted;
    @Override
    public void onBeginningOfSpeech() {
        Log.i(LOG_TAG, "onBeginningOfSpeech");
        speechStarted = true;
    }

    @Override
    public void onBufferReceived(byte[] buffer) {
        Log.i(LOG_TAG, "onBufferReceived: " + buffer);
    }

    @Override
    public void onEndOfSpeech() {
        Log.i(LOG_TAG, "onEndOfSpeech");
        btnMicrophone.clearAnimation();
        speechStarted = false;
    }

    @Override
    public void onError(int errorCode) {
        String errorMessage = getErrorText(errorCode);
        Log.d(LOG_TAG, "FAILED " + errorMessage);
        speakText("Didn't understand, please try again.");
        returnedText.setText(errorMessage);
        btnMicrophone.clearAnimation();
    }

    @Override
    public void onEvent(int arg0, Bundle arg1) {
        Log.i(LOG_TAG, "onEvent");
    }

    @Override
    public void onPartialResults(Bundle arg0) {
        Log.i(LOG_TAG, "onPartialResults");
    }

    @Override
    public void onReadyForSpeech(Bundle arg0) {
        Log.i(LOG_TAG, "onReadyForSpeech");
    }

    @Override
    public void onResults(Bundle results) {
        Log.i(LOG_TAG, "onResults");
        String text = "";
        ArrayList<String> matches = results
                .getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
        if(null != matches && matches.size() > 0){
            text = matches.get(0);
            speakNextQuestion();
        }

        returnedText.setText(text);
    }

    @Override
    public void onRmsChanged(float rmsdB) {
        Log.i(LOG_TAG, "onRmsChanged: " + rmsdB);
    }

    public static String getErrorText(int errorCode) {
        String message;
        switch (errorCode) {
            case SpeechRecognizer.ERROR_AUDIO:
                message = "Audio recording error";
                break;
            case SpeechRecognizer.ERROR_CLIENT:
                message = "Client side error";
                break;
            case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
                message = "Insufficient permissions";
                break;
            case SpeechRecognizer.ERROR_NETWORK:
                message = "Network error";
                break;
            case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
                message = "Network timeout";
                break;
            case SpeechRecognizer.ERROR_NO_MATCH:
                message = "No match";
                break;
            case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
                message = "RecognitionService busy";
                break;
            case SpeechRecognizer.ERROR_SERVER:
                message = "error from server";
                break;
            case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
                message = "No speech input";
                break;
            default:
                message = "Didn't understand, please try again.";
                break;
        }
        return message;
    }

    //@TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void startNarcoTest(){
        questionCounter = 0;
        TextView tView = (TextView)findViewById(R.id.textView4);
        //String question1 = questions.get(0).getQuestion();

        //String toSpeak = tView.getText().toString();
        //Toast.makeText(getApplicationContext(), toSpeak,Toast.LENGTH_SHORT).show();
        //String question1 = questions.get(0).getQuestion();

        speakNextQuestion();

        /*for(Question question : questions){
            String ques = question.getQuestion();
            tView.setText(ques);
            answer = null;
            while(tts.isSpeaking()){
            }
            tts.speak(ques, TextToSpeech.QUEUE_FLUSH, null);*/
            /*while(tts.isSpeaking()){
                tView.setText(ques);
            }*/
            //tts.stop();
            /*while(null == answer){

            }*/
            //answer = getAnswer();

            //boolean result = evaluateAnswer(question, answer);

            //markAnswer(result);
    }

    public void speakNextQuestion(){

        if(questionCounter < questions.size()){
            String ques = questions.get(questionCounter).getQuestion();
            speakText(ques);

            questionCounter++;
        }
    }

    public void speakText(String text){
        while(tts.isSpeaking()){
        }
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }
    public String getAnswer(){

        if(!speechStarted){
            while(tts.isSpeaking()){
            }
            tts.speak("Please scan your thumb and answer", TextToSpeech.QUEUE_FLUSH, null);
        }


        return null;
    }

    public boolean evaluateAnswer(Question question, String answer){

        return true;
    }

    public void markAnswer(boolean result){

    }

}
