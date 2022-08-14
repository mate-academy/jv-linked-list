package core.basesyntax;

import java.util.List;
import java.util.Objects;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size = 0;

    public MyLinkedList() {

    }

    @Override
    public void add(T value) {
        Node<T> newNode;
        if (size == 0) {
            newNode = new Node<>(null, value, null);
            head = newNode;
        } else {
            newNode = new Node<>(tail, value, null);
            tail.next = newNode;
        }
        tail = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        Node<T> nodeToAdd;
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("index: " + index + " Out of bound size : " + size);
        }
        if (index == size) {
            add(value);
            return;
        }
        Node<T> wantedNode = findNodeByIndex(index);
        if (index == 0) {
            nodeToAdd = new Node<>(null, value, wantedNode);
            head = nodeToAdd;
        } else {
            nodeToAdd = new Node<>(wantedNode.prev, value, wantedNode);
            wantedNode.prev.next = nodeToAdd;
        }
        wantedNode.prev = nodeToAdd;
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T listElement : list) {
            add(listElement);
        }
    }

    @Override
    public T get(int index) {
        indexCheck(index);
        Node<T> wantedNode = findNodeByIndex(index);
        return wantedNode.value;
    }

    @Override
    public T set(T value, int index) {
        indexCheck(index);
        Node<T> wantedNode = findNodeByIndex(index);
        T oldValue = wantedNode.value;
        wantedNode.value = value;
        return oldValue;
    }

    private void indexCheck(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("index: " + index + " Out of bound size : " + size);
        }
    }

    @Override
    public T remove(int index) {
        indexCheck(index);
        Node<T> wantedNode = findNodeByIndex(index);
        if (head == tail) {
            head = null;
            tail = null;
        } else if (index == 0) {
            head = wantedNode.next;
            wantedNode.next.prev = null;
        } else if (index == size - 1) {
            tail = wantedNode.prev;
            wantedNode.prev.next = null;
        } else {
            wantedNode.prev.next = wantedNode.next;
            wantedNode.next.prev = wantedNode.prev;
        }
        unlink(wantedNode);
        size--;
        return wantedNode.value;
    }

    @Override
    public boolean remove(T object) {
        boolean result = false;
        Node<T> node = head;
        for (int i = 0; i < size; i++) {
            if (Objects.equals(wantedNode.value, object)) {
                result = true;
                remove(i);
                break;
            }
            wantedNode = wantedNode.next;
        }
        return result;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private static class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        public Node(Node<T> previous, T value, Node<T> next) {
            this.prev = previous;
            this.value = value;
            this.next = next;
        }
    }

    private Node<T> findNodeByIndex(int index) {
        Node<T> wantedNode = (index <= size / 2) ? head : tail;
        if (wantedNode == head) {
            for (int i = 0; i < size; i++) {
                if (i == index) {
                    break;
                }
                wantedNode = wantedNode.next;
            }
        } else {
            for (int i = size - 1; i >= 0; i--) {
                if (i == index) {
                    break;
                }
                wantedNode = wantedNode.prev;
            }
        }
        return wantedNode;
    }

    private void unlink(Node<T> wantedNode) {
        wantedNode.prev = null;
        wantedNode.next = null;
    }
}
