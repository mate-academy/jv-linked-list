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
        } else {
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
        }
        if (index == size) {
            add(value);
            return;
        }
        if (index == 0) {
            head.prev = newNode;
            newNode.next = head;
            head = newNode;
            size++;
            return;
        }
        Node nodeByIndex = getNode(index);
        Node prevNode = nodeByIndex.prev;
        Node nextNode = nodeByIndex;
        newNode.prev = prevNode;
        newNode.next = nextNode;
        prevNode.next = newNode;
        nextNode.prev = newNode;
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T element : list) {
            add(element);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return (T) getNode(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node nodeByIndex = getNode(index);
        T previousValue = (T) nodeByIndex.value;
        nodeByIndex.value = value;
        return previousValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node removedNode = getNode(index);
        return (T) unlink(removedNode);
    }

    @Override
    public boolean remove(T object) {
        Node currentNode = head;
        while (currentNode != null) {
            if (currentNode.value == object
                    || currentNode.value != null && currentNode.value.equals(object)) {
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

    private T unlink(Node<T> removedElement) {
        if (size == 1) {
            head = null;
            tail = null;
        } else if (removedElement.equals(head)) {
            head.next.prev = null;
            head = head.next;
        } else if (removedElement.equals(tail)) {
            tail.prev.next = null;
            tail = tail.prev;
        } else {
            Node prevNode = removedElement.prev;
            Node nextNode = removedElement.next;
            prevNode.next = nextNode;
            nextNode.prev = prevNode;
        }
        size--;
        return removedElement.value;
    }

    static class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }
}
