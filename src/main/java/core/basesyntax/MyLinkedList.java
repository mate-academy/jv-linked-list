package core.basesyntax;

import java.util.List;

@SuppressWarnings("unchecked")
public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size = 0;
    private Node<T> head;
    private Node<T> tail;

    class Node<T> {
        private T data;
        private Node<T> prev = null;
        private Node<T> next = null;

        public Node(Node<T> prev, T data, Node<T> next) {
            this.prev = prev;
            this.data = data;
            this.next = next;
        }
    }

    public MyLinkedList() {

    }

    @Override
    public void add(T value) {
        addLast(value);
    }

    public void add(T value, int index) {
        indexValidationForAdd(index);
        if (index == 0) {
            addFirst(value);
        } else if (index == size) {
            addLast(value);
        } else {
            addToMiddle(value, index);
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T value : list) {
            add(value);
        }
    }

    @Override
    public T get(int index) {
        indexValidationForFind(index);
        Node<T> newNode = head;
        for (int i = 0; i <= index; i++) {
            if (i == index) {
                return newNode.data;
            }
            newNode = newNode.next;
        }
        throw new IndexOutOfBoundsException();
    }

    @Override
    public T set(T value, int index) {
        indexValidationForFind(index);
        Node<T> pointer = head;
        T oldValue = pointer.data;
        for (int i = 0; i <= index; i++) {
            if (i == index) {
                oldValue = pointer.data;
                pointer.data = value;
            }
            pointer = pointer.next;
        }
        return oldValue;
    }

    @Override
    public T remove(int index) {
        indexValidationForFind(index);
        Node<T> newNode = head;
        T removedData = null;
        for (int i = 0; i <= index; i++) {
            if (i == index) {
                removedData = newNode.data;
                removeByNode(newNode);
            } else {
                newNode = newNode.next;
            }
        }
        return removedData;
    }

    @Override
    public boolean remove(T object) {
        Node<T> newNode = head;
        if (head == tail) {
            removeHead();
            return true;
        }
        for (int i = 0; i < size; i++) {
            if (newNode.data == null ? newNode.data == object
                    : newNode.data.equals(object)) {
                removeByNode(newNode);
                return true;
            } else {
                newNode = newNode.next;
            }
        }
        return false;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    private void indexValidationForAdd(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }
    }

    private void indexValidationForFind(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
    }

    private void addFirst(T value) {
        Node<T> firstNode = head;
        Node<T> newNode = new Node<>(null, value, null);
        head = newNode;
        if (firstNode != null) {
            firstNode.prev = newNode;
            newNode.next = firstNode;
        } else {
            tail = newNode;
        }
        size++;
    }

    private void addLast(T value) {
        Node<T> lastNode = tail;
        Node<T> newNode = new Node<>(null, value, null);
        tail = newNode;
        if (lastNode != null) {
            lastNode.next = newNode;
            newNode.prev = lastNode;
        } else {
            head = newNode;
        }
        size++;
    }

    private void addToMiddle(T value, int index) {
        Node<T> pointer = head;
        Node<T> newNode = new Node<>(null, value, null);
        for (int i = 0; i <= index; i++) {
            if (i == index) {
                newNode.next = pointer;
                newNode.prev = pointer.prev;
                pointer.prev.next = newNode;
                pointer.prev = newNode;
                size++;
            }
            if (pointer.next != null) {
                pointer = pointer.next;
            }
        }
    }

    private void removeHead() {
        if (size == 1) {
            head = null;
            tail = null;
        }
        size--;
    }

    private void removeByNode(Node<T> node) {
        if (node == head) {
            head = node.next;
        }
        if (node.prev != null) {
            node.prev.next = node.next;
        }
        if (node.next != null) {
            node.next.prev = node.prev;
            node.next = null;
        }
        size--;
    }
}
