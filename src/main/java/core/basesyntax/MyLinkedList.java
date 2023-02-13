package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> head;
    private Node<T> tail;

    @Override
    public void add(T value) {
        if (size == 0) {
            addFirstHead(value);
        } else {
            addNewTail(value);
        }
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index do not exist");
        }
        if (size == index) {
            add(value);
        } else if (index == 0) {
            addNewHead(value);
        } else {
            addByIndex(value, index);
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T t : list) {
            addNewTail(t);
        }
    }

    @Override
    public T get(int index) {
        indexExistCheck(index);
        Node<T> result = head;
        int counter = 0;
        while (counter != index) {
            result = result.next;
            counter++;
        }
        return result.value;
    }

    @Override
    public T set(T value, int index) {
        indexExistCheck(index);
        Node<T> currentNode = findNode(index);
        T result;
        result = currentNode.value;
        currentNode.value = value;
        return result;
    }

    @Override
    public T remove(int index) {
        indexExistCheck(index);
        Node<T> result = removeNode(index);
        return result.value;
    }

    @Override
    public boolean remove(T object) {
        int index = 0;
        Node<T> currentNode = head;
        for (int i = 0; i <= size - 1; i++) {
            if (object == currentNode.value || object != null
                    && object.equals(currentNode.value)) {
                break;
            }
            index++;
            currentNode = currentNode.next;
        }
        if (index > size - 1) {
            return false;
        }
        removeNode(index);
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

    private Node<T> findNode(int index) {
        Node<T> currentNode = head;
        if (index <= size / 2) {
            for (int i = 0; i < index; i++) {
                currentNode = currentNode.next;
            }
        } else {
            currentNode = tail;
            for (int i = size; i > index + 1; i--) {
                currentNode = currentNode.prev;
            }
        }
        return currentNode;
    }

    private void indexExistCheck(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index do not exist");
        }
    }

    private Node<T> removeNode(int index) {
        Node<T> currentNode = head;
        if (size == 1) {
            head = null;
            tail = null;
        } else if (index == 0) {
            head.next.prev = null;
            head = head.next;
        } else if (index == size - 1) {
            currentNode = tail;
            tail.prev.next = null;
            tail = tail.prev;
        } else {
            currentNode = findNode(index);
            currentNode.prev.next = currentNode.next;
            currentNode.next.prev = currentNode.prev;
        }
        size--;
        return currentNode;
    }

    private void addFirstHead(T value) {
        Node<T> node = new Node<>(null, value, null);
        head = node;
        tail = node;
        size++;
    }

    private void addNewTail(T value) {
        Node<T> node2 = new Node<>(tail, value, null);
        tail.next = node2;
        tail = node2;
        size++;
    }

    private void addByIndex(T value, int index) {
        Node<T> currentNode = findNode(index);
        Node<T> newNode = new Node<>(currentNode.prev, value, currentNode);
        currentNode.prev.next = newNode;
        currentNode.prev = newNode;
        size++;
    }

    private void addNewHead(T value) {
        Node<T> node = head;
        Node<T> newNode = new Node<>(null, value, node);
        node.prev = newNode;
        head = newNode;
        size++;
    }

    static class Node<T> {
        private Node<T> prev;
        private T value;
        private Node<T> next;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }
}
