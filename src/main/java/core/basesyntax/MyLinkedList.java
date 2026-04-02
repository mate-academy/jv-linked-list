package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> first;
    private Node<T> last;
    private int size;

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
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }
        if (index == size) {
            add(value);
            return;
        }
        Node<T> target = node(index);
        Node<T> prev = target.prev;

        Node<T> newNode = new Node<>(prev, value, target);

        if (prev == null) {
            first = newNode;
        } else {
            prev.next = newNode;
        }
        target.prev = newNode;

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
        return node(index).value;
    }

    @Override
    public T set(T value, int index) {
        Node<T> targetNode = node(index);
        T oldValue = targetNode.value;
        targetNode.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        return unlink(node(index));
    }

    @Override
    public boolean remove(T object) {

        Node<T> current = first;
        while (current != null) {
            if (current.value == null
                    ? object == null
                    : current.value.equals(object)) {
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

    private void checkIndexOutOfBoundsException(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }

    private T unlink(Node<T> node) {
        Node<T> prev = node.prev;
        Node<T> next = node.next;

        if (prev == null) {
            first = next;
        } else {
            prev.next = next;
        }
        if (next == null) {
            last = prev;
        } else {
            next.prev = prev;
        }
        node.next = null;
        node.prev = null;
        size--;
        return node.value;
    }

    private Node<T> node(int index) {
        checkIndexOutOfBoundsException(index);
        if (index < (size / 2)) {
            Node<T> current = first;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
            return current;
        } else {
            Node<T> current = last;
            for (int i = size - 1; i > index; i--) {
                current = current.prev;
            }
            return current;
        }
    }

    private static class Node<T> {
        private T value;
        private Node<T> next;
        private Node<T> prev;

        Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }
}
