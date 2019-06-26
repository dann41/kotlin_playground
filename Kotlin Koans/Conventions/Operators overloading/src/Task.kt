import TimeInterval.*

data class MyDate(val year: Int, val month: Int, val dayOfMonth: Int)

enum class TimeInterval { DAY, WEEK, YEAR }

operator fun MyDate.plus(timeInterval: TimeInterval): MyDate = this.addTimeIntervals(timeInterval, 1)

class TimesTimeInterval(val times: Int, val timeInterval: TimeInterval)

operator fun TimeInterval.times(times: Int) : TimesTimeInterval = TimesTimeInterval(times, this)

operator fun MyDate.plus(timeTimesTimeInterval: TimesTimeInterval): MyDate = this.addTimeIntervals(timeTimesTimeInterval.timeInterval, timeTimesTimeInterval.times)


fun task1(today: MyDate): MyDate {
    return today + YEAR + WEEK
}

fun task2(today: MyDate): MyDate {
    return today + YEAR * 2 + WEEK * 3 + DAY * 5
}
