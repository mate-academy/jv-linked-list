package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    public MyLinkedList() {
        this.head = null;
        this.head = null;
        this.size = 0;
    }

    public static class Node<T> {
        private T data;
        private Node<T> next;
        private Node<T> previous;

        public Node(T data) {
            this.data = data;
            this.next = null;
            this.previous = null;
        }

        public T getData() {
            return data;
        }

        public Node<T> getNext() {
            return next;
        }

        public Node<T> getPrevious() {
            return previous;
        }

        public void setPrevious(Node<T> previous) {
            this.previous = previous;
        }

        public void setNext(Node<T> next) {
            this.next = next;
        }
    }

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(value);
        if (head == null) {
            head = newNode;
            tail = newNode;
        } else {
            tail.next = newNode;
            newNode.previous = tail;
            tail = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
        Node<T> newNode = new Node<>(value);
        if (index == 0) {
            newNode.next = head;
            if (head != null) {
                head.previous = newNode;
            }
            head = newNode;
            if (size == 0) {
                tail = newNode;
            }
        } else if (index == size) {
            tail.next = newNode;
            newNode.previous = tail;
            tail = newNode;
        } else {
            Node<T> current = getNodeAt(index);
            newNode.next = current;
            newNode.previous = current.previous;
            if (current.previous != null) {
                current.previous.next = newNode;
            }
            current.previous = newNode;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        if (list == null || list.isEmpty()) {
            throw new IllegalArgumentException("List cannot be null or empty");
        }

        for (T value : list) {
            add(value);
        }
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bounds: " + index);
        }

        return getNodeAt(index).data;
    }

    @Override
    public T set(T value, int index) {
        Node<T> current = getNodeAt(index);
        T oldValue = current.data;
        current.data = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }

        Node<T> removedNode;
        if (index == 0) {
            removedNode = head;
            head = head.next;
            if (head != null) {
                head.previous = null;
            }
            if (size == 1) {
                tail = null;
            }
        } else if (index == size - 1) {
            removedNode = tail;
            tail = tail.previous;
            if (tail != null) {
                tail.next = null;
            }
        } else {
            removedNode = getNodeAt(index);
            Node<T> previousNode = removedNode.previous;
            Node<T> nextNode = removedNode.next;
            if (previousNode != null) {
                previousNode.next = nextNode;
            }
            if (nextNode != null) {
                nextNode.previous = previousNode;
            }
        }
        size--;
        return removedNode.data;
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = head;
        while (current != null) {
            if ((object == null && current.data == null) || (current.data != null && current.data.equals(object))) {
                if (current == head) {
                    head = head.next;
                    if (head != null) {
                        head.previous = null;
                    }
                    if (current == tail) {
                        tail = null;
                    }
                } else if (current == tail) {
                    tail = tail.previous;
                    if (tail != null) {
                        tail.next = null;
                    }
                } else {
                    Node<T> previousNode = current.previous;
                    Node<T> nextNode = current.next;
                    if (previousNode != null) {
                        previousNode.next = nextNode;
                    }
                    if (nextNode != null) {
                        nextNode.previous = previousNode;
                    }
                }
                size--;
                return true;
            }
            current = current.next;
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

    private Node<T> getNodeAt(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bounds: " + index);
        }

        Node<T> current;
        if (index < size / 2) {
            current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
        } else {
            current = tail;
            for (int i = size - 1; i > index; i--) {
                current = current.previous;
            }
        }
        return current;
    }
}
