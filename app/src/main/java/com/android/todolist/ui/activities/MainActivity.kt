package com.android.todolist.ui.activities

import android.os.Bundle
import android.view.View
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.android.todolist.Managers.ManagerNotification
import com.android.todolist.Managers.SharedPreferencesManager
import com.android.todolist.R
import com.android.todolist.databinding.ActivityMainBinding
import com.android.todolist.models.Task
import com.android.todolist.ui.NewTaskDialog
import com.android.todolist.ui.tasks.TasksViewModel

class MainActivity : AppCompatActivity(), NewTaskDialog.NewTaskInterface {

    private lateinit var binding: ActivityMainBinding
    private lateinit var tasksViewModel: TasksViewModel
    private lateinit var navView:BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        tasksViewModel =
            ViewModelProvider(this).get(TasksViewModel::class.java)

        navView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)

        val appBarConfiguration = AppBarConfiguration(setOf(
            R.id.navigation_tasks, R.id.navigation_purchases, R.id.navigation_analytics
        ))

        navView.setupWithNavController(navController)

        initView()

        binding.fbAddNew.setOnClickListener{
            val dialog = NewTaskDialog(this, this, getFragmentTag())

            dialog.show()
        }

        tasksViewModel.setTasks(SharedPreferencesManager().getTasks(this))

        ManagerNotification.createNotificationChanel(this)
        ManagerNotification.sendNotification(this)

        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            binding.tvTitleCounter.text = when(getFragmentTag()){
                FragmentsEnum.PENDING_TASKS -> getString(R.string.pending_counter, "" + tasksViewModel.getPendingTasks().size)
                FragmentsEnum.PENDING_PURCHASES -> getString(R.string.pending_counter, "" + tasksViewModel.getPendingPurchases().size)
                FragmentsEnum.TASKS_DONE -> getString(R.string.pending_counter, "" + tasksViewModel.getDoneTasks().size)

                else -> {""}
            }

        }
    }

    private fun initView() {
        binding.etSearcher.visibility = View.GONE
        binding.ivHideSearcher.visibility = View.GONE

        binding.ivSearch.setOnClickListener {
            showSearcher(true)
        }

        binding.ivHideSearcher.setOnClickListener {
            binding.etSearcher.setText("")
            showSearcher(false)
        }

        binding.etSearcher.addTextChangedListener {
            tasksViewModel.setSearchWord(it.toString())
        }
    }

    private fun showSearcher(show: Boolean){
        binding.etSearcher.visibility = if(show) View.VISIBLE else View.GONE

        binding.ivHideSearcher.visibility = if(show) View.VISIBLE else View.GONE

        binding.ivSearch.visibility = if(show) View.GONE else View.VISIBLE
    }

    private fun getFragmentTag(): FragmentsEnum {
        if (navView.menu.findItem(R.id.navigation_tasks).isChecked)
            return FragmentsEnum.PENDING_TASKS
        else if (navView.menu.findItem(R.id.navigation_purchases).isChecked)
            return FragmentsEnum.PENDING_PURCHASES
        else if(navView.menu.findItem(R.id.navigation_analytics).isChecked)
            return FragmentsEnum.TASKS_DONE
        else
            return FragmentsEnum.NONE
    }

    override fun taskAdded(task: Task) {
        tasksViewModel.addNewTask(task)

        when(task.type){
            Task.TypeConstants.TASK_TYPE -> navView.selectedItemId = R.id.navigation_tasks

            Task.TypeConstants.PURCHASE_TYPE -> navView.selectedItemId = R.id.navigation_purchases
        }
    }

    override fun onBackPressed() {

    }

    override fun onStop() {
        super.onStop()
        SharedPreferencesManager().setTasks(tasksViewModel.tasksList.value!!, this)
    }

    override fun onDestroy() {
        super.onDestroy()
        SharedPreferencesManager().setTasks(tasksViewModel.tasksList.value!!, this)
    }

    enum class FragmentsEnum{
        PENDING_TASKS,
        PENDING_PURCHASES,
        TASKS_DONE,
        NONE
    }
}