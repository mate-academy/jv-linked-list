package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(value, tail, null);
        if (size == 0) {
            head = newNode;
            tail = newNode;
        } else {
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index > size || index < 0) {
            throw new ArrayIndexOutOfBoundsException("Can't find element by this index");
        }
        Node<T> newNode = new Node<>(value, null, null);
        if (index == 0) {
            if (head == null) {
                head = newNode;
                tail = newNode;
            } else {
                newNode.next = head;
                head.prev = newNode;
                head = newNode;
            }
        } else if (index == size) {
            newNode.prev = tail;
            tail.next = newNode;
            tail = newNode;
        } else {
            Node<T> current = findNode(index);
            newNode.prev = current.prev;
            newNode.next = current;
            current.prev.next = newNode;
            current.prev = newNode;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T item : list) {
            add(item);
        }
    }

    @Override
    public T get(int index) {
        return findNode(index).item;
    }

    @Override
    public T set(T value, int index) {
        indexCheck(index);
        Node<T> current = findNode(index);
        T oldItem = current.item;
        Node<T> newNode = new Node<>(value, current.prev, current.next);
        if (index == 0) {
            head = newNode;
            if (head.next != null) {
                head.next.prev = head;
            }
        } else if (index == size - 1) {
            tail = newNode;
            if (tail.prev != null) {
                tail.prev.next = tail;
            }
        } else {
            current.prev.next = newNode;
            current.next.prev = newNode;
        }
        return oldItem;
    }

    @Override
    public T remove(int index) {
        return removeObject(findNode(index));
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = head;
        while (current != null) {
            if ((object == null && current.item == null)
                    || (object != null && object.equals(current.item))) {
                removeObject(current);
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

    private void indexCheck(int index) {
        if (index >= size || index < 0) {
            throw new ArrayIndexOutOfBoundsException("Can't find element by this index");
        }
    }

    private Node<T> findNode(int index) {
        indexCheck(index);
        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current;
    }

    private T removeObject(Node<T> removingNode) {
        if (removingNode.prev != null) {
            removingNode.prev.next = removingNode.next;
        } else {
            head = removingNode.next;
        }
        if (removingNode.next != null) {
            removingNode.next.prev = removingNode.prev;
        } else {
            tail = removingNode.prev;
        }
        size--;
        return removingNode.item;
    }

    private class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        Node(T item, Node<T> prev, Node<T> next) {
            this.item = item;
            this.prev = prev;
            this.next = next;
        }
    }
}
