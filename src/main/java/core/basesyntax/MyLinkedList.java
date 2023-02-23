package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    private static class Node<T> {
        private Node<T> prev;
        private T data;
        private Node<T> next;

        public Node(Node<T> prev, T data, Node<T> next) {
            this.prev = prev;
            this.data = data;
            this.next = next;
        }
    }

    @Override
    public void add(T data) {
        Node<T> newNode;
        if (head == null) {
            newNode = new Node<>(null, data, null);
            head = tail = newNode;
        } else {
            newNode = new Node<>(this.tail, data, null);
            newNode.prev = tail;
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }

    @Override
    public void add(T data, int index) {
        checkIndex(index, size);
        Node<T> newNode;
        if (head == null) {
            newNode = new Node<>(null, data, null);
            head = tail = newNode;
        } else if (index == 0) {
            newNode = new Node<>(null, data, head);
            head = newNode;
            newNode.next.prev = newNode;
        } else if (index == size) {
            add(data);
            return;
        } else {
            Node<T> current = getNode(index - 1, size);
            newNode = new Node<>(current.prev, data, current.next);
            newNode.next.prev = newNode;
            current.next = newNode;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        if (list == null) {
            return;
        }
        for (T data : list) {
            add(data);
        }
    }

    @Override
    public T get(int index) {
        return getNode(index, size - 1).data;
    }

    @Override
    public T set(T data, int index) {
        Node<T> node = getNode(index, size - 1);
        T oldData = node.data;
        node.data = data;
        return oldData;
    }

    @Override
    public T remove(int index) {
        Node<T> removed = getNode(index, size - 1);
        unlink(removed);
        return removed.data;
    }

    @Override
    public boolean remove(T object) {
        Node<T> temp = head;
        while (temp != null) {
            if (temp.data == object || temp.data != null && temp.data.equals(object)) {
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
        return head == null;
    }

    private Node<T> getNode(int index, int length) {
        checkIndex(index, length);
        Node<T> current;
        if (index > size / 2) {
            current = tail;
            for (int i = size - 1; i > index; i--) {
                current = current.prev;
            }
        } else {
            current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
        }
        return current;
    }

    private void unlink(Node<T> node) {
        if (node == head) {
            head = node.next;
        }
        if (node == tail) {
            tail = node.prev;
        }
        if (node.prev != null) {
            node.prev.next = node.next;
        }
        if (node.next != null) {
            node.next.prev = node.prev;
        }
        node.prev = null;
        node.next = null;
        size--;
    }

    private void checkIndex(int index, int length) {
        if (index < 0 || index > length) {
            throw new IndexOutOfBoundsException("Index Out Of Bounds " + index);
        }
    }
}
