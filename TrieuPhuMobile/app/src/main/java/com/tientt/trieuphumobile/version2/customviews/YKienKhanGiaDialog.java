package com.tientt.trieuphumobile.version2.customviews;

import android.app.Dialog;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.tientt.trieuphumobile.R;
import com.tientt.trieuphumobile.version2.SoundManager;

import java.util.Random;

/**
 * Created by tiennd on 18/08/2016.
 */
public class YKienKhanGiaDialog extends Dialog implements View.OnClickListener {
    private int trueCase;
    private TextView tvVoteA, tvVoteB, tvVoteC, tvVoteD;
    private TextView tvClose;

    public YKienKhanGiaDialog(Context context, int trueCase, MediaPlayer.OnCompletionListener onCompleteSound) {
        super(context);
        this.trueCase = trueCase;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.layout_dialog_khangia);
        initViews();
        setValues();
        setCanceledOnTouchOutside(false);
        setCancelable(false);

//        SoundManager.getInstance(getOwnerActivity()).playSound(R.raw.bg_hoi_y_kien_chuyen_gia_01b, new MediaPlayer.OnCompletionListener() {
//            @Override
//            public void onCompletion(MediaPlayer mediaPlayer) {
//                tvClose.setEnabled(true);
//                tvClose.setBackgroundResource(R.drawable.atp__btn_template_active);
//            }
//        });

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                tvClose.setEnabled(true);
                tvClose.setBackgroundResource(R.drawable.atp__btn_template_active);
            }
        }, 5000);
        SoundManager.getInstance(getOwnerActivity()).playSound(R.raw.bg_hoi_y_kien_chuyen_gia_01b, onCompleteSound);


    }

    private void setValues() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Random rd = new Random();
                int percentTrue = 70 + rd.nextInt(20);
                int percent_1 = rd.nextInt(100 - percentTrue);
                int percent_2 = rd.nextInt(100 - percentTrue - percent_1);
                int percent_3 = 100 - percentTrue - percent_1 - percent_2;
                switch (trueCase) {
                    case 1:
                        tvVoteA.setText(percentTrue + "%");
                        tvVoteB.setText(percent_1 + "%");
                        tvVoteC.setText(percent_2 + "%");
                        tvVoteD.setText(percent_3 + "%");
                        break;
                    case 2:
                        tvVoteB.setText(percentTrue + "%");
                        tvVoteA.setText(percent_1 + "%");
                        tvVoteC.setText(percent_2 + "%");
                        tvVoteD.setText(percent_3 + "%");
                        break;
                    case 3:
                        tvVoteC.setText(percentTrue + "%");
                        tvVoteB.setText(percent_1 + "%");
                        tvVoteA.setText(percent_2 + "%");
                        tvVoteD.setText(percent_3 + "%");
                        break;
                    case 4:
                        tvVoteD.setText(percentTrue + "%");
                        tvVoteB.setText(percent_1 + "%");
                        tvVoteC.setText(percent_2 + "%");
                        tvVoteA.setText(percent_3 + "%");
                        break;
                    default:
                        break;
                }
            }
        }, 6000);

    }

    private void initViews() {
        tvVoteA = (TextView) findViewById(R.id.tv_vote_a);
        tvVoteB = (TextView) findViewById(R.id.tv_vote_b);
        tvVoteC = (TextView) findViewById(R.id.tv_vote_c);
        tvVoteD = (TextView) findViewById(R.id.tv_vote_d);
        tvClose = (TextView) findViewById(R.id.tv_close_dialog);
        tvClose.setOnClickListener(this);
        tvClose.setEnabled(false);
    }

    @Override
    public void onClick(View v) {
        SoundManager.getInstance(getOwnerActivity()).playSoundBG2(R.raw.touch_sound);
        dismiss();
    }
}
