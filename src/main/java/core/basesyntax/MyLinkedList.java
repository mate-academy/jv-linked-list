package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private static final String EXCEPTION_MESSAGE = "Index out of bounds";
    private Node<T> firstNode;
    private Node<T> lastNode;
    private int size;

    @Override
    public boolean add(T value) {
        addNode(new Node<>(lastNode, value, null));
        return true;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException(EXCEPTION_MESSAGE + index);
        }
        if (index == size) {
            add(value);
        } else {
            addElementByIndex(value, index);
        }
    }

    @Override
    public boolean addAll(List<T> list) {
        for (T v : list) {
            add(v);
        }
        return true;
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return findNodeByIndex(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> node = findNodeByIndex(index);
        T oldValue = node.item;
        node.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        return unlink(findNodeByIndex(index));
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = firstNode;
        for (int i = 0; i < size; i++) {
            if (object == current.item || object != null
                    && object.equals(current.item)) {
                unlink(current);
                return true;
            }
            current = current.next;
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

    private boolean isElementIndex(int index) {
        return index >= 0 && index < size;
    }

    private void checkIndex(int index) {
        if (!isElementIndex(index)) {
            throw new IndexOutOfBoundsException();
        }
    }

    private void addNode(Node<T> node) {
        if (firstNode == null) {
            firstNode = node;
        } else {
            lastNode.next = node;
        }
        lastNode = node;
        size++;
    }

    private void addElementByIndex(T value, int index) {
        Node<T> nodeIndex = findNodeByIndex(index);
        Node<T> newNode = new Node<>(nodeIndex.prev, value, nodeIndex);
        nodeIndex.prev = newNode;
        if (newNode.prev == null) {
            firstNode = newNode;
        } else {
            newNode.prev.next = newNode;
        }
        size++;
    }

    private Node<T> findNodeByIndex(int index) {
        if (index <= size / 2) {
            Node<T> currentNode = firstNode;
            for (int i = 0; i < index; i++) {
                currentNode = currentNode.next;
            }
        }
        Node<T> currentNode = lastNode;
        for (int i = 0; i < size - index - 1; i++) {
            currentNode = currentNode.prev;
        }
        return currentNode;
    }

    private T unlink(Node<T> node) {
        Node<T> nextNode = node.next;
        Node<T> prevNode = node.prev;
        if (prevNode == null) {
            firstNode = nextNode;
        } else {
            prevNode.next = nextNode;
            node.prev = null;
        }
        if (nextNode == null) {
            lastNode = prevNode;
        } else {
            nextNode.prev = prevNode;
            node.next = null;
        }
        size--;
        return node.item;
    }

    private static class Node<T> {
        private T item;
        private Node<T> prev;
        private Node<T> next;

        public Node(Node<T> prev, T item, Node<T> next) {
            this.prev = prev;
            this.item = item;
            this.next = next;
        }
    }
}
