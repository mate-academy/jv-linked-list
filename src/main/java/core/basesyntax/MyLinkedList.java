package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;

    public MyLinkedList() {
        head = null;
        tail = null;
    }

    private static class Node<T> {
        private final T item;
        private Node<T> next;
        private Node<T> prev;

        Node(Node<T> prev, T element, Node<T> next) {
            item = element;
            this.next = next;
            this.prev = prev;
        }

        T getItem() {
            return item;
        }

        Node<T> getNext() {
            return next;
        }

        Node<T> getPrev() {
            return prev;
        }
    }

    @Override
    public void add(T value) {
        if (head == null) {
            head = new MyLinkedList.Node<>(null, value, tail);
        } else {
            Node<T> prev = tail == null ? head : tail;
            Node<T> newNode = new MyLinkedList.Node<>(prev, value, null);
            prev.next = newNode;
            tail = newNode;
        }
    }

    @Override
    public void add(T value, int index) {
        checkAddIndex(index);

        if (index == 0) {
            if (head == null) {
                head = new MyLinkedList.Node<>(null, value, tail);
            } else {
                Node<T> newNode = new Node<>(null, value, head);
                head.prev = newNode;
                head = newNode;
            }
        } else if (index == size()) {
            add(value);
        } else {
            Node<T> currentNode = getNode(index);
            Node<T> newNode = new Node<>(currentNode.prev, value, currentNode);
            currentNode.prev.next = newNode;
            currentNode.prev = newNode;
        }
    }

    @Override
    public void addAll(List<T> list) {
        if (list == null) {
            throw new RuntimeException("The list is null");
        }

        for (int i = 0; i < list.size(); i++) {
            T value = list.get(i);
            add(value);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return getNode(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);

        Node<T> currentNode = getNode(index);
        Node<T> newNode = new Node<>(currentNode.prev, value, currentNode.next);
        if (currentNode.prev != null) {
            currentNode.prev.next = newNode;
        } else {
            head = newNode;
        }
        if (currentNode.next != null) {
            currentNode.next.prev = newNode;
        } else {
            tail = newNode;
        }
        return currentNode.item;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        T value = null;

        if (index == 0) {
            value = head.item;
            head = head.next;
            if (head != null) {
                head.prev = null;
            }
        } else if (index == size() - 1) {
            value = tail.item;
            tail = tail.prev;
            tail.next = null;
        } else {
            Node<T> currentNode = getNode(index);
            value = currentNode.item;
            currentNode.prev.next = currentNode.next;
            currentNode.next.prev = currentNode.prev;
        }
        return value;
    }

    @Override
    public boolean remove(T object) {
        int count = 0;
        Node<T> iterableNode = head;
        while (count < size()) {
            if ((object == null) && (iterableNode.item == null)
                    || (iterableNode.item != null && iterableNode.item.equals(object))
            ) {
                remove(count);
                return true;
            } else {
                iterableNode = iterableNode.next;
                count++;
            }
        }
        return false;
    }

    @Override
    public int size() {
        if (head == null) {
            return 0;
        }

        int count = 1;
        Node<T> currentNode = head;

        while (currentNode.next != null) {
            currentNode = currentNode.next;
            count++;
        }
        return count;
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    private Node<T> getPrev() {
        Node<T> currentNode = head;
        while (currentNode.next != null) {
            currentNode = currentNode.next;
        }
        return currentNode;
    }

    private Node<T> getNode(int index) {
        checkIndex(index);

        int count = 0;

        Node<T> currentNode = head;
        while (index != count) {
            currentNode = currentNode.next;
            count++;
        }
        return currentNode;
    }

    private void checkAddIndex(int index) {
        if (index < 0 || index > size()) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size());
        }
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size());
        }
    }
}
