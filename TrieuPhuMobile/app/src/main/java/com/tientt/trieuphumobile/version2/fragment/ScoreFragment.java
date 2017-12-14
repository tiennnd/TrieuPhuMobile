package com.tientt.trieuphumobile.version2.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tientt.trieuphumobile.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by nguyentien on 10/9/17.
 */

public class ScoreFragment extends BaseFragment {
    private static final String TAG = "ScoreFragment";

    @BindView(R.id.tv_cau1)
    TextView tvCau1;
    @BindView(R.id.tv_cau2)
    TextView tvCau2;
    @BindView(R.id.tv_cau3)
    TextView tvCau3;
    @BindView(R.id.tv_cau4)
    TextView tvCau4;
    @BindView(R.id.tv_cau5)
    TextView tvCau5;
    @BindView(R.id.tv_cau6)
    TextView tvCau6;
    @BindView(R.id.tv_cau7)
    TextView tvCau7;
    @BindView(R.id.tv_cau8)
    TextView tvCau8;
    @BindView(R.id.tv_cau9)
    TextView tvCau9;
    @BindView(R.id.tv_cau10)
    TextView tvCau10;
    @BindView(R.id.tv_cau11)
    TextView tvCau11;
    @BindView(R.id.tv_cau12)
    TextView tvCau12;
    @BindView(R.id.tv_cau13)
    TextView tvCau13;
    @BindView(R.id.tv_cau14)
    TextView tvCau14;
    @BindView(R.id.tv_cau15)
    TextView tvCau15;


    private ArrayList<TextView> arrTextView;
    private int indexQuestion = -1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView....");
        View view = inflater.inflate(R.layout.fragment_score, container, false);

        ButterKnife.bind(this, view);

        initTextView();
        return view;
    }

    private void initTextView() {
        arrTextView = new ArrayList<>();
        arrTextView.add(tvCau1);
        arrTextView.add(tvCau2);
        arrTextView.add(tvCau3);
        arrTextView.add(tvCau4);
        arrTextView.add(tvCau5);
        arrTextView.add(tvCau6);
        arrTextView.add(tvCau7);
        arrTextView.add(tvCau8);
        arrTextView.add(tvCau9);
        arrTextView.add(tvCau10);
        arrTextView.add(tvCau11);
        arrTextView.add(tvCau12);
        arrTextView.add(tvCau13);
        arrTextView.add(tvCau14);
        arrTextView.add(tvCau15);

        for (int i = 0; i < indexQuestion; i++) {
            arrTextView.get(i).setTextColor(getResources().getColor(R.color.blur));
        }

        switch (indexQuestion + 1) {
            case 1:
                tvCau1.setBackgroundResource(R.drawable.atp__activity_player_image_money_curent);
                break;
            case 2:
                tvCau2.setBackgroundResource(R.drawable.atp__activity_player_image_money_curent);
                break;
            case 3:
                tvCau3.setBackgroundResource(R.drawable.atp__activity_player_image_money_curent);
                break;
            case 4:
                tvCau4.setBackgroundResource(R.drawable.atp__activity_player_image_money_curent);
                break;
            case 5:
                tvCau5.setBackgroundResource(R.drawable.atp__activity_player_image_money_curent);
                break;
            case 6:
                tvCau6.setBackgroundResource(R.drawable.atp__activity_player_image_money_curent);
                break;
            case 7:
                tvCau7.setBackgroundResource(R.drawable.atp__activity_player_image_money_curent);
                break;
            case 8:
                tvCau8.setBackgroundResource(R.drawable.atp__activity_player_image_money_curent);
                break;
            case 9:
                tvCau9.setBackgroundResource(R.drawable.atp__activity_player_image_money_curent);
                break;
            case 10:
                tvCau10.setBackgroundResource(R.drawable.atp__activity_player_image_money_curent);
                break;
            case 11:
                tvCau11.setBackgroundResource(R.drawable.atp__activity_player_image_money_curent);
                break;
            case 12:
                tvCau12.setBackgroundResource(R.drawable.atp__activity_player_image_money_curent);
                break;
            case 13:
                tvCau13.setBackgroundResource(R.drawable.atp__activity_player_image_money_curent);
                break;
            case 14:
                tvCau14.setBackgroundResource(R.drawable.atp__activity_player_image_money_curent);
                break;
            case 15:
                tvCau15.setBackgroundResource(R.drawable.atp__activity_player_image_money_curent);
                break;

        }
    }

    public void setIndexQuestion(int indexQuestion) {
        this.indexQuestion = indexQuestion;

    }
}
