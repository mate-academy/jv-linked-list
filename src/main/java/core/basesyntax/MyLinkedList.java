package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> tail;
    private Node<T> head;
    private int size;

    @Override
    public void add(T value) {
        if (size == 0) {
            Node<T> firstNode = new Node<>(null, value,null);
            tail = firstNode;
            head = firstNode;
            size++;
        } else {
            Node<T> newNode = new Node<>(tail, value, null);
            tail.next = newNode;
            tail = newNode;
            size++;
        }
    }

    @Override
    public void add(T value, int index) {
        checkIndexAdd(index);
        if (index == 0) {
            if (size == 0) {
                Node<T> firstNode = new Node<>(null, value,null);
                tail = firstNode;
                head = firstNode;
                size++;
                return;
            }
            Node<T> newNode = new Node<>(null, value, head);
            head.prev = newNode;
            head = newNode;
            size++;
        }
        if (index > 0 && index < size) {
            Node<T> current = searchIndex(index);
            Node<T> newNode = new Node<>(current.prev, value, current);
            current.prev.next = newNode;
            current.prev = newNode;
            size++;
        }
        if (index == size) {
            add(value);
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
        checkIndex(index);
        Node<T> current = searchIndex(index);
        return current.item;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        T replacedElement;
        Node<T> current = searchIndex(index);
        replacedElement = current.item;
        current.item = value;
        return replacedElement;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> current = searchIndex(index);
        if (size == 1 && index == 0) {
            size--;
        } else if (index == 0) {
            current.next.prev = null;
            head = current.next;
            size--;
        } else if (index > 0 && index < size - 1) {
            current.prev.next = current.next;
            current.next.prev = current.prev;
            size--;
        } else if (index == size - 1) {
            current.prev.next = null;
            tail = current.prev;
            size--;
        }
        return current.item;
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = head;
        for (int i = 0; i < size; i++) {
            if (current.item == object || current.item.equals(object)) {
                remove(i);
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

    public void checkIndexAdd(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("IndexOutOfBoundsException");
        }
    }

    public void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("IndexOutOfBoundsException");
        }
    }

    public Node<T> searchIndex(int index) {
        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current;
    }

    public static class Node<E> {
        private E item;
        private Node<E> prev;
        private Node<E> next;

        public Node(Node<E> prev,E item, Node<E> next) {
            this.prev = prev;
            this.item = item;
            this.next = next;
        }
    }
}
