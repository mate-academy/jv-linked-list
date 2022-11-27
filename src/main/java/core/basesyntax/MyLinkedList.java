package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> head;
    private Node<T> tail;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(null, value, null);
        if (isEmpty()){
            head = tail = newNode;
        } else {
            newNode.prev = tail;
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkIndex(index);
        if (index == size) {
            add(value);
        }
        Node<T> newNode = new Node<>(getNode(index).prev, value, getNode(index));
        getNode(index).prev.next = newNode;
        getNode(index).prev = newNode;
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T t : list) {
            add(t);
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
        T temp = getNode(index).value;
        getNode(index).value = value;
        return temp;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        T temp = get(index);
        if (index == 0) {
            head = head.next;
        } else {
            Node<T> currentNode = getNode(index);
            //currentNode.next = currentNode.next.next;
            while (currentNode.next != null) {
                unlink(currentNode);
                currentNode = currentNode.next;
            }
        }
            size--;
            return temp;
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = head;
        while (currentNode.next != null) {
            if (currentNode.value == null && object == null
                    || currentNode.value != null && currentNode.value.equals(object)) {
                unlink(currentNode);
                size--;
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
    ////////////// DELETE PRINT ////////////
    public void print() {
        Node<T> currentNode = head;
        while (currentNode != null) {
            System.out.println(currentNode.value);
            currentNode = currentNode.next;
        }
    }

    private void checkIndex(int index) {
        if (index > size - 1 || index < 0) {
           throw new IndexOutOfBoundsException("Index is out of bounds " + index + "!");
        }
    }

    private Node<T> getNode(int index) {
        if (index == 0) {
            return head;
        }
        Node<T> currentNode = head;
        for (int i = 0; i < index; i++) {
            currentNode = currentNode.next;
        }
        return currentNode;
    }

    private void unlink(Node<T> node) {
        node.next.prev = node.prev;
        node.prev.next = node.next;
    }
}

class Node<T> {
    T value;
    Node<T> next;
    Node<T> prev;

    public Node(Node<T> prev, T value, Node<T> next) {
        this.value = value;
        this.next = next;
        this.prev = prev;
    }
}
