package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private static final int SETTING = 0;
    private static final int NOT_SETTING = 1;
    private int size;
    private transient Node<T> first;
    private transient Node<T> last;

    @Override
    public void add(T value) {
        linkLast(value);
    }

    @Override
    public void add(T value, int index) {
        checkIndex(index);
        if (index == size) {
            linkLast(value);
        } else {
            linkBefore(value, node(index));
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
        checkIndexOnSet(index);
        return node(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkIndexOnSet(index);
        Node<T> node = node(index);
        T oldValue = node.item;
        node.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndexOnSet(index);
        return unlink(node(index));
    }

    @Override
    public boolean remove(T object) {
        for (Node<T> currentNode = first; currentNode != null; currentNode = currentNode.next) {
            if ((object == null && currentNode.item == null)
                    || (object != null && object.equals(currentNode.item))) {
                unlink(currentNode);
                return true;
            }
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
        final Node<T> prev = last;
        final Node<T> newNode = new Node<>(prev,element,null);
        last = newNode;
        if (prev == null) {
            first = newNode;
        } else {
            prev.next = newNode;
        }
        size++;
    }

    private void checkIndex(int index) {
        if (!(index >= 0 && index <= size)) {
            throw new IndexOutOfBoundsException("the index " + index
                    + "is invalid, current size of the Linkedlist is " + size);
        }
    }

    private void checkIndexOnSet(int index) {
        if (!(index >= 0 && index < size)) {
            throw new IndexOutOfBoundsException("the index " + index
                    + "is invalid, current size of the Linkedlist is " + size);
        }
    }

    private void linkBefore(T value, Node<T> node) {
        if (node != null) {
            final Node<T> prev = node.prev;
            final Node<T> newNode = new Node<>(prev, value, node);
            node.prev = newNode;
            if (prev == null) {
                first = newNode;
            } else {
                prev.next = newNode;
            }
            size++;
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
        T removedItem = node.item;
        node.item = null;
        size--;
        return removedItem;
    }

    private Node<T> node(int index) {
        if (index < (size >> 1)) {
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
        private T item;
        private Node<T> next;
        private Node<T> prev;

        public Node(Node<T> prev,T element,Node<T> next) {
            this.next = next;
            this.prev = prev;
            this.item = element;
        }

    }
}
