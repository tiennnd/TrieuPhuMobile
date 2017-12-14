package com.tientt.trieuphumobile.version2;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;

import com.tientt.trieuphumobile.R;

/**
 * Created by nguyentien on 10/10/17.
 */

public class SoundManager {
    private static final String TAG = "SoundManager";
    private static SoundManager soundManager;
    private Context mContext;
    private MediaPlayer mediaBG;
    private MediaPlayer mediaGame;
    private long currentTime;

    private SoundManager(Context context) {
        this.mContext = context;
    }

    public static SoundManager getInstance(Context context) {
        if (soundManager == null) {
            soundManager = new SoundManager(context);
        }
        return soundManager;
    }

    public void playSoundBG(int idSound, boolean isLooping) {
        if (mediaBG != null) {
            mediaBG.release();
        }
        mediaBG = MediaPlayer.create(mContext, idSound);
        mediaBG.setLooping(isLooping);
        mediaBG.start();
    }

    public void playSoundBG2(int resId) {

        if (resId == R.raw.touch_sound){
            if (currentTime == 0){
                currentTime = System.currentTimeMillis();
            } else {
                long time = System.currentTimeMillis();
                float check = (time - currentTime)/1000;
                Log.d(TAG,"CHECK = " + check);
                if (check < 2) {
                    return;
                } else {
                    currentTime = time;
                }
            }
        }

        if (mediaBG != null) {
            mediaBG = MediaPlayer.create(mContext, resId);
            mediaBG.start();
        }
    }

    public void playSound(int idSound, MediaPlayer.OnCompletionListener event) {
        if (mediaGame != null) {
            mediaGame.release();
        }

        mediaGame = MediaPlayer.create(mContext, idSound);
        mediaGame.setOnCompletionListener(event);
        mediaGame.start();
    }

    public int getLengthSound() {
        return mediaGame.getDuration();
    }

    public int getLengthSoundBG() {
        return mediaBG.getDuration();
    }

    public void stop() {
        if (mediaBG != null) {
            mediaBG.release();
        }
        if (mediaGame != null) {
            mediaGame.release();
        }
    }

}
