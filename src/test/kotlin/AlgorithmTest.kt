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
    fun testHeightValue(n: Int) {
        val btree = BTree()
        val expectedHeight = floor(log2((n + 1).toDouble())).toInt()
        if (n >= 9) {
            println("1!1")
        }
        repeat(n) { btree.insert(0) }
        btree.print()
        Assertions.assertEquals(expectedHeight, btree.height)
    }
}
