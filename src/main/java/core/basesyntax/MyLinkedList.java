package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private static final String INDEX_OUT_OF_BOUND_MESSAGE = "Index is out of a range bound";
    private Node<T> first;
    private Node<T> last;
    private int size;

    @Override
    public boolean add(T value) {
        linkLast(value);
        return true;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException(INDEX_OUT_OF_BOUND_MESSAGE);
        }
        if (index == size) {
            linkLast(value);
        } else {
            linkBefore(value, getNodeByIndex(index));
        }
    }

    @Override
    public boolean addAll(List<T> list) {
        for (T element : list) {
            add(element);
        }
        return true;
    }

    @Override
    public T get(int index) {
        checkIndexPosition(index);
        return getNodeByIndex(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkIndexPosition(index);
        Node<T> node = getNodeByIndex(index);
        T oldValue = node.item;
        node.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndexPosition(index);
        return unlink(getNodeByIndex(index));
    }

    @Override
    public boolean remove(T object) {
        Node<T> node = first;
        for (int i = 0; i < size; i++) {
            if (object == node.item || object != null
                    && object.equals(node.item)) {
                unlink(node);
                return true;
            }
            node = node.next;
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

    private void linkLast(T element) {
        Node<T> previousNode = last;
        Node<T> newNode = new Node<>(previousNode, element, null);
        last = newNode;
        if (previousNode == null) {
            first = newNode;
        } else {
            previousNode.next = newNode;
        }
        size++;
    }

    private void linkBefore(T element, Node<T> node) {
        Node<T> previousNode = node.prev;
        Node<T> newNode = new Node<>(previousNode, element, node);
        node.prev = newNode;
        if (previousNode == null) {
            first = newNode;
        } else {
            previousNode.next = newNode;
        }
        size++;
    }

    private Node<T> getNodeByIndex(int index) {
        if (index < (size >> 1)) {
            Node<T> node = first;
            for (int i = 0; i < index; i++) {
                node = node.next;
            }
            return node;
        }
        Node<T> node = last;
        for (int i = size - 1; i > index; i--) {
            node = node.prev;
        }
        return node;
    }

    private T unlink(Node<T> node) {
        Node<T> previousNode = node.prev;
        Node<T> nextNode = node.next;
        if (previousNode != null) {
            previousNode.next = nextNode;
            node.prev = null;
        } else {
            first = nextNode;
        }
        if (nextNode != null) {
            nextNode.prev = previousNode;
            node.next = null;
        } else {
            last = previousNode;
        }
        size--;
        return node.item;
    }

    private void checkIndexPosition(int index) {
        if (!(index >= 0 && index < size)) {
            throw new IndexOutOfBoundsException(INDEX_OUT_OF_BOUND_MESSAGE);
        }
    }

    private static class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        Node(Node<T> previous, T element, Node<T> next) {
            this.item = element;
            this.next = next;
            this.prev = previous;
        }
    }
}
