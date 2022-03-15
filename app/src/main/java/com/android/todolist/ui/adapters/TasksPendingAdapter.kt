package com.android.todolist.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.todolist.R
import com.android.todolist.models.Task

class TasksPendingAdapter (private val tasksList: ArrayList<Task>, private var listener: TaskInterface) : RecyclerView.Adapter<TasksPendingAdapter.ViewHolder>() {

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view){

        fun render(task: Task, listener: TaskInterface, lastItem: Boolean){
            view.findViewById<TextView>(R.id.tv_name).text = task.name
            view.findViewById<TextView>(R.id.tv_date).text = task.startDate

            view.findViewById<Button>(R.id.b_done).setOnClickListener{ v ->
                listener.taskAction(Task.ActionConstants.STATUS_TO_DONE, task)
            }

            if (lastItem)
                view.findViewById<LinearLayout>(R.id.ll_extra).visibility = View.VISIBLE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        return ViewHolder(layoutInflater.inflate(R.layout.row_pending_task, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val lastItem = position == itemCount - 1

        holder.render(tasksList.get(position), listener, lastItem)
    }

    override fun getItemCount(): Int {
        return tasksList.size
    }

    interface TaskInterface{
        fun taskAction(action: Int, task: Task)
    }
}