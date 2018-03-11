package com.example.lab.android.nuc.materialtest.Other;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lab.android.nuc.materialtest.R;

import java.io.File;

/**
 * Created by 王浩 on 2018/3/11.
 */

public class SunsetFragment extends Fragment {
    public static SunsetFragment newIntance(){
        return new SunsetFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_main2,container,false);
        return view;
    }
}
