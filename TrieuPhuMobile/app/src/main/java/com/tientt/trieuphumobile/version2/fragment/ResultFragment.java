package com.tientt.trieuphumobile.version2.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.tientt.trieuphumobile.R;
import com.tientt.trieuphumobile.version2.SoundManager;
import com.tientt.trieuphumobile.version2.Utils;
import com.tientt.trieuphumobile.version2.activity.AdsActivity;
import com.tientt.trieuphumobile.version2.listener.ViewAdsListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by tiennd on 27/07/2016.
 */
public class ResultFragment extends BaseFragment {


    @BindView(R.id.tv_life)
    TextView tvLife;

    @BindView(R.id.tv_winner)
    TextView tvWinner;

    @BindView(R.id.tv_number)
    TextView tvNumber;

    @BindView(R.id.tv_score_result)
    TextView tvScoreResult;

    private int numberCurentQuestion;
    private int values;
    private int money;
    private boolean stopPositive;
    private ViewAdsListener adsListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_result, container, false);
        ButterKnife.bind(this, view);

        SoundManager.getInstance(getActivity()).stop();
        SoundManager.getInstance(getActivity()).playSound(R.raw.best_player, new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                SoundManager.getInstance(getActivity()).playSound(R.raw.lose2, null);
            }
        });

        initViews();
        return view;
    }

    @OnClick(R.id.bt_back)
    public void pressBack(View view) {
        SoundManager.getInstance(getActivity()).playSoundBG2(R.raw.touch_sound);
        getActivity().finish();
        SoundManager.getInstance(getActivity()).stop();
    }

    @Override
    public void onResume() {
        SharedPreferences preferences = getActivity().getSharedPreferences(FILE_SAVE, Context.MODE_PRIVATE);
        int life = preferences.getInt(COUNT_TIME_PLAY, -1);
        if (life != -1){
            tvLife.setText("" + life);
        }
        super.onResume();
    }

    private void initViews() {

        tvNumber.setText("CẢM ƠN BẠN ĐÃ THAM GIA CHƯƠNG TRÌNH BẠN ĐÃ DỪNG CUỘC CHƠI TẠI CÂU HỎI SỐ " + numberCurentQuestion);
        if (numberCurentQuestion == 15) {
            tvWinner.setText("CHÚC MỪNG NGƯỜI THẮNG CUỘC");
        }
        if (numberCurentQuestion < 5) {
            tvScoreResult.setText("0 VNĐ");
        } else if (numberCurentQuestion < 10) {
            tvScoreResult.setText("2.000.000 VNĐ");
        } else if (numberCurentQuestion < 15) {
            tvScoreResult.setText("22.000.000 VNĐ");
        }
        if (stopPositive) {
            tvScoreResult.setText(Utils.formatMoney(money + "") + " VNĐ");
        }

        /*set count play*/
        SharedPreferences preferences = getActivity().getSharedPreferences(FILE_SAVE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        values = preferences.getInt(COUNT_TIME_PLAY, -1);
        if (values != -1) {
            tvLife.setText("" + values);
            editor.putInt(COUNT_TIME_PLAY, values - 1);
        } else {
            editor.putInt(COUNT_TIME_PLAY, 10);
        }
        editor.commit();
        if (values == 0) {
            getActivity().finish();
        }
    }

    @OnClick(R.id.bt_ads)
    public void viewAds(){
        adsListener.ads();
    }

    public void setNumberCurentQuestion(int numberCurentQuestion, boolean stopPositive) {
        this.numberCurentQuestion = numberCurentQuestion;
        this.stopPositive = stopPositive;

        switch (numberCurentQuestion + 1) {
            case 1:
                this.money = 200000;
                break;
            case 2:
                this.money = 400000;
                break;
            case 3:
                this.money = 600000;
                break;
            case 4:
                this.money = 1000000;
                break;
            case 5:
                this.money = 2000000;
                break;
            case 6:
                this.money = 3000000;
                break;
            case 7:
                this.money = 6000000;
                break;
            case 8:
                this.money = 10000000;
                break;
            case 9:
                this.money = 14000000;
                break;
            case 10:
                this.money = 22000000;
                break;
            case 11:
                this.money = 30000000;
                break;
            case 12:
                this.money = 40000000;
                break;
            case 13:
                this.money = 60000000;
                break;
            case 14:
                this.money = 85000000;
                break;
            case 15:
                this.money = 150000000;
                break;

        }

    }

    public void setAdsListener(ViewAdsListener adsListener) {
        this.adsListener = adsListener;
    }
}
