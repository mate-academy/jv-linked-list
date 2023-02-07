package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private int size;

    class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }

    @Override
    public void add(T value) {
        if (isEmpty()) {
            head = new Node<>(null, value, null);
        } else if (head.next == null) {
            head.next = new Node<>(head, value, null);
        } else {
            Node<T> lastNode = head;
            while (lastNode.next != null) {
                lastNode = lastNode.next;
            }
            lastNode.next = new Node<>(lastNode, value, null);
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("Index " + index + " is out of size: " + size);
        }
        if (index == 0 && head == null) {
            add(value);
            return;
        }
        if (index == 0) {
            Node<T> newNode = new Node<>(null, value, head);
            head.prev = newNode;
            head = newNode;
            size++;
            return;
        }
        Node<T> indexNode = head;
        for (int i = 0; i < index - 1; i++) {
            indexNode = indexNode.next;
        }
        indexNode.next = new Node<>(indexNode, value, indexNode.next);
        size++;
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
        if (isEmpty()) {
            return null;
        }
        Node<T> result = head;
        int count = 0;
        while (result != null) {
            if (count == index) {
                return result.value;
            }
            result = result.next;
            count++;
        }
        return null;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        if (isEmpty()) {
            return null;
        }
        Node<T> indexNode = head;
        T result;
        int count = 0;
        while (indexNode != null) {
            if (count == index) {
                result = indexNode.value;
                indexNode.value = value;
                return result;
            }
            indexNode = indexNode.next;
            count++;
        }
        return null;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        if (isEmpty()) {
            return null;
        }
        if (index == 0) {
            return removeFirst();
        }
        Node<T> indexNode = head;
        int count = 0;
        while (indexNode != null) {
            if (count == index) {
                indexNode.prev.next = indexNode.next;
                if (indexNode.next != null) {
                    indexNode.next.prev = indexNode.prev;
                }
                size--;
                return indexNode.value;
            }
            indexNode = indexNode.next;
            count++;
        }
        return null;
    }

    @Override
    public boolean remove(T object) {
        Node<T> indexNode = head;
        int count = 0;
        while (indexNode != null) {
            if (indexNode.value == object || indexNode.value != null
                    && indexNode.value.equals(object)) {
                remove(count);
                return true;
            }
            indexNode = indexNode.next;
            count++;
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

    private void checkIndex(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Index " + index + " is out of size: " + size);
        }
    }

    private T removeFirst() {
        T result = head.value;
        size--;
        if (head.next != null) {
            head = head.next;
            head.prev = null;
            return result;
        } else {
            head = null;
            return null;
        }
    }
}
