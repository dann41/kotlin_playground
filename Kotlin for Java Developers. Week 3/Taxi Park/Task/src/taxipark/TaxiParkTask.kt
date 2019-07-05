package taxipark

import kotlin.math.roundToInt

/*
 * Task #1. Find all the drivers who performed no trips.
 */
fun TaxiPark.findFakeDrivers(): Set<Driver> =
        allDrivers.minus(trips.map { it.driver }.distinct())

/*
 * Task #2. Find all the clients who completed at least the given number of trips.
 */
fun TaxiPark.findFaithfulPassengers(minTrips: Int): Set<Passenger> =
        allPassengers.map { passenger -> passenger to false }
                .plus(trips.flatMap { trip -> trip.passengers.map { passenger -> passenger to true } })
                .groupBy({ (passenger, _) -> passenger }, { (_, withTrip) -> withTrip })
                .filter { (_, v) -> v.count { it } >= minTrips }
                .keys

/*
 * Task #3. Find all the passengers, who were taken by a given driver more than once.
 */
fun TaxiPark.findFrequentPassengers(driver: Driver): Set<Passenger> =
        trips.filter { trip -> trip.driver == driver }
                .flatMap { trip -> trip.passengers }
                .groupBy { passenger -> passenger }
                .filter { (_, v) -> v.size > 1 }
                .keys


/*
 * Task #4. Find the passengers who had a discount for majority of their trips.
 */
fun TaxiPark.findSmartPassengers(): Set<Passenger> =
        trips.flatMap { trip -> trip.passengers.map { passenger -> passenger to ((trip.discount ?: 0) != 0) } }
                .groupBy({ (passenger, _) -> passenger }, { (_, tripWithDiscount) -> tripWithDiscount })
                .filter { (_, tripsDiscount) -> tripsDiscount.count { it } > (tripsDiscount.size - tripsDiscount.count { it }) }
                .keys

/*
 * Task #5. Find the most frequent trip duration among minute periods 0..9, 10..19, 20..29, and so on.
 * Return any period if many are the most frequent, return `null` if there're no trips.
 */
fun TaxiPark.findTheMostFrequentTripDurationPeriod(): IntRange? {
    fun durationToRange(duration: Int): IntRange {
        val divider = (duration / 10) * 10
        return (divider..divider + 9)
    }

    return trips.map { trip -> durationToRange(trip.duration) }
            .groupBy { it }
            .filter { (_, items) -> items.isNotEmpty() }
            .maxBy { (_, items) -> items.size }
            ?.component1()
}

/*
 * Task #6.
 * Check whether 20% of the drivers contribute 80% of the income.
 */
fun TaxiPark.checkParetoPrinciple(): Boolean {
    if (trips.isEmpty()) return false

    val totalIncome = trips.sumByDouble { it.cost }
    val drivers20Percent = (allDrivers.size * 0.2).roundToInt()

    val incomeByMostSuccessful = trips.groupBy { trip -> trip.driver }
            .map { (driver, trips) -> driver to trips.sumByDouble { it.cost } }
            .sortedByDescending { (_, income) -> income }
            .map { (_, income) -> income }
            .take(drivers20Percent)
            .sum()
    return incomeByMostSuccessful >= (0.8 * totalIncome)
}