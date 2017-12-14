package com.tientt.trieuphumobile.version2.fragment;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import com.tientt.trieuphumobile.R;
import com.tientt.trieuphumobile.version2.SoundManager;
import com.tientt.trieuphumobile.version2.customviews.ConfirmDialog;
import com.tientt.trieuphumobile.version2.listener.ShowQuestionFragmentListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by tiennd on 19/07/2016.
 */
public class PlayerInfoFragment extends BaseFragment implements View.OnClickListener {


    private static final String TAG = "PlayerInfoFragment";
    @BindView(R.id.img_logo_bg)
    ImageView imgLogo;

    @BindView(R.id.img_load)
    ImageView imgLoad;

    @BindView(R.id.bt_skip)
    Button btSkip;

    private boolean checkPressSkip = false;
    private ConfirmDialog confirmDialog;
    private ShowQuestionFragmentListener profileListener;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {

        Log.d(TAG, "onCreateView....");
        View view = inflater.inflate(R.layout.fragment_simple_play_player_info, container, false);
        ButterKnife.bind(this, view);

        animationImageLoad();

        SoundManager.getInstance(getActivity()).playSound(R.raw.luatchoi_b, new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                SoundManager.getInstance(getActivity()).playSound(R.raw.ready_c, new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        if (!checkPressSkip) {
                            showDialogConfirm();
                        }
                    }
                });

            }
        });
        return view;
    }

    private void animationImageLoad() {
        imgLogo.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.rotate));
        imgLoad.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.rotate));
    }

    @OnClick(R.id.bt_skip)
    public void onSkip() {
        SoundManager.getInstance(getActivity()).playSoundBG2(R.raw.touch_sound);
        checkPressSkip = true;
        showDialogConfirm();
    }

    private void showDialogConfirm() {
        confirmDialog = new ConfirmDialog(getActivity(), this);
        confirmDialog.show();
    }

    @Override
    public void onClick(View view) {
        confirmDialog.dismiss();
        profileListener.showQuestionFrament(0);
        SoundManager.getInstance(getActivity()).playSoundBG2(R.raw.touch_sound);

    }

    public void setProfileListener(ShowQuestionFragmentListener profileListener) {
        this.profileListener = profileListener;
    }
}
