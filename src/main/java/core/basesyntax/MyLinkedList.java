package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    public MyLinkedList() {
        head = new Node<>(null, null, null);
        tail = new Node<>(null, null, null);
        size = 0;
    }

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(null, value, null);
        if (size == 0) {
            head = newNode;
            tail = newNode;
            size++;
        } else {
            tail.setNext(newNode);
            newNode.setPrev(tail);
            tail = newNode;
            size++;
        }
    }

    @Override
    public void add(T value, int index) {
        if (index == 0 && size == 0) {
            Node<T> newNode = new Node<>(null, value, null);
            head = newNode;
            tail = newNode;
            size++;
        } else {
            if (index == 0) {
                Node<T> currentNode = head;
                Node<T> newNode = new Node<>(null, value, null);
                newNode.setNext(currentNode);
                newNode.setPrev(null);
                currentNode.setPrev(newNode);
                size++;
                if (head == currentNode) {
                    head = newNode;
                }
                if (tail == currentNode) {
                    tail = newNode;
                }
            } else if (index == size) {
                Node<T> currentNode = tail;
                Node<T> newNode = new Node<>(null, value, null);
                newNode.setNext(null);
                newNode.setPrev(currentNode);
                currentNode.setNext(newNode);
                size++;
                if (tail == currentNode) {
                    tail = newNode;
                }
            } else {
                Node<T> currentNode = searchPosition(index, "add");
                Node<T> newNode = new Node<>(null, value, null);
                newNode.setNext(currentNode);
                newNode.setPrev(currentNode.getPrev());
                currentNode.getPrev().setNext(newNode);
                currentNode.setPrev(newNode);
                size++;
                if (head == currentNode) {
                    head = newNode;
                }
            }
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
        Node<T> currentNode = searchPosition(index, "get");
        return currentNode.getValue();
    }

    @Override
    public T set(T value, int index) {
        Node<T> currentNode = searchPosition(index, "set");
        T previousValue = currentNode.getValue();
        currentNode.setValue(value);
        ;
        return previousValue;
    }

    @Override
    public T remove(int index) {
        Node<T> currentNode = searchPosition(index, "remove");
        unlink(currentNode);
        return currentNode.getValue();
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = head;
        while (currentNode != null) {
            if (currentNode.getValue() == object || (currentNode.getValue() != null
                    && currentNode.getValue().equals(object))) {
                unlink(currentNode);
                return true;
            }
            currentNode = currentNode.getNext();
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        if (size == 0) {
            return true;
        }
        return false;
    }

    private Node<T> searchPosition(int index, String operation) {
        Node<T> node = new Node<>();
        if (operation == "add") {
            if (index < 0 || index > size) {
                throw new IndexOutOfBoundsException("Wrong index");
            } else if (index <= size / 2 + 1) {
                node = head;
                for (int i = 0; i < index; i++) {
                    node = node.getNext();
                }
            } else {
                node = tail;
                for (int i = 0; i <= size - index - 2; i++) {
                    node = node.getPrev();
                }
            }
        } else {
            if (index < 0 || index >= size) {
                throw new IndexOutOfBoundsException("Wrong index");
            } else if (index < size / 2) {
                node = head;
                for (int i = 0; i < index; i++) {
                    node = node.getNext();
                }
            } else {
                node = tail;
                for (int i = 0; i <= size - index - 2; i++) {
                    node = node.getPrev();
                }
            }
        }
        return node;
    }

    private void unlink(Node<T> node) {
        if (node == head && node == tail) {
            head = null;
            tail = null;
            size--;
        } else if (node == head) {
            head = node.getNext();
            head.setPrev(null);
            size--;
        } else if (node == tail) {
            tail = node.getPrev();
            tail.setNext(null);
            size--;
        } else {
            node.getPrev().setNext(node.getNext());
            node.getNext().setPrev(node.getPrev());
            size--;
        }
    }

    private class Node<T> {
        private Node<T> next;
        private Node<T> prev;
        private T value;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.next = next;
            this.prev = prev;
            this.value = value;
        }

        public Node() {
        }

        public Node<T> getNext() {
            return next;
        }

        public void setNext(Node<T> next) {
            this.next = next;
        }

        public Node<T> getPrev() {
            return prev;
        }

        public void setPrev(Node<T> prev) {
            this.prev = prev;
        }

        public T getValue() {
            return value;
        }

        public void setValue(T value) {
            this.value = value;
        }
    }
}
