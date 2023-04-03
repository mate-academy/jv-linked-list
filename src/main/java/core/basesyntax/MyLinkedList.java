package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> head;
    private Node<T> tail;

    @Override
    public void add(T value) {
        tail = new Node<>(tail, value, null);
        if (!isEmpty()) {
            tail.prev.next = tail;
        } else {
            head = tail;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
        } else {
            Node<T> newNode;
            Node<T> nodeByIndex = getNodeByIndex(index);
            if (index == 0) {
                newNode = new Node<>(null, value, nodeByIndex);
                head = newNode;
            } else {
                newNode = new Node<>(nodeByIndex.prev, value, nodeByIndex);
                nodeByIndex.prev.next = newNode;
            }
            nodeByIndex.prev = newNode;
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
        Node<T> nodeByIndex = getNodeByIndex(index);
        return nodeByIndex.item;
    }

    @Override
    public T set(T value, int index) {
        Node<T> nodeForSettingValue = getNodeByIndex(index);
        T valueBeforeSetting = nodeForSettingValue.item;
        nodeForSettingValue.item = value;
        return valueBeforeSetting;
    }

    @Override
    public T remove(int index) {
        Node<T> removedNode = getNodeByIndex(index);
        T removedValue = removedNode.item;
        unlink(removedNode);
        return removedValue;
    }

    @Override
    public boolean remove(T object) {
        Node<T> removedNode = head;
        T itemOfRemovedNode;
        for (int i = 0; i < size; i++) {
            itemOfRemovedNode = removedNode.item;
            if (itemOfRemovedNode != null && itemOfRemovedNode.equals(object)
                    || itemOfRemovedNode == object) {
                unlink(removedNode);
                return true;
            }
            removedNode = removedNode.next;
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
        }
        return getNodeIteratingFromTheHead(index);
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
            tail = null;
        } else if (removedNode == head) {
            head = removedNode.next;
            removedNode.next.prev = null;
        } else if (removedNode == tail) {
            removedNode.prev.next = null;
            tail = removedNode.prev;
        } else {
            removedNode.prev.next = removedNode.next;
            removedNode.next.prev = removedNode.prev;
        }
        size--;
    }

    private static class Node<T> {
        private T item;
        private Node<T> prev;
        private Node<T> next;

        public Node(Node<T> prev, T item, Node<T> next) {
            this.prev = prev;
            this.item = item;
            this.next = next;
        }
    }
}
