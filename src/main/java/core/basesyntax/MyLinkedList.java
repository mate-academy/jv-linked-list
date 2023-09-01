package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(null, value, null);
        if (size == 0) {
            head = tail = newNode;
        } else {
            newNode.prev = tail;
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkIndex(index, size + 1);
        if (size == 0) {
            Node<T> newNode = new Node<>(null, value, null);
            head = tail = newNode;
        } else if (index == 0 && size > 0) {
            Node<T> newNode = new Node<>(null, value, head);
            head.prev = newNode;
            head = newNode;
        } else if (index == size) {
            Node<T> newNode = new Node<>(tail, value, null);
            tail.next = newNode;
            tail = newNode;
        } else {
            Node<T> prev = findNodeByIndex(index - 1);
            Node<T> next = findNodeByIndex(index);
            Node<T> newNode = new Node<>(prev, value, next);
            findNodeByIndex(index - 1).next = newNode;
            findNodeByIndex(index).prev = newNode;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T element : list) {
            add(element);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index, size);
        return findNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index, size);
        T oldValue = findNodeByIndex(index).value;
        findNodeByIndex(index).value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index, size);
        return unlink(findNodeByIndex(index));
    }

    @Override
    public boolean remove(T value) {
        int indexValue = findIndexByValue(value);
        if (indexValue >= 0) {
            unlink(findNodeByIndex(indexValue));
            return true;
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

    public Node<T> findNodeByIndex(int index) {
        Node<T> currentNode = head;
        if (index <= size / 2) {
            for (int i = 0; i <= index; i++) {
                if (i == index) {
                    break;
                }
                currentNode = currentNode.next;
            }
        } else {
            currentNode = tail;
            for (int i = size - 1; i >= 0; i--) {
                if (i == index) {
                    break;
                }
                currentNode = currentNode.prev;
            }
        }
        return currentNode;
    }

    private int findIndexByValue(T value) {
        Node<T> currentNode = head;
        int index = 0;
        while (currentNode != null) {
            if (currentNode.value == value
                    || currentNode.value != null && currentNode.value.equals(value)) {
                return index;
            }
            currentNode = currentNode.next;
            index++;
        }
        return -1;
    }

    private void checkIndex(int index, int length) {
        if (index < 0 || index >= length) {
            throw new IndexOutOfBoundsException("Index " + index + " is out of bounds");
        }
    }

    private T unlink(Node<T> node) {
        int indexNode = findIndexByValue(node.value);
        checkIndex(indexNode, size);
        T unlinkedValue = findNodeByIndex(indexNode).value;
        if (indexNode == 0 && size > 1) {
            findNodeByIndex(indexNode + 1).prev = null;
            head = findNodeByIndex(indexNode + 1);
        } else if (indexNode == 0 && size == 1) {
            head = null;
            tail = null;
        } else if (indexNode == size - 1 && size > 1) {
            findNodeByIndex(indexNode - 1).next = null;
            tail = findNodeByIndex(indexNode - 1);
        } else {
            findNodeByIndex(indexNode + 1).prev = findNodeByIndex(indexNode - 1);
            findNodeByIndex(indexNode - 1).next = findNodeByIndex(indexNode + 1);
        }
        size--;
        return unlinkedValue;
    }

    private static class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        private Node(Node<T> prev, T value, Node<T> next) {
            this.value = value;
            this.prev = prev;
            this.next = next;
        }
    }
}
