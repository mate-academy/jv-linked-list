package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        if (isEmpty()) {
            linkEmptyList(value);
            return;
        }
        Node<T> newNode = new Node<>(tail, value);
        newNode.prev.next = newNode;
        tail = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == 0 && size == 0) {
            linkEmptyList(value);
            return;
        }
        if (index == 0) {
            linkHead(value);
            return;
        }
        if (index == size) {
            linkTail(value);
            return;
        }
        Node<T> current = getNode(index);
        Node<T> newNode = new Node<>(current.prev,value,current);
        current.prev.next = newNode;
        current.prev = newNode;
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T element:list) {
            add(element);
        }
    }

    @Override
    public T get(int index) {
        return getNode(index).value;
    }

    @Override
    public T set(T value, int index) {
        final Node<T> current = getNode(index);
        final T oldValue = current.value;
        current.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        return unlink(getNode(index));
    }

    @Override
    public boolean remove(T object) {
        for (Node<T> x = head; x != null; x = x.next) {
            if (x.value == object || x.value != null
                        && x.value.equals(object)) {
                unlink(x);
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

    private void linkEmptyList(T value) {
        Node<T> newNode = new Node<>(value);
        head = newNode;
        tail = newNode;
        size++;
    }

    private void linkHead(T value) {
        final Node<T> first = head;
        final Node<T> newNode = new Node<>(value, first);
        head = newNode;
        first.prev = newNode;
        size++;
    }

    private void linkTail(T value) {
        Node<T> last = tail;
        Node<T> newNode = new Node<>(last, value);
        tail = newNode;
        last.next = newNode;
        size++;
    }

    private Node<T> getNode(int index) {
        checkIndexOutOfBounds(index);
        if (index < size / 2) {
            Node<T> x = head;
            for (int i = 0; i < index; i++) {
                x = x.next;
            }
            return x;
        }
        Node<T> x = tail;
        for (int i = size - 1; i > index; i--) {
            x = x.prev;
        }
        return x;
    }

    private void checkIndexOutOfBounds(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("\"Index\" " + index + "\" Size\" " + size);
        }
    }

    private T unlink(Node<T> node) {
        final T element = node.value;
        final Node<T> next = node.next;
        final Node<T> prev = node.prev;
        if (prev == null) {
            head = next;
        } else if (next == null) {
            tail = prev;
        } else {
            next.prev = prev;
            prev.next = next;
        }
        size--;
        return element;
    }

    static class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        public Node(T value, Node<T> next) {
            this.value = value;
            this.next = next;
        }

        public Node(Node<T> prev, T value) {
            this.prev = prev;
            this.value = value;
        }

        public Node(T value) {
            this.value = value;
        }

        public Node(Node<T> prev, T value, Node<T> next) {
            this.value = value;
            this.prev = prev;
            this.next = next;
        }
    }
}
