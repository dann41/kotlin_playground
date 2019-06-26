data class MyDate(val year: Int, val month: Int, val dayOfMonth: Int) : Comparable<MyDate> {

    override fun compareTo(other: MyDate): Int = Comparator
            .comparingInt<MyDate> { it.year }
            .thenComparingInt { it.month }
            .thenComparingInt { it.dayOfMonth }
            .compare(this, other)

}

fun compare(date1: MyDate, date2: MyDate) = date1 < date2
