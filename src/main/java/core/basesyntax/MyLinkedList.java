package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> head;
    private Node<T> tail;

    class Node<T> {
        private T value;
        private Node<T> next;
        private Node<T> prev;

        public Node(T value) {
            this.value = value;
        }
    }

    public MyLinkedList() {

    }

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(value);
        if (head == null) {
            tail = head = newNode;
        } else {
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        Node<T> newNode = new Node<>(value);
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("Invalid index " + index);
        }
        if (head == null) {
            head = tail = newNode;
        } else if (index == 0) {
            newNode.next = head;
            head.prev = newNode;
            head = newNode;
        } else if (index == size) {
            newNode.prev = tail;
            tail.next = newNode;
            tail = newNode;
        } else {
            Node<T> prev = getNodeByIndex(index - 1);
            newNode.prev = prev;
            newNode.next = prev.next;
            prev.next = newNode;
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
        checkIfIndexInBounds(index);
        return getNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkIfIndexInBounds(index);
        Node<T> node = getNodeByIndex(index);
        T oldValue = node.value;
        node.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIfIndexInBounds(index);
        T removedElement;
        if (index == 0) {
            removedElement = head.value;
            head = head.next;
            if (head == null) {
                tail = null;
            }
        } else {
            Node<T> prev = getNodeByIndex(index - 1);
            removedElement = prev.next.value;
            prev.next = prev.next.next;
            if (index == size - 1) {
                tail = tail.prev;
            }
        }
        size--;
        return removedElement;
    }

    @Override
    public boolean remove(T object) {
        Node<T> currNode = head;
        for (int i = 0; i < size; i++) {
            if (object == null && currNode.value == null) {
                remove(i);
                return true;
            } else if (object != null && object.equals(currNode.value)) {
                remove(i);
                return true;
            }
            currNode = currNode.next;
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

    private Node<T> getNodeByIndex(int index) {
        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current;
    }

    private void checkIfIndexInBounds(int index) {
        if (index < 0 || (index != 0 && index == size)) {
            throw new IndexOutOfBoundsException("Invalid index " + index);
        }
    }
}
