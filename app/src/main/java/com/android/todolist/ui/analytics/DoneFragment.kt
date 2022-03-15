package com.android.todolist.ui.analytics

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.todolist.R
import com.android.todolist.databinding.FragmentAnalyticsBinding
import com.android.todolist.models.Task
import com.android.todolist.ui.adapters.TasksDoneAdapter
import com.android.todolist.ui.adapters.TasksPendingAdapter
import com.android.todolist.ui.tasks.TasksViewModel

class DoneFragment : Fragment(), TasksDoneAdapter.TaskDoneInterface {

    private lateinit var tasksViewModel: TasksViewModel
    private var _binding: FragmentAnalyticsBinding? = null
    private lateinit var rvDoneTasks: RecyclerView
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        tasksViewModel =
                ViewModelProvider(requireActivity()).get(TasksViewModel::class.java)

        _binding = FragmentAnalyticsBinding.inflate(inflater, container, false)

        rvDoneTasks = binding.rvDoneTasks
        rvDoneTasks.layoutManager = LinearLayoutManager(context)

        tasksViewModel.tasksList.observe( viewLifecycleOwner, Observer { list ->
            setAdapter()
        } )

        tasksViewModel.searchWord.observe( viewLifecycleOwner, Observer { list ->
            setAdapter()
        } )

        return binding.root
    }

    private fun setAdapter(){
        val pendingTasks: ArrayList<Task> = tasksViewModel.getDoneTasks()

        rvDoneTasks.adapter = TasksDoneAdapter(pendingTasks, this)

        //binding.tvTaskCounter.text = getString(R.string.pending_counter, "" + pendingTasks.size)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun action(action: Int, task: Task) {
        if(action == Task.ActionConstants.DELETE_TASK){
            if(tasksViewModel.deleteTask(task))
                Toast.makeText(context, getString(R.string.task_deleted), Toast.LENGTH_LONG).show()
            else
                Toast.makeText(context, getString(R.string.task_deleted), Toast.LENGTH_LONG).show()
        }
    }
}