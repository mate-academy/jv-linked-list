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
            addFirstNode(value, newNode);
        } else {
            tail.setNext(newNode);
            newNode.setPrev(tail);
            tail = newNode;
            size++;
        }
    }

    @Override
    public void add(T value, int index) {
        Node<T> newNode = new Node<>(null, value, null);
        if (index == 0 && size == 0) {
            addFirstNode(value, newNode);
        } else {
            if (index == 0) {
                newNode.setNext(head);
                newNode.setPrev(null);
                head.setPrev(newNode);
                head = newNode;
                size++;
            } else if (index == size) {
                newNode.setNext(null);
                newNode.setPrev(tail);
                tail.setNext(newNode);
                tail = newNode;
                size++;
            } else {
                checkCorrectIndexAdd(index);
                Node<T> currentNode = searchPosition(index);
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
        checkCorrectIndexGetRemove(index);
        Node<T> currentNode = searchPosition(index);
        return currentNode.getValue();
    }

    @Override
    public T set(T value, int index) {
        checkCorrectIndexGetRemove(index);
        Node<T> currentNode = searchPosition(index);
        T previousValue = currentNode.getValue();
        currentNode.setValue(value);
        return previousValue;
    }

    @Override
    public T remove(int index) {
        checkCorrectIndexGetRemove(index);
        Node<T> currentNode = searchPosition(index);
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
        return size == 0;
    }

    private void addFirstNode(T value, Node<T> newNode) {
        head = newNode;
        tail = newNode;
        size++;
    }

    private void checkCorrectIndexAdd(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Wrong index" + index);
        }
    }

    private void checkCorrectIndexGetRemove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Wrong index" + index);
        }
    }

    private Node<T> searchPosition(int index) {
        Node<T> currentNode = new Node<>();
        if (index < size / 2) {
            currentNode = head;
            int position = 0;
            while (position < index) {
                currentNode = currentNode.next;
                position++;
            }
        } else {
            currentNode = tail;
            int position = size - 1;
            while (position > index) {
                currentNode = currentNode.prev;
                position--;
            }
        }
        return currentNode;
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
