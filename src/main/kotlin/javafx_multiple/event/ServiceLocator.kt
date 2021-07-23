package javafx_multiple.event

object ServiceLocator {
    private val services: MutableMap<Class<*>, Class<*>> = mutableMapOf();
    private val cache: MutableMap<Class<*>, Any?> = mutableMapOf();

    // Class<out T> prevent and ensures that
    // we're not going to pass something that doesn't implements T or extends T
    fun <T> registerService(service: Class<T>, provider: Class<out T>) {
        services.put(service, provider);
    }

    // getting service instance back
    fun <T> getService(type: Class<T>): T {
        val provider: Class<*>? = services.get(type);

        try {
            var instance: Any?;
            if (cache.containsKey(type)) {
                instance = cache.get(type);
            } else {
                instance = provider?.getConstructor()?.newInstance();
                cache.put(type, instance);
            }
            return type.cast(instance)
        } catch (ex: Exception) {
            throw IllegalArgumentException("Service is not available");
        }
    }
}