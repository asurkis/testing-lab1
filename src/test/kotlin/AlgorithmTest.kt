import org.junit.jupiter.api.Assertions
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import kotlin.math.floor
import kotlin.math.log2

class AlgorithmTest {
    @ParameterizedTest
    @ValueSource(ints = [0, 1, 2, -1, -2])
    fun testContainsOne(key: Int) {
        val btree = BTree()
        btree.insert(key)
        Assertions.assertTrue(btree.contains(key))
    }

    @ParameterizedTest
    @ValueSource(ints = [0, 1, 2, 3, 10, 100])
    fun testHeightCalculation(n: Int) {
        val btree = BTree()
        for (i in 1..n) btree.insert(i)
        Assertions.assertEquals(btree.realHeight, btree.height)
    }

    @ParameterizedTest
    @ValueSource(ints = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 31, 32, 33, 63, 64, 65, 100])
    fun testHeightOneValue(n: Int) {
        val btree = BTree()
        repeat(n) { btree.insert(0) }
        val expected = floor(log2((n + 1).toDouble())).toInt()
        Assertions.assertEquals(expected, btree.height)
    }

    @ParameterizedTest
    @ValueSource(ints = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 31, 32, 33, 63, 64, 65, 100])
    fun testHeightDifferentValues(n: Int) {
        val btree = BTree()
        for (i in 1..n) btree.insert(i)
        val expected = floor(log2(n.toDouble())).toInt()
        Assertions.assertTrue(expected <= btree.height && btree.height <= expected + 1)
    }

    @ParameterizedTest
    @ValueSource(ints = [0, 1, 2, 3, 10, 100])
    fun testRemoving(n: Int) {
        val btree = BTree()
        for (i in 1..n) btree.insert(i)
        for (i in 1..n) btree.remove(i)
        Assertions.assertEquals(0, btree.size)
    }

    @ParameterizedTest
    @ValueSource(ints = [0, 1, 2, 3, 10, 100])
    fun testRemovingHalf(n: Int) {
        val btree = BTree()
        for (i in 1..n) btree.insert(i)
        for (i in 1..n / 2) btree.remove(i)
        Assertions.assertEquals((n + 1) / 2, btree.size)
    }

    @ParameterizedTest
    @ValueSource(ints = [0, 1, 2, 3, 10, 100])
    fun testRemovingSizeCalculations(n: Int) {
        val btree = BTree()
        for (i in 1..n) btree.insert(i)
        for (i in 1..n / 2) btree.remove(i)
        Assertions.assertEquals(btree.realSize, btree.size)
    }
}
