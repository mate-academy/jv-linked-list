package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size = 0;
    private Node<T> first;
    private Node<T> last;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(last, value, null);
        if (isEmpty()) {
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
        Node<T> oldNode = getNodeByIndex(index);
        Node<T> newNode = new Node<>(oldNode.prev, value, oldNode);
        if (index == 0) {
            first = newNode;
        } else {
            oldNode.prev.next = newNode;
        }
        oldNode.prev = newNode;
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
        return getNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        Node<T> node = getNodeByIndex(index);
        T currentValue = node.value;
        node.value = value;
        return currentValue;
    }

    @Override
    public T remove(int index) {
        Node<T> removeNode = getNodeByIndex(index);
        unlink(removeNode);
        return removeNode.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> node = first;
        for (int i = 0; i < size; i++) {
            if (object == node.value || (object != null && object.equals(node.value))) {
                unlink(node);
                return true;
            } else {
                node = node.next;
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

    private Node<T> getNodeByIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Incorrect index");
        }
        Node<T> current;
        if (size / 2 > index) {
            current = first;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
            return current;
        }
        current = last;
        for (int i = size - 1; i > index; i--) {
            current = current.prev;
        }
        return current;
    }

    private void unlink(Node<T> removedNode) {
        if (removedNode.prev == null) {
            first = removedNode.next;
        } else if (removedNode.next == null) {
            last = removedNode.prev;
        } else {
            removedNode.prev.next = removedNode.next;
            removedNode.next.prev = removedNode.prev;
        }
        size--;
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

        public Node(T value) {
            this.value = value;
        }
    }
}
