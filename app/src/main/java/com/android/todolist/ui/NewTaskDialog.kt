package com.android.todolist.ui

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import com.android.todolist.ui.activities.MainActivity
import com.android.todolist.R
import com.android.todolist.models.Task
import com.android.todolist.utils.GeneralUtils

class NewTaskDialog(context: Context, newTaskInterface: NewTaskInterface, val fragment: MainActivity.FragmentsEnum): Dialog(context) {

    /* override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            // Use the Builder class for convenient dialog construction
            val builder = AlertDialog.Builder(it)
            builder.setMessage("holaaa")
                .setPositiveButton("R.string.fire",
                    DialogInterface.OnClickListener { dialog, id ->
                        // FIRE ZE MISSILES!
                    })
                .setNegativeButton("R.string.cancel",
                    DialogInterface.OnClickListener { dialog, id ->
                        // User cancelled the dialog
                    })
            // Create the AlertDialog object and return it
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    } */

    var listener: NewTaskInterface

    init {
        setCancelable(true)

        listener = newTaskInterface
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.dialog_new_task)

        val etTaskName = findViewById<EditText>(R.id.et_task_name)
        val rgTaskType = findViewById<RadioGroup>(R.id.rg_task_type)

        if(fragment == MainActivity.FragmentsEnum.PENDING_TASKS)
            rgTaskType.check(R.id.rb_task)
        else
            rgTaskType.check(R.id.rb_pruchase)

        findViewById<Button>(R.id.b_cancel).setOnClickListener{
            dismiss()
        }

        findViewById<Button>(R.id.b_save_task).setOnClickListener{
            listener.taskAdded(
                Task(
                    etTaskName.text.toString(),
                    GeneralUtils.getNowDate(),
                    "",
                    getType(rgTaskType.checkedRadioButtonId),
                    Task.StatusConstants.PENDING)
            )

            dismiss()
        }
    }

    fun getType(idChecked: Int) : Int{
        var type: Int

        if(idChecked == R.id.rb_task)
            type = Task.TypeConstants.TASK_TYPE
        else if(idChecked == R.id.rb_pruchase)
            type = Task.TypeConstants.PURCHASE_TYPE
        else
            type = Task.TypeConstants.UNDEFINED_TYPE

        return type
    }

    interface NewTaskInterface{
        fun taskAdded(task: Task)
    }

}