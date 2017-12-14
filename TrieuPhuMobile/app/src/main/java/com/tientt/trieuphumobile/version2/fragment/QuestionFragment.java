package com.tientt.trieuphumobile.version2.fragment;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import com.tientt.trieuphumobile.Question;
import com.tientt.trieuphumobile.R;
import com.tientt.trieuphumobile.version2.SoundManager;
import com.tientt.trieuphumobile.version2.TaskCountTimeRemaining;
import com.tientt.trieuphumobile.version2.customviews.ConfirmDialog;
import com.tientt.trieuphumobile.version2.customviews.DonutProgress;
import com.tientt.trieuphumobile.version2.customviews.ExpertOpinionDialog;
import com.tientt.trieuphumobile.version2.customviews.YKienKhanGiaDialog;
import com.tientt.trieuphumobile.version2.listener.ChangeQuestionListener;
import com.tientt.trieuphumobile.version2.listener.OutTimeListener;
import com.tientt.trieuphumobile.version2.listener.ScoreFragmentListener;
import com.tientt.trieuphumobile.version2.listener.ShowResultFragmentListener;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by nguyentien on 10/9/17.
 */

public class QuestionFragment extends BaseFragment {

    public static final int CAN_ANSWER = 0;
    public static final int CAN_NOT_ANSWER = 99;
    private static final String TAG = "QuestionFragment";
    @BindView(R.id.tv_case_a)
    TextView caseA;
    @BindView(R.id.tv_case_b)
    TextView caseB;
    @BindView(R.id.tv_case_c)
    TextView caseC;
    @BindView(R.id.tv_case_d)
    TextView caseD;
    @BindView(R.id.tv_question_number)
    TextView numberQuestion;
    @BindView(R.id.tv_question_content)
    TextView contentQuestion;

    @BindView(R.id.btn_help_stop)
    Button btHelpStop;
    @BindView(R.id.btn_help_call)
    Button btHelpCall;
    @BindView(R.id.btn_help_audience)
    Button btHelpAudience;
    @BindView(R.id.btn_help_change_question)
    Button btHelpChangeQuestion;
    @BindView(R.id.btn_help_5050)
    Button btHelp5050;

    @BindView(R.id.donut_progress)
    DonutProgress countTime;


