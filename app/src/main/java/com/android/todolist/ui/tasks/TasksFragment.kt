package com.android.todolist.ui.tasks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.size
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.todolist.R
import com.android.todolist.databinding.FragmentTasksBinding
import com.android.todolist.models.Task
import com.android.todolist.ui.adapters.TasksPendingAdapter
import com.google.gson.Gson
import java.util.*
import kotlin.collections.ArrayList

class TasksFragment: Fragment(), TasksPendingAdapter.TaskInterface {

    private lateinit var tasksViewModel: TasksViewModel
    private var _binding: FragmentTasksBinding? = null
    private lateinit var rvPendingTasks: RecyclerView
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        tasksViewModel =
                ViewModelProvider(requireActivity()).get(TasksViewModel::class.java)

        _binding = FragmentTasksBinding.inflate(inflater, container, false)

        rvPendingTasks = binding.rvPendingTasks

        rvPendingTasks.layoutManager = LinearLayoutManager(context)

        tasksViewModel.tasksList.observe( viewLifecycleOwner, Observer { list ->
            setAdapter()
        })

        tasksViewModel.searchWord.observe( viewLifecycleOwner, Observer {
            setAdapter()
        } )

        return binding.root
    }

    private fun setAdapter(){
        val pendingTasks: ArrayList<Task> = tasksViewModel.getPendingTasks()

        rvPendingTasks.adapter = TasksPendingAdapter(pendingTasks, this)

        //binding.tvTaskCounter.text = getString(R.string.pending_counter, "" + pendingTasks.size)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun taskAction(action: Int, task: Task) {
        if(action == Task.ActionConstants.STATUS_TO_DONE){
            tasksViewModel.modifyStatus(task, Task.StatusConstants.DONE)
        }
    }
}