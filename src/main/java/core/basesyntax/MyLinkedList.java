package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    static class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        public Node(T item, Node<T> next, Node<T> prev) {
            this.item = item;
            this.next = next;
            this.prev = prev;
        }
    }

    @Override
    public String toString() {
        Node<T> current = head;
        String content = "";
        if (current != null) {
            content = current.item.toString();
            while (current.next != null) {
                current = current.next;
                content = content + " " + current.item;
            }
        }
        return "MyLinkedList {"
                + "content = " + content
                + ", size = " + size
                + '}';
    }

    @Override
    public boolean add(T value) {
        if (head == null && tail == null) {
            head = tail = new Node<>(value, null, null);
        } else {
            tail = new Node<>(value, null, tail);
            tail.prev.next = tail;
        }
        size++;
        return size > 1;
    }

    @Override
    public void add(T value, int index) {
        if (index > size() || index < 0) {
            throw new IndexOutOfBoundsException("Index: " + index + " is wrong!");
        }
        if (index == size()) {
            add(value);
            return;
        }
        Node<T> currentNode = head;
        for (int i = 0; i < index - 1; i++) {
            if (currentNode == null) {
                throw new IndexOutOfBoundsException("Index: " + index + " is wrong!");
            }
            currentNode = currentNode.next;
        }
        if (index == 0) {
            Node<T> newNode = new Node<>(value, currentNode, null);
            head = newNode;
            size++;
            return;
        }

        Node<T> newNode = new Node<>(value, currentNode.next, currentNode);
        currentNode.next = newNode;
        size++;
    }

    @Override
    public boolean addAll(List<T> list) {
        for (T elemFromList : list) {
            add(elemFromList);
        }
        return true;
    }

    private Node<T> getNode(int index) {
        Node<T> currentNode = head;
        for (int i = 0; i < index; i++) {
            currentNode = currentNode.next;
        }
        return currentNode;
    }

    @Override
    public T get(int index) {
        if (index >= size() || index < 0) {
            throw new IndexOutOfBoundsException("Index: " + index + " is wrong!");
        }
        Node<T> node = getNode(index);
        return node.item;
    }

    @Override
    public T set(T value, int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Index: " + index + " is wrong!");
        }
        Node<T> node = getNode(index);
        T item = node.item;
        node.item = value;
        return item;
    }

    @Override
    public T remove(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Index: " + index + " is wrong!");
        }
        Node<T> node = getNode(index);
        if (size() == 1 && index == 0) {
            head = null;
            tail = null;
            size--;
            return node.item;
        }
        if (index == 0 && size() > 0) {
            head.next.prev = null;
            head = head.next;
            size--;
            return node.item;
        }
        if (index == size - 1) {
            tail.prev.next = null;
            size--;
            return node.item;
        }

        Node<T> prevNode = node.prev;
        Node<T> nextNode = node.next;
        prevNode.next = nextNode;
        nextNode.prev = prevNode;
        size--;
        return node.item;
    }

    @Override
    public boolean remove(T object) {
        int index = -1;
        if (size() == 1 && object.equals(get(0))) {
            head = null;
            tail = null;
            size--;
            return true;
        }
        for (int i = 0; i < size(); i++) {
            if (object == null && get(i) == null) {
                index = i;
                break;
            }
            if (object != null && object.equals(get(i))) {
                index = i;
                break;
            }
        }
        if (index == - 1) {
            return false;
        }

        Node<T> node = getNode(index);
        if (index == 0) {
            head.next.prev = null;
            head = head.next;
            size--;
            return true;
        }
        Node<T> prevNode = node.prev;
        Node<T> nextNode = node.next;
        prevNode.next = nextNode;
        nextNode.prev = prevNode;
        size--;
        return true;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }
}
