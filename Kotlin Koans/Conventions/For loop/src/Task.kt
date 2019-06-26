class DateRange(val start: MyDate, val end: MyDate) : Iterable<MyDate> {
    override fun iterator(): Iterator<MyDate> = object : Iterator<MyDate> {

        var started : Boolean = false
        var lastIteratedtDate : MyDate = start

        override fun hasNext(): Boolean = start <= lastIteratedtDate && lastIteratedtDate < end

        override fun next(): MyDate {
            val dateToReturn : MyDate = if (!started) start else lastIteratedtDate!!.nextDay()
            started = true
            lastIteratedtDate = dateToReturn
            return dateToReturn
        }
    }
}

fun iterateOverDateRange(firstDate: MyDate, secondDate: MyDate, handler: (MyDate) -> Unit) {
    for (date in firstDate..secondDate) {
        handler(date)
    }
}