package com.tientt.trieuphumobile.version2;

import android.os.AsyncTask;

import com.tientt.trieuphumobile.version2.customviews.DonutProgress;
import com.tientt.trieuphumobile.version2.listener.OutTimeListener;

/**
 * Created by nguyentien on 10/11/17.
 */

public class TaskCountTimeRemaining extends AsyncTask<Void, Integer, Boolean> {

    private int maxTime;
    private DonutProgress time;
    private boolean run = true;
    private boolean stop = false;
    private OutTimeListener outTimeListener;

    public TaskCountTimeRemaining(int maxTime, DonutProgress time, OutTimeListener outTimeListener) {
        this.maxTime = maxTime;
        this.time = time;
        this.outTimeListener = outTimeListener;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        try {
            while (maxTime > 0) {
                Thread.sleep(1000);
                if (run) {
                    maxTime--;
                    publishProgress(maxTime);
                }
                if (stop) {
                    return true;
                }
            }
            return true;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        time.setProgress(values[0]);
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        outTimeListener.onOutTime(aBoolean);
        super.onPostExecute(aBoolean);
    }

    public boolean isRun() {
        return run;
    }

    public void setRun(boolean run) {
        this.run = run;
    }

    public void setStop(boolean stop) {
        this.stop = stop;
    }

    public void setMaxTime(int maxTime) {
        this.maxTime = maxTime;
    }
}
