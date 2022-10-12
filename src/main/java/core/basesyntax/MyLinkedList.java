package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    public static final String MESSAGE_WRONG_INDEX = "Wrong index";
    public static final String MESSAGE_INPUT_IS_NULL = "The list is null";
    private transient int size = 0;
    private transient Node<T> first;
    private transient Node<T> last;

    private static class Node<T> {
        private Node<T> prev;
        private Node<T> next;
        private T element;

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
        checkAddIndex(index);
        if (index == size) {
            add(value);
        } else {
            final Node<T> nextNode = getNodeBy(index);
            final Node<T> prevNode = nextNode.prev;
            final Node<T> newNode = new Node<>(prevNode, value, nextNode);
            if (prevNode != null) {
                prevNode.next = newNode;
            } else {
                first = newNode;
            }
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
            throw new NullPointerException(MESSAGE_INPUT_IS_NULL);
        }
    }

    @Override
    public T get(int index) {
        checkSearchIndex(index);
        return getNodeBy(index).element;
    }

    @Override
    public T set(T value, int index) {
        checkSearchIndex(index);
        final Node<T> oldNode = getNodeBy(index);
        T oldValue = oldNode.element;
        oldNode.element = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkSearchIndex(index);
        final Node<T> node = getNodeBy(index);
        final Node<T> prevNode = node.prev;
        final Node<T> nextNode = node.next;
        final T value = node.element;

        if (prevNode == null) {
            first = nextNode;
        } else {
            prevNode.next = nextNode;
            node.prev = null;
        }

        if (nextNode == null) {
            last = prevNode;
        } else {
            nextNode.prev = prevNode;
            node.next = null;
        }
        node.element = null;
        size--;
        return value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> startNode = first;
        for (int i = 0; i < size; i++) {
            if (object == startNode.element
                    || object != null && object.equals(startNode.element)) {
                remove(i);
                return true;
            }
            startNode = startNode.next;
        }
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

    private void checkAddIndex(int index) {
        if (!(index >= 0 && index <= size)) {
            showOutOfBounds();
        }
    }

    private void checkSearchIndex(int index) {
        if (!(index >= 0 && index < size)) {
            showOutOfBounds();
        }
    }

    private void showOutOfBounds() {
        throw new IndexOutOfBoundsException(MESSAGE_WRONG_INDEX);
    }
}
