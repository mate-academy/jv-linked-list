package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> head;
    private Node<T> tail;

    private static class Node<T> {
        private Node<T> prev;
        private T value;
        private Node<T> next;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(tail, value, null);
        if (size == 0) {
            head = newNode;
        } else {
            tail.next = newNode;
        }
        tail = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkIndexForAdd(index);

        if (index == size) {
            add(value);
        } else if (index == 0) {
            Node<T> currentNode = getNode(index);
            Node<T> newNode = new Node<>(null, value, currentNode);
            currentNode.prev = newNode;
            head = newNode;
            size++;
        } else {
            Node<T> currentNode = getNode(index);
            Node<T> newNode = new Node<>(currentNode.prev, value, currentNode);
            newNode.prev.next = newNode;
            newNode.next.prev = newNode;
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
        checkIndexForGet(index);
        return getNode(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkIndexForGet(index);
        T oldNodeValue = get(index);
        getNode(index).value = value;
        return oldNodeValue;
    }

    @Override
    public T remove(int index) {
        checkIndexForGet(index);
        Node<T> oldNode = getNode(index);
        if (size == 1) {
            head = null;
            tail = null;
        } else {
            if (index == 0) {
                head = head.next;
                head.prev = null;
            } else if (index == (size - 1)) {
                tail = tail.prev;
                tail.next = null;
            } else {
                oldNode.prev.next = oldNode.next;
                oldNode.next.prev = oldNode.prev;
            }
        }
        size--;
        return oldNode.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> nodeToFind = head;
        int i = 0;
        while (i < size) {
            if (nodeToFind.value == object
                    || (nodeToFind.value != null && nodeToFind.value.equals(object))) {
                remove(i);
                return true;
            }
            nodeToFind = nodeToFind.next;
            i++;
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

    private Node<T> getNode(int index) {
        Node<T> current;
        int i;
        if ((double) index / size >= 0.5) {
            current = tail;
            i = size - 1;
            while (i != index) {
                current = current.prev;
                i--;
            }
        } else {
            current = head;
            i = 0;
            while (i != index) {
                current = current.next;
                i++;
            }
        }
        return current;
    }

    private void checkIndexForGet(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Can`t reach index " + index);
        }
    }

    private void checkIndexForAdd(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Can`t add element at index " + index
            + ", with list size " + size);
        }
    }

}
