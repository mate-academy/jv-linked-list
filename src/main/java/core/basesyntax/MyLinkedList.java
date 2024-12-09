package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> first;
    private Node<T> last;
    private int size;

    private static class Node<T> {
        private Node<T> prev;
        private Node<T> next;
        private T value;

        Node(Node<T> prev, T value, Node<T> next) {
            this.value = value;
            this.prev = prev;
            this.next = next;
        }
    }

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(last, value, null);
        if (last == null) {
            first = newNode;
        } else {
            last.next = newNode;
        }
        last = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkPositionIndex(index);
        if (index == size) {
            add(value);
        } else {
            Node<T> current = node(index);
            Node<T> newNode = new Node<>(current.prev, value, current);
            if (current.prev == null) {
                first = newNode;
            } else {
                current.prev.next = newNode;
            }
            current.prev = newNode;
            size++;
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
        checkElementIndex(index);
        return node(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkElementIndex(index);
        Node<T> node = node(index);
        T oldValue = node.value;
        node.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkElementIndex(index);
        Node<T> node = node(index);

        if (node.prev == null) {
            first = node.next;
        } else {
            node.prev.next = node.next;
        }

        if (node.next == null) {
            last = node.prev;
        } else {
            node.next.prev = node.prev;
        }

        size--;
        return node.value;
    }

    @Override
    public boolean remove(T object) {
        if (object == null) {
            for (Node<T> current = first; current != null; current = current.next) {
                if (current.value == null) {
                    removeNode(current);
                    return true;
                }
            }
        } else {
            for (Node<T> current = first; current != null; current = current.next) {
                if (object.equals(current.value)) {
                    removeNode(current);
                    return true;
                }
            }
        }
        return false;
    }

    private void removeNode(Node<T> node) {
        if (node.prev == null) {
            first = node.next;
        } else {
            node.prev.next = node.next;
        }

        if (node.next == null) {
            last = node.prev;
        } else {
            node.next.prev = node.prev;
        }

        size--;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private void checkElementIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }

    private void checkPositionIndex(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }

    private Node<T> node(int index) {
        checkElementIndex(index);
        Node<T> current;
        if (index < (size >> 1)) {
            current = first;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
        } else {
            current = last;
            for (int i = size - 1; i > index; i--) {
                current = current.prev;
            }
        }
        return current;
    }
}
