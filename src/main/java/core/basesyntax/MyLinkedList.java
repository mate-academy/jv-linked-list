package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    private static class Node<T> {
        private Node<T> prev;
        private T value;
        private Node<T> next;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(null, value, null);
        if (head == null) {
            head = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
        }
        tail = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        addIndexValidator(index);

        if (index == size) {
            add(value);
        } else if (index == 0) {
            addToTheBegin(value);
        } else {
            Node<T> newNode = new Node<>(null, value, null);
            Node<T> currentNode = getNodeByIndex(index);
            newNode.prev = currentNode.prev;
            newNode.next = currentNode;
            currentNode.prev.next = newNode;
            currentNode.prev = newNode;
            size++;
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
        indexValidator(index);
        Node<T> currentNode = getNodeByIndex(index);
        return currentNode.value;
    }

    @Override
    public T set(T value, int index) {
        indexValidator(index);
        Node<T> currentNode = getNodeByIndex(index);
        T oldValue = currentNode.value;
        currentNode.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        indexValidator(index);
        Node<T> currentNode = getNodeByIndex(index);
        T removedValue = currentNode.value;
        removeNode(currentNode);
        return removedValue;
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = head;

        while (currentNode != null) {
            if (object == null) {
                if (currentNode.value == null) {
                    removeNode(currentNode);
                    return true;
                }
            } else if (object.equals(currentNode.value)) {
                removeNode(currentNode);
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

    private Node<T> getNodeByIndex(int index) {
        int currentIndex;
        Node<T> currentNode;

        if (index == 0) {
            return head;
        }
        if (index == size) {
            return tail;
        }
        if (index <= size / 2) {
            currentIndex = 0;
            currentNode = head;
            while (currentIndex != index) {
                currentNode = currentNode.next;
                currentIndex++;
            }
        } else {
            currentIndex = size - 1;
            currentNode = tail;
            while (currentIndex != index) {
                currentNode = currentNode.prev;
                currentIndex--;
            }
        }
        return currentNode;
    }

    private void removeNode(Node<T> currentNode) {
        if (currentNode.prev != null) {
            currentNode.prev.next = currentNode.next;
        } else {
            head = currentNode.next;
        }

        if (currentNode.next != null) {
            currentNode.next.prev = currentNode.prev;
        } else {
            tail = currentNode.prev;
        }

        currentNode.prev = null;
        currentNode.next = null;
        size--;
    }

    private void addToTheBegin(T value) {
        Node<T> newNode = new Node<>(null, value, null);
        if (head != null) {
            newNode.next = head;
            head.prev = newNode;
        }
        head = newNode;
        size++;
    }

    private void indexValidator(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index is not valid");
        }
    }

    private void addIndexValidator(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index is not valid");
        }
    }
}
