package com.aje.logic16.app;

import com.aje.logic16.app.GameLogic.Conjunction;
import com.aje.logic16.app.GameLogic.GameLogic;
import com.aje.logic16.app.util.SystemUiHider;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 *
 * @see SystemUiHider
 */
public class GameScreen extends Activity {
    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */
    private static final boolean AUTO_HIDE = false;

    /**
     * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

    /**
     * If set, will toggle the system UI visibility upon interaction. Otherwise,
     * will show the system UI visibility upon interaction.
     */
    private static final boolean TOGGLE_ON_CLICK = false;

    /**
     * The flags to pass to {@link SystemUiHider#getInstance}.
     */
    private static final int HIDER_FLAGS = SystemUiHider.FLAG_HIDE_NAVIGATION;

    /**
     * The instance of the {@link SystemUiHider} for this activity.
     */
    private SystemUiHider mSystemUiHider;

    private DisplayMetrics mMetrics = new DisplayMetrics();

    private GameLogic mGameLogic = null;

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindowManager().getDefaultDisplay().getMetrics(mMetrics);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        mGameLogic = new GameLogic(this, mMetrics);

        //final LayoutInflater inflater = (LayoutInflater)this.getSystemService
        //       (Context.LAYOUT_INFLATER_SERVICE);
        //final ViewGroup gameScreen = (ViewGroup) inflater.inflate(R.layout.activity_game_screen,null);

        createLayout();

        mGameLogic.loadGame();
    }

    private void createLayout()
    {
        LinearLayout.LayoutParams LLVParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        final LinearLayout llVertical = new LinearLayout(this);
        llVertical.setOrientation(LinearLayout.VERTICAL);
        llVertical.setBackgroundColor(getResources().getColor(R.color.backgroundBlue));
        llVertical.setGravity(Gravity.CENTER);
        //llVertical.setWeightSum(6f);
        llVertical.setLayoutParams(LLVParams);

        llVertical.addView(mGameLogic.getHeaderRow());

        // Alle Konjunktionen holen und zur View hinzufügen
        Conjunction[] conjunctions = mGameLogic.getConjunctions();
        for (Conjunction conjunction : conjunctions) {
            llVertical.addView(conjunction);
        }

        llVertical.addView(mGameLogic.getButtonRow(this, mMetrics));
        addContentView(llVertical,LLVParams);
    }


