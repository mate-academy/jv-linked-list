package core.basesyntax;

import java.util.List;
import java.util.Objects;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size = 0;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(null, value, null);
        if (tail != null) {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
        if (head == null && tail == null) {
            head = newNode;
            tail = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("public void add(T value, int index) {");
        }
        if (size == index) {
            add(value);
        } else if (index == 0) {
            Node<T> newNode = new Node<>(null, value, head);
            head.prev = newNode;
            head = newNode;
            size++;
        } else {
            Node<T> result = head;
            for (int i = 0; i < index; i++) {
                result = result.next;
            }
            Node<T> newNode = new Node<>(result.prev, value, result);
            result.prev.next = newNode;
            result.prev = newNode;
            size++;
        }

    }

    @Override
    public void addAll(List<T> list) {
        for (T l : list) {
            add(l);
        }
    }

    @Override
    public T get(int index) {
        if (index > size - 1 || index < 0) {
            throw new IndexOutOfBoundsException("public T get(int index) {");
        }
        Node<T> result = head;
        for (int i = 0; i < index; i++) {
            result = result.next;
        }
        return result.value;
    }

    @Override
    public T set(T value, int index) {
        T t = get(index);
        if (index > size - 1 || index < 0) {
            throw new IndexOutOfBoundsException("public T set(T value, int index) {");
        }
        if (index == 0) {
            head.value = value;
        } else if (index == size - 1) {
            tail.value = value;
        } else {
            findNoteByIndex(index).value = value;
        }
        return t;
    }

    @Override
    public T remove(int index) {
        T t = get(index);
        if (index == 0) {
            if (size == 1) {
                head = null;
                tail = null;
            } else {
                head.next.prev = null;
                head = head.next;
            }
        } else if (index == size - 1) {
            tail.prev.next = null;
            tail = tail.prev;
        } else {
            Node<T> noteByIndex = findNoteByIndex(index);
            noteByIndex.prev.next = noteByIndex.next;
            noteByIndex.next.prev = noteByIndex.prev;
            noteByIndex.next = null;
            noteByIndex.prev = null;
        }
        size--;
        return t;
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = head != null ? head : null;
        for (int i = 0; i < size; i++) {
            if (Objects.equals(currentNode.value, object)) {
                remove(i);
                return true;
            }
            currentNode = currentNode.next;
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

    private static class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.value = value;
            this.prev = prev;
            this.next = next;
        }
    }

    private Node<T> findNoteByIndex(int index) {
        if (index > size - 1 || index < 0) {
            throw new IndexOutOfBoundsException("private Node<T> findNoteByIndex(int index) {");
        }
        Node<T> node = head;
        for (int i = 0; i < index; i++) {
            node = node.next;
        }
        return node;
    }
}
