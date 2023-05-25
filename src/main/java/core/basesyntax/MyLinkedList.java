package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> tail;
    private Node<T> head;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(tail, value);
        if (head == null) {
            tail = newNode;
            head = newNode;
        } else {
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        Node<T> nodeForeAdd = new Node<>(value);
        if (size == index) {
            add(value);
            return;
        } else if (index == 0) {
            nodeForeAdd.next = head;
            head = nodeForeAdd;
        } else {
            insert(value,index);
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T value : list) {
            add(value);
        }
    }

    @Override
    public T get(int index) {
        Node<T> current = getByIndex(index);
        return current.value;
    }

    @Override
    public T set(T value,int index) {
        Node<T> current = getByIndex(index);
        T oldValue = current.value;
        current.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        Node<T> node = getByIndex(index);
        unlink(node);
        return node.value;
    }

    @Override
    public boolean remove(T object) {
        int indexNodeForRemove = getIndexByValue(object);
        if (indexNodeForRemove != -1) {
            return unlink(getByIndex(indexNodeForRemove));
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

    private void insert(T value,int index) {
        checkIndex(index);
        Node<T> nodeForeAdd;
        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        nodeForeAdd = new Node<>(current.prev, value, current);
        current.prev.next = nodeForeAdd;
        current.prev = nodeForeAdd;
    }

    private Node<T> getByIndex(int index) {
        checkIndex(index);
        Node<T> current;
        if (index < size / 2) {
            current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
        } else {
            current = tail;
            for (int i = size - 1; i > index; i--) {
                current = current.prev;
            }
        }
        return current;
    }

    private int getIndexByValue(T value) {
        Node<T> current = head;
        for (int i = 0; i < size; i++) {
            if (current.value != null
                    && current.value.equals(value)
                    || current.value == value) {
                return i;
            }
            current = current.next;
        }
        return -1;
    }

    private boolean unlink(Node<T> node) {
        if (node == head) {
            head = head.next;
            size--;
            return true;
        } else if (node == tail) {
            tail = tail.prev;
            size--;
            return true;
        } else if (node != null) {
            node.next.prev = node.prev;
            node.prev.next = node.next;
            size--;
            return true;
        }
        return false;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Check index " + index);
        }
    }

    private static class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        private Node(T value) {
            this.value = value;
        }

        private Node(Node<T> prev,T value) {
            this.value = value;
            this.prev = prev;
        }

        private Node(Node<T> prev, T value, Node<T> next) {
            this.value = value;
            this.prev = prev;
            this.next = next;
        }
    }
}
