package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> first;
    private Node<T> last;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(last, value, null);
        if (first == null) {
            first = newNode;
            last = newNode;
        } else {
            last.next = newNode;
            last = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        Node<T> newNode = new Node<>(null, value, null);
        if (index == 0) {
            first.prev = newNode;
            newNode.prev = null;
            newNode.next = first;
            first = newNode;
        } else {
            Node<T> currentNode = getNodeByIndex(index - 1);
            newNode.next = currentNode.next;
            newNode.prev = currentNode;
            currentNode.next = newNode;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T each : list) {
            add(each);
        }
    }

    @Override
    public T get(int index) {
        Node<T> gettedNode = getNodeByIndex(index);
        return gettedNode.value;
    }

    @Override
    public T set(T value, int index) {
        Node<T> settedNode = getNodeByIndex(index);
        T oldValue = settedNode.value;
        settedNode.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        Node<T> removedNode = getNodeByIndex(index);
        return unlink(removedNode);
    }

    @Override
    public boolean remove(T object) {
        Node<T> removedNode = first;
        for (int i = 0; i < size; i++) {
            if (object == removedNode.value || object != null && object.equals(removedNode.value)) {
                unlink(removedNode);
                return true;
            }
            removedNode = removedNode.next;
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
            throw new IndexOutOfBoundsException("Index is incorrect " + index);
        }
    }

    private Node<T> getNodeByIndex(int index) {
        checkIndex(index);
        Node<T> currentNode = first;
        for (int i = 0; i < index; i++) {
            currentNode = currentNode.next;
        }
        return currentNode;
    }

    private T unlink(Node<T> removedNode) {
        if (removedNode.prev == null) {
            first = removedNode.next;
        } else {
            removedNode.prev.next = removedNode.next;
        }
        if (removedNode.next == null) {
            last = removedNode.prev;
        } else {
            removedNode.next.prev = removedNode.prev;
        }
        T removedValue = removedNode.value;
        removedNode.value = null;
        size--;
        return removedValue;
    }

    private class Node<T> {
        private T value;
        private Node<T> next;
        private Node<T> prev;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }
}
