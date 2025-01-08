package ru.webrelab.kie.cerealstorage

class CerealStorageImpl(
    override val containerCapacity: Float,
    override val storageCapacity: Float
) : CerealStorage {

    /**
     * Блок инициализации класса.
     * Выполняется сразу при создании объекта
     */
    init {
        require(containerCapacity >= 0) {
            "Ёмкость контейнера не может быть отрицательной"
        }
        require(storageCapacity >= containerCapacity) {
            "Ёмкость хранилища не должна быть меньше ёмкости одного контейнера"
        }
    }

    private val storage = mutableMapOf<Cereal, Float>()

    private fun checkAmount(amount: Float) {
        if(amount < 0) {
            throw IllegalArgumentException("The amount is negative!");
        }
    }

    private fun isStorageFull(): Boolean {
        return (storageCapacity - storage.size * containerCapacity) < containerCapacity
    }

    override fun addCereal(cereal: Cereal, amount: Float): Float {
        checkAmount(amount)

        if (storage[cereal] == null) {
            if (isStorageFull())  {
                throw IllegalStateException("Cannot create a new container!")
            }
        }

        val current = getAmount(cereal)
        val remain = containerCapacity - current

        return if (remain > amount) {
            storage[cereal] = current + amount
            0f
        } else {
            storage[cereal] = containerCapacity
            amount - remain
        }
    }

    override fun getCereal(cereal: Cereal, amount: Float): Float {
        checkAmount(amount)
        val current = getAmount(cereal)

        return if (current > amount) {
            storage[cereal] = current - amount
            amount
        } else {
            storage[cereal] = 0f
            current
        }
    }

    override fun getAmount(cereal: Cereal): Float {
        return storage.getOrDefault(cereal, 0f)
    }

    override fun getSpace(cereal: Cereal): Float {
        return if ((storage[cereal] == null) && (isStorageFull())) {
            0f
        } else {
            containerCapacity - getAmount(cereal)
        }
    }

    override fun removeContainer(cereal: Cereal): Boolean {
        return if (getAmount(cereal) > 0) {
            false
        } else {
            storage.remove(cereal)
            true
        }
    }

    override fun toString(): String {
        return storage.toString()
    }
}
