class DateRange(val start: MyDate, val endInclusive: MyDate) {

    operator fun contains(date: MyDate) : Boolean {
        return date >= start && date <= endInclusive
    }

}/* TODO */

fun checkInRange(date: MyDate, first: MyDate, last: MyDate): Boolean {
    return date in DateRange(first, last)
}
