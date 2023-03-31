package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> tail;
    private Node<T> head;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(tail, value, null);
        if (tail == null) {
            head = newNode;
        } else {
            tail.next = newNode;
        }
        tail = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        checkIndex(index);
        if (index == 0) {
            Node<T> newNode = new Node<>(null, value, head);
            head.prev = newNode;
            head = newNode;
        } else {
            Node<T> indexMark = head;
            for (int i = 0; i < index; i++) {
                indexMark = indexMark.next;
            }
            Node<T> newNode = new Node<>(indexMark.prev, value, indexMark);
            indexMark.prev.next = newNode;
            indexMark.prev = newNode;
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
        return getNode(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> actual = getNode(index);
        T finalValue = actual.value;
        actual.value = value;
        return finalValue;

    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> deletedNode = getNode(index);
        if (deletedNode != null) {
            T listResult = deletedNode.value;
            disintegrate(deletedNode);
            return listResult;
        }
        return null;
    }

    @Override
    public boolean remove(T object) {
        Node<T> pointer = head;
        while (pointer != null) {
            if (pointer.value == object || pointer.value != null
                    && (pointer.value.equals(object))) {
                disintegrate(pointer);
                return true;
            }
            pointer = pointer.next;
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

    static class Node<T> {
        private Node<T> prev;
        private T value;
        private Node<T> next;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }

    private Node<T> getNode(int index) {
        if (index < size / 2) {
            Node<T> mark = head;
            for (int i = 0; i < index; i++) {
                mark = mark.next;
            }
            return mark;
        } else {
            Node<T> mark = tail;
            for (int i = size - 1; i > index; i--) {
                mark = mark.prev;
            }
            return mark;
        }
    }

    private void disintegrate(Node<T> node) {
        if (node.prev == null) {
            head = node.next;
        } else {
            node.prev.next = node.next;
        }
        if (node.next == null) {
            tail = node.prev;
        } else {
            node.next.prev = node.prev;
        }
        size--;
    }

    private void checkIndex(int index) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("index is less than 0");
        }
        if (index >= size) {
            throw new IndexOutOfBoundsException("index is exceeding " + size);
        }
    }
}
