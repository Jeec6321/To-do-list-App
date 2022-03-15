package com.android.todolist.ui.tasks

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.todolist.utils.GeneralUtils
import com.android.todolist.models.Task
import com.google.gson.Gson

class TasksViewModel : ViewModel() {

    private val _tasksList = MutableLiveData<ArrayList<Task>>().apply {
        value = ArrayList()
    }
    val tasksList: LiveData<ArrayList<Task>> = _tasksList

    private val _searchWord = MutableLiveData<String>().apply {
        value = ""
    }
    val searchWord: LiveData<String> = _searchWord

    fun setTasks (listTasks: ArrayList<Task>) {
        _tasksList.value = listTasks
    }

    fun addNewTask (task: Task) {
        _tasksList.value!!.add(task)

        setTasks(_tasksList.value!!)
    }

    fun setSearchWord (word: String) {
        _searchWord.value = word
    }

    fun getPendingTasks(): ArrayList<Task> {
        val pendingTasks: ArrayList<Task> = ArrayList()

        for(task: Task in _tasksList.value!!) {
            if ( task.type == Task.TypeConstants.TASK_TYPE && task.status == Task.StatusConstants.PENDING &&
                compareStrings(task.name, _searchWord.value.toString()) ){

                pendingTasks.add(task)
            }
        }

        return pendingTasks
    }

    fun getPendingPurchases(): ArrayList<Task> {
        val pendingTasks: ArrayList<Task> = ArrayList()

        for(task: Task in _tasksList.value!!) {
            if ( task.type == Task.TypeConstants.PURCHASE_TYPE && task.status == Task.StatusConstants.PENDING &&
                compareStrings(task.name, _searchWord.value.toString()) ){

                pendingTasks.add(task)
            }
        }

        return pendingTasks
    }

    fun getDoneTasks(): ArrayList<Task> {
        val doneTasks: ArrayList<Task> = ArrayList()

        for(task: Task in _tasksList.value!!) {
            if ( task.status == Task.StatusConstants.DONE &&
                compareStrings(task.name, _searchWord.value.toString()) ){

                doneTasks.add(task)
            }
        }

        return doneTasks
    }

    private fun compareStrings(completeText: String, partialText: String): Boolean{
        //TODO Improve compare String with separated words
        if(clearString(completeText).contains( clearString(partialText) )){
            return true
        } else {
            return false
        }
    }

    private fun clearString(text: String): String{
        return text.lowercase()
    }

    fun modifyStatus (taskOne: Task, newStatus: Int) {
        for (taskTwo: Task in _tasksList.value!!) {
            if (GeneralUtils.compareObjects(taskOne, taskTwo)) {
                taskTwo.status = newStatus
                taskTwo.endDate = GeneralUtils.getNowDate()
                break
            }
        }

        setTasks(_tasksList.value!!)
    }

    fun deleteTask(task: Task): Boolean{
        var success = false

        for(taskInList: Task in _tasksList.value!!){
            if(GeneralUtils.compareObjects(taskInList, task)) {
                _tasksList.value!!.remove(task)
                success = true
                break
            }
        }

        setTasks(_tasksList.value!!)

        return success
    }

}