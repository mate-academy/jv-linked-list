package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private static final String INVALID_INDEX_MESSAGE = "Index is invalid";
    private Node<T> first;
    private Node<T> last;
    private int size;

    private static class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this. next = next;
        }
    }

    @Override
    public boolean add(T value) {
        linkLast(value);
        return true;
    }

    @Override
    public void add(T value, int index) {
        checkIndex(index, size);
        if (index == size) {
            linkLast(value);
        } else {
            linkBefore(value, findNode(index));
        }
    }

    @Override
    public boolean addAll(List<T> list) {
        for (T item : list) {
            add(item);
        }
        return true;
    }

    @Override
    public T get(int index) {
        checkIndex(index, size - 1);
        return findNode(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index, size - 1);
        Node<T> current = findNode(index);
        T oldValue = current.value;
        current.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index,size - 1);
        return unlink(findNode(index));
    }

    @Override
    public boolean remove(T object) {
        for (Node<T> node = first; node != null; node = node.next) {
            if (object == node.value
                    || object != null && object.equals(node.value)) {
                unlink(node);
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

    private void checkIndex(int index, int size) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException(INVALID_INDEX_MESSAGE);
        }
    }

    private Node<T> findNode(int index) {
        Node<T> x = index < (size >> 1) ? first : last;
        if (index < (size >> 1)) {
            for (int i = 0; i < index; i++) {
                x = x.next;
            }
        } else {
            for (int i = size - 1; i > index; i--) {
                x = x.prev;
            }
        }
        return x;
    }

    private void linkLast(T item) {
        Node<T> lastNode = last;
        Node<T> newNode = new Node<>(lastNode, item, null);
        last = newNode;
        if (lastNode == null) {
            first = newNode;
        } else {
            lastNode.next = newNode;
        }
        size++;
    }

    private void linkBefore(T value, Node<T> current) {
        Node<T> previus = current.prev;
        Node<T> newNode = new Node<>(previus, value, current);
        current.prev = newNode;
        if (previus == null) {
            first = newNode;
        } else {
            previus.next = newNode;
        }
        size++;
    }

    private T unlink(Node<T> unlinkNnode) {
        Node<T> prev = unlinkNnode.prev;
        Node<T> next = unlinkNnode.next;
        if (next == null) {
            last = prev;
        } else {
            next.prev = prev;
            unlinkNnode.next = null;
        }

        if (prev == null) {
            first = next;
        } else {
            prev.next = next;
            unlinkNnode.prev = null;
        }

        T value = unlinkNnode.value;
        unlinkNnode.value = null;
        size--;
        return value;
    }
}
