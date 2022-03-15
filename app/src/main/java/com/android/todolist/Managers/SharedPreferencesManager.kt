package com.android.todolist.Managers

import android.app.Activity
import android.content.Context
import com.android.todolist.models.Task
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.Exception

class SharedPreferencesManager {

    //------------------------------------------------------------
    private val TASKS_FILE: String = "TASKS_FILE"
    private val TASKS_VAR: String = "TASKS_VAR"
    //------------------------------------------------------------

    fun getTasks(activity: Activity): ArrayList<Task> {
        var data: ArrayList<Task> = ArrayList()

        try{
            val preferences = activity.getSharedPreferences(TASKS_FILE, Context.MODE_PRIVATE)

            val stringData = preferences.getString(TASKS_VAR, "")

            val type = object : TypeToken<ArrayList<Task>>() {}.type

            data = Gson().fromJson(stringData, type)
        } catch (e: Exception){ }

        return data
    }

    fun setTasks(tasks: ArrayList<Task>, activity: Activity): Boolean{
        try{
            val preferences = activity.getSharedPreferences(TASKS_FILE, Context.MODE_PRIVATE)

            val editor = preferences.edit()

            editor.putString(TASKS_VAR, Gson().toJson(tasks))

            editor.apply()

            editor.commit()

            return true
        } catch (e: Exception){
            return false
        }
    }

    fun addTasks(task: Task, activity: Activity): Boolean{
        try{
            val preferences = activity.getSharedPreferences(TASKS_FILE, Context.MODE_PRIVATE)

            val savedTasks: ArrayList<Task> = getTasks(activity)

            savedTasks.add( task )

            println(Gson().toJson(savedTasks))

            val editor = preferences.edit()

            editor.putString(TASKS_VAR, Gson().toJson(savedTasks))

            editor.apply()

            editor.commit()

            return true
        } catch (e: Exception){
            return false
        }
    }

}