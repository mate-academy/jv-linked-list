package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {

    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        if (head == null) {
            head = tail = new Node<>(null, value, null);
        } else {
            Node<T> node = new Node<>(tail, value, null);
            tail.next = node;
            tail = node;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkIndexForAdd(index);
        if (head == null && tail == null) {
            head = tail = new Node<>(null, value, null);
        } else if (index == 0) {
            Node<T> node = new Node<>(null, value, head);
            head.prev = node;
            head = node;
        } else if (index == size) {
            Node<T> node = new Node<>(tail, value, null);
            tail.next = node;
            tail = node;
        } else {
            Node<T> current = getNodeByIndex(index);
            Node<T> newNode = new Node<>(current.prev, value, current);
            current.prev.next = newNode;
            current.prev = newNode;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T element : list) {
            add(element);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return getNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> current = getNodeByIndex(index);
        T result = current.value;
        current.value = value;
        return result;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> node = getNodeByIndex(index);
        unlink(node);
        return node.value;
    }

    @Override
    public boolean remove(T object) {
        int index = getIndexByNode(object);
        if (index == -1) {
            return false;
        } else {
            unlink(getNodeByIndex(index));
            return true;
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return head == null;
    }

    private Node<T> getNodeByIndex(int index) {
        Node<T> node = head;
        for (int i = 0; i != index; i++) {
            node = node.next;
        }
        return node;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
    }

    private void checkIndexForAdd(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
    }

    private int getIndexByNode(T value) {
        Node<T> node = head;
        for (int i = 0; i < size; i++) {
            if ((value == null && null == node.value)
                    || (value != null && value.equals(node.value))) {
                return i;
            }
            node = node.next;
        }
        return -1;
    }

    private void unlink(Node<T> node) {
        if (tail == head) {
            head = tail = null;
        } else if (node == head) {
            node.next.prev = null;
            head = node.next;
        } else if (node == tail) {
            node.prev.next = null;
            tail = node.prev;
        } else {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }
        size--;
    }

    private static class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.next = next;
            this.value = value;
        }
    }
}

