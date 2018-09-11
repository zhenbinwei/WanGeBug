package com.example.weizhenbin.wangebug.tools.permission;

import android.support.annotation.NonNull;

/**
 * Created by weizhenbin on 2018/9/11.
 */

public interface IPermissionGrantResult {

    void onGrantResult(@NonNull String[] permissions, @NonNull int[] grantResults);

}
