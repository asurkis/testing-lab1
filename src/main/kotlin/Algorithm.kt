val NODE_CAPACITY = 3

class BTree {
    private class Node(
        var parent: Node? = null,
        var size: Int = 0,
        var keys: Array<Int> = Array(NODE_CAPACITY) { 0 },
        var children: Array<Node?> = Array(NODE_CAPACITY + 1) { null }
    ) {
        val isLeaf get() = children[0] == null
        val height: Int get() = 1 + (children[0]?.height ?: 0)

        fun findGeq(key: Int): Int {
            var i = 0
            while (i < size && keys[i] < key) ++i
            return i
        }

        fun contains(key: Int): Boolean {
            val pos = findGeq(key)
            if (keys[pos] == key) return true
            return children[pos]?.contains(key) == true
        }

        fun insert(key: Int): Boolean {
            if (size == NODE_CAPACITY) return false
            val pos = findGeq(key)
            for (i in size++ downTo pos + 1) {
                keys[i] = keys[i - 1]
                children[i] = children[i - 1]
            }
            keys[pos] = key
            children[pos] = null
            return true
        }

        fun delete(key: Int): Boolean {
            val pos = findGeq(key)
            if (pos == size || keys[pos] != key) {
                return false
            }
            for (i in pos until --size) {
                keys[i] = keys[i + 1]
                children[i] = children[i + 1]
            }
            keys[size] = 0
            children[size] = null
            return true
        }

        fun split() {
            val left = Node(parent = this, size = size / 2)
            val right = Node(parent = this, size = (size - 1) / 2)

            left.keys[0] = keys[0]
            left.children[0] = children[0]
            left.children[1] = children[1]
            children[0]?.parent = left
            children[1]?.parent = left

            right.keys[0] = keys[2]
            right.children[0] = children[2]
            right.children[1] = children[3]
            children[2]?.parent = right
            children[3]?.parent = right

            size = 1
            keys[0] = keys[1]
            keys[1] = 0
            keys[2] = 0
            children[0] = left
            children[1] = right
            children[2] = null
            children[3] = null
        }
    }

    private var root = Node()
    var height = 1
        private set
    val realHeight get() = root.height
    var size = 0
        private set

    fun contains(key: Int) = root.contains(key)

    fun insert(key: Int) {
        ++size
        var node = root
        while (!node.isLeaf) {
            val pos = node.findGeq(key)
            node = node.children[pos]!!
        }
        node.insert(key)
        if (node.size == NODE_CAPACITY) {
            split(node)
        }
    }

    private fun split(node: Node) {
        node.split()
        val parent = node.parent
        if (parent == null) {
            ++height
            return
        }
        node.children[0]!!.parent = parent
        node.children[1]!!.parent = parent

        var pos = 0
        while (pos < parent.size && parent.children[pos] != node) ++pos
        assert(parent.children[pos] == node)

        parent.children[parent.size + 1] = parent.children[parent.size]
        for (i in parent.size++ downTo pos + 1) {
            parent.keys[i] = parent.keys[i - 1]
            parent.children[i] = parent.children[i - 1]
        }

        assert(parent.children[pos] == node)
        assert(parent.children[pos + 1] == node)
        parent.children[pos] = node.children[0]
        parent.children[pos + 1] = node.children[1]
        parent.keys[pos] = node.keys[0]

        if (parent.size == NODE_CAPACITY) split(parent)
    }

    private fun merge(node: Node) {

    }

    fun print() = printNode(root, 0)

    private fun printNode(node: Node, indent: Int) {
//        print(" ".repeat(indent))
        for (i in 0 until node.size) {
            print("${node.keys[i]} ")
        }
        println()
        for (i in 0..node.size) {
            print(" ".repeat(7 * indent))
            print("[$i] => ")
            val child = node.children[i]
            if (child != null) {
                printNode(child, indent + 1)
            } else {
                println("null")
            }
        }
    }
}
