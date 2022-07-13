package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> head;
    private Node<T> tail;

    public MyLinkedList() {}

    public MyLinkedList(int size, Node<T> head, Node<T> tail) {
        this.size = size;
        this.head = head;
        this.tail = tail;
    }

    @Override
    public void add(T value) {
        add(value, size);
    }

    @Override
    public void add(T value, int index) {
        checkIndex(index, size);
        if (index == 0) {
            addFirst(value);
        } else if (index == size) {
            addLast(value);
        } else {
            Node<T> oldNode = findElement(index);
            Node<T> newNode = new Node<T>(oldNode.prev, value, oldNode);
            oldNode.prev.next = newNode;
            oldNode.prev = newNode;
            size++;
        }

    }

    @Override
    public void addAll(List<T> list) {
        for (T element : list) {
            add(element);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index, size - 1);
        return findElement(index).value;
    }

    @Override
    public T set(T value, int index) {
        T removed = remove(index);
        add(value, index);
        return removed;
    }

    @Override
    public T remove(int index) {
        checkIndex(index, size - 1);
        if (index == 0) {
            return removeFirst();
        } else if (index == size - 1) {
            return removeLast();
        } else {
            Node<T> oldNode = findElement(index);
            unlink(oldNode);
            size--;
            return oldNode.value;
        }
    }

    @Override
    public boolean remove(T object) {
        if (size == 0) {
            return false;
        }
        int index = -1;
        Node<T> currentNode = head;
        for (int i = 0; i < size; i++) {
            if (currentNode.value == object || (currentNode.value != null
                    && currentNode.value.equals(object))) {
                index = i;
                break;
            }
            currentNode = currentNode.next;
        }
        if (index == -1) {
            return false;
        }
        if (index == 0) {
            removeFirst();
            return true;
        } else if (index == size - 1) {
            removeLast();
            return true;
        } else {
            unlink(currentNode);
            size--;
            return true;
        }

    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

     private class Node<T> {
        Node<T> prev;
        T value;
        Node<T> next;

        public Node( Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }

    private Node<T> findElement(int index) {
        Node<T> currentNode;
        if (index < size / 2) {
            currentNode = head;
            for (int i = 0; i < index; i++) {
                currentNode = currentNode.next;
            }
        } else {
            currentNode = tail;
            for (int i = size - 1; i > index; i--) {
                currentNode = currentNode.prev;
            }
        }
        return currentNode;
    }

    private void addFirst(T value) {
        Node<T> oldNode = head;
        head = new Node<T>(null, value, oldNode);
        if (tail == null) {
            tail = head;
        } else {
            oldNode.prev = head;
        }
        size++;
    }

    private void addLast(T value) {
        Node<T> oldNode = tail;
        tail = new Node<T>(oldNode, value, null);
        if (head == null) {
            head = tail;
        } else {
            oldNode.next = tail;
        }
        size++;
    }

    private void unlink(Node<T> node){
        node.next.prev = node.prev;
        node.prev.next = node.next;
        node.prev = null;
        node.next = null;
    }

    private void checkIndex(int index, int limit) {
        if (index < 0 || index > limit) {
            throw new IndexOutOfBoundsException("Index is outside");
        }
    }

    private T removeFirst() {
        Node<T> oldNode = head;
        head = head.next;
        if (head != null) {
            head.prev = null;
        } else {
            tail = null;
        }
        oldNode.next = null;
        size--;
        return oldNode.value;
    }

    private T removeLast() {
        Node<T> oldNode = tail;
        tail = tail.prev;
        if (tail != null) {
            tail.next = null;
        } else {
            head = null;
        }
        oldNode.prev = null;
        size--;
        return oldNode.value;
    }


}
