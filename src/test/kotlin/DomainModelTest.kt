import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

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

        arthur.lookingAt = ford
        Assertions.assertNotEquals(addCornflakes, arthur.isAnxious)
    }

    @ParameterizedTest
    @ValueSource(booleans = [false, true])
    fun testBlinking(lookingAtFord: Boolean) {
        val room = mutableListOf<PhysicalObject>()
        val arthur = HumanFromEarth("Arthur", room)
        val ford = HumanFromBetelgeuse("Ford", room)
        val fishContainer = FishContainer(ford.hands)
        val dentrassiLingerie = DentrassiLingerie(room)
        val skvornshellMattress = SkvornshellMattress(room)
        val yellowFish = Fish(fishContainer, "yellow")

        if (lookingAtFord) {
            arthur.lookingAt = ford
        }

        Assertions.assertEquals(lookingAtFord, arthur.isBlinking)
    }

    @ParameterizedTest
    @ValueSource(booleans = [false, true])
    fun testBlinkingFordOnly(lookingAtFord: Boolean) {
        val room = mutableListOf<PhysicalObject>()
        val arthur = HumanFromEarth("Arthur", room)
        val ford = HumanFromBetelgeuse("Ford", room)

        if (lookingAtFord) {
            arthur.lookingAt = ford
        }

        Assertions.assertEquals(lookingAtFord, arthur.isBlinking)
    }

    @ParameterizedTest
    @ValueSource(booleans = [false, true])
    fun testOnlyCornflakes(addCornflakes: Boolean) {
        val room = mutableListOf<PhysicalObject>()
        val arthur = HumanFromEarth("Arthur", room)
        if (addCornflakes) {
            val cornflakes = BagOfCornflakes(room)
        }
        Assertions.assertNotEquals(addCornflakes, arthur.isAnxious)
    }

    @ParameterizedTest
    @ValueSource(booleans = [false, true])
    fun testTwoHumans(addHuman: Boolean) {
        val room = mutableListOf<PhysicalObject>()
        val arthur = HumanFromEarth("Arthur", room)
        if (addHuman) {
            val vladimir = HumanFromEarth("Vladimir", room)
        }
        Assertions.assertNotEquals(addHuman, arthur.isAnxious)
    }

    @ParameterizedTest
    @ValueSource(booleans = [false, true])
    fun testTwoHumansBlinking(addHuman: Boolean) {
        val room = mutableListOf<PhysicalObject>()
        val arthur = HumanFromEarth("Arthur", room)
        if (addHuman) {
            val vladimir = HumanFromEarth("Vladimir", room)
            arthur.lookingAt = vladimir
        }
        Assertions.assertFalse(arthur.isBlinking)
    }

    @Test
    fun testFishContainer() {
        val fishContainer = FishContainer(null)
        val fish = Fish(fishContainer, "qwerqwer")
        Assertions.assertTrue(fishContainer.contains(fish))
    }

    @Test
    fun testFishContainerIncorrect() {
        val fishContainer = FishContainer(null)
        Assertions.assertThrows(TypeCastException::class.java) { DentrassiLingerie(fishContainer) }
    }
}
