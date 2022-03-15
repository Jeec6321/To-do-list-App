package com.android.todolist.utils

import android.os.Build
import androidx.annotation.RequiresApi
import com.android.todolist.models.Task
import com.google.gson.Gson
import java.text.SimpleDateFormat
import java.util.*

class GeneralUtils {

    companion object{
        fun compareObjects(taskOne: Task, taskTwo: Task): Boolean{
            if(Gson().toJson(taskOne).equals(Gson().toJson(taskTwo)))
                return true

            return false
        }

        fun getNowDate(): String {
            val sdf = SimpleDateFormat("dd-MM-yyyy HH:mm")

            return sdf.format(Date())
        }
    }


}