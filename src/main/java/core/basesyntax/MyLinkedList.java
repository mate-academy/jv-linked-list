package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private static final String INDEX_OUT_OF_BOUNDS_MESSAGE = "Index %d "
            + "does not exist in this LinkedList!";
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> current = new Node<>(null, value, null);
        if (tail == null) {
            tail = current;
            head = tail;
        } else {
            current.prev = tail;
            current.prev.next = current;
            tail = current;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkAddIndexExistence(index);
        Node<T> current;
        if (index == 0) {
            head = new Node<>(null, value, head);
            tail = head;
        } else if (index == size) {
            add(value);
            return;
        } else {
            current = getNodeByIndex(index);
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
        return getNodeByIndex(index).item;
    }

    @Override
    public T set(T value, int index) {
        Node<T> current = getNodeByIndex(index);
        T oldObject = current.item;
        current.item = value;
        return oldObject;
    }

    @Override
    public T remove(int index) {
        Node<T> current = getNodeByIndex(index);
        if (current.next == null && current.prev == null) {
            head = null;
            tail = null;
        } else if (current.next == null) {
            current.prev.next = null;
            tail = current.prev;
        } else if (current.prev == null) {
            current.next.prev = null;
            head = current.next;
        } else {
            current.prev.next = current.next;
            current.next.prev = current.prev;
        }
        size--;
        return current.item;
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = head;
        for (int i = 0; i < size; i++) {
            if ((object == current.item)
                    || (object != null && object.equals(current.item))) {
                remove(i);
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

    private static class Node<T> {
        private T item;
        private MyLinkedList.Node<T> next;
        private MyLinkedList.Node<T> prev;

        Node(MyLinkedList.Node<T> prev, T element, MyLinkedList.Node<T> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }

    private Node<T> getNodeByIndex(int index) {
        checkIndexExistence(index);
        boolean isIndexLessHalf = index <= size / 2;
        Node<T> node;
        if (isIndexLessHalf) {
            node = head;
            while (index != 0) {
                node = node.next;
                index--;
            }
        } else {
            node = tail;
            int stepCounter = size - index;
            while (stepCounter != 1) {
                node = node.prev;
                stepCounter--;
            }
        }
        return node;
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
}
