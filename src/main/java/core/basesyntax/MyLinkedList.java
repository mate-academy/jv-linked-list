package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> first;
    private Node<T> last;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(null, value, null);
        if (isEmpty()) {
            first = newNode;
        } else {
            newNode.prev = last;
            last.next = newNode;
        }
        last = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
        } else if (index == 0) {
            Node<T> newNode = new Node<>(null, value, first);
            first.prev = newNode;
            first = newNode;
            size++;
        } else {
            addNode(value, index);
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T value : list) {
            add(value);
        }
    }

    @Override
    public T get(int index) {
        Node<T> result = getNodeByIndex(index);
        return result.value;
    }

    @Override
    public T set(T value, int index) {
        Node<T> result = getNodeByIndex(index);
        T oldValue = result.value;
        result.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        return delNode(getNodeByIndex(index));
    }

    @Override
    public boolean remove(T value) {
        for (Node<T> obj = first; obj != null; obj = obj.next) {
            if ((obj.value == value || (obj.value != null && obj.value.equals(value)))) {
                delNode(obj);
                return true;
            }
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

    private T delNode(Node<T> node) {
        T oldValue = node.value;
        if (node.prev == null && node.next == null) {
            first = null;
        } else if (node == first) {
            first = node.next;
            node.next.prev = null;
        } else if (node == last) {
            last = node.prev;
            node.prev.next = null;
        } else {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }
        size--;
        return oldValue;
    }

    private Node<T> getNodeByIndex(int index) {
        checkIndex(index);
        Node<T> node = first;
        if (index < size / 2) {
            for (int i = 0; i < index; i++) {
                node = node.next;
            }
        } else {
            node = last;
            for (int i = size - 1; i > index; i--) {
                node = node.prev;
            }
        }
        return node;
    }

    private void addNode(T value, int index) {
        Node<T> oldNode = getNodeByIndex(index);
        Node<T> newNode = new Node<>(oldNode.prev, value, oldNode);
        oldNode.prev.next = newNode;
        oldNode.prev = newNode;
        size++;
    }

    private void checkIndex(int index) {
        if (index < 0 || size <= index) {
            throw new IndexOutOfBoundsException("Index " + index + " is out of range!");
        }
    }

    private static class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        private Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }
}
