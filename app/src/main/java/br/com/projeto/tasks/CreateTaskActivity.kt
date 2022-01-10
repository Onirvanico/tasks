package br.com.projeto.tasks

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.DatePicker
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import br.com.projeto.tasks.databinding.ActivityCreateTaskBinding
import com.google.android.material.datepicker.MaterialDatePicker
import java.util.*
import java.util.regex.Pattern

class CreateTaskActivity : AppCompatActivity() {
    lateinit var binding: ActivityCreateTaskBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCreateTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pattern = Pattern.compile("([0-2]\\d)|([3][01])")
        val fourNumbersPattern = Pattern.compile("^\\d{2}[/]\\d{2}$")

        val datePicker = MaterialDatePicker.Builder.datePicker()

        binding.textInputDataLayout.setOnClickListener {
            /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                val datePicker1 = DatePickerDialog(this)
                datePicker1.show()
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    datePicker1.datePicker.setOnDateChangedListener { datePicker, year, month, day ->
                        val monthReal = month + 1

                        binding.textInputDataLayout.setText("$day/$monthReal/$year")
                       // Log.i("DATE", "Dia $day / Mês $monthReal / Ano $year" )
                    }
                }*/

            val datePickerFragment = DatePickerFragment()
            datePickerFragment.show(supportFragmentManager, "DatePicker")

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

    class DatePickerFragment() : DialogFragment(), DatePickerDialog.OnDateSetListener {

        override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            // Create a new instance of DatePickerDialog and return it
            return DatePickerDialog(requireContext(), this, year, month, day)
        }
        override fun onDateSet(p0: DatePicker?, year: Int, month: Int, day: Int) {
            Log.i("DATE", "$day/$month/$year")
        }
    }

}