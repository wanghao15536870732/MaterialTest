package com.example.lab.android.nuc.materialtest.Other;

/**
 * Created by 王浩 on 2018/3/11.
 */

public class Question {

    private int mTestResId;

    private boolean mAnswerTrue;

    public Question(int testResId,boolean answerTrue){
        mTestResId = testResId;
        mAnswerTrue = answerTrue;

    }

    public int getTestResId() {
        return mTestResId;
    }

    public void setTestResId(int testResId) {
        mTestResId = testResId;
    }

    public boolean isAnswerTrue(){
        return mAnswerTrue;
    }

    public void setAnswerTrue(boolean answerTrue) {
        mAnswerTrue = answerTrue;
    }
}
