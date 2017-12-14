package com.tientt.trieuphumobile.version2.customviews;

import android.app.Dialog;
import android.content.Context;
import android.media.MediaPlayer;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.tientt.trieuphumobile.R;
import com.tientt.trieuphumobile.version2.SoundManager;

/**
 * Created by tiennd on 27/07/2016.
 */
public class ExpertOpinionDialog extends Dialog implements View.OnClickListener {
    private final MediaPlayer.OnCompletionListener completeSound;
    private TextView tvBacSi, tvGiaoVien, tvKiSu, tvPhongVien;
    private String type = "";

    public ExpertOpinionDialog(Context context, MediaPlayer.OnCompletionListener completionListener) {
        super(context);
        this.completeSound = completionListener;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.layout_dialog_ykienchuyengia);
        initViews();
        setCanceledOnTouchOutside(false);
        setCancelable(false);


    }

    private void initViews() {
        tvBacSi = (TextView) findViewById(R.id.tv_doctor);
        tvKiSu = (TextView) findViewById(R.id.tv_engineer);
        tvGiaoVien = (TextView) findViewById(R.id.tv_teacher);
        tvPhongVien = (TextView) findViewById(R.id.tv_reporter);

        tvBacSi.setOnClickListener(this);
        tvKiSu.setOnClickListener(this);
        tvGiaoVien.setOnClickListener(this);
        tvPhongVien.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        SoundManager.getInstance(getOwnerActivity()).playSoundBG2(R.raw.touch_sound);
        type = "";
        switch (v.getId()) {
            case R.id.tv_doctor:
                type = "doctor";
                break;
            case R.id.tv_engineer:
                type = "engineer";
                break;
            case R.id.tv_teacher:
                type = "teacher";
                break;
            case R.id.tv_reporter:
                type = "reporter";
                break;
        }


        ExpertOpinionDialogAnswer answer = new ExpertOpinionDialogAnswer(getOwnerActivity(), type, completeSound);
        answer.setOwnerActivity(getOwnerActivity());
        answer.show();
        dismiss();


    }
}
