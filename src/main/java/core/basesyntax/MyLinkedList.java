package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> first;
    private Node<T> last;
    private int size;

    private static class Node<T> {
        private T value;
        private Node<T> next;
        private Node<T> prev;

        public Node(T value) {
            this.value = value;
        }
    }

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(value);
        if (first == null) {
            first = newNode;
            last = newNode;
        } else {
            last.next = newNode;
            newNode.prev = last;
            last = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throwOutOfBoundsException(index);
        }

        if (index == size) {
            add(value);
            return;
        }

        Node<T> newNode = new Node<>(value);
        Node<T> current = getNodeAtIndex(index);

        if (index == 0) {
            first = newNode;
        } else {
            newNode.prev = current.prev;
            current.prev.next = newNode;
        }

        newNode.next = current;
        current.prev = newNode;
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T value : list) {
            add(value);
        }
    }

    @Override
    public T get(int index) {
        validateIndex(index);
        return getNodeAtIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        validateIndex(index);
        Node<T> current = getNodeAtIndex(index);
        T oldValue = current.value;
        current.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        validateIndex(index);

        Node<T> current = getNodeAtIndex(index);
        if (index == 0) {
            first = current.next;
        } else {
            current.prev.next = current.next;
        }
        if (index == size - 1) {
            last = current.prev;
        } else {
            current.next.prev = current.prev;
        }
        size--;
        return current.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = first;
        while (current != null) {
            if (object == null ? current.value == null : object.equals(current.value)) {
                if (current == first) {
                    first = current.next;
                } else {
                    current.prev.next = current.next;
                }
                if (current == last) {
                    last = current.prev;
                } else {
                    current.next.prev = current.prev;
                }
                size--;
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

    private Node<T> getNodeAtIndex(int index) {
        if (index < size / 2) {
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

    private void throwOutOfBoundsException(int index) {
        throw new IndexOutOfBoundsException("Index is out of bounds, given index: " + index);
    }

    private void validateIndex(int index) {
        if (index < 0 || index >= size) {
            throwOutOfBoundsException(index);
        }
    }

}
