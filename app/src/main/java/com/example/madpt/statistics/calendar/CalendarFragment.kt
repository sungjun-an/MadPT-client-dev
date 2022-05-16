package com.example.madpt.statistics.calendar

import android.os.Bundle
import android.util.TypedValue
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.children
import com.example.madpt.R
import com.example.madpt.databinding.CalendarDayBinding
import com.example.madpt.databinding.CalendarHeaderBinding
import com.example.madpt.databinding.FragmentCalendarBinding
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.model.CalendarMonth
import com.kizitonwose.calendarview.model.DayOwner
import com.kizitonwose.calendarview.ui.DayBinder
import com.kizitonwose.calendarview.ui.MonthHeaderFooterBinder
import com.kizitonwose.calendarview.ui.ViewContainer
import com.kizitonwose.calendarview.utils.next
import com.kizitonwose.calendarview.utils.previous
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.*

data class MonthData(val date: Long, val daily_diet_Kcal: Double, val daily_exercise_Kcal: Double) {
    val targetDate: LocalDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(date), TimeZone.getDefault().toZoneId())
}

class CalendarFragment : Fragment() {

    private var selectedDate: LocalDate? = null
    private val monthTitleFormatter = DateTimeFormatter.ofPattern("MMMM")
    private lateinit var binding: FragmentCalendarBinding
    private val monthData = generateMonth().groupBy{ it.targetDate.toLocalDate() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCalendarBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCalendarBinding.bind(view)

        val daysOfWeek = daysOfWeekFromLocale()

        val currentMonth = YearMonth.now()
        binding.calendar.setup(currentMonth.minusMonths(100), currentMonth.plusMonths(100), daysOfWeek.first())
        binding.calendar.scrollToMonth(currentMonth)

        class DayViewContainer(view: View) : ViewContainer(view) {
            lateinit var day: CalendarDay
            val binding = CalendarDayBinding.bind(view)
            init {
                view.setOnClickListener {
                    if(day.owner == DayOwner.THIS_MONTH) {
                        val oldDate = selectedDate
                        selectedDate = day.date
                        val binding = this@CalendarFragment.binding
                        binding.calendar.notifyDateChanged(day.date)
                        oldDate?.let { binding.calendar.notifyDateChanged(it) }
                        updateAdapterForDate(day.date)
                    }
                }
            }
        }

        binding.calendar.dayBinder = object : DayBinder<DayViewContainer> {
            override fun create(view: View) = DayViewContainer(view)
            override fun bind(container: DayViewContainer, day: CalendarDay) {
                container.day = day
                val textView = container.binding.calendarDayText
                val layout = container.binding.calendarDayLayout
                textView.text = day.date.dayOfMonth.toString()

                val monthDietKcalView = container.binding.calendarDietKcal
                val monthExerciseKcalView = container.binding.calendarExerciseKcal
                val monthDietKcalText = container.binding.calendarDietText
                val monthExerciseKcalText = container.binding.calendarExerciseText

                monthDietKcalView.background = null
                monthExerciseKcalView.background = null
                monthDietKcalText.text = ""
                monthExerciseKcalText.text = ""

                layout.layoutParams = LinearLayout.LayoutParams(binding.calendar.daySize.width, binding.calendar.daySize.width*2)

                if (day.owner == DayOwner.THIS_MONTH) {
                    textView.setTextColorRes(R.color.calendar_text_grey)
                    layout.setBackgroundResource(if (selectedDate == day.date) R.drawable.calendar_selected_bg else 0)

                    if (monthData[day.date] != null) {
                        var monthTotalData = arrayListOf<MonthData>()
                        monthTotalData.addAll(monthData[day.date].orEmpty())
                        if (monthTotalData[0].daily_diet_Kcal != 0.0) {
                            monthDietKcalView.setBackgroundColor(view.context.getColorCompat(R.color.diet_kcal_purple))
                            monthDietKcalText.text = monthTotalData[0].daily_diet_Kcal.toString()
                        }
                        if (monthTotalData[0].daily_exercise_Kcal != 0.0) {
                            monthExerciseKcalView.setBackgroundColor(view.context.getColorCompat(R.color.exKcal_purple))
                            monthExerciseKcalText.text = monthTotalData[0].daily_exercise_Kcal.toString()
                        }
                    }
                }else {
                    textView.setTextColorRes(R.color.calendar_text_grey)
                    layout.background = null
                }
            }
        }
        class MonthViewContainer(view: View): ViewContainer(view) {
            val legendLayout = CalendarHeaderBinding.bind(view).legendLayout.root
        }
        binding.calendar.monthHeaderBinder = object : MonthHeaderFooterBinder<MonthViewContainer> {
            override fun bind(container: MonthViewContainer, month: CalendarMonth) {
                if (container.legendLayout.tag == null) {
                    container.legendLayout.tag = month.yearMonth
                    container.legendLayout.children.map { it as TextView }.forEachIndexed { index, textView ->
                        textView.text = daysOfWeek[index].getDisplayName(TextStyle.SHORT, Locale.KOREA)
                        textView.setTextColorRes(R.color.calendar_text_grey)
                        textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10F)
                    }
                    month.yearMonth
                }
            }
            override fun create(view: View) = MonthViewContainer(view)
        }

        binding.calendar.monthScrollListener = { month ->
            val title = "${month.yearMonth.year} ${monthTitleFormatter.format(month.yearMonth)}"
            binding.calendarMonthYearText.text = title

            selectedDate?.let {
                selectedDate = null
                binding.calendar.notifyDateChanged(it)
            }
        }

        binding.calendarNextMonthImage.setOnClickListener {
            binding.calendar.findFirstVisibleMonth()?.let {
                binding.calendar.smoothScrollToMonth(it.yearMonth.next)
            }
        }

        binding.calendarPreviousMonthImage.setOnClickListener {
            binding.calendar.findFirstVisibleMonth()?.let {
                binding.calendar.smoothScrollToMonth(it.yearMonth.previous)
            }
        }
    }

    private fun updateAdapterForDate(date: LocalDate?){
    }
}