package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> head;
    private Node<T> tail;

    class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.value = value;
            this.prev = prev;
            this.next = next;
        }
    }

    @Override
    public void add(T value) {
        Node<T> node;
        if (isEmpty()) {
            node = new Node<>(null, value, null);
            head = node;
            tail = node;
        } else {
            node = new Node<>(tail, value, null);
            tail.next = node;
            tail = node;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index is not valid");
        }
        if (index == size) {
            add(value);
            return;
        }
        Node<T> nodeFromArrayByIndex = node(index);
        Node<T> newElement = new Node<>(nodeFromArrayByIndex.prev, value,
                nodeFromArrayByIndex);
        nodeFromArrayByIndex.prev = newElement;
        if (newElement.prev != null) {
            newElement.prev.next = newElement;
        } else {
            head = newElement;
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
        if (checkIndex(index) && node(index) != null) {
            return node(index).value;
        }
        return null;
    }

    @Override
    public T set(T value, int index) {
        if (checkIndex(index)) {
            T result = get(index);
            node(index).value = value;
            return result;
        }
        return null;
    }

    @Override
    public T remove(int index) {
        T removedElement = null;
        if (checkIndex(index)) {
            removedElement = unlink(index);
        }
        size--;
        return removedElement;
    }

    @Override
    public boolean remove(T object) {
        Node<T> node = head;
        for (int i = 0; i < size; i++) {
            if ((node.value == null && object == null)
                    || (node.value != null && node.value.equals(object))) {
                remove(i);
                return true;
            } else {
                node = node.next;
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

    private boolean checkIndex(int index) {
        if (index < size && index > -1) {
            return true;
        }
        throw new IndexOutOfBoundsException("Index is not valid");
    }

    private Node<T> node(int index) {
        Node<T> node = head;
        while (index > 0) {
            node = node.next;
            index--;
        }
        return node;
    }

    private T unlink(int index) {
        T removedElement;
        if (index == 0) {
            removedElement = head.value;
            head = head.next;
            if (head == null) {
                tail = null;
            }
        } else {
            Node<T> prev = node(index - 1);
            removedElement = prev.next.value;
            prev.next = prev.next.next;
            if (index == size - 1) {
                tail = prev;
            }
        }
        return removedElement;
    }
}
