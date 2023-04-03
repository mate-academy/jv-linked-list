package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> currentNode;
    private Node<T> previousNode;
    private Node<T> head;
    private Node<T> tail;

    static class Node<T> {
        private T item;
        private Node<T> prev;
        private Node<T> next;

        public Node(Node<T> prev, T item, Node<T> next) {
            this.prev = prev;
            this.item = item;
            this.next = next;
        }
    }

    @Override
    public void add(T value) {
        if (!isEmpty()) {
            if (size == 1) {
                previousNode = currentNode;
            }
            currentNode = new Node<>(previousNode, value, null);
            previousNode.next = currentNode;
            previousNode = currentNode;
        } else {
            currentNode = new Node<>(previousNode, value, null);
            head = currentNode;
        }
        tail = currentNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
        } else {
            checkIndex(index);
            Node<T> newNode;
            if (index == 0) {
                newNode = new Node<>(null, value, getNodeByIndex(index));
                head = newNode;
            } else {
                newNode = new Node<>(getNodeByIndex(index).prev, value, getNodeByIndex(index));
                getNodeByIndex(index).prev.next = newNode;
            }
            getNodeByIndex(index).prev = newNode;
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
        checkIndex(index);
        Node<T> nodeByIndex = getNodeByIndex(index);
        return nodeByIndex.item;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        T valueBeforeSetting = getNodeByIndex(index).item;
        getNodeByIndex(index).item = value;
        return valueBeforeSetting;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        T removedValue = get(index);
        unlink(getNodeByIndex(index));
        size--;
        return removedValue;
    }

    @Override
    public boolean remove(T object) {
        for (int i = 0; i < size; i++) {
            if (get(i) != null && get(i).equals(object) || get(i) == object) {
                if (size == 1) {
                    currentNode = null;
                    size--;
                    return true;
                }
                unlink(getNodeByIndex(i));
                size--;
                return true;
            }
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

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid index: " + index + ". Size: " + size);
        }
    }

    private Node<T> getNodeByIndex(int index) {
        checkIndex(index);
        if (index > size / 2) {
            return getNodeIteratingFromTheTail(index);
        } else {
            return getNodeIteratingFromTheHead(index);
        }
    }

    private Node<T> getNodeIteratingFromTheTail(int index) {
        Node<T> nodeByIndex = tail;
        int count = size - 1;
        while (count != index) {
            nodeByIndex = nodeByIndex.prev;
            count--;
        }
        return nodeByIndex;
    }

    private Node<T> getNodeIteratingFromTheHead(int index) {
        Node<T> nodeByIndex = head;
        int count = 0;
        while (count != index) {
            nodeByIndex = nodeByIndex.next;
            count++;
        }
        return nodeByIndex;
    }

    private void unlink(Node<T> removedNode) {
        if ((removedNode == head || removedNode == tail) && size == 1) {
            currentNode = null;
        } else if (removedNode == head) {
            head = removedNode.next;
            removedNode.next.prev = null;
        } else if (removedNode == tail) {
            removedNode.prev.next = null;
            currentNode = removedNode.prev;
            tail = currentNode;
        } else {
            removedNode.prev.next = removedNode.next;
            removedNode.next.prev = removedNode.prev;
        }
    }
}
