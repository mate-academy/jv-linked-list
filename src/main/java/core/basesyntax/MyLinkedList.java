package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node head;
    private Node tail;
    private int size;

    {
        size = 0;
    }

    private static class Node<T> {
        private T item;
        private Node next;
        private Node prev;

        Node(Node prev, T element, Node next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }

    public void addIfZeroSize(T value) {
        Node node = new Node(null, value, null);
        head = node;
        tail = node;
    }

    public void addToTail(T value) {
        Node node = new Node(tail, value, null);
        tail.next = node;
        tail = node;
    }

    @Override
    public void add(T value) {
        if (size == 0) {
            addIfZeroSize(value);
        } else {
            addToTail(value);
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkIndexIfAdd(index);
        if (size == 0 && index == 0) {
            addIfZeroSize(value);
        } else if (size > 0 && index == 0) {
            Node node = new Node(null, value, head);
            head.prev = node;
            head = node;
        } else if (size > 0 && index == size) {
            addToTail(value);
        } else {
            Node currentNode = indexIterator(index);
            Node node = new Node(currentNode.prev, value, currentNode);
            currentNode.prev.next = node;
            currentNode.prev = node;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            add(list.get(i));
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return (T) indexIterator(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node currentNode = indexIterator(index);
        T tempValue = (T) currentNode.item;
        currentNode.item = value;
        return tempValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        if (size == 1) {
            Node currentNode = head;
            unlinkNode(currentNode, "zero_only");
            return (T) currentNode.item;
        }
        if (size > 1 && index == 0) {
            Node currentNode = head;
            unlinkNode(currentNode, "head");
            return (T) currentNode.item;
        } else if (size > 1 && index == size - 1) {
            Node currentNode = tail;
            unlinkNode(currentNode, "tail");
            return (T) currentNode.item;
        }
        Node currentNode = indexIterator(index);
        unlinkNode(currentNode, "middle");
        return (T) currentNode.item;
    }

    @Override
    public boolean remove(T object) {
        Node currentNode = head;
        if (size == 1 && equalityCheck(currentNode, object)) {
            unlinkNode(currentNode, "zero_only");
            return true;
        }
        if (size > 1 && equalityCheck(currentNode, object)) {
            unlinkNode(currentNode, "head");
            return true;
        }
        while (currentNode != null) {
            if (currentNode.next != null && equalityCheck(currentNode, object)) {
                unlinkNode(currentNode, "middle");
                return true;
            } else if (currentNode.next == null && equalityCheck(currentNode, object)) {
                unlinkNode(currentNode, "tail");
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
        return (size == 0);
    }

    private Node indexIterator(int index) {
        if (index < size / 2) {
            Node currentNode = head;
            for (int i = 0; i < index; i++) {
                currentNode = currentNode.next;
            }
            return currentNode;
        }
        Node currentNode = tail;
        for (int i = size - 1; i > index; i--) {
            currentNode = currentNode.prev;
        }
        return currentNode;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index is out of bounds");
        }
    }

    private void checkIndexIfAdd(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index is out of bounds");
        }
    }

    private void unlinkNode(Node node, String nodePlace) {
        switch (nodePlace) {
            case "zero_only" :
                tail = null;
                head = null;
                break;
            case "head" :
                node.next.prev = null;
                head = node.next;
                break;
            case "tail" :
                node.prev.next = null;
                tail = node.prev;
                break;
            case "middle" :
            default :
                node.prev.next = node.next;
                node.next.prev = node.prev;
        }
        size--;
    }

    private boolean equalityCheck(Node node, T object) {
        return (node.item == object
                || object != null && node.item.equals(object));
    }
}
