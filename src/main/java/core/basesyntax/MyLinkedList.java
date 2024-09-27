package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> first;
    private Node<T> last;
    private int size = 0;

    static class Node<T> {
        private T value;
        private Node<T> next;
        private Node<T> prev;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }

    private Node<T> getNode(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
        Node<T> result;
        if (index <= size / 2) {
            result = first;
            for (int i = 1; i <= index; i++) {
                result = result.next;
            }
        } else {
            result = last;
            for (int i = size - 1; i > index; i--) {
                result = result.prev;
            }
        }
        return result;
    }

    private void removeNode(Node<T> node, int index) {
        if (index == 0) {
            if (size != 1) {
                node.next.prev = null;
                first = node.next;
            }
        } else if (index == size - 1) {
            node.prev.next = null;
            last = node.prev;
        } else {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }
        size--;
        node = null;
    }

    @Override
    public void add(T value) {
        Node<T> current;
        if (size == 0) {
            current = new Node<>(null, value, null);
            first = current;
        } else {
            current = last;
            current = new Node<>(current, value, null);
            current.prev.next = current;
        }
        last = current;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        if (index == 0) {
            Node<T> current = new Node<>(null, value, first);
            first.prev = current;
            first = current;
        } else {
            Node<T> current = getNode(index);
            current = new Node<>(current.prev, value, current);
            current.prev.next = current;
            current.next.prev = current;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T t : list) {
            add(t);
        }
    }

    @Override
    public T get(int index) {
        return getNode(index).value;
    }

    @Override
    public T set(T value, int index) {
        T oldValue = getNode(index).value;
        getNode(index).value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        Node<T> current = getNode(index);
        T oldValue = current.value;
        removeNode(current, index);
        return oldValue;
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = first;
        for (int i = 0; i < size; i++) {
            if (current.value != null && current.value.equals(object)
                    || current.value == null && object == null) {
                removeNode(current, i);
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
}
