class Node(
    var parent: Node? = null,
    var size: Int = 0,
    var values: Array<Int> = Array(3) { 0 },
    var children: Array<Node?> = Array(4) { null }
) {
    fun contains(v: Int): Boolean {
        var i = 0
        while (i < size && v < values[i]) i += 1
        if (v == values[i]) return true
        return children[i]?.contains(v) == true
    }

    fun insert(value: Int) {

    }

    fun remove(value: Int): Boolean = false
}

class BTree {

}
