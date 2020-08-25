package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> head;
    private Node<T> tail;

    public MyLinkedList() {
        size = 0;
    }

    @Override
    public boolean add(T value) {
        Node<T> tmp = tail;
        tail = new Node<>(value);

        if (size == 0) {
            head = tail;
            wire(head, tail);
        } else {
            wire(tmp, tail);
        }
        size++;

        return true;
    }

    @Override
    public void add(T value,int index) {
        if (index < 0 || index > size) {
            throw new ArrayIndexOutOfBoundsException(index);
        }

        Node<T> target = getNodeByIndex(index);
        Node<T> newNode = null;

        if (index == 0) {
            head = new Node<>(value);
            if (target == null) {
                add(value);
                return;
            }
            wire(head, target);
        } else if (index == size) {
            add(value);
            return;
        } else {
            newNode = new Node<>(value);
            wire(target.prev, newNode);
            wire(newNode, target);
        }
        size++;
    }

    @Override
    public boolean addAll(List<T> list) {
        for (T t : list) {
            add(t);
        }
        return true;
    }

    @Override
    public T get(int index) {
        validateIndex(index);
        return getNodeByIndex(index).value;
    }

    @Override
    public T set(T value,int index) {
        validateIndex(index);
        Node<T> target = getNodeByIndex(index);
        T result = target.value;
        target.value = value;
        return result;
    }

    @Override
    public T remove(int index) {
        validateIndex(index);
        Node<T> target = getNodeByIndex(index);
        if (size == 1) {
            head = null;
            tail = null;
            size--;
            return target.value;
        }
        if (index == size - 1) {
            target.prev.next = null;
            tail = target.prev;
            size--;
            return target.value;
        }
        if (index == 0) {
            target.next.prev = null;
            head = target.next;
        }
        wire(target.prev, target.next);
        size--;
        return target.value;
    }

    @Override
    public boolean remove(T t) {
        for (int i = 0; i < size; i++) {
            if (get(i) == t || get(i) != null && get(i).equals(t)) {
                remove(i);
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
        return (size == 0);
    }

    private void validateIndex(int index) {
        if (index < 0 || index >= size) {
            throw new ArrayIndexOutOfBoundsException(index);
        }
    }

    private void wire(Node<T> node1, Node<T> node2) {
        node1.next = node2;
        node2.prev = node1;
    }

    private Node<T> getNodeByIndex(int index) {
        Node<T> result = null;

        if (index < size / 2) {
            result = head;
            for (int i = 0; i < index; i++) {
                result = result.next;
            }
        } else {
            result = tail;
            for (int i = size - 1; i > index; i--) {
                result = result.prev;
            }
        }
        return result;
    }

    private static class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        Node(T value) {
            this.value = value;
        }
    }
}
