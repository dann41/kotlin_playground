package games.game2048

/*
 * This function moves all the non-null elements to the beginning of the list
 * (by removing nulls) and merges equal elements.
 * The parameter 'merge' specifies the way how to merge equal elements:
 * it returns a new element that should be present in the resulting list
 * instead of two merged elements.
 *
 * If the function 'merge("a")' returns "aa",
 * then the function 'moveAndMergeEqual' transforms the input in the following way:
 *   a, a, b -> aa, b
 *   a, null -> a
 *   b, null, a, a -> b, aa
 *   a, a, null, a -> aa, a
 *   a, null, a, a -> aa, a
 *
 * You can find more examples in 'TestGame2048Helper'.
*/
fun <T : Any> List<T?>.moveAndMergeEqual(merge: (T) -> T): List<T> {
    // TODO find an elegant way to do that
    val result = mutableListOf<T>()

    val filteredList = this.filterNotNull()

    if (filteredList.size < 2)
        return filteredList

    var i = 0
    var j = 1

    while ( i < filteredList.size && j < filteredList.size) {
        if (filteredList[i] == filteredList[j]) {
            result.add(merge(filteredList[i]))
            // Move to next pair
            i = j + 1
            j = i + 1
        } else {
            result.add(filteredList[i])
            ++i
            ++j
        }
    }

    if (i == filteredList.size - 1) {
        result.add(filteredList[i])
    }

    return result
}

