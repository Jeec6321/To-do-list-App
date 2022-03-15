package com.android.todolist.models

class Task (var name: String, var startDate: String, var endDate: String, var type: Int, var status: Int){

    object TypeConstants{
        const val TASK_TYPE = 0
        const val PURCHASE_TYPE = 1
        const val UNDEFINED_TYPE = -1
    }

    object StatusConstants{
        const val PENDING = 0
        const val DONE = 1
    }

    object ActionConstants{
        const val STATUS_TO_DONE = 0
        const val DELETE_TASK = 1
    }

}