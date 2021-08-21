package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> firstNode;
    private Node<T> lastNode;
    private int size;

    private class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }

    @Override
    public void add(T value) {
        Node<T> addedNode = new Node<>(lastNode, value, null);
        if (size > 0) {
            lastNode.next = addedNode;
        } else {
            firstNode = addedNode;
        }
        lastNode = addedNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        checkIndex(index);
        if (index == 0) {
            Node<T> addedNode = new Node<>(null, value, firstNode);
            firstNode.prev = addedNode;
            firstNode = addedNode;
        } else {
            Node<T> currentNode = getNode(index);
            Node<T> prevNode = currentNode.prev;
            Node<T> addedNode = new Node<>(prevNode, value, currentNode);
            currentNode.prev = addedNode;
            prevNode.next = addedNode;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T t : list) {
            add(t);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return getNode(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> currentNode = getNode(index);
        T oldValue = currentNode.value;
        currentNode.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        return unlink(getNode(index));
    }

    @Override
    public boolean remove(T object) {
        Node<T> node = getNode(object);
        if (node != null) {
            unlink(node);
            return true;
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

    private void checkIndex(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Invalid index, must be 0 < i < " + size);
        }
    }

    private boolean indexInSecondHalf(int index) {
        return index >= size / 2;
    }

    private Node<T> getNode(int index) {
        Node<T> neededNode = firstNode;
        if (indexInSecondHalf(index)) {
            neededNode = lastNode;
            while (index < size - 1) {
                neededNode = neededNode.prev;
                index++;
            }
        } else {
            while (index > 0) {
                neededNode = neededNode.next;
                index--;
            }
        }
        return neededNode;
    }

    private Node<T> getNode(T value) {
        for (int i = 0; i < size; i++) {
            if ((getNode(i).value == value) || (value != null && value.equals(getNode(i).value))) {
                return getNode(i);
            }
        }
        return null;
    }

    private T unlink(Node<T> node) {
        Node<T> nextNode = node.next;
        Node<T> prevNode = node.prev;
        if (prevNode == null) {
            firstNode = nextNode;
        } else {
            prevNode.next = nextNode;
        }
        if (nextNode == null) {
            lastNode = prevNode;
        } else {
            nextNode.prev = prevNode;
        }
        size--;
        return node.value;
    }
}
