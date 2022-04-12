package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size = 0;
    private Node<T> first;
    private Node<T> last;

    private static class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }

    public void checkOutIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Wrong index");
        }
    }

    @Override
    public void add(T value) {
        Node<T> node = new Node<>(last, value, null);
        Node<T> oldNode = last;
        last = node;
        if (size == 0) {
            first = node;
        } else {
            oldNode.next = node;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (size == 0 && index == 0) {
            Node<T> node = new Node<>(null, value, null);
            first = last = node;
            size++;
            return;
        }
        if (index == 0) {
            Node<T> node = new Node<>(null, value, first);
            Node<T> oldNode = first;
            first = oldNode.prev = node;
            size++;
            return;
        }
        if (index == size) {
            Node<T> node = new Node<>(last, value, null);
            Node<T> oldNode = last;
            last = oldNode.next = node;
            size++;
            return;
        }
        checkOutIndex(index);
        Node<T> oldNode = first;
        int i = 0;
        while (i < index) {
            oldNode = oldNode.next;
            i++;
        }
        Node<T> node = new Node<>(oldNode.prev, value, oldNode);
        oldNode.prev.next = oldNode.prev = node;
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        int i = 0;
        while (i < list.size()) {
            add(list.get(i));
            i++;
        }
    }

    @Override
    public T get(int index) {
        checkOutIndex(index);
        Node<T> getNode = first;
        int i = 0;
        while (i < index) {
            getNode = getNode.next;
            i++;
        }
        return getNode.value;
    }

    @Override
    public T set(T value, int index) {
        checkOutIndex(index);
        Node<T> setNode = first;
        int i = 0;
        while (i < index) {
            setNode = setNode.next;
            i++;
        }
        T oldValue = setNode.value;
        setNode.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkOutIndex(index);
        if (index == 0) {
            final T removeValue = first.value;
            if (size == 1) {
                first = last = null;
            } else {
                first = first.next;
                first.prev = null;
            }
            size--;
            return removeValue;
        }
        if (index == size - 1) {
            final T removeValue = last.value;
            last = last.prev;
            last.next = null;
            size--;
            return removeValue;
        }
        int i = 0;
        Node<T> removeNode = first;
        while (i < index) {
            removeNode = removeNode.next;
            i++;
        }
        final T removeValue = removeNode.value;
        removeNode.prev.next = removeNode.next;
        removeNode.next.prev = removeNode.prev;
        size--;
        return removeValue;
    }

    @Override
    public boolean remove(T object) {
        Node<T> removeNode = first;
        int i = 0;
        while (i < size) {
            if ((removeNode.value == null && object == null) || removeNode.value.equals(object)) {
                remove(i);
                return true;
            }
            removeNode = removeNode.next;
            i++;
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
}
