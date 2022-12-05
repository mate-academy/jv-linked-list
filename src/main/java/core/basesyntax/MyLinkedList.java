package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head = null;
    private Node<T> tail = null;
    private int size;

    private static class Node<T> {
        private T value;
        private Node<T> next;
        private Node<T> prev;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }

    @Override
    public void add(T value) {
        Node<T> inputValue = new Node<>(null, value,null);
        if (head == null) {
            head = inputValue;
            tail = inputValue;
        } else {
            tail.next = inputValue;
            inputValue.prev = tail;
        }
        tail = inputValue;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        checkIndex(index);
        Node<T> requiredIndex = getIndex(index);
        Node<T> inputValue = new Node<>(requiredIndex.prev, value, requiredIndex);
        if (requiredIndex.prev == null) {
            head = inputValue;
        } else {
            requiredIndex.prev.next = inputValue;
            requiredIndex.prev = inputValue;
        }
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
        Node<T> requiredNode = getIndex(index);
        return requiredNode.value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> requiredNode = getIndex(index);
        T replaceValue = requiredNode.value;
        requiredNode.value = value;
        return replaceValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> getIndexNode = getIndex(index);
        unlink(getIndexNode);
        return getIndexNode.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> node = head;
        for (int i = 0; i < size; i++) {
            if (node.value != null && node.value.equals(object) || node.value == object) {
                unlink(node);
                return true;
            }
            node = node.next;
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

    public void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
    }

    public Node<T> getIndex(int index) {
        checkIndex(index);
        Node<T> node;
        if (index < size << 1) {
            node = head;
            for (int i = 0; i < index; i++) {
                node = node.next;
            }
        } else {
            node = tail;
            for (int i = size - 1; i > index; i--) {
                node = node.prev;
            }
        }
        return node;
    }

    public void unlink(Node<T> node) {
        Node<T> previousNode = node.prev;
        Node<T> nextNode = node.next;
        if (previousNode == null) {
            head = nextNode;
        } else {
            previousNode.next = nextNode;
        }
        if (nextNode == null) {
            tail = previousNode;
        } else {
            nextNode.prev = previousNode;
        }
        size--;
    }

    public void displayList() {
        Node<T> node = head;
        if (node == null) {
            System.out.println("no elements to display");
            return;
        }
        while (node != null) {
            System.out.println(node.value + " ");
            node = node.next;
        }
    }

    public void removeHead() {
        head = head.next;
        size--;
    }

    public void removeTail() {
        Node<T> node = head;
        for (int i = 0; i < size - 2; i++) {
            node = node.next;
        }
        tail = node;
        tail.next = null;
        size--;
    }
}
