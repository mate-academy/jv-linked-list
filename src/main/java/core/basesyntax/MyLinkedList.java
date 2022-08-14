package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size = 0;

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
        Node<T> node = findNodeByIndex(index);
        if (index == 0) {
            nodeToAdd = new Node<>(null, value, node);
            head = nodeToAdd;
        } else {
            nodeToAdd = new Node<>(node.prev, value, node);
            node.prev.next = nodeToAdd;
        }
        node.prev = nodeToAdd;
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
        Node<T> node = findNodeByIndex(index);
        return node.value;
    }

    @Override
    public T set(T value, int index) {
        Node<T> node = findNodeByIndex(index);
        T oldValue = node.value;
        node.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        Node<T> node = findNodeByIndex(index);
        unlink(node,index);
        return node.value;
    }

    @Override
    public boolean remove(T object) {
        boolean result = false;
        Node<T> node = head;
        for (int i = 0; i < size; i++) {
            if (node.value == object || node.value != null && node.value.equals(object)) {
                result = true;
                remove(i);
                break;
            }
            node = node.next;
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

    private Node<T> findNodeByIndex(int index) {
        checkElementIndex(index);
        Node<T> node = (index <= size / 2) ? head : tail;
        if (node == head) {
            for (int i = 0; i < size; i++) {
                if (i == index) {
                    break;
                }
                node = node.next;
            }
        } else {
            for (int i = size - 1; i >= 0; i--) {
                if (i == index) {
                    break;
                }
                node = node.prev;
            }
        }
        return node;
    }

    private void unlink(Node<T> node,int index) {
        if (head == tail) {
            head = null;
            tail = null;
        } else if (index == 0) {
            head = node.next;
            node.next.prev = null;
        } else if (index == size - 1) {
            tail = node.prev;
            node.prev.next = null;
        } else {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }
        node.prev = null;
        node.next = null;
        size--;
    }

    private void checkElementIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("index: " + index + " Out of bound size : " + size);
        }
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
}
