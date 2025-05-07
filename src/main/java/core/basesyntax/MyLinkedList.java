package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<T>();
        newNode.item = value;
        newNode.next = null;
        if (head == null) {
            head = newNode;
            size++;
        } else {
            Node<T> currentNode = head;
            while (currentNode.next != null) {
                currentNode = currentNode.next;
            }
            currentNode.next = newNode;
            size++;
        }
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index is not exist in LinkedList");
        }
        if (isEmpty() && index == 0) {
            add(value);
            return;
        }
        Node<T> newNode = new Node<>(value, null);
        Node<T> currentNode = head;
        if (addToTheTop(index, newNode)) {
            return;
        }
        changeLinks(index, newNode, currentNode);
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
        validateIndex(index);
        if (isEmpty()) {
            return null;
        }
        Node<T> currentNode = head;
        int i = -1;
        while (currentNode != null) {
            i++;
            if (index == i) {
                return currentNode.item;
            }
            currentNode = currentNode.next;
        }
        return null;
    }

    @Override
    public T set(T value, int index) {
        validateIndex(index);
        if (size >= 1 && index == 0) {
            T oldItem = get(index);
            head.item = value;
            return oldItem;
        }
        Node<T> currentNode = head;
        T oldValue = get(index);
        for (int i = 0; i <= index; i++) {
            if (i == index) {
                currentNode.item = value;
            }
            currentNode = currentNode.next;
        }
        return oldValue;
    }

    @Override
    public T remove(int index) {
        validateIndex(index);
        if (isEmpty()) {
            throw new IndexOutOfBoundsException("Index is not exist in LinkedList list");
        }
        T value = get(index);
        if (index == 0) {
            head = head.next;
            size--;
            return value;
        }
        Node<T> currentNode = head;
        for (int i = 0; i < index - 1; i++) {
            currentNode = currentNode.next;
        }
        unlink(currentNode);
        return value;
    }

    @Override
    public boolean remove(T object) {
        if (isEmpty()) {
            throw new IndexOutOfBoundsException("Index is not exist in LinkedList list");
        }
        Node<T> currentNode = head;
        for (int i = 0; i < size; i++) {
            if (object == null && currentNode.item == null
                    || object != null && object.equals(currentNode.item)) {
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

    private boolean addToTheTop(int index, Node<T> newNode) {
        if (index == 0) {
            newNode.next = head;
            head = newNode;
            size++;
            return true;
        }
        return false;
    }

    private void validateIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index is not exist in LinkedList list");
        }
    }

    private void unlink(Node<T> currentNode) {
        Node<T> node;
        node = currentNode.next;
        currentNode.next = node.next;
        size--;
    }

    private void changeLinks(int index, Node<T> newNode, Node<T> currentNode) {
        for (int i = 0; i < index - 1; i++) {
            currentNode = currentNode.next;
        }
        newNode.next = currentNode.next;
        currentNode.next = newNode;
    }

    private static class Node<T> {
        private T item;
        private Node<T> next;

        public Node() {
        }

        Node(T value, Node<T> next) {
            this.item = value;
            this.next = next;
        }
    }
}
