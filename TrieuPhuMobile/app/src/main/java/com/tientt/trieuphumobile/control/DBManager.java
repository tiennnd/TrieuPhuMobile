package com.tientt.trieuphumobile.control;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import com.tientt.trieuphumobile.Question;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tiennd on 16/07/2016.
 */
public class DBManager {

    public static final String SQL_GET_15_QUESTION = "SELECT * FROM (SELECT * FROM QUESTION ORDER BY RANDOM()) GROUP BY LEVEL ORDER BY LEVEL LIMIT 15";
    public static final String INSERT_HIGHT_SCORE = "INSERT INTO HIGHTSCORE VALUES('T3H',200000)";
    private static final String PATH_DB = Environment.getDataDirectory().getPath() + "/data/com.tientt.trieuphumobile/databases";
    private static final String DB_NAME = "Question";
    private static final String TAG = DBManager.class.getName();
    private List<Question> arrQ = new ArrayList<>();
    private SQLiteDatabase mSqLiteDB;

    public DBManager(Context context) {
        copyDB(context);
    }

    private void openDB() {
        if (mSqLiteDB == null || !mSqLiteDB.isOpen()) {
            mSqLiteDB = SQLiteDatabase.openDatabase(PATH_DB + "/" + DB_NAME, null, SQLiteDatabase.OPEN_READWRITE);
        }
    }

    public List<Question> get15Question() {
        openDB();
        Cursor cursor = mSqLiteDB.rawQuery(SQL_GET_15_QUESTION, null);
        //selectionArgs[] la mang các thuộc tính của bản ghi muốn lấy ra
        if (cursor != null) {

            //di chuyển con trỏ đến dòng thứ nhất
            cursor.moveToFirst();

            //lay vi tri cua cac truong
            int questionIndex = cursor.getColumnIndex("question");
            int levelIndex = cursor.getColumnIndex("level");
            int caseAIndex = cursor.getColumnIndex("casea");
            int caseBIndex = cursor.getColumnIndex("caseb");
            int caseCIndex = cursor.getColumnIndex("casec");
            int caseDIndex = cursor.getColumnIndex("cased");
            int trueCaseIndex = cursor.getColumnIndex("truecase");

            while (cursor.isAfterLast() == false) {
                String question, caseA, caseB, caseC, caseD;
                int level, truecase;

                question = cursor.getString(questionIndex);
                caseA = cursor.getString(caseAIndex);
                caseB = cursor.getString(caseBIndex);
                caseC = cursor.getString(caseCIndex);
                caseD = cursor.getString(caseDIndex);
                truecase = Integer.parseInt(cursor.getString(trueCaseIndex));
                level = Integer.parseInt(cursor.getString(levelIndex));

                Question question1 = new Question(question, level, truecase, caseA, caseB, caseC, caseD);
                arrQ.add(question1);
                //di chuyen den dong tiep theo
                cursor.moveToNext();
            }
        }
        closeDB();

        return arrQ;
    }

    public void closeDB() {
        if (mSqLiteDB != null) {
            mSqLiteDB.close();
        }
    }

    private void copyDB(Context context) {
        new File(PATH_DB).mkdir();
        File dbFile = new File(PATH_DB + "/" + DB_NAME);

        if (dbFile.exists()) {
            return;
        }

        AssetManager assetManager = context.getAssets();
        try {
            InputStream inputStream = assetManager.open(DB_NAME);
            FileOutputStream outputStream = new FileOutputStream(dbFile);

            byte[] b = new byte[1024];
            int length = inputStream.read(b);
            while (length > 0) {
                outputStream.write(b, 0, length);
                length = inputStream.read(b);
            }
            inputStream.close();
            outputStream.close();
//            Log.i(TAG, "DB is copied to /data/data...");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Question getQuestion(int level) {
        openDB();
        Question question = null;
        String sql = "SELECT * FROM QUESTION" + level + " ORDER BY RANDOM() LIMIT 1";
        Cursor cursor = mSqLiteDB.rawQuery(sql, null);
        //selectionArgs[] la mang các thuộc tính của bản ghi muốn lấy ra
        if (cursor != null) {

            //di chuyển con trỏ đến dòng thứ nhất
            cursor.moveToFirst();

            //lay vi tri cua cac truong
            int questionIndex = cursor.getColumnIndex("Question");
            int caseAIndex = cursor.getColumnIndex("CaseA");
            int caseBIndex = cursor.getColumnIndex("CaseB");
            int caseCIndex = cursor.getColumnIndex("CaseC");
            int caseDIndex = cursor.getColumnIndex("CaseD");
            int trueCaseIndex = cursor.getColumnIndex("TrueCase");

            String questionString, caseA, caseB, caseC, caseD;
            int truecase;

            questionString = cursor.getString(questionIndex);
            caseA = cursor.getString(caseAIndex);
            caseB = cursor.getString(caseBIndex);
            caseC = cursor.getString(caseCIndex);
            caseD = cursor.getString(caseDIndex);
            truecase = Integer.parseInt(cursor.getString(trueCaseIndex));

            question = new Question(questionString, level, truecase, caseA, caseB, caseC, caseD);
            cursor.close();
        }
        closeDB();
        return question;
    }

}
