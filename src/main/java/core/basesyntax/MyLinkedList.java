package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size = 0;

    @Override
    public boolean add(T value) {
        Node<T> temp = tail;
        Node<T> newNode = new Node<>(temp, value, null);
        tail = newNode;
        if (temp == null) {
            head = newNode;
        } else {
            temp.next = newNode;
        }
        size++;
        return true;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        if (index == 0) {
            Node<T> temp = head;
            Node<T> newNode = new Node<>(null, value, temp);
            temp.prev = newNode;
            head = newNode;
        } else {
            Node<T> temp = getNodeByIndex(index);
            Node<T> newNode = new Node<>(temp.prev, value, temp);
            temp.prev.next = newNode;
            temp.prev = newNode;
        }
        size++;
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
        return getNodeByIndex(index).item;
    }

    @Override
    public T set(T value, int index) {
        Node<T> temp = getNodeByIndex(index);
        T oldItem = temp.item;
        temp.item = value;
        return oldItem;
    }

    @Override
    public T remove(int index) {
        return unlink(getNodeByIndex(index));
    }

    @Override
    public boolean remove(T t) {
        Node<T> temp = head;
        for (int i = 0; i < size; i++) {
            if (t != null ? t.equals(temp.item) : temp.item == null) {
                unlink(temp);
                return true;
            }
            temp = temp.next;
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

    private Node<T> getNodeByIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        Node<T> temp;
        if (index < size / 2) {
            temp = head;
            for (int i = 0; i < size / 2; i++) {
                if (index == i) {
                    return temp;
                }
                temp = temp.next;
            }
        } else {
            temp = tail;
            for (int i = size - 1; i >= size / 2; i--) {
                if (index == i) {
                    return temp;
                }
                temp = temp.prev;
            }
        }
        return temp;
    }

    private T unlink(Node<T> temp) {
        Node<T> prev = temp.prev;
        Node<T> next = temp.next;
        if (prev == null) {
            head = next;
        } else {
            prev.next = next;
        }
        if (next == null) {
            tail = prev;
        } else {
            next.prev = prev;
        }
        size--;
        return temp.item;
    }

    private static class Node<T> {
        private Node<T> prev;
        private T item;
        private Node<T> next;

        public Node(Node<T> prev, T item, Node<T> next) {
            this.prev = prev;
            this.item = item;
            this.next = next;
        }
    }
}
