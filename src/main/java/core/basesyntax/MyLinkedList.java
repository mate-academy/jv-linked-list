package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    public static final String WRONG_INDEX_MESSAGE = "Wrong index";
    private transient int size = 0;
    private transient Node<T> first;
    private transient Node<T> last;

    private static class Node<T> {
        Node<T> prev;
        Node<T> next;
        T element;

        public Node(Node<T> prev, T element, Node<T> next) {
            this.prev = prev;
            this.element = element;
            this.next = next;
        }
    }

    @Override
    public void add(T value) {
        Node<T> node;
        if (isEmpty()) {
            node = new Node<>(null, value, null);
            first = node;
        } else {
            node = new Node<>(last, value, null);
            last.next = node;
        }
        last = node;
        size++;
    }

    @Override
    public void add(T value, int index) {

    }

    @Override
    public void addAll(List<T> list) {
    }

    @Override
    public T get(int index) {
        if (checkIndex(index)) {
            return getNodeBy(index).element;
        } else {
            throw new IndexOutOfBoundsException(WRONG_INDEX_MESSAGE);
        }
    }

    @Override
    public T set(T value, int index) {
        return null;
    }

    @Override
    public T remove(int index) {
        return null;
    }

    @Override
    public boolean remove(T object) {
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    private Node<T> getNodeBy(int index) {
        Node<T> requiredNode;
        if (index < size / 2) {
            requiredNode = first;
            for (int i = 0; i < index; i++) {
                requiredNode = requiredNode.next;
            }
        } else {
            requiredNode = last;
            for (int i = size - 1; i > index; i--) {
                requiredNode = requiredNode.prev;
            }
        }
        return requiredNode;
    }

    private boolean checkIndex(int index) {
        return index >= 0 && index <= size;
    }
}
