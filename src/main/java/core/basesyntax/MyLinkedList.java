package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private static final String INDEX_OUT_OF_BOUNDS_MESSAGE = "Index %d "
            + "does not exist in this LinkedList!";
    private static final int DEFAULT_DIVISOR = 2;
    private static final int FIRST_ELEMENT_INDEX = 0;
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> current = new Node<>(null, value, null);
        if (head == null) {
            head = current;
        } else {
            current.prev = tail;
            current.prev.next = current;
        }
        tail = current;
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkAddIndexExistence(index);
        if (index == FIRST_ELEMENT_INDEX) {
            head = new Node<>(null, value, head);
            tail = head;
        } else if (index == size) {
            add(value);
            return;
        } else {
            Node<T> current = getNodeByIndex(index);
            current = new Node<>(current.prev, value, current);
            current.next.prev = current;
            current.prev.next = current;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T node : list) {
            add(node);
        }
    }

    @Override
    public T get(int index) {
        return getNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        Node<T> current = getNodeByIndex(index);
        T valueBeforeSet = current.value;
        current.value = value;
        return valueBeforeSet;
    }

    @Override
    public T remove(int index) {
        return unlink(getNodeByIndex(index));
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = head;
        for (int i = 0; i < size; i++) {
            if ((object == current.value)
                    || (object != null && object.equals(current.value))) {
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

    private Node<T> getNodeByIndex(int index) {
        checkIndexExistence(index);
        Node<T> node;
        if (index <= size / DEFAULT_DIVISOR) {
            node = head;
            while (index != FIRST_ELEMENT_INDEX) {
                node = node.next;
                index--;
            }
        } else {
            node = tail;
            for (int i = 1; i < size - index; i++) {
                node = node.prev;
            }
        }
        return node;
    }

    private T unlink(Node<T> current) {
        if (current.next == null && current.prev == null) {
            head = null;
            tail = null;
        } else if (current.next == null) {
            current.prev.next = null;
            tail = current.prev;
            current.prev = null;
        } else if (current.prev == null) {
            current.next.prev = null;
            head = current.next;
            current.next = null;
        } else {
            current.prev.next = current.next;
            current.next.prev = current.prev;
            current.next = current.prev = null;
        }
        size--;
        return current.value;
    }

    private void checkAddIndexExistence(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException(String
                    .format(INDEX_OUT_OF_BOUNDS_MESSAGE, index));
        }
    }

    private void checkIndexExistence(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(String
                    .format(INDEX_OUT_OF_BOUNDS_MESSAGE, index));
        }
    }

    private static class Node<T> {
        private T value;
        private Node<T> next;
        private Node<T> prev;

        private Node(Node<T> prev, T element, Node<T> next) {
            this.value = element;
            this.next = next;
            this.prev = prev;
        }
    }
}
