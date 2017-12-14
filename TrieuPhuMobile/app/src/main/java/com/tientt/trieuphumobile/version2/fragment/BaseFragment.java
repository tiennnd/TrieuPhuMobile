package com.tientt.trieuphumobile.version2.fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.tientt.trieuphumobile.R;

/**
 * Created by nguyentien on 10/9/17.
 */

public class BaseFragment extends Fragment {

    public static final String FILE_SAVE = "FILE_SAVE";
    public static final String COUNT_TIME_PLAY = "COUNT_TIME_PLAY";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }


    protected void showFrament(BaseFragment fragment) {
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.layout_trialplay, fragment)
                .commit();
    }

    public void increasingLife(int n) {
        SharedPreferences preferences = getActivity().getSharedPreferences(FILE_SAVE, Context.MODE_PRIVATE);

        int life = preferences.getInt(COUNT_TIME_PLAY, -1);
        if (life != -1) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt(COUNT_TIME_PLAY, life + n);
            editor.commit();
        } else {
            Toast.makeText(getActivity(), "Thêm lần chơi gặp lỗi !", Toast.LENGTH_SHORT).show();
        }


    }
}
