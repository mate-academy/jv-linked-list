package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private static final String BOUNDS_EXCEPTION = "This index is not correct: ";
    private Node head;
    private Node tail;
    private int size = 0;

    public MyLinkedList() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    private class Node {
        private Node prev;
        private T value;
        private Node next;

        public Node(Node prev, T value, Node next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }

    }

    @Override
    public void add(T value) {
        Node addNode = new Node(tail, value, null);
        tail = (tail == null) ? (head = addNode) : (tail.next = addNode);
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException(BOUNDS_EXCEPTION + index);
        }
        if (index == size) {
            add(value);
            return;
        }
        Node currentNode = getNodeByIndex(index);
        Node addNode = new Node(currentNode.prev, value, currentNode);
        if (currentNode.prev == null) {
            head = addNode;
        } else {
            currentNode.prev.next = addNode;
        }
        currentNode.prev = addNode;
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T value : list) {
            add(value);
        }
    }

    @Override
    public T get(int index) {
        checkException(index);
        Node currentNode = getNodeByIndex(index);
        return currentNode.value;
    }

    @Override
    public T set(T value, int index) {
        checkException(index);
        Node current = getNodeByIndex(index);
        T oldValue = current.value;
        current.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkException(index);
        Node removeNode = getNodeByIndex(index);
        unlink(removeNode);
        return removeNode.value;
    }

    @Override
    public boolean remove(T object) {
        if (object == null) {
            return removeNull();
        }
        Node currentNode = head;
        while (currentNode != null) {
            if (object.equals(currentNode.value)) {
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

    private void unlink(Node node) {
        Node prevNode = node.prev;
        Node nextNode = node.next;
        head = (prevNode == null) ? nextNode : head;
        tail = (nextNode == null) ? prevNode : tail;
        if (prevNode != null) {
            prevNode.next = nextNode;
            node.prev = null;
        }
        if (nextNode != null) {
            nextNode.prev = prevNode;
            node.next = null;
        }
        size--;
    }

    private Node getNodeByIndex(int index) {
        Node currentNode;
        if (index < (size / 2)) {
            currentNode = head;
            for (int i = 0; i < index; i++) {
                currentNode = currentNode.next;
            }
        } else {
            currentNode = tail;
            for (int i = size - 1; i > index; i--) {
                currentNode = currentNode.prev;
            }
        }
        return currentNode;
    }

    private boolean removeNull() {
        Node currentNode = head;
        while (currentNode != null) {
            if (currentNode.value == null) {
                unlink(currentNode);
                return true;
            }
            currentNode = currentNode.next;
        }
        return false;
    }

    private void checkException(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(BOUNDS_EXCEPTION + index);
        }
    }
}
