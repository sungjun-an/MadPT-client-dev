package com.example.madpt.statistics.calendar

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.view.children
import com.example.madpt.API.food.GetMonthDataList
import com.example.madpt.API.statistic.GetMonthDataCall
import com.example.madpt.API.statistic.MonthDataDateBy
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
import java.sql.Timestamp
import java.time.*
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.*
import kotlin.collections.ArrayList

class CalendarFragment : Fragment(), GetMonthDataList{

    private var selectedDate: LocalDate? = null
    private val monthTitleFormatter = DateTimeFormatter.ofPattern("MMMM")
    private lateinit var binding: FragmentCalendarBinding
    private var dailyDate = LocalDate.now()
    @RequiresApi(Build.VERSION_CODES.O)
    private var monthData = arrayListOf<MonthDataDateBy>().groupBy { LocalDateTime.ofInstant(Instant.ofEpochMilli(it.date), TimeZone.getDefault().toZoneId()).toLocalDate() }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCalendarBinding.inflate(inflater, container, false)
        var monthDataList = arguments?.getParcelableArrayList<MonthDataDateBy>("monthDataList")
        monthData = monthDataList!!.groupBy { LocalDateTime.ofInstant(Instant.ofEpochMilli(it.date), TimeZone.getDefault().toZoneId()).toLocalDate()}
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCalendarBinding.bind(view)

        val daysOfWeek = daysOfWeekFromLocale()

        val currentMonth = YearMonth.now()
        binding.calendar.setup(currentMonth.minusMonths(100), currentMonth.plusMonths(100), daysOfWeek.first())
        binding.calendar.scrollToMonth(currentMonth)

        updateData()

        class MonthViewContainer(view: View): ViewContainer(view) {
            val legendLayout = CalendarHeaderBinding.bind(view).legendLayout.root
        }
        binding.calendar.monthHeaderBinder = object : MonthHeaderFooterBinder<MonthViewContainer> {
            override fun bind(container: MonthViewContainer, month: CalendarMonth) {
                if (container.legendLayout.tag == null) {
                    container.legendLayout.tag = month.yearMonth
                    container.legendLayout.children.map { it as TextView }.forEachIndexed { index, textView ->
                        textView.text = daysOfWeek[index].getDisplayName(TextStyle.SHORT, Locale.KOREA)
                        textView.setTextColorRes(R.color.white)
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
            dailyDate=dailyDate.withMonth(month.month)
            Log.d("YMC","${dailyDate}, ${month.month}")
            GetMonthDataCall(this, requireContext()).getMonthData(dailyDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli())
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

    private fun updateData(){
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
                    }
                }
            }
        }

        binding.calendar.dayBinder = object : DayBinder<DayViewContainer> {
            override fun create(view: View) = DayViewContainer(view)
            @RequiresApi(Build.VERSION_CODES.O)
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
                    textView.setTextColorRes(R.color.white)
                    layout.setBackgroundResource(if (selectedDate == day.date) R.drawable.calendar_selected_bg else 0)

                    if (monthData[day.date] != null) {
                        var monthTotalData = arrayListOf<MonthDataDateBy>()
                        monthTotalData.addAll(monthData[day.date].orEmpty())
                        if (monthTotalData[0].dailyDietKcal != 0.0) {
                            monthDietKcalView.setBackgroundColor(view!!.context.getColorCompat(R.color.puple))
                            monthDietKcalText.text = "%.2f".format(monthTotalData[0].dailyDietKcal)
                        }
                        if (monthTotalData[0].dailyExerciseKcal != 0.0) {
                            monthExerciseKcalView.setBackgroundColor(view!!.context.getColorCompat(R.color.mint))
                            monthExerciseKcalText.text = "%.2f".format(monthTotalData[0].dailyExerciseKcal)
                        }
                    }
                }else {
                    textView.setTextColorRes(R.color.calendar_text_grey)
                    layout.background = null
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun getMonthDataList(monthDataList: ArrayList<MonthDataDateBy>) {
        monthData= monthDataList.groupBy { LocalDateTime.ofInstant(Instant.ofEpochMilli(it.date), TimeZone.getDefault().toZoneId()).toLocalDate() }
        updateData()
    }
}