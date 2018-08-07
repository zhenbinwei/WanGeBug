package com.example.weizhenbin.wangebug.net.interfaces;

import java.util.HashMap;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

/**
 * Created by weizhenbin on 18/8/7.
 */

public interface IHttpProcessor {

      void get(String url, HashMap<String,String> param, IResult iResult);

      void post(String url,HashMap<String,String> param,IResult iResult);
}
