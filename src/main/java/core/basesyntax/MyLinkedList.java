package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size = 0;

    @Override
    public void add(T value) {
        //nullCheck(value);
        if (size == 0) {
            head = new Node<T>(null, value, null);
            tail = head;
            size++;
        } else {
            Node<T> newNode = new Node<>(tail, value, null);
            tail.next = newNode;
            tail = newNode;
            size++;
        }
    }

    @Override
    public void add(T value, int index) {
        addIndexCheck(index);
        //nullCheck(value);

        if (index == size) {
            add(value);
        } else if (index == 0) {
            Node<T> newNode = new Node<>(null, value, head);
            if (head != null) {
                head.prev = newNode;
            } else {
                tail = newNode; // якщо список був порожній
            }
            head = newNode;
            size++;
        } else {
            int tempindex = 0;
            Node<T> currentNode = head;
            while (index != tempindex) {
                currentNode = currentNode.next;
                tempindex++;
            }
            Node<T> newNode = new Node<T>(currentNode.prev, value, currentNode);

            if (currentNode.prev != null) {
                currentNode.prev.next = newNode;
            } else {
                head = newNode; // вставка в початок
            }

            currentNode.prev = newNode;
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        if (!list.isEmpty()) {
            for (int i = 0; i < list.size(); i++) {
                add(list.get(i));
            }
        }
    }

    @Override
    public T get(int index) {
        getIndexCheck(index);
        Node<T> currentNode = head;
        int tempIndex = 0;
        while (index != tempIndex) {
            tempIndex++;
            currentNode = currentNode.next;
        }
        return currentNode.item;
    }

    @Override
    public T set(T value, int index) {
        removeIndexCheck(index);
        Node<T> currentNode = head;
        int tempIndex = 0;

        while (index != tempIndex) {
            tempIndex++;
            currentNode = currentNode.next;
        }
        T oldValue = currentNode.item;
        currentNode.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        removeIndexCheck(index);
        Node<T> currentNode = head;
        int tempIndex = 0;

        while (index != tempIndex) {
            tempIndex++;
            currentNode = currentNode.next;
        }

        if (currentNode.prev != null) {
            currentNode.prev.next = currentNode.next;
        } else {
            head = currentNode.next;
        }

        if (currentNode.next != null) {
            currentNode.next.prev = currentNode.prev;
        } else {
            tail = currentNode.prev;
        }
        size--;
        T oldvalue = currentNode.item;
        return oldvalue;
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = head;

        while (currentNode != null) {
            if (object == null ? currentNode.item == null : object.equals(currentNode.item)) {
                if (currentNode.prev != null) {
                    currentNode.prev.next = currentNode.next;
                } else {
                    head = currentNode.next;
                }

                if (currentNode.next != null) {
                    currentNode.next.prev = currentNode.prev;
                } else {
                    tail = currentNode.prev;
                }

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
        if (size == 0) {
            return true;
        }
        return false;
    }

    private static class Node<E> {
        private E item;
        private Node<E> next;
        private Node<E> prev;

        Node(Node<E> prev, E element, Node<E> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }

    private void addIndexCheck(int index) {
        if (!(index >= 0 && index <= size)) {
            throw new IndexOutOfBoundsException("Incorrect index");
        }
    }

    private void getIndexCheck(int index) {
        if (!(index >= 0 && index < size)) {
            throw new IndexOutOfBoundsException("Incorrect index");
        }
    }

    private void removeIndexCheck(int index) {
        if (!(index >= 0 && index <= size - 1)) {
            throw new IndexOutOfBoundsException("Incorrect index");
        }
    }

    private void nullCheck(T value) {
        if (value == null) {
            throw new NullPointerException("value cannot be null");
        }
    }
}
