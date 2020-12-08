package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    public MyLinkedList() {
        this.size = 0;
    }

    private boolean isValidIndex(int index, boolean isNextIndexValid) {
        if (isNextIndexValid && index == size) {
            return true;
        } else if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid index for LinkedList with size " + size);
        }
        return false;
    }

    @Override
    public boolean add(T value) {
        Node<T> newNode = new Node<>(this.tail, value, null);
        if (this.tail == null) {
            this.head = newNode;
        } else {
            this.tail.next = newNode;
        }
        this.tail = newNode;
        size++;
        return true;
    }

    @Override
    public void add(T value, int index) {
        isValidIndex(index, true);
        if (isEmpty() || (index == size)) {
            this.add(value);
        } else {
            Node<T> iterationNode = head;
            Node<T> newNode;
            for (int i = 0; i < index; i++) {
                iterationNode = iterationNode.next;
            }
            if (iterationNode == this.head) {
                newNode = new Node<>(null, value, iterationNode);
                iterationNode.prev = newNode;
                this.head = newNode;
            } else {
                newNode = new Node<>(iterationNode.prev, value, iterationNode);
                iterationNode.prev.next = newNode;
                iterationNode.prev = newNode;
            }
            size++;
        }
    }

    @Override
    public boolean addAll(List<T> list) {
        for (T item : list) {
            this.add(item);
        }
        return true;
    }

    @Override
    public T get(int index) {
        isValidIndex(index, false);
        Node<T> iterationNode = head;
        for (int i = 0; i < index; i++) {
            iterationNode = iterationNode.next;
        }
        return iterationNode.value;
    }

    @Override
    public T set(T value, int index) {
        isValidIndex(index, false);
        Node<T> iterationNode = head;
        for (int i = 0; i < index; i++) {
            iterationNode = iterationNode.next;
        }
        T oldValue = iterationNode.value;
        iterationNode.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        isValidIndex(index, false);
        Node<T> iterationNode = head;
        for (int i = 0; i < index; i++) {
            iterationNode = iterationNode.next;
        }
        analyzeFoundNode(iterationNode);
        size--;
        return iterationNode.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> iterationNode = head;
        if (object == null) {
            while (iterationNode != null && iterationNode.value != null) {
                iterationNode = iterationNode.next;
            }
            if (analyzeFoundNode(iterationNode) != null) {
                size--;
                return true;
            } else {
                return false;
            }
        } else {
            for (int i = 0; i < this.size; i++) {
                if (iterationNode == null) {
                    return false;
                } else if (iterationNode.value.equals(object)) {
                    size--;
                    return analyzeFoundNode(iterationNode) != null;
                }
                iterationNode = iterationNode.next;
            }
        }
        return false;
    }

    private Boolean analyzeFoundNode(Node<T> node) {
        if (node == null) {
            return null;
        } else if (node == this.tail) {
            this.tail.next = null;
            this.tail = node.prev;
            return true;
        } else if (node == head) {
            this.head = node.next;
            this.head.prev = null;
            return true;
        } else {
            node.prev.next = node.next;
            node.next.prev = node.prev;
            return true;
        }
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    private static class Node<T> {
        Node<T> prev;
        T value;
        Node<T> next;

        Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }
}