/*
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindowManager().getDefaultDisplay().getMetrics(mMetrics);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        final LayoutInflater inflater = (LayoutInflater)this.getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
        final ViewGroup gameScreen = (ViewGroup) inflater.inflate(R.layout.activity_game_screen,null);

        LinearLayout.LayoutParams LLVParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        LinearLayout.LayoutParams LLHParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        //LLVParams.topMargin = 5;
        //LLVParams.rightMargin = 5;
        final LinearLayout llVertical = new LinearLayout(this);
        llVertical.setOrientation(LinearLayout.VERTICAL);
        llVertical.setBackgroundColor(getResources().getColor(R.color.backgroundBlue));
        llVertical.setGravity(Gravity.CENTER);
        //llVertical.setWeightSum(6f);
        llVertical.setLayoutParams(LLVParams);

        int imageWidth = mMetrics.widthPixels / 14 ; // 8 images + 1 result image + some place (5)
        int imageHeight = mMetrics.heightPixels / 22 ; // 16 imageRows, + change Button (1) + some place (5)
        ContextThemeWrapper rectangleImageViewContext = new ContextThemeWrapper(this, R.style.rectangleImageView);

        LinearLayout.LayoutParams imageLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        imageLayoutParams.setMargins((int)Math.round(mMetrics.density*2),(int)Math.round(mMetrics.density*2),(int)Math.round(mMetrics.density*2),(int)Math.round(mMetrics.density*2));

        //Example for a CountDownTimer
        CountDownTimer mCountDownTimer;
        int total=100;
        final int thirtySeconds= 1 * 30 * 1000;


        ViewGroup CountDownElement = (ViewGroup) getLayoutInflater().inflate(R.layout.count_down, null);
        final ProgressBar pb = (ProgressBar) CountDownElement.findViewById(R.id.progressBar);
        final TextView pbText = (TextView) CountDownElement.findViewById(R.id.seconds);
        pbText.setText(Integer.toString(Math.round((thirtySeconds / 1000))));

        pb.setProgress(total);
        pb.setMax(total);
        Animation an = new RotateAnimation(0.0f, 90.0f, 250f, 273f);
        an.setFillAfter(true);
        pb.startAnimation(an);
        mCountDownTimer=new CountDownTimer(thirtySeconds,1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                double un = ((double)millisUntilFinished/ (double)thirtySeconds);
                int total = (int) Math.round(un * 100);
                pb.setProgress(total);
                pbText.setText(Integer.toString(Math.round(millisUntilFinished/1000)));
            }

            @Override
            public void onFinish() {
                //Do what you want
                pb.setProgress(0);
                pbText.setText("0");
            }
        };
        LinearLayout.LayoutParams CCTParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        llVertical.addView(CountDownElement,CCTParams);
        mCountDownTimer.start();
        //Example for a CountDownTimer END

      //  api myApi = new api();
      //  highScore score = new highScore("TestEintrag",23456786);
      //  myApi.addScore(score);
      //  score.toString();

        for (int i=0; i<16 ;i++)
        {
            LinearLayout llHorizontal = new LinearLayout(this);
            llHorizontal.setOrientation(LinearLayout.HORIZONTAL);
            llHorizontal.setBackgroundColor(getResources().getColor(R.color.backgroundBlue));
            llHorizontal.setLayoutParams(LLHParams);
            llHorizontal.setGravity(Gravity.CENTER_HORIZONTAL);

            for (int j=0;j<8;j++)
            {
                TextView image = new TextView(rectangleImageViewContext);
                image.setMinimumWidth(imageWidth);
                image.setMinimumHeight(imageHeight);
                image.setLayoutParams(imageLayoutParams);
                image.setBackgroundColor(Color.GREEN);
                image.setText("+");
                image.setGravity(Gravity.CENTER);
                llHorizontal.addView(image);
            }

            TextView imageRes = new TextView(rectangleImageViewContext);
            imageRes.setMinimumHeight(imageHeight);
            imageRes.setMinimumWidth(imageWidth);
            imageRes.setLayoutParams(imageLayoutParams);
            Drawable round = getResources().getDrawable(R.drawable.rounded_corner);
            //round.setColorFilter(Color.GREEN, PorterDuff.Mode.MULTIPLY);
            //imageRes.setBackgroundColor(Color.GREEN);

            ColorFilter filter = new LightingColorFilter( Color.GREEN, Color.GREEN );
            round.setColorFilter(filter);
            //imageRes.setImageResource(getResources().getColor(R.color.green));
            //imageRes.setImageDrawable(round);
            imageRes.setBackground(round);

            llHorizontal.addView(imageRes);

            llVertical.addView(llHorizontal);
        }

        //add Buttons

        LinearLayout llHorizontal = new LinearLayout(this);
        llHorizontal.setOrientation(LinearLayout.HORIZONTAL);
        llHorizontal.setBackgroundColor(getResources().getColor(R.color.backgroundBlue));
        llHorizontal.setLayoutParams(LLHParams);
        llHorizontal.setGravity(Gravity.CENTER_HORIZONTAL);


        View.OnClickListener myClickListener = new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent;
                intent = new Intent(getBaseContext(),WonGameActivity.class);
                startActivity(intent);
            }
        };

        View.OnClickListener my2ndClickListener = new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent;
                intent = new Intent(getBaseContext(),GameOverActivity.class);
                startActivity(intent);
            }
        };

        for (int j=0;j<9;j++)
        {

            TextView image = new TextView(rectangleImageViewContext);
            image.setMinimumWidth(imageWidth);
            image.setMinimumHeight(imageHeight);
            image.setLayoutParams(imageLayoutParams);
            image.setBackgroundColor(Color.GREEN);
            image.setText("+");
            if (j<4)
            {
                image.setOnClickListener(myClickListener);
            } else
            {
                image.setOnClickListener(my2ndClickListener);
            }
            image.setGravity(Gravity.CENTER);
            if (j==8) image.setVisibility(View.INVISIBLE);
            llHorizontal.addView(image);
        }



        llVertical.addView(llHorizontal);

        addContentView(llVertical,LLVParams);



        // Set up an instance of SystemUiHider to control the system UI for
        // this activity.
        mSystemUiHider = SystemUiHider.getInstance(this, llVertical, HIDER_FLAGS);
        mSystemUiHider.setup();
        mSystemUiHider
                .setOnVisibilityChangeListener(new SystemUiHider.OnVisibilityChangeListener() {
                    // Cached values.
                    int mControlsHeight;
                    int mShortAnimTime;

                    @Override
                    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
                    public void onVisibilityChange(boolean visible) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
                            // If the ViewPropertyAnimator API is available
                            // (Honeycomb MR2 and later), use it to animate the
                            // in-layout UI controls at the bottom of the
                            // screen.
                            if (mControlsHeight == 0) {
                                mControlsHeight = llVertical.getHeight();
                            }
                            if (mShortAnimTime == 0) {
                                mShortAnimTime = getResources().getInteger(
                                        android.R.integer.config_shortAnimTime);
                            }
                            llVertical.animate()
                                    .translationY(visible ? 0 : mControlsHeight)
                                    .setDuration(mShortAnimTime);
                        } else {
                            // If the ViewPropertyAnimator APIs aren't
                            // available, simply show or hide the in-layout UI
                            // controls.
                            llVertical.setVisibility(visible ? View.VISIBLE : View.GONE);
                        }

                        if (visible && AUTO_HIDE) {
                            // Schedule a hide().
                            delayedHide(AUTO_HIDE_DELAY_MILLIS);
                        }
                    }
                });

        // Set up the user interaction to manually show or hide the system UI.
        llVertical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TOGGLE_ON_CLICK) {
                    mSystemUiHider.toggle();
                } else {
                    mSystemUiHider.show();
                }
            }
        });

        // Upon interacting with UI controls, delay any scheduled hide()
        // operations to prevent the jarring behavior of controls going away
        // while interacting with the UI.
        //findViewById(R.id.dummy_button).setOnTouchListener(mDelayHideTouchListener);
        }
    */

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
       // delayedHide(100);
    }


    /**
     * Touch listener to use for in-layout UI controls to delay hiding the
     * system UI. This is to prevent the jarring behavior of controls going away
     * while interacting with activity UI.
     */
    View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (AUTO_HIDE) {
                delayedHide(AUTO_HIDE_DELAY_MILLIS);
            }
            return false;
        }
    };

    Handler mHideHandler = new Handler();
    Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            mSystemUiHider.hide();
        }
    };




    /**
     * Schedules a call to hide() in [delay] milliseconds, canceling any
     * previously scheduled calls.
     */
    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }


}
