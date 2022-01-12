package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    private static class Node<T> {
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
        Node<T> newNode = new Node<>(null, value, null);
        if (head == null) {
            head = this.tail = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        indexCheck(index, size + 1);
        Node<T> newNode = new Node<>(null, value, null);
        if (head == null && index == 0) {
            head = this.tail = newNode;
        } else if (index == 0) {
            newNode.next = head;
            head.prev = newNode;
            head = newNode;
        } else if (index == size) {
            add(value);
            size--;
        } else {
            Node<T> current = getNodeByIndex(index);
            newNode.next = current;
            newNode.prev = current.prev;
            current.prev.next = newNode;
            current.prev = newNode;
        }
        size++;
    }

    private Node<T> getNodeByIndex(int index) {
        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current;
    }

    @Override
    public void addAll(List<T> list) {
        for (T lists: list) {
            add(lists);
        }
    }

    @Override
    public T get(int index) {
        indexCheck(index, size);
        return getNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        indexCheck(index, size);
        Node<T> current = getNodeByIndex(index);
        T oldValue = current.value;
        current.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        indexCheck(index, size);
        T removeElement;
        if (index == 0) {
            removeElement = head.value;
            unlink(head);
        } else if (index == size - 1) {
            removeElement = tail.value;
            unlink(tail);
        } else {
            Node<T> current = getNodeByIndex(index);
            removeElement = current.value;
            unlink(current);
        }
        size--;
        return removeElement;
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = head;
        for (int i = 0; i < size; i++) {
            if (i == 0 && size == 1 && (object != null && object.equals(current.value))) {
                unlink(head);
                size--;
                return true;
            } else if (i == 0 && (object != null && object.equals(current.value))) {
                unlink(head);
                size--;
                return true;
            } else if (i == size - 1 && (object != null && object.equals(current.value))) {
                tail = current.prev;
                unlink(tail);
                size--;
                return true;
            } else if (object == null && current.value == null) {
                current.prev.next = current.next;
                current.next.prev = current.prev;
                size--;
                return true;
            } else if (object != null && object.equals(current.value)) {
                unlink(current);
                size--;
                return true;
            }
            current = current.next;
        }
        return false;
    }

    private void unlink(Node nodeForRemove) {
        if (nodeForRemove == head) {
            head = head.next;
            if (head == null) {
                tail = null;
            }
        } else if (nodeForRemove == tail) {
            tail = tail.prev;
        } else {
            nodeForRemove.next.prev = nodeForRemove.prev;
            nodeForRemove.prev.next = nodeForRemove.next;
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private void indexCheck(int index, int upperLimit) {
        if (index >= upperLimit || index < 0) {
            throw new IndexOutOfBoundsException("Exception");
        }
    }
}
