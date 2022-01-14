package br.com.projeto.tasks

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import br.com.projeto.tasks.database.TaskDAO
import br.com.projeto.tasks.model.Task
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.*

class TaskViewModel(private val taskDAO: TaskDAO) : ViewModel() {
    private fun insertTask(task: Task) {
        viewModelScope.launch {
            taskDAO.insertTask(task)
        }

    }

    private fun getNewTaskEntry(title: String, description: String, date: Date, time: Date) =
        Task(title = title, description = description, date = date, time = time)


    fun addNewTask(title: String, description: String, date: Date, time: Date) {
            val newTask = getNewTaskEntry(title, description, date, time)
            insertTask(newTask)

    }

    fun getAllTasks() = taskDAO.getAllTasks()

    fun updateTask(task: Task) {
        viewModelScope.launch {
            taskDAO.updateTask(task)
        }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch {
            taskDAO.deleteTask(task)
        }
    }
}



class TaskViewModelFactory(private val taskDAO: TaskDAO) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TaskViewModel::class.java))
            @Suppress("UNCHECKED_CAST")
            return TaskViewModel(taskDAO) as T

        throw IllegalArgumentException("Unknow viewModel class")

    }


}
