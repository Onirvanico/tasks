package br.com.projeto.tasks.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.appcompat.view.menu.MenuView
import androidx.recyclerview.widget.RecyclerView
import br.com.projeto.tasks.R
import br.com.projeto.tasks.TaskApp
import br.com.projeto.tasks.TaskViewModel
import br.com.projeto.tasks.TaskViewModelFactory
import br.com.projeto.tasks.databinding.TaskItemBinding
import br.com.projeto.tasks.model.Task

class TaskAdapter(private val tasks: MutableList<Task>) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    var listenerEdit: (Task) -> Unit = {}
    var listenerDelete: (Task, Int) -> Unit = { task: Task, i: Int -> }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val binding = TaskItemBinding.inflate(LayoutInflater.from(parent.context))
        return TaskViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bindViews(tasks.get(position))
    }

    override fun getItemCount() = tasks.size

    inner class TaskViewHolder(private val binding: TaskItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindViews(task: Task) {
            with (binding) {
               titleItem.text = task.title
               descriptionItem.text = task.description
               configMoreOptions(task)
            }
        }

        private fun TaskItemBinding.configMoreOptions(task: Task) {
            optionsItem.setOnClickListener { it ->
                showPopup(it, task)
            }
        }

        private fun showPopup(view: View, task: Task) {
            configPopupMenu(view, task)
        }

        private fun configPopupMenu(view: View, task: Task) {
            val popupMenu = PopupMenu(view.context, view)
            popupMenu.menuInflater.inflate(R.menu.options_more_menu, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener { it ->
                when (it.itemId) {
                    R.id.edit_task -> listenerEdit(task)

                    R.id.delete_task -> listenerDelete(task, adapterPosition)
                }
                return@setOnMenuItemClickListener true
            }
            popupMenu.show()
        }
    }

    fun remove(position: Int) {
        tasks.remove(tasks[position])
        notifyItemRemoved(position)
    }
}