import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import kotlin.test.assertNotEquals

class DomainModelTest {
    @ParameterizedTest
    @ValueSource(booleans = [false, true])
    fun testText(addCornflakes: Boolean) {
        val room = mutableListOf<PhysicalObject>()
        val arthur = HumanFromEarth("Arthur", room)
        val ford = HumanFromBetelgeuse("Ford", room)
        val fishContainer = FishContainer(ford.hands)
        val dentrassiLingerie = DentrassiLingerie(room)
        val skvornshellMattress = SkvornshellMattress(room)
        val yellowFish = Fish(fishContainer, "yellow")

        if (addCornflakes) {
            val cornflakes = BagOfCornflakes(room)
        }

        assertNotEquals(addCornflakes, arthur.isAnxious)
    }

    @Test
    fun testFishContainer() {
        val fishContainer = FishContainer(null)
        val fish = Fish(fishContainer, "qwerqwer")
        assert(fishContainer.contains(fish))
    }

    @Test
    fun testFishContainerIncorrect() {
        val fishContainer = FishContainer(null)
        assertThrows<TypeCastException> { DentrassiLingerie(fishContainer) }
    }
}
