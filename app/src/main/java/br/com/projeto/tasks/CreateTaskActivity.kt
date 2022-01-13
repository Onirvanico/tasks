package br.com.projeto.tasks

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import br.com.projeto.tasks.databinding.ActivityCreateTaskBinding
import br.com.projeto.tasks.extensions.format
import br.com.projeto.tasks.model.TaskField
import br.com.projeto.tasks.utils.TimePickerUtil
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.util.*
import java.util.regex.Pattern

class CreateTaskActivity : AppCompatActivity() {
    val viewModel: TaskViewModel by lazy {
        TaskViewModelFactory((this.application as TaskApp).db.taskDAO()).create(TaskViewModel::class.java)

    }

    lateinit var binding: ActivityCreateTaskBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCreateTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.taskField = TaskField()


        val pattern = Pattern.compile("([0-2]\\d)|([3][01])")
        val fourNumbersPattern = Pattern.compile("^\\d{2}[/]\\d{2}$")

        val datePicker = MaterialDatePicker.Builder.datePicker().build()

        val timePicker = MaterialTimePicker.Builder().setTimeFormat(TimeFormat.CLOCK_24H).build()


        configInputDate(datePicker)

        binding.textInputTimeLayout.setOnClickListener {
            timePicker.show(supportFragmentManager, "TimeDialog")
            timePicker.addOnPositiveButtonClickListener {
                binding.textInputTimeLayout.setText(
                    TimePickerUtil.formatHourAndMinute(timePicker.hour, timePicker.minute)
                )
            }
        }

        configCancelButton()
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