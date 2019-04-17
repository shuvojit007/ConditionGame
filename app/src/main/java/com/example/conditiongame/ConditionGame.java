package com.example.conditiongame;

import android.content.Context;
import android.graphics.PorterDuff;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.GenericTransitionOptions;
import com.bumptech.glide.Glide;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.zip.CheckedOutputStream;

public class ConditionGame extends AppCompatActivity implements View.OnClickListener{

    Context cn;
    private CountDownTimerPausable countDownTimer;
    private ProgressBar progressBarCircle;

    Button btn_true, btn_false;
    private TextView progresstext,question;
    private ImageView condition_img,pizza;
    private int[] imageOption;


    String[] gamequestion;
    Boolean [] gameans;
    int Counter = 0;
    int Ans= 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);
        cn = this;

        init();
    }


    private void init() {
        btn_true = findViewById(R.id.btn_true);
        btn_false = findViewById(R.id.btn_false);
        btn_false.setOnClickListener(this);
        btn_true.setOnClickListener(this);

        question = findViewById(R.id.question);
        imageOption = new int[]{
                R.drawable.age17, R.drawable.age18, R.drawable.age18_1,
                R.drawable.age19, R.drawable.age23, R.drawable.age25};

        condition_img = findViewById(R.id.condition_img);
        pizza = findViewById(R.id.pizza);
        progressBarCircle = findViewById(R.id.progressBar);
        progresstext = findViewById(R.id.progresstext);
        setUpQuestionAndAnswer();
        startCountDownTimer();
    }

    private void setUpQuestionAndAnswer() {
        gamequestion = new String[]{"age > 20:", "age < 17:", "age == 24:", "age != 24:", "age >= 18:", "age <= 30"};
        gameans = new Boolean[]{Boolean.FALSE,Boolean.FALSE,Boolean.FALSE,Boolean.TRUE,Boolean.TRUE,Boolean.TRUE};
    }


    private void SetImage() {
        Log.d("CGAME", "SetImage: "+Counter);
        question.setText(gamequestion[Counter]);

        Glide.with(ConditionGame.this)

                .load(imageOption[Counter])
                .transition(GenericTransitionOptions.with(android.R.anim.slide_in_left))
                .into(condition_img);

    }

    public void SetPizza(){
        if (Ans==1){
            Glide.with(cn).load(R.drawable.slice1).into(pizza);
        }else if (Ans==2){
            Glide.with(cn).load(R.drawable.slice2).into(pizza);
        }else if (Ans==3){
            Glide.with(cn).load(R.drawable.slice3).into(pizza);
        }else if (Ans==4){
            Glide.with(cn).load(R.drawable.slice4).into(pizza);
        }else if (Ans==5){
            Glide.with(cn).load(R.drawable.slice5).into(pizza);
        }else if (Ans==6){
            Glide.with(cn).load(R.drawable.slice6).into(pizza);
        }
    }

    private void startCountDownTimer() {
        setProgressBarValues();
        SetImage();
        countDownTimer = new CountDownTimerPausable(8000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                if (millisUntilFinished <= 5000) {
                    progresstext.setText(hmsTimeFormatter(millisUntilFinished));
                    progressBarCircle.setProgress((int) (millisUntilFinished / 1000));
                } else {
                    progresstext.setText(hmsTimeFormatter(5000));
                    progressBarCircle.setProgress((int) (5000 / 1000));
                }
            }

            @Override
            public void onFinish() {
                progresstext.setText(hmsTimeFormatter(00));
                progressBarCircle.setProgress(0);
                Counter++;

                CancelTheTimer();
                if (Counter <= 5) {
                    startCountDownTimer();
                }
            }
        };
        countDownTimer.start();
    }


    private void setProgressBarValues() {
        progressBarCircle.setMax((int) 5000 / 1000);
        progressBarCircle.setProgress((int) 5000 / 1000);
    }

    private String hmsTimeFormatter(long milliSeconds) {
        String hms = String.format("%02d",
                TimeUnit.MILLISECONDS.toSeconds(milliSeconds) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(milliSeconds)));
        return hms;
    }

    public void CancelTheTimer() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
            countDownTimer = null;
        }

        if (Counter>=5){
            btn_true.setOnClickListener(null);
            btn_false.setOnClickListener(null);
        }
    }


    @Override protected void onDestroy() {
        CancelTheTimer();
        super.onDestroy();
    }
    @Override public void onStart() {
        super.onStart();
    }
    @Override public void onStop() {
        super.onStop();
    }
    @Override public void onBackPressed() {
        CancelTheTimer();
        super.onBackPressed();

    }
    @Override protected void onPause() {
        super.onPause();
        countDownTimer.pause();
    }
    @Override protected void onResume() {
        super.onResume();
        if (countDownTimer.isPaused){
            Log.d("CGAME", "isPaused: "+countDownTimer.isPaused);
            countDownTimer.start();
        }
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btn_true:
                if (gameans[Counter]==Boolean.TRUE){
                    Ans++;
                    SetPizza();
                    Toast.makeText(cn, "Correct", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(cn, "False", Toast.LENGTH_SHORT).show();
                }

                CancelTheTimer();
                if (Counter<5){
                    Counter++;
                    startCountDownTimer();
                }
                Log.d("CGAME", "True: ");
                break;


            case R.id.btn_false:
                if (gameans[Counter]==Boolean.FALSE){
                    Ans++;
                    SetPizza();
                    Toast.makeText(cn, "Correct", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(cn, "False", Toast.LENGTH_SHORT).show();
                }
                Log.d("CGAME", "False: ");
                CancelTheTimer();
                if (Counter<5){
                    Counter++;
                    startCountDownTimer();
                }

                break;
        }
    }


    public abstract class CountDownTimerPausable {
        long millisInFuture = 0;
        long countDownInterval = 0;
        long millisRemaining =  0;

        CountDownTimer countDownTimer = null;

        boolean isPaused = true;

        public CountDownTimerPausable(long millisInFuture, long countDownInterval) {
            this.millisInFuture = millisInFuture;
            this.countDownInterval = countDownInterval;
            this.millisRemaining = this.millisInFuture;
        }
        private void createCountDownTimer(){
            countDownTimer = new CountDownTimer(millisRemaining,countDownInterval) {

                @Override
                public void onTick(long millisUntilFinished) {
                    millisRemaining = millisUntilFinished;
                    CountDownTimerPausable.this.onTick(millisUntilFinished);

                }

                @Override
                public void onFinish() {
                    CountDownTimerPausable.this.onFinish();

                }
            };
        }
        /**
         * Callback fired on regular interval.
         *
         * @param millisUntilFinished The amount of time until finished.
         */
        public abstract void onTick(long millisUntilFinished);
        /**
         * Callback fired when the time is up.
         */
        public abstract void onFinish();
        /**
         * Cancel the countdown.
         */
        public final void cancel(){
            if(countDownTimer!=null){
                countDownTimer.cancel();
            }
            this.millisRemaining = 0;
        }
        /**
         * Start or Resume the countdown.
         * @return CountDownTimerPausable current instance
         */
        public synchronized final CountDownTimerPausable start(){
            if(isPaused){
                createCountDownTimer();
                countDownTimer.start();
                isPaused = false;
            }
            return this;
        }
        /**
         * Pauses the CountDownTimerPausable, so it could be resumed(start)
         * later from the same point where it was paused.
         */
        public void pause()throws IllegalStateException{
            if(!isPaused){
                countDownTimer.cancel();
            } else{
                throw new IllegalStateException("CountDownTimerPausable is already in pause state, start counter before pausing it.");
            }
            isPaused = true;
        }
        public boolean isPaused() {
            return isPaused;
        }
    }
    
}

