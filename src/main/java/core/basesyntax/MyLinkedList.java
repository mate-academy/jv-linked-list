package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> head;
    private Node<T> tail;

    @Override
    public void add(T value) {
        if (size == 0) {
            linkHead(value);
        } else {
            linkTail(value);
        }
    }

    @Override
    public void add(T value, int index) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("The index out of bounds!");
        }
        if (size == 0 || index == 0) {
            linkHead(value);
        } else if (index == size) {
            linkTail(value);
        } else {
            Node<T> currentNode = getElement(index);
            Node<T> newNode = new Node<>(currentNode.prev, value, currentNode);
            currentNode.prev.next = newNode;
            currentNode.prev = newNode;
            size++;
        }

    }

    @Override
    public void addAll(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            add(list.get(i));
        }
    }

    @Override
    public T get(int index) {
        return getElement(index).value;
    }

    @Override
    public T set(T value, int index) {
        Node<T> current = getElement(index);
        T oldValue = current.value;
        current.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        Node<T> current = getElement(index);
        final T value = current.value;
        unlink(index, current);
        return value;
    }

    @Override
    public boolean remove(T object) {
        int index = getIndexUnlink(object);
        return index >= 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private class Node<T> {
        private T value;
        private Node prev;
        private Node next;

        public Node(Node prev, T value, Node next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }

    private void linkHead(T value) {
        Node<T> h = head;
        Node<T> newNode = new Node<>(null, value, h);
        head = newNode;
        if (h == null) {
            tail = newNode;
        } else {
            h.prev = newNode;
        }
        size++;
    }

    private void linkTail(T value) {
        Node<T> t = tail;
        Node<T> newNode = new Node<>(t, value, null);
        tail = newNode;
        if (t == null) {
            head = newNode;
        } else {
            t.next = newNode;
        }
        size++;
    }

    private void unlink(int index, Node<T> current) {
        if (index == 0 && size == 1) {
            current = null;
            head = current;
        } else if (index == 0 && size > 1) {
            current.next.prev = null;
            head = current.next;
        } else if (index > 0 && index < size - 1) {
            current.next.prev = current.prev;
            current.prev.next = current.next;
        } else if (index > 0 && index == size - 1) {
            current.prev.next = null;
            tail = current.prev;
        }
        size--;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("The index out of bounds!");
        }
    }

    private Node<T> getElement(int index) {
        checkIndex(index);
        if (index < size >> 1) {
            Node<T> current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
            return current;
        } else {
            Node<T> current = tail;
            for (int i = size - 1; i > index; i--) {
                current = current.prev;
            }
            return current;
        }
    }

    private int getIndexUnlink(T object) {
        Node<T> current = head;
        int index = -1;
        for (int i = 0; i < size; i++) {
            if (current == object || current.value == object
                    || current.value != null && current.value.equals(object)) {
                index = i;
                unlink(index, current);
                break;
            }
            current = current.next;
        }
        return index;
    }
}
