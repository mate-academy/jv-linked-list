package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> head;
    private Node<T> tail;

    private static class Node<T> {
        private Node<T> prev;
        private T item;
        private Node<T> next;

        Node(Node<T> prev, T item, Node<T> next) {
            this.prev = prev;
            this.item = item;
            this.next = next;
        }
    }

    @Override
    public void add(T value) {
        if (size == 0) {
            head = (Node<T>) new Node<>(null, value, null);
            tail = head;
        }
        if (size > 0) {
            Node<T> tempNode = tail;
            tail = (Node<T>) new Node<>(tempNode, value, null);
            tempNode.next = tail;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("Invalid Index");
        }
        if (size == 0) {
            head = (Node<T>) new Node<>(null, value, null);
            tail = head;
            size++;
            return;
        }
        Node<T> tempNode = (Node<T>) new Node<>(null, value, null);

        if (index == 0) {
            tempNode.next = head;
            head.prev = tempNode;
            head = tempNode;
            size++;
            return;
        }
        if (index == size) {
            tempNode.prev = tail;
            tail.next = tempNode;
            tail = tempNode;
            size++;
            return;
        }
        Node<T> prevNode = head;
        Node<T> nextNode = head;

        for (int i = 0; i < index; i++) {
            nextNode = nextNode.next;
            if (i != index - 1) {
                prevNode = prevNode.next;
            }
        }
        tempNode.prev = prevNode;
        prevNode.next = tempNode;
        tempNode.next = nextNode;
        nextNode.prev = tempNode;

        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            Node<T> tempNode = tail;
            tail = (Node<T>) new Node<>(tempNode, list.get(i), null);
            tempNode.next = tail;
            size++;
        }
    }

    @Override
    public T get(int index) {
        if (size > index && index >= 0) {
            Node<T> tempNode = head;
            for (int i = 0; i < index; i++) {
                tempNode = tempNode.next;
            }
            return tempNode.item;
        } else {
            throw new IndexOutOfBoundsException("Invalid Index");
        }
    }

    @Override
    public T set(T value, int index) {
        if (size > index && index >= 0) {
            Node<T> nodeToReplace = head;
            for (int i = 0; i < index; i++) {
                nodeToReplace = nodeToReplace.next;
            }
            Node<T> newNode = new Node<>(null, value, null);
            if (index == 0) {
                newNode.next = head.next;
                head.next.prev = newNode;
                head = newNode;
            } else {
                newNode.prev = nodeToReplace.prev;
                newNode.prev.next = newNode;
                newNode.next = nodeToReplace.next;
                newNode.next.prev = newNode;
            }
            return nodeToReplace.item;
        } else {
            throw new IndexOutOfBoundsException("Invalid Index");
        }
    }

    @Override
    public T remove(int index) {
        if (size > index && index >= 0) {
            Node<T> nodeToDelete = head;
            for (int i = 0; i < index; i++) {
                nodeToDelete = nodeToDelete.next;
            }
            if (index == 0 && size == 1) {
                nodeToDelete.next = null;
                nodeToDelete.prev = null;
            } else if (index == 0) {
                nodeToDelete.next.prev = null;
                head = nodeToDelete.next;
            } else if (index == size - 1) {
                nodeToDelete.prev.next = null;
                tail = nodeToDelete.prev;
            } else {
                nodeToDelete.prev.next = nodeToDelete.next;
                nodeToDelete.next.prev = nodeToDelete.prev;
            }
            size--;
            return nodeToDelete.item;
        } else {
            throw new IndexOutOfBoundsException("Invalid Index");
        }
    }

    @Override
    public boolean remove(T object) {
        Node<T> nodeToDelete = head;
        for (int i = 0; i < size; i++) {
            if (nodeToDelete.item == null && object == null
                    || object != null && object.equals(nodeToDelete.item)) {
                if (i == 0 && size == 1) {
                    nodeToDelete.next = null;
                    nodeToDelete.prev = null;
                } else if (i == 0) {
                    nodeToDelete.next.prev = null;
                    head = nodeToDelete.next;
                } else if (i == size - 1) {
                    nodeToDelete.prev.next = null;
                    tail = nodeToDelete.prev;
                } else {
                    nodeToDelete.prev.next = nodeToDelete.next;
                    nodeToDelete.next.prev = nodeToDelete.prev;
                }
                size--;
                return true;
            }
            nodeToDelete = nodeToDelete.next;
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        if (size == 0) {
            return true;
        }
        return false;
    }
}
