package ru.webrelab.kie.cerealstorage

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class CerealStorageImplTest {
    private val storage = CerealStorageImpl(10f, 20f)

    @Test
    fun `should throw IllegalArgumentException if containerCapacity is negative`() {
        assertThrows(IllegalArgumentException::class.java) {
            CerealStorageImpl(-4f, 10f)
        }
    }

    @Test
    fun `should throw IllegalArgumentException if containerCapacity is more than storageCapacity`() {
        assertThrows(IllegalArgumentException::class.java) {
            CerealStorageImpl(40f, 10f)
        }
    }

    @Test
    fun `addCereal should throw IllegalArgumentException if the amount is negative`() {
        assertThrows(IllegalArgumentException::class.java) {
            storage.addCereal(Cereal.BUCKWHEAT, -10f)
        }
    }

    @Test
    fun `addCereal should throw IllegalStateException if we cannot add a container`() {
        storage.addCereal(Cereal.BUCKWHEAT, 20f)
        storage.addCereal(Cereal.RICE, 10f)
        assertThrows(IllegalStateException::class.java) {
            storage.addCereal(Cereal.MILLET, 5f)
        }
    }

    @Test
    fun `addCereal should return a remainder`() {
        val test1 = storage.addCereal(Cereal.RICE, 8f)
        assertEquals(0f, test1, 0.01f)
        val test2 = storage.addCereal(Cereal.RICE, 7f)
        assertEquals(5f, test2, 0.01f)
    }

    @Test
    fun `getCereal should throw IllegalArgumentException if the amount is negative`() {
        val test1 = storage.addCereal(Cereal.BUCKWHEAT, 8f)
        assertThrows(IllegalArgumentException::class.java) {
            storage.getCereal(Cereal.BUCKWHEAT, -10f)
        }
    }

    @Test
    fun `getCereal should return a remainder`() {
        storage.addCereal(Cereal.RICE, 8f)
        val test1 = storage.getCereal(Cereal.RICE, 5f)
        assertEquals(5f, test1, 0.01f)
        val test2 = storage.getCereal(Cereal.RICE, 5f)
        assertEquals(3f, test2, 0.01f)
    }

    @Test
    fun `removeContainer should return false if not empty`() {
        storage.addCereal(Cereal.BULGUR, 8f)
        val test1 = storage.removeContainer(Cereal.BULGUR)
        assertFalse(test1)
        storage.getCereal(Cereal.BULGUR, 8f)
        val test2 = storage.removeContainer(Cereal.BULGUR)
        assertTrue(test2)
    }

    @Test
    fun `getAmount should return correct amount`() {
        val test1 = storage.getAmount(Cereal.MILLET)
        assertEquals(0f, test1, 0.01f)
        storage.addCereal(Cereal.MILLET, 8f)
        val test2 = storage.getAmount(Cereal.MILLET)
        assertEquals(8f, test2, 0.01f)
        storage.addCereal(Cereal.MILLET, 8f)
        val test3 = storage.getAmount(Cereal.MILLET)
        assertEquals(10f, test3, 0.01f)
    }

    @Test
    fun `getSpace should return correct amount`() {
        storage.addCereal(Cereal.BUCKWHEAT, 8f)
        val test1 = storage.getSpace(Cereal.BUCKWHEAT)
        assertEquals(2f, test1, 0.01f)
        storage.addCereal(Cereal.BUCKWHEAT, 8f)
        val test2 = storage.getSpace(Cereal.BUCKWHEAT)
        assertEquals(0f, test2, 0.01f)
    }

    @Test
    fun `getSpace should return 0 if the storage is full`() {
        storage.addCereal(Cereal.PEAS, 1f)
        storage.addCereal(Cereal.BUCKWHEAT, 1f)
        val test = storage.getSpace(Cereal.MILLET)
        assertEquals(0f, test, 0.01f)
    }

    @Test
    fun `toString should return correct information`() {
        storage.addCereal(Cereal.PEAS, 1.5f)
        storage.addCereal(Cereal.BUCKWHEAT, 7.8f)
        assertEquals("{PEAS=1.5, BUCKWHEAT=7.8}", storage.toString())
    }
}
