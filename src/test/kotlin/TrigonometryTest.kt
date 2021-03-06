import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import org.junit.jupiter.params.provider.ValueSource
import kotlin.math.PI
import kotlin.math.abs

private const val EPSILON = 0.02
private const val ESTIMATED_INFINITY = 1e9

class TrigonometryTest {
    @ParameterizedTest
    @CsvSource(
        value = [
            " -1.0471975511966   , -1.73205080756888     ",
            " -1.02974425867665  , -1.66427948235052     ",
            " -1.06465084371654  , -1.80404775527142     ",
            " -0.785398163397448 , -1                    ",
            " -0.767944870877505 , -0.965688774807074    ",
            " -0.802851455917391 , -1.03553031379057     ",
            " -0.523598775598299 , -0.577350269189626    ",
            " -0.506145483078356 , -0.554309051452769    ",
            " -0.541052068118242 , -0.60086061902756     ",
            "  0                 ,  0                    ",
            "  0.017453292519943 ,  0.017455064928218    ",
            " -0.017453292519943 , -0.017455064928218    ",
            "  0.523598775598299 ,  0.577350269189626    ",
            "  0.541052068118242 ,  0.60086061902756     ",
            "  0.506145483078356 ,  0.554309051452769    ",
            "  0.785398163397448 ,  1                    ",
            "  0.802851455917391 ,  1.03553031379057     ",
            "  0.767944870877505 ,  0.965688774807074    ",
            "  1.0471975511966   ,  1.73205080756888     ",
            "  1.06465084371654  ,  1.80404775527142     ",
            "  1.02974425867665  ,  1.66427948235052     ",
            "  2.0943951023932   , -1.73205080756888     ",
            "  2.11184839491314  , -1.66427948235052     ",
            "  2.07694180987325  , -1.80404775527142     ",
            "  2.35619449019234  , -1                    ",
            "  2.37364778271229  , -0.965688774807075    ",
            "  2.3387411976724   , -1.03553031379057     ",
            "  2.61799387799149  , -0.577350269189626    ",
            "  2.63544717051144  , -0.554309051452769    ",
            "  2.60054058547155  , -0.600860619027561    ",
            "  3.14159265358979  , -1.22464679914735E-16 ",
            "  3.15904594610974  ,  0.017455064928217    ",
            "  3.12413936106985  , -0.017455064928218    ",
            "  3.66519142918809  ,  0.577350269189626    ",
            "  3.68264472170804  ,  0.60086061902756     ",
            "  3.64773813666815  ,  0.554309051452769    ",
            "  3.92699081698724  ,  1                    ",
            "  3.94444410950718  ,  1.03553031379057     ",
            "  3.9095375244673   ,  0.965688774807074    ",
            "  4.18879020478639  ,  1.73205080756888     ",
            "  4.20624349730633  ,  1.80404775527142     ",
            "  4.17133691226645  ,  1.66427948235052     ",
            " -4.18879020478639  , -1.73205080756888     ",
            " -4.17133691226645  , -1.66427948235052     ",
            " -4.20624349730633  , -1.80404775527142     ",
            " -3.92699081698724  , -1                    ",
            " -3.9095375244673   , -0.965688774807074    ",
            " -3.94444410950718  , -1.03553031379057     ",
            " -3.66519142918809  , -0.577350269189626    ",
            " -3.64773813666815  , -0.554309051452769    ",
            " -3.68264472170804  , -0.60086061902756     ",
            " -3.14159265358979  ,  1.22464679914735E-16 ",
            " -3.12413936106985  ,  0.017455064928218    ",
            " -3.15904594610974  , -0.017455064928217    ",
            " -2.61799387799149  ,  0.577350269189626    ",
            " -2.60054058547155  ,  0.600860619027561    ",
            " -2.63544717051144  ,  0.554309051452769    ",
            " -2.35619449019234  ,  1                    ",
            " -2.3387411976724   ,  1.03553031379057     ",
            " -2.37364778271229  ,  0.965688774807075    ",
            " -2.0943951023932   ,  1.73205080756888     ",
            " -2.07694180987325  ,  1.80404775527142     ",
            " -2.11184839491314  ,  1.66427948235052     ",
        ]
    )
    fun testTg(x: Double, y: Double) {
        Assertions.assertTrue(abs(tg(x) - y) < EPSILON)
    }

    @ParameterizedTest
    @ValueSource(ints = [0, 1, -1])
    fun testTgHalfPi(k: Int) {
        Assertions.assertTrue(abs(tg((0.5 + k) * PI)) > ESTIMATED_INFINITY)
    }

    @Test
    fun testPositiveInfinity() {
        Assertions.assertEquals(Double.NaN, tg(Double.POSITIVE_INFINITY))
    }

    @Test
    fun testNegativeInfinity() {
        Assertions.assertEquals(Double.NaN, tg(Double.NEGATIVE_INFINITY))
    }
}
