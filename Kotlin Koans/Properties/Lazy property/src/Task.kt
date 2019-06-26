class LazyProperty(val initializer: () -> Int) {
    /* TODO */
    val lazy: Int by lazy {
            initializer()
        }
}
