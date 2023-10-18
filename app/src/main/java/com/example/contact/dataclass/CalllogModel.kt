package com.example.contact.dataclass

import android.provider.CallLog

data class CalllogModel(
    var id:Long,
    var number:String,
    var date:Long,
    var duration:Long,
    var type:Int
)
