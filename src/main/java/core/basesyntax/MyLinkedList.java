package core.basesyntax;

import java.util.List;
import java.util.Objects;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> head;
    private Node<T> tail;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(tail, value, null);
        tail = newNode;
        if (size == 0) {
            head = newNode;
        } else {
            tail.prev.next = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index <= size && index >= 0) {
            if (index == size) {
                add(value);
            } else if (index == 0) {
                Node<T> newNode = new Node<>(null, value, head);
                head = newNode;
                head.next.prev = newNode;
                size++;
            } else {
                Node<T> tmpNode = head;
                for (int i = 0; i < index; i++) {
                    tmpNode = tmpNode.next;
                }
                Node<T> newNode = new Node<>(tmpNode.prev, value, tmpNode);
                tmpNode.prev.next = newNode;
                tmpNode.prev = newNode;
                size++;
            }
        } else {
            throw new IndexOutOfBoundsException("invalid index " + index);
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T item : list) {
            add(item);
        }
    }

    @Override
    public T get(int index) {
        if (index < size && index >= 0) {
            Node<T> tmpNode = head;
            for (int i = 0; i < index; i++) {
                tmpNode = tmpNode.next;
            }
            return tmpNode.value;
        } else {
            throw new IndexOutOfBoundsException("invalid index " + index);
        }
    }

    @Override
    public T set(T value, int index) {
        if (index < size && index >= 0) {
            Node<T> tmpNode = head;
            for (int i = 0; i < index; i++) {
                tmpNode = tmpNode.next;
            }
            T oldValue = tmpNode.value;
            tmpNode.value = value;
            return oldValue;
        } else {
            throw new IndexOutOfBoundsException("invalid index " + index);
        }
    }

    @Override
    public T remove(int index) {
        Node<T> removedNode = head;
        if (index < size && index >= 0) {
            if (size == 1) {
                head = null;
                tail = null;
            } else if (index == 0) {
                head.next.prev = null;
                head = head.next;
            } else if (index == size - 1) {
                removedNode = tail;
                tail.prev.next = null;
                tail = tail.prev;
            } else {
                Node<T> tmpNode = head;
                for (int i = 0; i < index; i++) {
                    tmpNode = tmpNode.next;
                }
                removedNode = tmpNode;
                tmpNode.prev.next = tmpNode.next;
                tmpNode.next.prev = tmpNode.prev;
            }
            size--;
        } else {
            throw new IndexOutOfBoundsException("invalid index " + index);
        }
        return removedNode.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> tmpNode = head;
        boolean isElementExist = false;
        for (int i = 0; i < size; i++) {
            if (Objects.equals(tmpNode.value, object)) {
                isElementExist = true;
                remove(i);
                break;
            }
            tmpNode = tmpNode.next;
        }
        return isElementExist;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    static class Node<T> {
        private Node<T> next;
        private Node<T> prev;
        private T value;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }
}
