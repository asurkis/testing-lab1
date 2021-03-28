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
        val totalSize: Int get() = size + (0..size).sumOf { children[it]?.totalSize ?: 0 }

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

        fun leafInsert(key: Int): Boolean {
            if (size == NODE_CAPACITY) return false
            val pos = findGeq(key)
            for (i in size++ downTo pos + 1)
                keys[i] = keys[i - 1]
            keys[pos] = key
            return true
        }

        fun remove(pos: Int) {
            for (i in pos until --size) {
                keys[i] = keys[i + 1]
                children[i + 1] = children[i + 2]
            }
            children[size + 1] = null
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

        fun pullMerge(pos: Int) {
            val left = children[pos]!!
            val right = children[pos + 1]!!
            assert(left.size < NODE_CAPACITY)
            assert(pos < NODE_CAPACITY)
            left.keys[left.size++] = keys[pos]
            for (i in 0 until right.size) {
                val child = right.children[i]
                left.children[left.size] = child
                child?.parent = left
                left.keys[left.size++] = right.keys[i]
            }
            val child = right.children[right.size]
            left.children[left.size] = right.children[right.size]
            child?.parent = left
            right.parent = null
            remove(pos)
        }

        fun parentPos(): Int? {
            val p = parent ?: return null
            var result = 0
            while (p.children[result] != this)
                ++result
            return result
        }
    }

    private var root = Node()
    var height = 1
        private set
    val realHeight get() = root.height
    var size = 0
        private set
    val realSize get() = root.totalSize
    val invariantCorrect get() = checkInvariant(root)

    private fun checkInvariant(node: Node): Boolean {
        for (i in 1 until node.size) {
            if (node.keys[i] < node.keys[i - 1])
                return false
        }
        if (!node.isLeaf) {
            for (i in 0 until node.size) {
                val child = node.children[i]!!
                if (child.keys[child.size - 1] > node.keys[i])
                    return false
            }
            for (i in 1..node.size) {
                val child = node.children[i]!!
                if (child.keys[0] < node.keys[i - 1])
                    return false
            }
            for (i in 0..node.size) {
                if (!checkInvariant(node.children[i]!!))
                    return false
            }
        }
        return true
    }

    fun contains(key: Int) = root.contains(key)

    fun insert(key: Int) {
        ++size
        var node = root
        while (!node.isLeaf) {
            val pos = node.findGeq(key)
            node = node.children[pos]!!
        }
        node.leafInsert(key)
        if (node.size == NODE_CAPACITY) {
            split(node)
        }
    }

    fun remove(key: Int) {
        var node = root
        while (!node.isLeaf) {
            val pos = node.findGeq(key)
            if (pos < node.size && node.keys[pos] == key)
                break
            node = node.children[pos]!!
        }
        val deletePos = node.findGeq(key)
        if (node.keys[deletePos] != key)
            return
        if (node.isLeaf) {
            removeFromLeaf(node, deletePos)
            return
        }
        var prev = node.children[deletePos]!!
        while (!prev.isLeaf)
            prev = prev.children[prev.size]!!
        node.keys[deletePos] = prev.keys[prev.size - 1]
        removeFromLeaf(prev, prev.size - 1)
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

        parent.children[parent.size + 1] = parent.children[parent.size]
        for (i in parent.size++ downTo pos + 1) {
            parent.keys[i] = parent.keys[i - 1]
            parent.children[i] = parent.children[i - 1]
        }

        parent.children[pos] = node.children[0]
        parent.children[pos + 1] = node.children[1]
        parent.keys[pos] = node.keys[0]

        if (parent.size == NODE_CAPACITY) split(parent)
    }

    private fun removeFromLeaf(node: Node, pos: Int) {
        --size
        node.remove(pos)
        if (node.size == 0)
            emptyMerge(node)
    }

    private fun emptyMerge(node: Node) {
        val parent = node.parent
        if (parent == null) {
            if (!node.isLeaf) {
                root = node.children[0]!!
                root.parent = null
                --height
            }
            return
        }

        val parentPos = node.parentPos()!!
        val left = if (parentPos > 0) parent.children[parentPos - 1] else null
        val right = if (parentPos < parent.size) parent.children[parentPos + 1] else null

        if (left != null && left.size > 1) {
            node.children[1] = node.children[0]
            node.children[0] = left.children[left.size]
            node.children[0]?.parent = node
            node.size = 1

            node.keys[0] = parent.keys[parentPos - 1]
            parent.keys[parentPos - 1] = left.keys[left.size - 1]
            left.remove(left.size - 1)
        } else if (right != null && right.size > 1) {
            node.children[1] = right.children[0]
            node.children[1]?.parent = node
            node.size = 1

            node.keys[0] = parent.keys[parentPos]
            parent.keys[parentPos] = right.keys[0]
            right.children[0] = right.children[1]
            right.remove(0)
        } else if (left != null) {
            parent.pullMerge(parentPos - 1)
        } else {
            parent.pullMerge(parentPos)
        }

        if (parent.size == 0)
            emptyMerge(parent)
    }

    /*fun print() = printNode(root, 0)
    private fun printNode(node: Node, indent: Int) {
        print("(${node.size}, ${node.totalSize})")
        for (i in 0 until node.size) {
            print(" ${node.keys[i]}")
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
    }*/
}
