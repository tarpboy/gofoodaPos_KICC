package app.payfun.com.gofoodapos.Login;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.HashMap;

import app.payfun.com.gofoodapos.Popup.OnEventOkListener;
import app.payfun.com.gofoodapos.Popup.QuestionPopup;
import app.payfun.com.gofoodapos.R;


public class ActivityLogin extends AppCompatActivity implements View.OnClickListener{



    private AnimatorSet animatorSet;
    private ImageView main_thumb;
    private LinearLayout login_ll;

    public final static int FRAGMENT_REGI = 0;
    public final static int FRAGMENT_LOGIN = 1;
    TextView tv_register, tv_login;


    public static String token = "";

    protected PowerManager.WakeLock mWakeLock;

    @Override
    public void onDestroy() {
        this.mWakeLock.release();
        if(animatorSet != null) {

            animatorSet.end();
            animatorSet.cancel();

        }


        super.onDestroy();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);

        final PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        this.mWakeLock = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "My Tag");
        this.mWakeLock.acquire();



        FirebaseMessaging.getInstance().subscribeToTopic("notice");
        token = FirebaseInstanceId.getInstance().getToken();
        while ("".equals(token))
        {
            token = FirebaseInstanceId.getInstance().getToken();
            Log.e("Jonathan", "token?? ");
        }
        Log.e("Joanthan", "token 1 : " + token);


//        TestPopup popup = new TestPopup(this, token, new OnEventOkListener() {
//            @Override
//            public void onOk() {
//
//            }
//
//            @Override
//            public void onOk(String addMenu) {
//
//            }
//
//            @Override
//            public void onOk(HashMap<String, String> addOrigin) {
//
//            }
//        });
//
//        popup.setCanceledOnTouchOutside(false);
//        popup.show();



        login_ll = (LinearLayout) findViewById(R.id.login_ll);
        main_thumb = (ImageView)findViewById(R.id.login_thimb);



        playIntroAni();


        fragmentReplace(FRAGMENT_LOGIN);



    }


    @Override
    public void onClick(View view) {

    }




    private Fragment getFragment(int idx) {
        Fragment newFragment = null;

        switch (idx) {
            case FRAGMENT_REGI:
//                newFragment = new FragmentRegi();
                break;

            case FRAGMENT_LOGIN:
                newFragment = new FragmentLogin();
                break;


            default:
                break;
        }

        return newFragment;
    }



    public HashMap<String, String> getData(){

        HashMap<String, String> LoginData = new HashMap<>();

        LoginData.put("TYPE",  "");
//        LoginData.put("EMAIL", loginEmail);
//        LoginData.put("BIRTH", loginBirthday);
//        LoginData.put("ID", loginId);
//        LoginData.put("GENDER", loginGender);
//        LoginData.put("NAME", loginName);
//        LoginData.put("AGE", loginAge);
//        LoginData.put("TOKEN", loginToken);



        return LoginData;
    }


    public void fragmentReplace(int reqNewFragmentIndex) {

        Fragment newFragment = null;


        newFragment = getFragment(reqNewFragmentIndex);


        final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

//        transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
//        transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);


        transaction.replace(R.id.ll_fragment, newFragment, String.valueOf(reqNewFragmentIndex)).addToBackStack("tag");

        transaction.commit();

    }



    /**
     * description : 배경사진 좌우이동 애니메이션 play
     */
    public void playIntroAni()  {


//        final ImageView login_thimb = (ImageView) findViewById(R.id.login_thimb);
//        final ImageView login_thimb1 = (ImageView) findViewById(R.id.login_thimb1);
//
//
//        final ValueAnimator animator = ValueAnimator.ofFloat(0.0f, 1.0f);
//        animator.setRepeatCount(ValueAnimator.INFINITE);
//        animator.setInterpolator(new LinearInterpolator());
//        animator.setDuration(10000L);
//        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation) {
//                final float progress = (float) animation.getAnimatedValue();
//                final float width = login_thimb.getWidth();
//                final float translationX = width * progress;
//                login_thimb.setTranslationX(translationX);
//                login_thimb1.setTranslationX(translationX - width);
//            }
//        });
//        animator.start();


        ValueAnimator scaleXAni = ObjectAnimator.ofFloat(login_ll, "translationX", new float[]{0,-25000});

        scaleXAni.setDuration(500000);
        scaleXAni.setRepeatCount(ValueAnimator.INFINITE);
        scaleXAni.setRepeatMode(ValueAnimator.REVERSE);

        animatorSet = new AnimatorSet();
        animatorSet.play(scaleXAni);

        animatorSet.start();

    }


    public void showQuestionPopup( String body, String data )
    {
        QuestionPopup popup = new QuestionPopup(this, body, data, new OnEventOkListener() {

            @Override
            public void onOk() {
                moveTaskToBack(true);
                finish();
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                android.os.Process.killProcess(android.os.Process.myPid());
            }

            @Override
            public void onOk(String addMenu) {
                moveTaskToBack(true);
                finish();
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                android.os.Process.killProcess(android.os.Process.myPid());

            }

            @Override
            public void onOk(HashMap<String, String> addOrigin) {
                moveTaskToBack(true);
                finish();
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                android.os.Process.killProcess(android.os.Process.myPid());

            }
        });
        popup.setCanceledOnTouchOutside(false);
        popup.show();
    }


    @Override
    public void onBackPressed() {

        Log.e("Jonathan","onBackPressed : " + getFragmentManager().getBackStackEntryCount());

        if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
//            this.finish();
            showQuestionPopup("종료하시겠습니까?", "");
        } else {
            getSupportFragmentManager().popBackStack();

        }
    }



}
