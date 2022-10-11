package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    public static final String WRONG_INDEX_MESSAGE = "Wrong index";
    public static final String INPUT_IS_NULL_MESSAGE = "The list is null";
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
        checkIndex(index);
        if (index == size) {
            add(value);
        } else {
            Node<T> nextNode = getNodeBy(index);
            Node<T> prevNode = nextNode.prev;
            Node<T> newNode = new Node<>(prevNode, value, nextNode);
            prevNode.next = newNode;
            nextNode.prev = newNode;
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        if (list != null) {
            for (T element : list) {
                add(element);
            }
        } else {
            throw new NullPointerException(INPUT_IS_NULL_MESSAGE);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return getNodeBy(index).element;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> oldNode = getNodeBy(index);
        T oldValue = oldNode.element;
        oldNode.element = value;
        return oldValue;
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

    private void checkIndex(int index) {
        if (!(index >= 0 && index < size)) {
            throw new IndexOutOfBoundsException(WRONG_INDEX_MESSAGE);
        }
    }
}
