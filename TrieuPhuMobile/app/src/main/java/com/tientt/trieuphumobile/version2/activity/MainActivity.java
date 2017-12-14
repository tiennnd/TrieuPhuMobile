package com.tientt.trieuphumobile.version2.activity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.tientt.trieuphumobile.Question;
import com.tientt.trieuphumobile.R;
import com.tientt.trieuphumobile.control.DBManager;
import com.tientt.trieuphumobile.version2.SoundManager;
import com.tientt.trieuphumobile.version2.customviews.ConfirmDialog;
import com.tientt.trieuphumobile.version2.fragment.PlayerInfoFragment;
import com.tientt.trieuphumobile.version2.fragment.QuestionFragment;
import com.tientt.trieuphumobile.version2.fragment.ResultFragment;
import com.tientt.trieuphumobile.version2.fragment.ScoreFragment;
import com.tientt.trieuphumobile.version2.listener.ChangeQuestionListener;
import com.tientt.trieuphumobile.version2.listener.ScoreFragmentListener;
import com.tientt.trieuphumobile.version2.listener.ShowQuestionFragmentListener;
import com.tientt.trieuphumobile.version2.listener.ShowResultFragmentListener;
import com.tientt.trieuphumobile.version2.listener.ViewAdsListener;

import java.util.List;

/**
 * Created by nguyentien on 10/9/17.
 */

public class MainActivity extends BaseActivity implements ScoreFragmentListener, ShowQuestionFragmentListener, ShowResultFragmentListener, ChangeQuestionListener, ViewAdsListener {


    private static final String TAG = "MainActivity";
    private QuestionFragment questionFragment;
    private ScoreFragment scoreFragment;
    private PlayerInfoFragment playerInfoFragment;
    private ResultFragment resultFragment;
    private List<Question> arrQuestion;
    private DBManager dbManager;
    private ConfirmDialog confirmExitDialog;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_play);


        if (playerInfoFragment == null) {
            playerInfoFragment = new PlayerInfoFragment();
            playerInfoFragment.setProfileListener(this);
        }
        showFrament(playerInfoFragment);
    }

    @Override
    protected void onPause() {
        SoundManager.getInstance(this).stop();
        super.onPause();
    }


    @Override
    public void onLoadScoreFragment(final int indexQuestion) {
        if (indexQuestion == 14){
            if (resultFragment == null) {
                resultFragment = new ResultFragment();
                resultFragment.setAdsListener(this);
            }
            resultFragment.setNumberCurentQuestion(14, true);
            showFrament(resultFragment);
            return;
        }

        if (scoreFragment == null) {
            scoreFragment = new ScoreFragment();
        }
        scoreFragment.setIndexQuestion(indexQuestion);
        showFrament(scoreFragment);

        playSoundIndexQuestion(indexQuestion, new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                questionFragment.setQuestion(arrQuestion.get(indexQuestion));
                questionFragment.setIndexQuestion(indexQuestion);
                showFrament(questionFragment);
                if (indexQuestion + 1 >= 2) {
                    SoundManager.getInstance(MainActivity.this).playSound(R.raw.background_music_b, null);
                }
                Log.d(TAG, "Cau " + indexQuestion + ": " + arrQuestion.get(indexQuestion).toString());
            }
        });
    }


    private void playSoundIndexQuestion(int indexQuestion, MediaPlayer.OnCompletionListener listener) {


        switch (indexQuestion + 1) {
            case 1:
                SoundManager.getInstance(this).playSound(R.raw.ques1_b, listener);
                break;

            case 2:
                SoundManager.getInstance(this).playSound(R.raw.ques2, listener);
                break;

            case 3:
                SoundManager.getInstance(this).playSound(R.raw.ques3, listener);
                break;

            case 4:
                SoundManager.getInstance(this).playSound(R.raw.ques4, listener);
                break;

            case 5:
                SoundManager.getInstance(this).playSound(R.raw.ques5, listener);
                break;

            case 6:
                SoundManager.getInstance(this).playSound(R.raw.ques6, listener);
                break;

            case 7:
                SoundManager.getInstance(this).playSound(R.raw.ques7, listener);
                break;

            case 8:
                SoundManager.getInstance(this).playSound(R.raw.ques8, listener);
                break;

            case 9:
                SoundManager.getInstance(this).playSound(R.raw.ques9, listener);
                break;

            case 10:
                SoundManager.getInstance(this).playSound(R.raw.ques10, listener);
                break;
            case 11:
                SoundManager.getInstance(this).playSound(R.raw.ques11, listener);
                break;
            case 12:
                SoundManager.getInstance(this).playSound(R.raw.ques12, listener);
                break;
            case 13:
                SoundManager.getInstance(this).playSound(R.raw.ques13, listener);
                break;
            case 14:
                SoundManager.getInstance(this).playSound(R.raw.ques14, listener);
                break;

            case 15:
                SoundManager.getInstance(this).playSound(R.raw.ques15, listener);
                break;


        }
    }

    @Override
    public void showQuestionFrament(int indexQuestion) {
        if (questionFragment == null) {
            dbManager = new DBManager(this);
            arrQuestion = dbManager.get15Question();
            questionFragment = new QuestionFragment();
            questionFragment.setScoreFragmentListener(this);
            questionFragment.setResultFragmentListener(this);
            questionFragment.setChangeQuestionListener(this);
        }
        questionFragment.setIndexQuestion(indexQuestion);
        showFrament(questionFragment);
    }

    @Override
    public void showResult(int indexQuestion, boolean positiveStop) {
        if (resultFragment == null) {
            resultFragment = new ResultFragment();
            resultFragment.setAdsListener(this);
        }
        resultFragment.setNumberCurentQuestion(indexQuestion, positiveStop);
        showFrament(resultFragment);
    }

    @Override
    public void onChangeQuestion(final int indexQuestion) {
        if (scoreFragment == null) {
            scoreFragment = new ScoreFragment();
        }
        scoreFragment.setIndexQuestion(indexQuestion);
        showFrament(scoreFragment);

        Question question = dbManager.getQuestion(indexQuestion + 1);
        Log.d(TAG, "Tong so cau hoi = " + arrQuestion.size());
        Log.d(TAG, "Cau " + (indexQuestion + 1) + ": " + arrQuestion.get(indexQuestion).toString());

        arrQuestion.set(indexQuestion, question);

        Log.d(TAG, "Tong so cau hoi = " + arrQuestion.size());
        Log.d(TAG, "Cau " + (indexQuestion + 1) + ": " + arrQuestion.get(indexQuestion).toString());

        playSoundIndexQuestion(indexQuestion, new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                questionFragment.setQuestion(arrQuestion.get(indexQuestion));
                questionFragment.setIndexQuestion(indexQuestion);
                showFrament(questionFragment);
            }
        });
    }

    @Override
    public void onBackPressed() {
        confirmExitDialog = new ConfirmDialog(this, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmExitDialog.dismiss();
                MainActivity.this.finish();
            }
        });
        confirmExitDialog.setVisibilityCancel(View.VISIBLE);
        confirmExitDialog.setTextButton("Tiếp tục", "Thoát");
        confirmExitDialog.setContent("Bạn đang chơi dở. Bạn có muốn thoát khỏi không?");
        confirmExitDialog.show();
    }

    @Override
    public void ads() {
        admobFull(resultFragment);
    }
}
