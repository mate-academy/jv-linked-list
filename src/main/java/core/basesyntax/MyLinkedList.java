package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size = 0;

    @Override
    public void add(T value) {
        linkTail(value);
    }

    @Override
    public void add(T value, int index) {
        checkIndexForOperation(index);
        if (index == 0) {
            linkHead(value);
        } else if (index == size) {
            linkTail(value);
        } else {
            linkByIndex(value, index);
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T t : list) {
            linkTail(t);
        }
    }

    @Override
    public T get(int index) {
        checkIndexForElement(index);
        return nodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkIndexForElement(index);
        T valueForReturn = nodeByIndex(index).value;
        nodeByIndex(index).value = value;
        return valueForReturn;
    }

    @Override
    public T remove(int index) {
        checkIndexForElement(index);
        return unlink(nodeByIndex(index));
    }

    @Override
    public boolean remove(T object) {
        Node<T> forSearching = head;
        while (forSearching != null) {
            if (forSearching.value == object
                    || object != null && object.equals(forSearching.value)) {
                unlink(forSearching);
                return true;
            }
            forSearching = forSearching.next;
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

    private static class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.value = value;
            this.prev = prev;
            this.next = next;
        }
    }

    private T unlink(Node<T> node) {
        T value = node.value;
        Node<T> next = node.next;
        Node<T> prev = node.prev;

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
        node.value = null;
        size--;
        return value;
    }

    private void checkIndexForOperation(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index out of bound");
        }
    }

    private void checkIndexForElement(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bound. There are " + size
                    + " elements. You can use index from 0 to " + (size - 1));
        }
    }

    private Node<T> nodeByIndex(int index) {
        Node<T> nodeByIndex;
        if (index < (size >> 1)) {
            nodeByIndex = head;
            for (int i = 0; i < index; i++) {
                nodeByIndex = nodeByIndex.next;
            }
            return nodeByIndex;
        } else {
            nodeByIndex = tail;
            for (int i = size - 1; i > index; i--) {
                nodeByIndex = nodeByIndex.prev;
            }
            return nodeByIndex;
        }
    }

    private void linkHead(T value) {
        Node<T> first = head;
        Node<T> newNode = new Node<>(null, value, first);
        head = newNode;
        if (first == null) {
            tail = newNode;
        } else {
            first.prev = newNode;
        }
        size++;
    }

    private void linkTail(T value) {
        Node<T> last = tail;
        Node<T> newNode = new Node<>(last, value, null);
        tail = newNode;
        if (last == null) {
            head = newNode;
        } else {
            last.next = newNode;
        }
        size++;
    }

    private void linkByIndex(T value, int index) {
        Node<T> currentNode = nodeByIndex(index);
        Node<T> newNode = new Node<>(currentNode.prev, value, currentNode);
        currentNode.prev.next = newNode;
        currentNode.prev = newNode;
        size++;
    }
}
