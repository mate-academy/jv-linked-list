package core.basesyntax;

import java.util.List;
import java.util.Objects;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node head;
    private Node tail;
    private int size;

    private void validateIndexExclusive(int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("Wrong index provided");
        }
    }

    private void validateIndex(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Wrong index provided");
        }
    }

    private Node getNode(int index) {
        validateIndex(index);
        if (index == 0) {
            return head;
        } else if (index == size - 1) {
            return tail;
        }
        Node currentNode;
        int steps = stepsToTake(index);
        currentNode = steps > 0 ? head : tail;

        // Should i reverse if and for order, it will add to readability, but it will execute a bit longer
        if (steps > 0) {
            for (int i = 0; i < steps; i++) {
                currentNode = currentNode.next;
            }
        } else {
            // Should change steps sign to plus
            for (int i = 0; i < -steps; i++) {
                currentNode = currentNode.prev;
            }
        }
        return currentNode;
    }

    private boolean equals(T a, T b) {
        return Objects.equals(a, b);
    }

    private int getIndexOfNode(T value) {
        for (int i = 0; i < size; i++) {
            if (equals(value, getNode(i).data)) {
                return i;
            }
        }
        return -1;
    }

    private int stepsToTake(int index) {
        if (index <= size / 2) {
            return index;
        } else {
            return -(size - 1 - index);
        }
    }

    private void insertBefore(Node nodeToInsertAfter, Node newNode) {
        newNode.prev = nodeToInsertAfter.prev;
        newNode.next = nodeToInsertAfter;
        nodeToInsertAfter.prev.next = newNode;
        nodeToInsertAfter.prev = newNode;
    }

    public void printLinkedList() {
        Node currentNode = head;  // Assuming 'head' is the reference to the first node in your list

        while (currentNode != null) {
            System.out.println(currentNode.data);
            currentNode = currentNode.next;
        }
    }

    public void printReversedLinkedList() {
        Node currentNode = tail;  // Assuming 'tail' is the reference to the last node in your list

        while (currentNode != null) {
            System.out.println(currentNode.data);
            currentNode = currentNode.prev;
        }
    }

    private Node unlink(Node node) {
        size--;
        if (node == tail) {
            tail = node.prev;
            return node;
        }
        if (node == head) {
            head = node.next;
            return node;
        }
        node.prev.next = node.next;
        node.next.prev = node.prev;
        return node;
    }

    @Override
    public void add(T value) {
        add(value, size);
    }

    @Override
    public void add(T value, int index) {
        validateIndexExclusive(index);
        Node newNode = new Node(value);
        if (isEmpty()) {
            head = newNode;
            tail = newNode;
        } else if (index == 0) {
            newNode.next = head;
            head.prev = newNode;
            head = newNode;
        } else if (index == size) {
            newNode.prev = tail;
            tail.next = newNode;
            tail = newNode;
        } else {
            Node currentNode = getNode(index);
            insertBefore(currentNode, newNode);
        }
        size++;
        System.out.println("------------");
        printLinkedList();
        System.out.println("------------");
        printReversedLinkedList();
    }

    @Override
    public void addAll(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            add(list.get(i));
        }
    }

    @Override
    public T get(int index) {
        return getNode(index).data;
    }

    @Override
    public T set(T value, int index) {
        Node nodeToUpdate = getNode(index);
        T oldValue = nodeToUpdate.data;
        nodeToUpdate.data = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        Node nodeToRemove = getNode(index);
        return unlink(nodeToRemove).data;
    }

    @Override
    public boolean remove(T object) {
        int index = getIndexOfNode(object);
        if (index == -1) {
//            throw new NoSuchElementException("Node is not found");
            return false;
        }
        remove(index);
        return true;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    class Node {
        private T data;
        private Node next;
        private Node prev;

        private Node(T data) {
            this.data = data;
        }
    }
}
