package com.tientt.trieuphumobile.version2.customviews;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.tientt.trieuphumobile.R;
import com.tientt.trieuphumobile.version2.SoundManager;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by nguyentien on 10/9/17.
 */

public class ConfirmDialog extends Dialog {

    @BindView(R.id.btn_boqua)
    Button btnCancel;

    @BindView(R.id.btn_sansang)
    Button btnReady;

    @BindView(R.id.tv_content_notify)
    TextView tvContent;


    public ConfirmDialog(@NonNull Context context, View.OnClickListener onReady) {
        super(context);

        Activity activity = (Activity) context;
        View view = activity.getLayoutInflater().inflate(R.layout.layout_dialog, null);
//        View view = LayoutInflater.from(context).inflate(R.layout.layout_dialog, null);
        ButterKnife.bind(this, view);

        btnReady.setOnClickListener(onReady);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SoundManager.getInstance(getOwnerActivity()).playSoundBG2(R.raw.touch_sound);
                dismiss();
            }
        });


        setCancelable(false);
        setCanceledOnTouchOutside(false);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(view);
    }

    public void setOnClickReady(View.OnClickListener clickReady) {
        btnReady.setOnClickListener(clickReady);
    }

    public void setContent(String content) {
        tvContent.setText(content);
    }

    public void setTextButton(String nameButtonCancel, String nameButtonReady) {
        btnReady.setText(nameButtonReady);
        btnCancel.setText(nameButtonCancel);
    }

    public void setVisibilityCancel(int visibility) {
        btnCancel.setVisibility(visibility);
    }
}
