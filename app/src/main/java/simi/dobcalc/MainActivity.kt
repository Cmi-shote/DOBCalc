package simi.dobcalc

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private var tvDate: TextView? = null
    private var tvHours: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnDatePicker : Button = findViewById(R.id.button2)
        tvDate = findViewById(R.id.textDate)
        tvHours = findViewById((R.id.textHours))

        btnDatePicker.setOnClickListener {
            clickDatePicker()

            //Toast.makeText(this, "Button was clicked",Toast.LENGTH_LONG).show()...this is just to check if the button is working
        }
    }


    private fun clickDatePicker(){

        var myCalendar = Calendar.getInstance()
        val year = myCalendar.get(Calendar.YEAR)
        val month = myCalendar.get(Calendar.MONTH)
        val day = myCalendar.get(Calendar.DAY_OF_MONTH)

        val simi = DatePickerDialog(this, DatePickerDialog.OnDateSetListener{ _, Selectedyear, Selectedmonth, SelecteddayOfMonth ->
            val selectedDate = "$SelecteddayOfMonth/${Selectedmonth+1}/$Selectedyear"   //its selectedmonth + 1 because the months start from index zero here

            tvDate?.text = selectedDate


            val sdf = SimpleDateFormat("dd/MM/yyy", Locale.ENGLISH)

            val theDate = sdf.parse(selectedDate)

            theDate?.let{
                val selectedDateInDays = theDate.time / (3600000 * 24)   //this gets the amount of time that has passed btw the selected date and 1970, 1 january

                val currentDate = sdf.parse(sdf.format(System.currentTimeMillis())) //gets the amount of time that has passed btw the present/current time and 1970, 1 january

                currentDate?.let{
                    val currentDateInDays = currentDate.time / (3600000 * 24)

                    val differenceInDays = currentDateInDays - selectedDateInDays

                    tvHours?.text = differenceInDays.toString()
                }

            }

        },
            year,
            month,
            day
        )

        simi.datePicker.maxDate = System.currentTimeMillis() - (24 * 3600000)   //there are 3.6million milliseconds in an hour, so we are doing this to limit the user from picking a date in the future and you can select dates starting from a day before the present date
        simi.show()
    }
}