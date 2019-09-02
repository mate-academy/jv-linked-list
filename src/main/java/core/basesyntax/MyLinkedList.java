package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> first;
    private Node<T> last;

    public MyLinkedList() {
        first = null;
        last = null;
    }

    private Node<T> nodeByIndex(int index) {
        Node<T> newNode;
        if (index < size / 2) {
            newNode = first;
            for (int i = 0; i < index; i++) {
                newNode = newNode.next;
            }
            return newNode;
        } else {
            newNode = last;
            for (int i = size - 1; i > index; i--) {
                newNode = newNode.prev;
            }
            return newNode;
        }
    }

    private void addFirst(T value) {
        Node<T> newNode = new Node(value);
        first = last = newNode;
    }

    private void addFront(T value) {
        Node<T> newNode = new Node(value);
        first.prev = newNode;
        newNode.next = first;
        newNode.prev = null;
        first = newNode;
    }

    private void addBack(T value) {
        Node<T> newNode = new Node(value);
        newNode.prev = last;
        last.next = newNode;
        newNode.next = null;
        last = newNode;
    }

    @Override
    public void add(T value) {
        if (size == 0) {
            addFirst(value);
        } else {
            addBack(value);
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (size < index || index < 0) {
            throw new IndexOutOfBoundsException();
        }
        if (index == 0) {
            if (size == 0) {
                addFirst(value);
            } else {
                addFront(value);
            }
        }
        if (index == size) {
            addBack(value);
        }
        Node<T> newNode = new Node(value);
        Node<T> node = nodeByIndex(index - 1);
        node.next = newNode;
        newNode.next = node.next;
        newNode.prev = node;
        node.next.prev = newNode;
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
        if (size <= index || index < 0) {
            throw new IndexOutOfBoundsException();
        }
        return nodeByIndex(index).item;
    }

    @Override
    public void set(T value, int index) {
        if (size <= index || index < 0) {
            throw new IndexOutOfBoundsException();
        }
        nodeByIndex(index).item = value;
    }

    @Override
    public T remove(int index) {
        Node<T> nodeToBeRemoved = null;
        if (size <= index || index < 0) {
            throw new IndexOutOfBoundsException();
        }
        if (size == 1) {
            nodeToBeRemoved = first;
            first = last = null;
            size--;
            return nodeToBeRemoved.item;
        }
        if (index == 0) {
            nodeToBeRemoved = first;
            first.next.prev = null;
            first = nodeToBeRemoved.next;
        } else {
            if (index == size - 1) {
                nodeToBeRemoved = last;
                last.prev.next = null;
                last = nodeToBeRemoved.prev;
            } else {
                nodeToBeRemoved = nodeByIndex(index);
                nodeToBeRemoved.prev.next = nodeToBeRemoved.next;
                nodeToBeRemoved.next.prev = nodeToBeRemoved.prev;
                nodeToBeRemoved.prev = null;
                nodeToBeRemoved.next = null;
            }
        }
        size--;
        if (size == 0) {
            throw new IndexOutOfBoundsException();
        }
        return nodeToBeRemoved.item;
    }

    @Override
    public T remove(T t) {
        Node<T> newNode = first;
        int count = 0;
        for (int i = 0; i < size; i++) {
            if (t.equals(newNode.item)) {
                break;
            }
            count++;
            newNode = newNode.next;
        }
        if (count == size) {
            return null;
        } else {
            return remove(count);
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
        private T item;
        private Node<T> next;
        private Node<T> prev;

        private Node(T element) {
            this.item = element;
        }
    }
}


