package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {

    private Node head;
    private Node tail;
    private int elementCount = 0;

    @Override
    public void add(T value) {

        Node newNode = new Node(value);
        if (head == null) {
            head = newNode;
            tail = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
        elementCount++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > elementCount) {
            throw new IndexOutOfBoundsException();
        }
        if (index == elementCount) {
            add(value);

        } else if (index == 0) {
            Node newNode = new Node(value);
            Node oldHead = head;

            newNode.next = oldHead;
            newNode.prev = null;
            oldHead.prev = newNode;
            head = newNode;
            elementCount++;

        } else {
            Node newNode = new Node(value);
            Node prevNode = head;
            for (int i = 0; i < index - 1; i++) {
                prevNode = prevNode.next;

            }
            Node nextNode = prevNode.next;
            prevNode.next = newNode;
            newNode.prev = prevNode;
            newNode.next = nextNode;
            nextNode.prev = newNode;
            elementCount++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T value : list) {
            add(value);
        }
    }

    @Override
    public T get(int index) {
        Node currentNode = head;

        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException();
        }
        for (int i = 0; i < index; i++) {
            currentNode = currentNode.next;
        }
        return currentNode.value;
    }

    @Override
    public T set(T value, int index) {
        Node currentNode = head;

        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException();
        }
        for (int i = 0; i < index; i++) {
            currentNode = currentNode.next;
        }
        T oldValue = currentNode.value;
        currentNode.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {

        if (index < 0 || index >= elementCount) {
            throw new IndexOutOfBoundsException();
        }

        if (index == 0) {
            T oldValue = head.value;
            head = head.next;
            if (head != null) {
                head.prev = null;
            }
            elementCount--;
            return oldValue;

        } else if (index == elementCount - 1) {
            T oldValue = tail.value;
            tail = tail.prev;
            if (tail != null) {
                tail.next = null;
            }
            elementCount--;
            return oldValue;

        } else {
            Node nodeToDelete = head;
            for (int i = 0; i < index; i++) {
                nodeToDelete = nodeToDelete.next;
            }
            T oldValue = nodeToDelete.value;

            Node prevNode = nodeToDelete.prev;
            Node nextNode = nodeToDelete.next;

            prevNode.next = nextNode;
            nextNode.prev = prevNode;

            elementCount--;
            return oldValue;

        }
    }

    @Override
    public boolean remove(T object) {
        Node current = head;

        while (current != null) {
            if ((object == null && current.value == null)
                    || (object != null && object.equals(current.value))) {
                if (current == head) {
                    head = head.next;
                    if (head != null) {
                        head.prev = null;
                    }
                    elementCount--;
                    return true;

                } else if (current == tail) {
                    tail = tail.prev;
                    if (tail != null) {
                        tail.next = null;
                    }
                    elementCount--;
                    return true;

                } else {
                    Node prevNode = current.prev;
                    Node nextNode = current.next;
                    prevNode.next = nextNode;
                    nextNode.prev = prevNode;
                    elementCount--;
                    return true;
                }
            }
            current = current.next;
        }
        return false;
    }

    @Override
    public int size() {
        return elementCount;
    }

    @Override
    public boolean isEmpty() {
        return elementCount == 0;
    }

    public class Node {
        T value;
        Node next;
        Node prev;

        public Node(T value) {
            this.value = value;
        }

    }
}
