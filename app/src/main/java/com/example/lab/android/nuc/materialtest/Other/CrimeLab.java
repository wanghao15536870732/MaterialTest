package com.example.lab.android.nuc.materialtest.Other;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;



public class CrimeLab extends Crime {

    private static CrimeLab sCrimeLab;

    private List<CrimeLab> mCrimes;

//    public static CrimeLab get(Context context){
//        if (sCrimeLab == null){
//            sCrimeLab = new CrimeLab();
//        }
//        return sCrimeLab;
//    }
    private CrimeLab(Context context){
        mCrimes = new ArrayList<>();
    }

    public List<CrimeLab> getmCrimes() {
        return mCrimes;
    }

    public Crime getCrime(UUID id){
        for (Crime crime : mCrimes ) {
            if (crime.getId().equals(id)){
                return crime;
            }
        }
        return null;
    }
}
