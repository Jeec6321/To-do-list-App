package com.android.todolist.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.todolist.R
import com.android.todolist.models.Task

class TasksDoneAdapter(private val tasks: ArrayList<Task>, private val listener: TaskDoneInterface) : RecyclerView.Adapter<TasksDoneAdapter.ViewHolder> () {

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view){

        fun render(task: Task, listener: TaskDoneInterface, lastItem: Boolean){
            view.findViewById<TextView>(R.id.tv_name).text = task.name
            view.findViewById<TextView>(R.id.tv_start_date).text = task.startDate
            view.findViewById<TextView>(R.id.tv_end_date).text = task.endDate

            view.findViewById<ImageButton>(R.id.ib_delete).setOnClickListener {
                listener.action(Task.ActionConstants.DELETE_TASK, task)
            }

            if(lastItem)
                view.findViewById<LinearLayout>(R.id.ll_extra).visibility = View.VISIBLE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        return ViewHolder(layoutInflater.inflate(R.layout.row_done_task, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.render(tasks.get(position), listener, (position == itemCount -1))
    }

    override fun getItemCount(): Int {
        return tasks.size
    }

    interface TaskDoneInterface{
        fun action(action: Int, task: Task)
    }

}