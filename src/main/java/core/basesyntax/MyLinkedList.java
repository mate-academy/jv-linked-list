package core.basesyntax;

import java.util.List;
import java.util.Objects;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node head;
    private Node tail;
    private int size;

    private class Node {
        private T element;
        private Node next;
        private Node prev;

        public Node(T element) {
            this.element = element;
        }
    }

    @Override
    public void add(T value) {
        add(value, size);
    }

    @Override
    public void add(T value, int index) {
        Node newNode = new Node(value);
        if (index == size) {
            if (size == 0) {
                tail = head = newNode;
            } else {
                tail.next = newNode;
                newNode.prev = tail;
                tail = newNode;
            }
            size++;
            return;
        }
        Node findNode = findNode(index);
        if (findNode != null) {
            newNode.next = findNode;
            newNode.prev = findNode.prev;
            if (findNode.prev != null) {
                findNode.prev.next = newNode;
            } else {
                head = newNode;
            }
            findNode.prev = newNode;
            size++;
            return;
        }
        throw new IndexOutOfBoundsException("Can't add by index " + index);
    }

    @Override
    public void addAll(List<T> list) {
        if (list == null) {
            return;
        }
        for (T item : list) {
            add(item);
        }
    }

    @Override
    public T get(int index) {
        Node findNode = findNode(index);
        if (findNode == null) {
            throw new IndexOutOfBoundsException("Can't get by index " + index);
        }
        return findNode.element;
    }

    @Override
    public T set(T value, int index) {
        Node findNode = findNode(index);
        if (findNode == null) {
            throw new IndexOutOfBoundsException("Can't set by index " + index);
        }
        T tmp = findNode.element;
        findNode.element = value;
        return tmp;
    }

    @Override
    public T remove(int index) {
        Node findNode = findNode(index);
        if (findNode == null) {
            throw new IndexOutOfBoundsException("Can't remove by index " + index);
        }
        return unlink(findNode);
    }

    @Override
    public boolean remove(T object) {
        Node currentNode = head;
        while (currentNode != null) {
            if (Objects.equals(object, currentNode.element)) {
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

    private Node findNode(int index) {
        if (size == 0 || index < 0 || index >= size) {
            return null;
        }
        return (index <= size >> 1) ? getNodeStartHead(index) : getNodeStartTail(index);
    }

    private Node getNodeStartHead(int index) {
        int currentIndex = 0;
        Node currentNode = head;
        while (currentIndex++ != index) {
            currentNode = currentNode.next;
        }
        return currentNode;
    }

    private Node getNodeStartTail(int index) {
        int currentIndex = size - 1;
        Node currentNode = tail;
        while (currentIndex-- != index) {
            currentNode = currentNode.prev;
        }
        return currentNode;
    }

    private T unlink(Node node) {
        if (node.prev != null) {
            node.prev.next = node.next;
        }
        if (node.next != null) {
            node.next.prev = node.prev;
        }
        if (node == head) {
            head = node.next;
        }
        if (node == tail) {
            tail = node.prev;
        }
        size--;
        return node.element;
    }
}
