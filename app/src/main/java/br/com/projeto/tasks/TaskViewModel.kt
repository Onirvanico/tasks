package br.com.projeto.tasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.projeto.tasks.model.Task
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.supervisorScope
import java.util.*

class TaskViewModel(private val taskDAO: TaskDAO) : ViewModel() {
    private suspend fun insertTask(task: Task) {
          taskDAO.insertTask(task)
    }

    private fun getNewTaskEntry(title: String, description: String, date: Date, time: Date): Task {
        return Task(title = title, description = description, date = date, time = time)
    }

    suspend fun addNewTask(title: String, description: String, date: Date, time: Date) {
        val newTask = getNewTaskEntry(title, description, date, time)
        insertTask(newTask)
    }
}

class TaskViewModelFactory(private val taskDAO: TaskDAO) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(TaskViewModel::class.java))
            @Suppress("UNCHECKED_CAST")
            return TaskViewModel(taskDAO) as T

        throw IllegalArgumentException("Unknow viewModel class")
    }

}
