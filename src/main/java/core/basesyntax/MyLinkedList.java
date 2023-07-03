package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size = 0;

    @Override
    public void add(T value) {
        if (isEmpty()) {
            linkEmptyList(value);
            return;
        }
        Node<T> newNode = new Node<>(tail, value, null);
        newNode.prev.next = newNode;
        tail = newNode;
        newNode.prev = tail.prev;
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
        checkIndex(index);
        Node<T> current = getNnode(index);
        Node<T> newNode = new Node<>(current.prev,value,current);
        current.prev.next = newNode;
        current.prev = newNode;
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        if (list.size() == 0) {
            return;
        }
        for (T element:list) {
            add(element);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return getNnode(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        final Node<T> current = getNnode(index);
        final T oldValue = current.value;
        current.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        return unlink(getNnode(index));
    }

    @Override
    public boolean remove(T object) {
        if (object == null) {
            for (Node<T> x = head; x != null; x = x.next) {
                if (x.value == null) {
                    unlink(x);
                    return true;
                }
            }
        } else {
            for (Node<T> x = head; x != null; x = x.next) {
                if (object.equals(x.value)) {
                    unlink(x);
                    return true;
                }
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
        Node<T> newNode = new Node<>(null, value, null);
        head = newNode;
        tail = newNode;
        size++;
    }

    private void linkHead(T value) {
        final Node<T> f = head;
        final Node<T> newNode = new Node<>(null, value, f);
        head = newNode;
        f.prev = newNode;
        size++;
    }

    private void linkTail(T value) {
        Node<T> l = tail;
        Node<T> newNode = new Node<>(l, value,null);
        tail = newNode;
        l.next = newNode;
        size++;
    }

    private Node<T> getNnode(int index) {
        if (index < size >> 1) {
            Node<T> x = head;
            for (int i = 0; i < index; i++) {
                x = x.next;
            }
            return x;
        } else {
            Node<T> x = tail;
            for (int i = size - 1; i > index; i--) {
                x = x.prev;
            }
            return x;
        }
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("\"Index\" " + index + "\" Size\" " + size);
        }
    }

    private void checkList() {
        if (isEmpty()) {
            throw new NullPointerException("Can't unlinked from from an empty list");
        }
    }

    private T unlink(Node<T> node) {
        checkList();
        final T element = node.value;
        final Node<T> next = node.next;
        final Node<T> prev = node.prev;
        if (next == null && prev == null) {
            size = 0;
            return element;
        }
        if (prev == null) {
            head = next;
            next.prev = head;
            size--;
            return element;
        }
        if (next == null) {
            tail = prev;
            prev.next = tail;
            size--;
            return element;
        }
        next.prev = prev;
        prev.next = next;
        size--;
        return element;
    }

    static class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        public Node(Node<T> prev,T value, Node<T> next) {
            this.value = value;
            this.prev = prev;
            this.next = next;
        }
    }
}
