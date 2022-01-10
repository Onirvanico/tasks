package br.com.projeto.tasks

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import br.com.projeto.tasks.databinding.ActivityCreateTaskBinding
import com.google.android.material.datepicker.MaterialDatePicker
import java.util.regex.Pattern

class CreateTaskActivity : AppCompatActivity() {
    lateinit var binding: ActivityCreateTaskBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val pattern = Pattern.compile("([0-2]\\d)|([3][01])")
        val fourNumbersPattern = Pattern.compile("^\\d{2}[/]\\d{2}$")
        binding = ActivityCreateTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val datePicker = MaterialDatePicker.Builder.datePicker()

        binding.textInputDataLayout.addTextChangedListener {
            if(pattern.matcher(it.toString()).matches()) it?.append("/")
            if(fourNumbersPattern.matcher(it.toString()).matches()) it?.append("/")

            Log.i("LISTENER", "onCreate: " + it.toString())
        }

        binding.createTaskButton.setOnClickListener {
          //  Log.i("TOUCH", "clicado" + Date(datePicker.build().selection ?: 0).time)
        }

    }
}