package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    private static class Node<T> {
        private Node<T> next;
        private T value;
        private Node<T> previous;

        public Node(Node<T> next, T value, Node<T> previous) {
            this.next = next;
            this.value = value;
            this.previous = previous;
        }
    }

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(null, value, null);
        if (size == 0) {
            head = newNode;
        } else {
            newNode.previous = tail;
            tail.next = newNode;
        }
        tail = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkIndex(index);
<<<<<<< HEAD
        if (index == size) {
            addLast(value);
=======
        Node<T> newNode = new Node<>(null, value, null);
        if (size == 0) {
            head = tail = newNode;
        } else if (index == size) {
            newNode.previous = tail;
            tail.next = newNode;
            tail = newNode;
>>>>>>> origin/implement
        } else if (index == 0) {
            newNode.next = head;
            head.previous = newNode;
            head = newNode;
        } else {
            Node<T> nodeByIndex = nodeSearch(index);
            Node<T> previousNode = nodeByIndex.previous;
            newNode.next = nodeByIndex;
            nodeByIndex.previous = newNode;
            newNode.previous = previousNode;
            previousNode.next = newNode;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T element : list) {
            add(element);
        }
    }

    @Override
    public T get(int index) {
<<<<<<< HEAD
        checkIndexOutOfBoundsInclusiveSize(index);
        return getNode(index).value;
=======
        checkByIndexEqualSize(index);
        return nodeSearch(index).value;
>>>>>>> origin/implement
    }

    @Override
    public T set(T value, int index) {
<<<<<<< HEAD
        checkIndexOutOfBoundsInclusiveSize(index);
        Node<T> current = getNode(index);
        T oldValue = current.value;
        current.value = value;
=======
        checkByIndexEqualSize(index);
        Node<T> nodeSearch = nodeSearch(index);
        T oldValue = nodeSearch.value;
        nodeSearch.value = value;
>>>>>>> origin/implement
        return oldValue;
    }

    @Override
    public T remove(int index) {
<<<<<<< HEAD
        checkIndexOutOfBoundsInclusiveSize(index);
        Node<T> current = getNode(index);
        unlink(current);
        return current.value;
=======
        checkByIndexEqualSize(index);
        Node<T> deletedNode = nodeSearch(index);
        if (index == 0) {
            head = head.next;
        } else if (index == size - 1) {
            tail = tail.previous;
        } else {
            deletedNode.previous.next = deletedNode.next;
            deletedNode.next.previous = deletedNode.previous;
        }
        size--;
        return deletedNode.value;
>>>>>>> origin/implement
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = head;
        while (currentNode != null) {
            if (currentNode.value == object || (currentNode.value != null
                    && currentNode.value.equals(object))) {
                if (currentNode == head) {
                    head = head.next;
                } else if (currentNode == tail) {
                    tail = tail.previous;
                } else {
                    currentNode.previous.next = currentNode.next;
                    currentNode.next.previous = currentNode.previous;
                }
                size--;
                return true;
            }
            currentNode = currentNode.next;
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private void checkByIndexEqualSize(int index) {
        if (index >= size || index < 0) {
            trowException(index);
        }
    }

    private void checkIndex(int index) {
        if (index > size || index < 0) {
            trowException(index);
        }
    }

    private void trowException(int index) {
        throw new IndexOutOfBoundsException("Not a valid index - ["
                + index + "], outside the"
                + " limit of the size ["
                + size + "]");
    }

<<<<<<< HEAD
    private void unlink(Node<T> node) {
        if (node == null) {
            return;
        }
        Node<T> prev = node.prev;
        Node<T> next = node.next;
        if (prev == null) {
            head = next;
        } else {
            prev.next = next;
            node.prev = null;
        }
        if (next == null) {
            tail = prev;
        } else {
            next.prev = prev;
            node.next = null;
        }
        size--;
    }

    private void checkIndex(int index) {
        if (index < 0 || index > size) {
            throwIndexOutOfBoundsEx(index);
        }
    }

    private void checkIndexOutOfBoundsInclusiveSize(int index) {
        if (index < 0 || index >= size) {
            throwIndexOutOfBoundsEx(index);
        }
    }

    private void throwIndexOutOfBoundsEx(int index) {
        throw new IndexOutOfBoundsException(
                "Index is out of bounds, index: [" + index
                        + "] when size: ["
                        + size + "]");
    }

    private static class Node<T> {
        private Node<T> prev;
        private T value;
        private Node<T> next;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
=======
    private Node<T> nodeSearch(int index) {
        int indexNode = 0;
        Node<T> currentNode = head;
        while (indexNode != index) {
            currentNode = currentNode.next;
            indexNode++;
>>>>>>> origin/implement
        }
        return currentNode;
    }
}
