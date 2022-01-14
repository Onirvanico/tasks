package br.com.projeto.tasks

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewStub
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import br.com.projeto.tasks.adapter.TaskAdapter
import br.com.projeto.tasks.databinding.ActivityMainBinding
import br.com.projeto.tasks.model.Task
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val viewModel: TaskViewModel by lazy {
        TaskViewModelFactory((this.application as TaskApp).db.taskDAO()).create(TaskViewModel::class.java)
    }
    private val stub: ViewStub? by lazy {
        binding.root.findViewById(R.id.empty_list)
    }

    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fillList()
        configAddNewTaskButton()
    }

    private fun fillList() {
        lifecycleScope.launch {
            viewModel.getAllTasks().collect { tasks ->
                if(tasks.size > 0) {
                    stub?.visibility = View.GONE
                    configAdapter(tasks)
                }
                else {
                    stub?.visibility = View.VISIBLE
                }
            }

        }
    }

    private fun configAdapter(tasks: List<Task>) {

        val adapter = TaskAdapter(tasks.toMutableList())
        binding.recyclerview.setHasFixedSize(true)
        binding.recyclerview.adapter = adapter

        configEdit(adapter)

        configDelete(adapter)
    }

    private fun configDelete(adapter: TaskAdapter) {
        adapter.listenerDelete = { task: Task, i: Int ->
            viewModel.deleteTask(task)
            adapter.remove(i)
        }
    }

    private fun configEdit(adapter: TaskAdapter) {
        adapter.listenerEdit = {
            val intent = Intent(this, CreateTaskActivity::class.java).apply {
                putExtra("task", it)
            }
            startActivity(intent)
        }
    }

    private fun configAddNewTaskButton() {
        binding.addNewTaskButton
            .setOnClickListener {
                goToCreateTask()
            }
    }

    private fun goToCreateTask() {
        startActivity(Intent(this, CreateTaskActivity::class.java))
    }
}