package com.tientt.trieuphumobile.version2.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.tientt.trieuphumobile.R;
import com.tientt.trieuphumobile.version2.SoundManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by nguyentien on 10/9/17.
 */

public class FirstActivity extends BaseActivity {

    private static final String TAG = "FirstActivity";

    @BindView(R.id.img_logo_bg)
    ImageView imgLogo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        ButterKnife.bind(this);


        SoundManager.getInstance(this).playSoundBG(R.raw.bgmusic, true);
        imgLogo.startAnimation(AnimationUtils.loadAnimation(this, R.anim.rotate));

    }


    @OnClick(R.id.bt_choi_don)
    public void singlePlay() {
        SoundManager.getInstance(this).stop();
        SoundManager.getInstance(this).playSoundBG2(R.raw.touch_sound);
        Intent intent = new Intent(FirstActivity.this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onPause() {
        Log.d(TAG, "onPause........>>.");
        SoundManager.getInstance(this).stop();
        super.onPause();
    }

    @Override
    protected void onResume() {
        SoundManager.getInstance(this).playSoundBG(R.raw.bgmusic, true);
        super.onResume();
    }
}
