package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> first;
    private Node<T> last;

    private static class Node<T> {
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
        if (size == 0) {
            addFirst(value);
        } else {
            addLast(value);
        }
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Wrong index value. Index: " + index);
        }
        if (size == 0) { // item is actually the very first in the list
            addFirst(value);
        } else if (index == size) { // item is adding at the last position, next by last
            addLast(value);
        } else {
            Node<T> positionNode = getPositionByIndex(index);
            addBefore(value, positionNode);
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T item: list) {
            add(item);
        }
    }

    @Override
    public T get(int index) {
        checkIndexOnGet(index);
        return getPositionByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkIndexOnGet(index);
        Node<T> node = getPositionByIndex(index);
        T oldValue = node.value;
        node.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndexOnGet(index);
        return unlink(getPositionByIndex(index));
    }

    @Override
    public boolean remove(T object) {
        if (size == 0) {
            return false;
        }
        Node<T> currentNode = first;
        for (int i = 0; i < size; i++) {
            if ((currentNode.value == object && object == null)
                    || currentNode.value.equals(object)) {
                unlink(currentNode);
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

    private T unlink(Node<T> node) {
        if (node == first && node == last) { // node is the one and only in the list
            first = last = null;
        } else if (node == last) { // node is last in the list
            last = node.prev;
            node.prev.next = null;
            node.prev = null;
        } else if (node == first) { // node is first in the list
            first = node.next;
            node.next.prev = null;
            node.next = null;
        } else { // node is in between
            node.prev.next = node.next;
            node.next.prev = node.prev;
            node.next = null;
        }
        --size;
        return node.value;
    }

    private Node<T> getPositionByIndex(int index) {
        if (index <= size / 2) {
            return lookFromFirst(index);
        } else {
            return lookFromLast(index);
        }
    }

    private Node<T> lookFromFirst(int index) {
        Node<T> currentNode = first;
        for (int i = 0; i < index; i++) {
            currentNode = currentNode.next;
        }
        return currentNode;
    }

    private Node<T> lookFromLast(int index) {
        Node<T> currentNode = last;
        for (int i = size - 1; i > index; i--) {
            currentNode = currentNode.prev;
        }
        return currentNode;
    }

    private void addFirst(T value) {
        Node<T> newNode = new MyLinkedList.Node<>(null, value, null);
        first = last = newNode;
        ++size;
    }

    private void addLast(T value) {
        Node<T> newNode = new MyLinkedList.Node<>(last, value, null);
        last.next = newNode;
        last = newNode;
        ++size;
    }

    private void addBefore(T value, Node<T> node) {
        if (node == first) {
            first = new MyLinkedList.Node<>(null, value, node);
        } else {
            Node<T> newNode = new MyLinkedList.Node<>(node.prev, value, node);
            newNode.prev.next = newNode;
            node.prev = newNode;
        }
        ++size;
    }

    private void checkIndexOnGet(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Wrong index value. Index: " + index);
        }
    }
}
