package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> node = new Node<>(tail, value, null);
        if (tail == null) {
            head = node;
        } else {
            tail.next = node;
        }
        tail = node;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("Index " + index + " out of bounds.");
        }
        if (index == size) {
            add(value);
        } else {
            Node<T> nodeByIndex = getNodeByIndex(index);
            Node<T> prevNodeByIndex = nodeByIndex.prev;
            Node<T> newNode = new Node<>(prevNodeByIndex, value, nodeByIndex);
            nodeByIndex.prev = newNode;
            if (prevNodeByIndex == null) {
                head = newNode;
            } else {
                prevNodeByIndex.next = newNode;
            }
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
        Node<T> neededNode = getNodeByIndex(index);
        return neededNode.value;
    }

    @Override
    public T set(T value, int index) {
        Node<T> setNode = getNodeByIndex(index);
        T oldValue = setNode.value;
        setNode.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        Node<T> removedNode = getNodeByIndex(index);
        unlink(removedNode);
        size--;
        return removedNode.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> actualtNode = head;
        while (actualtNode != null) {
            if (object == actualtNode.value || object != null && object.equals(actualtNode.value)) {
                unlink(actualtNode);
                size--;
                return true;
            }
            actualtNode = actualtNode.next;
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

    private class Node<T> {
        private Node<T> next;
        private Node<T> prev;
        private T value;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }

    private void checkIndex(int index) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("Index: " + index + " less then 0");
        }
        if (index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index
                    + " more or equals  then size: " + size);
        }
    }

    private void unlink(Node<T> currentNode) {
        if (currentNode == head) {
            if (head == tail) {
                head = null;
                tail = null;
            } else {
                head = head.next;
            }
        } else if (currentNode == tail) {
            tail = tail.prev;
            tail.next = null;
        } else {
            currentNode.prev.next = currentNode.next;
            currentNode.next.prev = currentNode.prev;
        }
    }

    private Node<T> getNodeByIndex(int index) {
        checkIndex(index);
        if (index < (size / 2)) {
            Node<T> current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
            return current;
        } else {
            Node<T> current = tail;
            for (int i = size - 1; i > index; i--) {
                current = current.prev;
            }
            return current;
        }
    }
}