    private ScoreFragmentListener scoreFragmentListener;
    private ShowResultFragmentListener resultFragmentListener;
    private ChangeQuestionListener changeQuestionListener;
    private int indexQuestion = -1;
    private Question question;
    private boolean checkRunFirstTime = true;
    private int answered = 0;
    private boolean canHelp5050 = true;
    private boolean canHelpAudience = true;
    private boolean canHelpStop = true;
    private boolean canHelpChangeQuestion = true;
    private boolean canHelpCall = true;
    private ConfirmDialog confirmDialog;
    private TaskCountTimeRemaining taskCountTime;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.fragment_simple_play_main_question, container, false);
        ButterKnife.bind(this, view);

        Log.d(TAG, "onCreateView....");
        Log.d(TAG, "Index Question = " + indexQuestion);


        // run first time
        if (checkRunFirstTime) {
            SoundManager.getInstance(getActivity()).playSound(R.raw.gofind, new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    scoreFragmentListener.onLoadScoreFragment(indexQuestion++);
                    checkRunFirstTime = false;
                }
            });


        }

        checkCanHelp();


        if (question != null) {
            numberQuestion.setText("Câu " + (indexQuestion + 1));
            contentQuestion.setText(question.getQuestion());
            caseA.setText("A. " + question.getCaseA());
            caseB.setText("B. " + question.getCaseB());
            caseC.setText("C. " + question.getCaseC());
            caseD.setText("D. " + question.getCaseD());

            taskCountTime = new TaskCountTimeRemaining(31, countTime, new OutTimeListener() {
                @Override
                public void onOutTime(boolean b) {
                    SoundManager.getInstance(getActivity()).playSound(R.raw.out_of_time, new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mediaPlayer) {
                            answered = CAN_NOT_ANSWER;
                            taskCountTime.setRun(false);
                            taskCountTime.setStop(true);
                            helpStop(0);
                        }
                    });
                }
            });
            taskCountTime.execute();

        }

        return view;
    }


    private void checkCanHelp() {
        if (!canHelp5050) {
            btHelp5050.setBackgroundResource(R.drawable.atp__activity_player_button_image_help_5050_x);
            btHelp5050.setEnabled(false);
        }
        if (!canHelpAudience) {
            btHelpAudience.setBackgroundResource(R.drawable.atp__activity_player_button_image_help_audience_x);
            btHelpAudience.setEnabled(false);
        }
        if (!canHelpCall) {
            btHelpCall.setBackgroundResource(R.drawable.atp__activity_player_button_image_help_call_x);
            btHelpCall.setEnabled(false);
        }
        if (!canHelpChangeQuestion) {
            btHelpChangeQuestion.setBackgroundResource(R.drawable.atp__activity_player_button_image_help_change_question_x);
            btHelpChangeQuestion.setEnabled(false);
        }
    }


    @OnClick({R.id.btn_help_5050, R.id.btn_help_audience, R.id.btn_help_call, R.id.btn_help_stop, R.id.btn_help_change_question})
    public void help(final View view) {
        SoundManager.getInstance(getActivity()).playSoundBG2(R.raw.touch_sound);

        Log.d(TAG, "Answer = " + answered);
        if (answered > 0 || question == null) {
            return;
        }


        confirmDialog = new ConfirmDialog(getActivity(), null);
        confirmDialog.setVisibilityCancel(View.VISIBLE);
        confirmDialog.setTextButton("Bỏ qua", "Đồng ý");


        switch (view.getId()) {
            case R.id.btn_help_5050:

                confirmDialog.setContent("Bạn có chắc chắn sử dụng sự trợ giúp \"50/50\" không?");
                confirmDialog.setOnClickReady(new View.OnClickListener() {
                    @Override
                    public void onClick(View view1) {
                        answered = CAN_NOT_ANSWER;
                        taskCountTime.setRun(false);
                        confirmDialog.dismiss();
                        view.setBackgroundResource(R.drawable.atp__activity_player_button_image_help_5050_x);
                        view.setEnabled(false);
                        canHelp5050 = false;

                        SoundManager.getInstance(getActivity()).playSound(R.raw.sound5050_2, new MediaPlayer.OnCompletionListener() {
                            @Override
                            public void onCompletion(MediaPlayer mediaPlayer) {
                                help5050(question.getTrueCase());
                                answered = CAN_ANSWER;
                                taskCountTime.setRun(true);
                            }
                        });
                    }
                });
                confirmDialog.show();


                break;
            case R.id.btn_help_audience:
                confirmDialog.setContent("Bạn có chắc chắn sử dụng sự trợ giúp \"Hỏi ý kiến của khán giả\" không?");
                confirmDialog.setOnClickReady(new View.OnClickListener() {
                    @Override
                    public void onClick(View view1) {
                        answered = CAN_NOT_ANSWER;
                        taskCountTime.setRun(false);

                        confirmDialog.dismiss();
                        view.setBackgroundResource(R.drawable.atp__activity_player_button_image_help_audience_x);
                        view.setEnabled(false);
                        canHelpAudience = false;

                        SoundManager.getInstance(getActivity()).playSound(R.raw.khan_gia, new MediaPlayer.OnCompletionListener() {
                            @Override
                            public void onCompletion(MediaPlayer mediaPlayer) {
                                YKienKhanGiaDialog khanGiaDialog = new YKienKhanGiaDialog(getActivity(), question.getTrueCase(), new MediaPlayer.OnCompletionListener() {
                                    @Override
                                    public void onCompletion(MediaPlayer mediaPlayer) {
                                        answered = CAN_ANSWER;
                                        taskCountTime.setRun(true);
                                    }
                                });
                                khanGiaDialog.setOwnerActivity(getActivity());
                                khanGiaDialog.show();

                            }
                        });
                    }
                });
                confirmDialog.show();


                break;
            case R.id.btn_help_call:

                confirmDialog.setContent("Bạn có chắc chắn sử dụng sự trợ giúp \"Gọi điện thoại cho người thân\" không?");
                confirmDialog.setOnClickReady(new View.OnClickListener() {
                    @Override
                    public void onClick(View view1) {
                        answered = CAN_NOT_ANSWER;
                        taskCountTime.setRun(false);

                        confirmDialog.dismiss();
                        view.setBackgroundResource(R.drawable.atp__activity_player_button_image_help_call_x);
                        view.setEnabled(false);
                        canHelpCall = false;
                        SoundManager.getInstance(getActivity()).playSound(R.raw.help_call, new MediaPlayer.OnCompletionListener() {
                            @Override
                            public void onCompletion(MediaPlayer mediaPlayer) {
                                ExpertOpinionDialog dialog = new ExpertOpinionDialog(getActivity(), new MediaPlayer.OnCompletionListener() {
                                    @Override
                                    public void onCompletion(MediaPlayer mediaPlayer) {
                                        answered = CAN_ANSWER;
                                        taskCountTime.setRun(true);
                                    }
                                });
                                dialog.setOwnerActivity(getActivity());
                                dialog.show();
                            }
                        });
                    }
                });
                confirmDialog.show();


                break;

            case R.id.btn_help_change_question:
                confirmDialog.setContent("Bạn có chắc chắn sử dụng sự trợ giúp \"Đổi câu hỏi\" không?");
                confirmDialog.setOnClickReady(new View.OnClickListener() {
                    @Override
                    public void onClick(View view1) {

                        confirmDialog.dismiss();
                        view.setBackgroundResource(R.drawable.atp__activity_player_button_image_help_change_question_x);
                        view.setEnabled(false);
                        canHelpChangeQuestion = false;
                        changeQuestionListener.onChangeQuestion(indexQuestion);
                    }
                });
                confirmDialog.show();
                break;


            case R.id.btn_help_stop:
                confirmDialog.setContent("Bạn có chắc chắn Dừng cuộc chơi tại đây không?");
                confirmDialog.setOnClickReady(new View.OnClickListener() {
                    @Override
                    public void onClick(View view1) {
                        answered = CAN_NOT_ANSWER;
                        taskCountTime.setRun(false);
                        btHelpStop.setEnabled(false);
                        answered = question.getTrueCase();
                        confirmDialog.dismiss();

                        helpStop(2000);

                    }
                });
                confirmDialog.show();
                break;

        }
    }

    private void helpStop(int duraton) {
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                View viewTrue = getViewAnswerTrue();
                viewTrue.setBackgroundResource(R.drawable.atp__activity_player_layout_play_answer_background_true);
                viewTrue.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.button_alpha));

                playSoundLoose(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        resultFragmentListener.showResult(indexQuestion, false);
                    }
                });
            }
        }, duraton);
    }

    private void help5050(int trueCase) {
        Random rd = new Random();
        int a = 1 + rd.nextInt(4);
        while (a == trueCase) {
            a = 1 + rd.nextInt(4);
        }
        int b = 1 + rd.nextInt(4);
        while (b == a || b == trueCase) {
            b = 1 + rd.nextInt(4);
        }

        if (a == 1 || b == 1) caseA.setVisibility(View.INVISIBLE);
        if (a == 2 || b == 2) caseB.setVisibility(View.INVISIBLE);
        if (a == 3 || b == 3) caseC.setVisibility(View.INVISIBLE);
        if (a == 4 || b == 4) caseD.setVisibility(View.INVISIBLE);
    }


    @OnClick({R.id.tv_case_a, R.id.tv_case_b, R.id.tv_case_c, R.id.tv_case_d})
    public void answer(final View view) {
        SoundManager.getInstance(getActivity()).playSoundBG2(R.raw.touch_sound);
        if (answered > 0 || question == null) {
            return;
        }
        Log.d(TAG, question.toString());
        view.setBackgroundResource(R.drawable.atp__activity_player_layout_play_answer_background_selected);
        view.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.button_alpha));

        switch (view.getId()) {
            case R.id.tv_case_a:
                answered = 1;
                SoundManager.getInstance(getActivity()).playSound(R.raw.ans_a, null);
                break;

            case R.id.tv_case_b:
                answered = 2;
                SoundManager.getInstance(getActivity()).playSound(R.raw.ans_b, null);
                break;

            case R.id.tv_case_c:
                answered = 3;
                SoundManager.getInstance(getActivity()).playSound(R.raw.ans_c, null);
                break;

            case R.id.tv_case_d:
                answered = 4;
                SoundManager.getInstance(getActivity()).playSound(R.raw.ans_d, null);
                break;

        }
        taskCountTime.setRun(false);

        if (answered == question.getTrueCase()) {

            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    view.setBackgroundResource(R.drawable.atp__activity_player_layout_play_answer_background_true);
                    playSoundTrue(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mediaPlayer) {
                            scoreFragmentListener.onLoadScoreFragment(++indexQuestion);
                            answered = CAN_ANSWER;
                        }
                    });
                }
            }, 3000);

        } else {
            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {

                    View viewTrue = getViewAnswerTrue();
                    viewTrue.setBackgroundResource(R.drawable.atp__activity_player_layout_play_answer_background_true);
                    viewTrue.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.button_alpha));

                    view.setBackgroundResource(R.drawable.atp__activity_player_layout_play_answer_background_wrong);
                    playSoundLoose(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mediaPlayer) {
                            resultFragmentListener.showResult(indexQuestion, false);
                        }
                    });
                }
            }, 4000);


        }

    }

    private View getViewAnswerTrue() {
        if (question.getTrueCase() == 1) {
            return getView().findViewById(R.id.tv_case_a);
        }
        if (question.getTrueCase() == 2) {
            return getView().findViewById(R.id.tv_case_b);
        }
        if (question.getTrueCase() == 3) {
            return getView().findViewById(R.id.tv_case_c);
        }
        if (question.getTrueCase() == 4) {
            return getView().findViewById(R.id.tv_case_d);
        }

        return null;
    }

    private void playSoundTrue(MediaPlayer.OnCompletionListener listener) {

        switch (answered) {
            case 1:
                SoundManager.getInstance(getActivity()).playSound(R.raw.true_a, listener);
                break;

            case 2:
                SoundManager.getInstance(getActivity()).playSound(R.raw.true_b, listener);
                break;

            case 3:
                SoundManager.getInstance(getActivity()).playSound(R.raw.true_c, listener);
                break;

            case 4:
                SoundManager.getInstance(getActivity()).playSound(R.raw.true_d2, listener);
                break;
        }
    }

    private void playSoundLoose(MediaPlayer.OnCompletionListener listener) {

        switch (question.getTrueCase()) {
            case 1:
                SoundManager.getInstance(getActivity()).playSound(R.raw.lose_a, listener);
                break;

            case 2:
                SoundManager.getInstance(getActivity()).playSound(R.raw.lose_b, listener);
                break;

            case 3:
                SoundManager.getInstance(getActivity()).playSound(R.raw.lose_c, listener);
                break;

            case 4:
                SoundManager.getInstance(getActivity()).playSound(R.raw.lose_d, listener);
                break;
        }
    }

    @Override
    public void onPause() {
        Log.d(TAG, "onPause.............>>>..");
        if (taskCountTime != null) {
            taskCountTime.cancel(true);
            taskCountTime = null;
        }
        super.onPause();
    }

    public void setScoreFragmentListener(ScoreFragmentListener scoreFragmentListener) {
        this.scoreFragmentListener = scoreFragmentListener;
    }

    public void setIndexQuestion(int indexQuestion) {
        this.indexQuestion = indexQuestion;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public void setResultFragmentListener(ShowResultFragmentListener resultFragmentListener) {
        this.resultFragmentListener = resultFragmentListener;
    }

    public void setChangeQuestionListener(ChangeQuestionListener changeQuestionListener) {
        this.changeQuestionListener = changeQuestionListener;
    }
}
