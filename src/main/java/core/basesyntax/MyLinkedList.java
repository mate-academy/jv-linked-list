package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    private static class Node<T> {
        private Node<T> prev;
        private T value;
        private Node<T> next;

        public Node(T value) {
            this.value = value;
        }

        public Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }

    public T getHeadValue() {
        return head.value;
    }

    public T getTailValue() {
        return tail.value;
    }

    @Override
    public void add(T value) {
        insertNodeToTail(value);
    }

    @Override
    public void add(T value, int index) {
        validateIndex(index, 1);
        if (index == size) {
            insertNodeToTail(value);
            return;
        }
        if (index == 0) {
            insertNodeToHead(value);
            return;
        }
        insertNode(findNode(index), value);
    }

    @Override
    public void addAll(List<T> list) {
        for (T data : list) {
            add(data);
        }
    }

    @Override
    public T get(int index) {
        validateIndex(index, 0);
        if (index == 0) {
            return getHeadValue();
        }
        if (index == size - 1) {
            return getTailValue();
        }
        return findNode(index).value;
    }

    @Override
    public T set(T value, int index) {
        validateIndex(index, 0);
        final T val;
        if (index == 0) {
            val = head.value;
            head.value = value;
            return val;
        }
        if (index == size - 1) {
            val = tail.value;
            tail.value = value;
            return val;
        }
        Node<T> node = findNode(index);
        val = node.value;
        node.value = value;
        return val;
    }

    @Override
    public T remove(int index) {
        validateIndex(index, 0);
        final T val;
        if (index == 0) {
            return removeFromHead();
        }
        if (index == size - 1) {
            return removeFromTail();
        }
        val = removeNode(findNode(index));
        return val;
    }

    @Override
    public boolean remove(T object) {
        return findEqualsNode(object);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private T removeFromHead() {
        T val;
        val = head.value;
        if (head.next != null) {
            head = head.next;
        } else {
            head = null;
        }
        size--;
        return val;
    }

    private T removeFromTail() {
        T val;
        val = tail.value;
        tail = tail.prev;
        tail.next = null;
        size--;
        return val;
    }

    private Node<T> findNode(int index) {
        return index > size >> 1 ? iterFromTail(index) : iterFromHead(index);
    }

    private void insertNodeToTail(T value) {
        Node<T> t = tail;
        Node<T> newNode = new Node<>(tail, value, null);
        tail = newNode;
        if (t == null) {
            head = newNode;
        } else {
            t.next = newNode;
        }
        size++;
    }

    private void insertNodeToHead(T value) {
        head.prev = new Node<>(value);
        head.prev.next = head;
        head = head.prev;
        size++;
    }

    private T removeNode(Node<T> node) {
        final T value = node.value;
        if (node.prev != null) {
            node.prev.next = node.next;
        } else {
            head = node.next;
        }
        if (node.next != null) {
            node.next.prev = node.prev;
        } else {
            tail = node.prev;
        }
        size--;
        return value;
    }

    private boolean findEqualsNode(T obj) {
        Node<T> node = head;
        for (int i = 0; i < size; i++) {
            if (obj == node.value || (node.value != null && node.value.equals(obj))) {
                removeNode(node);
                return true;
            }
            node = node.next;
        }
        return false;
    }

    private void insertNode(Node<T> node, T value) {
        node = node.prev;
        node.next = new Node<>(node, value, node.next);
        node.next.next.prev = node.next;
        size++;
    }

    private Node<T> iterFromHead(int index) {
        int currIndex = 0;
        Node<T> node = head;
        while (currIndex < index) {
            node = node.next;
            currIndex = currIndex + 1;
        }
        return node;
    }

    private Node<T> iterFromTail(int index) {
        int currIndex = size - 1;
        Node<T> node = tail;
        while (currIndex > index) {
            node = node.prev;
            currIndex = currIndex - 1;
        }
        return node;
    }

    private void validateIndex(int index, int shift) {
        if (index < 0 || index >= size + shift) {
            throw new IndexOutOfBoundsException("index: " + index + " is invalid index");
        }
    }
}
