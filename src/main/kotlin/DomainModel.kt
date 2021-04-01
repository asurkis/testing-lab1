interface ObjectFromEarth

typealias Location = MutableCollection<PhysicalObject>

open class PhysicalObject(location: Location?) {
    var location: Location? = null

    init {
        moveTo(location)
    }

    fun moveTo(anotherLocation: Location?) {
        location?.remove(this)
        anotherLocation?.add(this)
        location = anotherLocation
    }
}

open class PhysicalContainer(location: Location?) : PhysicalObject(location), Location {
    val elements: Location = mutableListOf()

    override val size: Int
        get() = elements.size

    override fun contains(element: PhysicalObject) = elements.contains(element)
    override fun containsAll(elements: Collection<PhysicalObject>) = this.elements.containsAll(elements)
    override fun isEmpty() = elements.isEmpty()
    override fun add(element: PhysicalObject) = elements.add(element)
    override fun addAll(elements: Collection<PhysicalObject>) = this.elements.addAll(elements)
    override fun clear() = elements.clear()
    override fun iterator() = elements.iterator()
    override fun remove(element: PhysicalObject) = elements.remove(element)
    override fun removeAll(elements: Collection<PhysicalObject>) = this.elements.removeAll(elements)
    override fun retainAll(elements: Collection<PhysicalObject>) = this.elements.retainAll(elements)
}

open class Human(val name: String, location: Location?) : PhysicalObject(location) {
    var lookingAt: PhysicalObject? = null
    open val isAnxious get() = false
    open val isBlinking get() = true
    val hands = PhysicalContainer(null)
}

class HumanFromBetelgeuse(name: String, location: Location?) : Human(name, location)

class HumanFromEarth(name: String, location: Location?) : Human(name, location),
    ObjectFromEarth {
    override val isAnxious get() = location?.all { it == this || it !is ObjectFromEarth } != false
    override val isBlinking get() = lookingAt is HumanFromBetelgeuse
}

class DentrassiLingerie(location: Location?) : PhysicalObject(location)
class SkvornshellMattress(location: Location?) : PhysicalObject(location)
class Fish(location: Location?, color: String) : PhysicalObject(location)

class FishContainer(location: Location?) : PhysicalContainer(location) {
    override fun add(element: PhysicalObject): Boolean {
        if (element !is Fish) throw TypeCastException("FishContainer can only contain Fish")
        return super.add(element)
    }

    override fun addAll(elements: Collection<PhysicalObject>): Boolean {
        if (!elements.all { it is Fish }) throw TypeCastException("FishContainer can only contain Fish")
        return super.addAll(elements)
    }
}

class BagOfCornflakes(location: Location?) : PhysicalObject(location), ObjectFromEarth
