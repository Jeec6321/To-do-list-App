package com.android.todolist.Managers

import android.app.Application
import android.content.Context

class ApplicationManager: Application() {

    fun getContext(): Context{
        return applicationContext
    }
}