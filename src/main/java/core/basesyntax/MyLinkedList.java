package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    public MyLinkedList() {
        size = 0;
    }

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(tail, value, null);
        if (tail == null) {
            head = newNode;
            tail = head;
        } else {
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        Node<T> nodeOnIndex = find(index);
        Node<T> newNode = new Node<>(nodeOnIndex.prev, value, nodeOnIndex);
        if (nodeOnIndex == head) {
            head.prev = newNode;
            head = newNode;
        } else {
            nodeOnIndex.prev.next = newNode;
            nodeOnIndex.prev = newNode;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        list.forEach(this::add);
    }

    @Override
    public T get(int index) {
        return find(index).value;
    }

    @Override
    public T set(T value, int index) {
        Node<T> node = find(index);
        T oldValue = node.value;
        node.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        Node<T> nodeOnIndex = find(index);
        unlink(nodeOnIndex);
        return nodeOnIndex.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = head;
        for (int i = 0; i < size; i++) {
            if ((currentNode.value == null && object == null)
                    || (currentNode.value != null && currentNode.value.equals(object))) {
                unlink(currentNode);
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
        return size == 0;
    }

    private Node<T> find(int index) {
        checkIndex(index);
        Node<T> currentNode;
        if (size / 2 >= index) {
            currentNode = head;
            for (int i = 0; i < size; i++) {
                if (i == index) {
                    return currentNode;
                }
                currentNode = currentNode.next;
            }
        } else {
            currentNode = tail;
            for (int i = size - 1; i > 0; i--) {
                if (i == index) {
                    return currentNode;
                }
                currentNode = currentNode.prev;
            }
        }
        return currentNode;
    }

    private void checkIndex(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException(String.format(
                    "Index %d out of size %d",
                    index, size)
            );
        }
    }

    private void unlink(Node<T> node) {
        if (node == head && node == tail) {
            head = tail = null;
        } else if (node == head) {
            head.next.prev = null;
            head = head.next;
        } else if (node == tail) {
            tail.prev.next = null;
            tail = tail.prev;
        } else {
            node.next.prev = node.prev;
            node.prev.next = node.next;
        }
        size--;
    }

    private static class Node<T> {
        private Node<T> prev;
        private Node<T> next;
        private T value;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.next = next;
            this.value = value;
        }
    }
}
