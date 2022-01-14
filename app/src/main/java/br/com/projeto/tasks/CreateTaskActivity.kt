package br.com.projeto.tasks

import android.os.Bundle
import android.text.format.DateUtils
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import br.com.projeto.tasks.databinding.ActivityCreateTaskBinding
import br.com.projeto.tasks.extensions.format
import br.com.projeto.tasks.model.Task
import br.com.projeto.tasks.model.TaskField
import br.com.projeto.tasks.utils.TimePickerUtil
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern

class CreateTaskActivity : AppCompatActivity() {

    private var isToEdit = false
    private val viewModel: TaskViewModel by lazy {
        TaskViewModelFactory((this.application as TaskApp).db.taskDAO()).create(TaskViewModel::class.java)
    }

    lateinit var binding: ActivityCreateTaskBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCreateTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        configToolbarNavigation()

        var taskField = TaskField()
        val task: Task?

        if(intent.hasExtra("task")) {
            isToEdit = true
             task = intent.getParcelableExtra<Task>("task")
            Log.i("TASK", "onCreate: " + task?.title)
            taskField = TaskField(task?.id ?: 0, task?.title ?: "",
                task?.description ?: "",
                task?.date?.format() ?: "",
                SimpleDateFormat("HH:mm", Locale.getDefault()).format(task?.time?.time))
        }

        binding.taskField = taskField


        val pattern = Pattern.compile("([0-2]\\d)|([3][01])")
        val fourNumbersPattern = Pattern.compile("^\\d{2}[/]\\d{2}$")


        val datePicker = MaterialDatePicker.Builder.datePicker().build()

        val timePicker = MaterialTimePicker.Builder().setTimeFormat(TimeFormat.CLOCK_24H).build()

        configInputDate(datePicker)

        configInputTime(timePicker)

        configCreateTask(taskField)

        configCancelButton()
    }

    private fun configToolbarNavigation() {
        binding.createTaskToolbar.setNavigationOnClickListener {
            finish()
        }
    }

    private fun configCreateTask(taskField: TaskField) {
        binding.createTaskButton.setOnClickListener {
            val title = taskField.title.get() ?: ""
            val description = taskField.description.get() ?: ""
            val date = taskField.date.get() ?: ""
            val time = taskField.time.get() ?: ""

            if (!title.isEmpty() && !description.isEmpty()
                && !date.isEmpty() && !time.isEmpty()
            ) {
                val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
                val formatedDate = dateFormat.parse(date) ?: Calendar.getInstance().time
                val formatedTime = timeFormat.parse(time) ?: Calendar.getInstance().time

                if(isToEdit) viewModel.updateTask(Task(taskField.id, title = title, description = description,
                            date= formatedDate, time = formatedTime))
                else
                    viewModel.addNewTask(title, description, formatedDate, formatedTime)
                    finish()
            } else
                Snackbar.make(binding.root, "Preencha todos os campos", 4000)
                    .show()
        }
    }

    private fun configInputTime(timePicker: MaterialTimePicker) {
        binding.textInputTimeLayout.setOnClickListener {
            timePicker.show(supportFragmentManager, "TimeDialog")
            timePicker.addOnPositiveButtonClickListener {
                binding.textInputTimeLayout.setText(
                    TimePickerUtil.formatHourAndMinute(timePicker.hour, timePicker.minute)
                )
            }
        }
    }

    private fun configCancelButton() {
        binding.cancelButton.setOnClickListener {
            finish()
        }
    }

    private fun configInputDate(datePicker: MaterialDatePicker<Long>) {
        binding.textInputDateLayout.setOnClickListener {
            datePicker.addOnPositiveButtonClickListener {
                val date = Date(it)

                binding.textInputDateLayout.setText(date.format())
                Snackbar.make(binding.root, date.toString(), 4000).show()
                Log.i("CHOICE", date.toString())
            }
            datePicker
                .show(supportFragmentManager, "Dialog")

        }
    }

    /*binding.textInputDataLayout.addTextChangedListener {
        if(pattern.matcher(it.toString()).matches()) it?.append("/")
        if(fourNumbersPattern.matcher(it.toString()).matches()) it?.append("/")

        Log.i("LISTENER", "onCreate: " + it.toString())

    }*/

    /*   binding.createTaskButton.setOnClickListener {
         //  Log.i("TOUCH", "clicado" + Date(datePicker.build().selection ?: 0).time)
       }*/

}