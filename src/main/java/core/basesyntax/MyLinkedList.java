package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    public MyLinkedList() {
        head = null;
        tail = null;
        size = 0;
    }

    @Override
    public void add(T value) {
        if (tail != null) {
            Node<T> newNode = new Node<>(value, tail, null);
            tail.next = newNode;
            tail = newNode;
        } else {
            head = new Node<>(value, null, null);
            tail = head;
        }

        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("List index out of bounds. Size of list: " + size);
        }

        if (index == size) {
            add(value);
            return;
        }

        Node<T> indexNode = findNodeByIndex(index);

        if (indexNode != null) {
            Node<T> newNode = new Node<>(value, indexNode.prev, indexNode);
            indexNode.prev = newNode;

            if (newNode.prev != null) {
                newNode.prev.next = newNode;
            } else {
                head = newNode;
            }
            size++;
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
        checkIndex(index);
        Node<T> indexNode = findNodeByIndex(index);
        return indexNode != null ? indexNode.value : null;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> indexNode = findNodeByIndex(index);
        if (indexNode != null) {
            T prevValue = indexNode.value;
            indexNode.value = value;
            return prevValue;
        }
        return null;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> indexNode = findNodeByIndex(index);
        if (indexNode != null) {
            unlink(indexNode);
            return indexNode.value;
        }
        return null;
    }

    @Override
    public boolean remove(T object) {
        if (head == null) {
            return false;
        }

        Node<T> currentNode = head;
        for (int i = 0; i < size; i++) {
            if ((object == currentNode.value)
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

    private Node<T> findNodeByIndex(int index) {
        Node<T> resultNode;
        if (index > size / 2) {
            resultNode = tail;
            for (int i = size - 1; i > index; i--) {
                resultNode = resultNode.prev;
            }

        } else {
            resultNode = head;
            for (int i = 0; i < index; i++) {
                resultNode = resultNode.next;
            }
        }

        return resultNode;
    }

    private void checkIndex(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("List index out of bounds. Size of list: " + size);
        }
    }

    private void unlink(Node<T> node) {
        Node<T> prevNode = node.prev != null ? node.prev : null;
        Node<T> nextNode = node.next != null ? node.next : null;

        if (nextNode != null) {
            nextNode.prev = prevNode;
        } else {
            tail = prevNode;
        }

        if (prevNode != null) {
            prevNode.next = nextNode;
        } else {
            head = nextNode;
        }

        size--;
    }

    public class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        public Node(T value, Node<T> prev, Node<T> next) {
            this.value = value;
            this.prev = prev;
            this.next = next;
        }
    }
}
