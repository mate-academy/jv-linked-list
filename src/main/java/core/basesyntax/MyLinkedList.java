package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private static final String MESSAGE_EXCEPTION = "Invalid index value = ";
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(value);
        if (size == 0) {
            tail = head = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
        } else {
            Node<T> newNode = new Node<>(value);
            insertNode(findNode(index), newNode);
        }
    }

    @Override
    public void addAll(List<T> list) {
        if (list != null) {
            for (T item : list) {
                add(item);
            }
        }
    }

    @Override
    public T get(int index) {
        return findNode(index).element;
    }

    @Override
    public T set(T value, int index) {
        Node<T> findNode = findNode(index);
        T tmp = findNode.element;
        findNode.element = value;
        return tmp;
    }

    @Override
    public T remove(int index) {
        return unlink(findNode(index));
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = head;
        while (currentNode != null) {
            if (isEquals(currentNode.element,object)) {
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

    private static class Node<T> {
        private T element;
        private Node<T> next;
        private Node<T> prev;

        public Node(T element) {
            this.element = element;
        }
    }

    private void insertNode(Node<T> findNode, Node<T> newNode) {
        newNode.next = findNode;
        newNode.prev = findNode.prev;
        if (head == findNode) {
            head = newNode;
        } else {
            findNode.prev.next = newNode;
        }
        findNode.prev = newNode;
        size++;
    }

    private Node<T> findNode(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(MESSAGE_EXCEPTION + index);
        }
        return (index <= size >> 1) ? getNodeStartHead(index) : getNodeStartTail(index);
    }

    private Node<T> getNodeStartHead(int index) {
        int currentIndex = 0;
        Node<T> currentNode = head;
        while (currentIndex++ != index) {
            currentNode = currentNode.next;
        }
        return currentNode;
    }

    private Node<T> getNodeStartTail(int index) {
        int currentIndex = size - 1;
        Node<T> currentNode = tail;
        while (currentIndex-- != index) {
            currentNode = currentNode.prev;
        }
        return currentNode;
    }

    private T unlink(Node<T> node) {
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

    private boolean isEquals(T obj1, T obj2) {
        return obj1 == obj2 || obj1 != null && obj1.equals(obj2);
    }
}
