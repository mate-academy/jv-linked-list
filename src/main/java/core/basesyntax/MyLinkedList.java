package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> first;
    private Node<T> last;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(value);
        if (first == null) {
            first = last = newNode;
        } else {
            last.next = newNode;
            last = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkIndex(index, size + 1);
        Node<T> newNode = new Node<>(value);
        if (first == null) {
            first = last = newNode;
        } else if (index == 0) {
            newNode.next = first;
            first = newNode;
        } else if (index == size) {
            last.next = newNode;
            last = newNode;
        } else {
            Node<T> prev = getNodeByIndex(index - 1);
            newNode.next = prev.next;
            prev.next = newNode;
        }
        size++;
    }

    private Node<T> getNodeByIndex(int index) {
        Node<T> current = first;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current;
    }

    private void unlink(Node<T> node) {
        if (node == first) {
            first = first.next;
            if (first == null) {
                last = null;
            }
        } else {
            Node<T> prev = getNodeBefore(node);
            if (prev != null) {
                prev.next = node.next;
                if (node == last) {
                    last = prev;
                }
            }
        }
        size--;
    }

    private Node<T> getNodeBefore(Node<T> node) {
        Node<T> current = first;
        while (current != null && current.next != node) {
            current = current.next;
        }
        return current;
    }

    @Override
    public void addAll(List<T> list) {
        for (T value : list) {
            add(value);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index, size);
        Node<T> node = getNodeByIndex(index);
        return (node != null) ? node.value : null;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index, size);
        Node<T> node = getNodeByIndex(index);
        if (node != null) {
            T oldValue = node.value;
            node.value = value;
            return oldValue;
        }
        return null;
    }

    @Override
    public T remove(int index) {
        checkIndex(index, size);
        Node<T> removedNode = getNodeByIndex(index);
        T removedValue = removedNode.value;
        unlink(removedNode);
        return removedValue;
    }

    public boolean remove(T object) {
        if (first == null) {
            return false;
        }

        if (first.value == null ? object == null : first.value.equals(object)) {
            unlink(first);
            return true;
        }

        Node<T> current = first;
        while (current.next != null) {
            if (current.next.value == null ? object == null : current.next.value.equals(object)) {
                unlink(current.next);
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
        return first == null;
    }

    private static class Node<T> {
        private T value;
        private Node<T> next;

        public Node(T value) {
            this.value = value;
        }
    }

    private void checkIndex(int index, int size) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index is not available: " + index);
        }
    }
}



