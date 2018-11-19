package com.example.weizhenbin.wangebug.base

import java.io.Serializable

/**
 * Created by weizhenbin on 2018/10/10.
 */
enum class UrlTypeEnum(val typeValue:Int) : Serializable {
     UNKNOWN(-1), CODE(1), NEWS(2);

     companion object {
          fun getValueFromType(typeValue: Int):UrlTypeEnum{
               return when(typeValue){
                    1->UrlTypeEnum.CODE
                    2->UrlTypeEnum.NEWS
                    else -> UrlTypeEnum.UNKNOWN
               }
          }
     }
}


