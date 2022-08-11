package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size = 0;
    private Node<T> head;
    private Node<T> tail;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(tail, value, null);
        if (size == 0) {
            head = newNode;
        } else { //addAsTail
            tail.next = newNode;
        }
        tail = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        Node<T> newNode = new Node<>(null, value, null);
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("you use invalid index "
                    + index + ", size = " + size);
        } else if (index == size) {
            add(value);
        } else if (index == 0) {
            head.prev = newNode;
            newNode.next = head;
            head = newNode;
            size++;
        } else {
            Node nodeByIndex = getNode(index);
            Node prevNode = nodeByIndex.prev;
            Node nextNode = nodeByIndex;
            newNode.prev = prevNode;
            newNode.next = nextNode;
            prevNode.next = newNode;
            nextNode.prev = newNode;
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
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("you use invalid index "
                    + index + ", size = " + size);
        }
        Node nodeByIndex = getNode(index);
        return (T) nodeByIndex.value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> newNode = new Node<>(null, value, null);
        Node previousNode;
        if (index == 0) { //setAsHead
            previousNode = head;

            newNode.next = head.next;
            head.next.prev = newNode;

            head = newNode;
        } else if (index == size - 1) { //addAsTail
            previousNode = tail;

            newNode.prev = tail.prev;
            tail.prev.next = newNode;

            tail = newNode;
        } else {
            previousNode = getNode(index);
            Node prevNode = previousNode.prev;
            Node nextNode = previousNode.next;
            newNode.prev = prevNode;
            newNode.next = nextNode;
            prevNode.next = newNode;
            nextNode.prev = newNode;
        }
        return (T) previousNode.value;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node removedNode;
        if (index == 0) {
            removedNode = head;
            head = head.next;
            if (head == null) {
                tail = null;
            } else {
                head.prev = null;
            }
        } else if (index == size - 1) {
            removedNode = tail;
        } else {
            removedNode = getNode(index);
        }

        Node prevNode = removedNode.prev;
        Node nextNode = removedNode.next;
        if (prevNode != null) {
            prevNode.next = nextNode;
        }
        if (nextNode != null) {
            nextNode.prev = prevNode;
        }
        size--;
        return (T) removedNode.value;
    }

    @Override
    public boolean remove(T object) {
        Node currentNode = head;
        while (currentNode != null) {
            if (currentNode.value == object
                    || currentNode.value != null && currentNode.value.equals(object)) {
                if (size == 1) {
                    size--;
                    return true;
                }
                Node prevNode = currentNode.prev;
                Node nextNode = currentNode.next;
                if (prevNode == null) {
                    nextNode.prev = null;
                    head = nextNode;
                } else {
                    prevNode.next = nextNode;
                }
                if (nextNode == null) {
                    prevNode.next = null;
                    tail = prevNode;
                } else {
                    nextNode.prev = prevNode;
                }
                size--;
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

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("you use invalid index "
                    + index + ", size = " + size);
        }
    }

    private Node getNode(int index) {
        Node currentNode;
        if (index <= size / 2) {
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

    static class Node<T> {
        private T value;
        private Node<T> prev;
        private Node next;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }
}
