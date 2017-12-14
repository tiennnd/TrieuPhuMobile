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
 * Created by tiennd on 27/07/2016.
 */
public class ExpertOpinionDialogAnswer extends Dialog implements View.OnClickListener {

    private String type;
    private TextView tvType, tvAnswer, tvClose;
    private int ans;
    private String[] a = {"A", "B", "C", "D"};

    public ExpertOpinionDialogAnswer(Context context, String type, final MediaPlayer.OnCompletionListener completionListener) {
        super(context);
        this.type = type;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.layout_dialog_ykienchuyengia_answer);
        initViews();
        setCanceledOnTouchOutside(false);
        setCancelable(false);

        SoundManager.getInstance(getOwnerActivity()).playSound(R.raw.call, new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        tvAnswer.setText("Theo tôi đáp án đúng là " + a[ans - 1]);
                        tvClose.setEnabled(true);
                        tvClose.setBackgroundResource(R.drawable.atp__btn_template_active);
                    }
                }, 3000);
                SoundManager.getInstance(getOwnerActivity()).playSound(R.raw.help_callb, completionListener);
            }
        });

    }

    private void initViews() {
        tvType = (TextView) findViewById(R.id.tv_person);
        tvClose = (TextView) findViewById(R.id.tv_close);
        tvAnswer = (TextView) findViewById(R.id.tv_content_answer);
        tvClose.setEnabled(false);

        ans = -1;

        switch (type) {
            case "doctor":
                tvType.setText("Bác sĩ");
                tvType.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.atp__activity_player_layout_help_call_01, 0, 0);
                ans = 1 + new Random().nextInt(4);
                break;
            case "engineer":
                tvType.setText("Kĩ sư");
                tvType.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.atp__activity_player_layout_help_call_03, 0, 0);
                ans = 1 + new Random().nextInt(4);
                break;
            case "teacher":
                tvType.setText("Giáo viên");
                tvType.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.atp__activity_player_layout_help_call_02, 0, 0);
                ans = 1 + new Random().nextInt(4);
                break;
            case "reporter":
                tvType.setText("Phóng viên");
                tvType.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.atp__activity_player_layout_help_call_04, 0, 0);
                ans = 1 + new Random().nextInt(4);
                break;
        }

        tvClose.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        SoundManager.getInstance(getOwnerActivity()).playSoundBG2(R.raw.touch_sound);
        dismiss();
    }
}
