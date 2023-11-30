package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> head;
    private Node<T> tail;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(null, value, null);
        if (head == null) {
            head = newNode;
            tail = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        Node<T> newNode = new Node<>(null, value, null);
        if (index == 0) {
            newNode.next = head;
            if (head != null) {
                head.prev = newNode;
            }
            head = newNode;
            if (tail == null) {
                tail = newNode;
            }
        } else if (index == size) {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        } else {
            Node<T> current = findNodeByIndex(index - 1);
            newNode.next = current.next;
            newNode.prev = current;
            current.next.prev = newNode;
            current.next = newNode;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T newNodes : list) {
            add(newNodes);
        }
    }

    @Override
    public T get(int index) {
        Node<T> returnNode = findNodeByIndex(index);
        return returnNode.data;
    }

    @Override
    public T set(T value, int index) {
        Node<T> updateNode = findNodeByIndex(index);
        T oldNode = updateNode.data;
        updateNode.data = value;
        return oldNode;
    }

    @Override
    public T remove(int index) {
        validIndex(index);
        Node<T> current = null;
        T nodeToRemove = null;
        if (size == 1) {
            nodeToRemove = head.data;
            head = null;
            tail = null;
            size--;
            return nodeToRemove;
        } else if (index == 0) {
            nodeToRemove = head.data;
            head = head.next;
            head.prev = null;
            size--;
            return nodeToRemove;
        } else if (index == size - 1) {
            nodeToRemove = tail.data;
            tail = tail.prev;
            tail.next = null;
            size--;
            return nodeToRemove;
        } else {
            current = findNodeByIndex(index);
            nodeToRemove = current.data;
            unlink(current);
        }
        return nodeToRemove;
    }

    @Override
    public boolean remove(T object) {
        Node<T> nodeToRemove = head;
        while (nodeToRemove != null) {
            if (object == nodeToRemove.data
                    || object != null && object.equals(nodeToRemove.data)) {
                if (nodeToRemove == head) {
                    head = head.next;
                    size--;
                    return true;
                }
                if (nodeToRemove == tail) {
                    tail = nodeToRemove.prev;
                    if (tail != null) {
                        tail.next = null;
                        size--;
                        return true;
                    }
                } else {
                    unlink(nodeToRemove);
                    return true;
                }
            }
            nodeToRemove = nodeToRemove.next;
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
        validIndex(index);
        Node<T> current = null;
        if (index < size / 2) {
            current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
        }
        if (index >= size / 2) {
            current = tail;
            for (int i = size - 1; i > index; i--) {
                current = current.prev;
            }
        }
        return current;
    }

    private void unlink(Node<T> nodeToDelete) {
        nodeToDelete.prev.next = nodeToDelete.next;
        nodeToDelete.next.prev = nodeToDelete.prev;
        size--;
    }

    private void validIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Wrong index: " + index);
        }
    }

    private static class Node<T> {
        private T data;
        private Node<T> next;
        private Node<T> prev;

        Node(Node<T> prev, T data, Node<T> next) {
            this.data = data;
            this.prev = prev;
            this.next = next;
        }
    }
}
