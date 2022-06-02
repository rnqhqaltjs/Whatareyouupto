package com.example.whatareyouupto.CalendarDecorator

import android.graphics.Typeface
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade

class BoldDecorator(min: CalendarDay, max:CalendarDay): DayViewDecorator {
    val maxDay = max
    val minDay = min
    override fun shouldDecorate(day: CalendarDay?): Boolean {
        return true
    }
    override fun decorate(view: DayViewFacade?) {
        view?.addSpan(object: StyleSpan(Typeface.BOLD){})
        view?.addSpan(object: RelativeSizeSpan(1.4f){})
    }
}