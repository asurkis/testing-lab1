import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import org.junit.jupiter.params.provider.ValueSource
import kotlin.math.PI
import kotlin.math.abs

class TrigonometryTest {
    @ParameterizedTest
    @CsvSource(
        value = [
            // Добавить небольшие сдвиги
            // tg (-x) = tg x
            // tg 0 = 0
            " 0.000000000,  0.000000000, 0.02",
            // tg (pi/6) = 1 / sqrt 3
            " 0.523598775,  0.577350269, 0.02",
            "-0.523598775, -0.577350269, 0.02",
            // tg (pi/4) = 1
            " 0.785398163,  1.000000000, 0.02",
            "-0.785398163, -1.000000000, 0.02",
            // tg (pi/3) = sqrt 3
            " 1.047197551,  1.732050808, 0.02",
            "-1.047197551, -1.732050808, 0.02",
            // tg (x + pi) = tg x
            // fails
            " 3.141592654,  0.000000000, 0.02",
            "-3.141592654,  0.000000000, 0.02",
            " 3.665191429,  0.577350269, 0.02",
            "-3.665191429, -0.577350269, 0.02",
            " 3.926990817,  1.000000000, 0.02",
            "-3.926990817, -1.000000000, 0.02",
            " 4.188790205,  1.732050808, 0.02",
            "-4.188790205, -1.732050808, 0.02",
        ]
    )
    fun testTg(x: Double, y: Double, epsilon: Double) {
        assert(abs(tg(x) - y) < epsilon)
    }

    @ParameterizedTest
    @ValueSource(ints = [0, 1, -1])
    fun testTgHalfPi(k: Int) {
        assert(abs(tg((0.5 + k) * PI)) > 1e9)
    }
}
