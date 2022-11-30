package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    static class Node<T> {
        private T value;
        private Node<T> next;

        public Node(T value) {
            this.value = value;
        }
    }

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(value);
        if (head == null) {
            head = tail = newNode;
        } else {
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkIndexOutOfBoundForAdd(index);
        Node<T> newNode = new Node<>(value);
        if (head != null && index < size && index > 0) {
            Node<T> previous = getByIndex(index - 1);
            newNode.next = previous.next;
            previous.next = newNode;
            size++;
        } else if (head != null && index < size && index == 0) {
            newNode.next = head;
            head = newNode;
            size++;
        } else {
            add(value);
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T t : list) {
            add(t);
        }
    }

    @Override
    public T get(int index) {
        checkIndexOutOfBound(index);
        return getByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkIndexOutOfBound(index);
        Node<T> node = getByIndex(index);
        T oldNode = node.value;
        node.value = value;
        return oldNode;
    }

    @Override
    public T remove(int index) {
        checkIndexOutOfBound(index);
        T removedElement;
        if (index == 0) {
            removedElement = head.value;
            head = head.next;
            if (head == null) {
                tail = null;
            }
        } else {
            Node<T> previous = getByIndex(index - 1);
            removedElement = previous.next.value;
            previous.next = previous.next.next;
            if (index == size - 1) {
                tail = previous;
            }
        }
        size--;

        return removedElement;
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = head;
        boolean res = false;
        for (int i = 0; i < size; i++) {
            if ((current.value != null && current.value.equals(object))
                    || (object == null && current.value == null)) {
                remove(i);
                res = true;
                break;
            }
            current = current.next;
        }
        return res;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return head == null;
    }

    private void checkIndexOutOfBoundForAdd(int index) {
        if (index < 0 || index >= size + 1) {
            throw new IndexOutOfBoundsException("Index is invalid " + index);
        }
    }

    private void checkIndexOutOfBound(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index is invalid " + index);
        }
    }

    private Node<T> getByIndex(int index) {
        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current;
    }
}

