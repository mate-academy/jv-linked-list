package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        addLast(value);
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkValidIndexForAdd(index);
        if (index == size) {
            addLast(value);
        } else if (index == 0) {
            addFirst(value);
        } else {
            Node<T> previous = getNodeByIndex(index - 1);
            Node<T> newNode = new Node<>(value, previous, previous.next);
            previous.next.prev = newNode;
            previous.next = newNode;
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
        checkValidIndexForGet(index);
        return getNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkValidIndexForGet(index);
        Node<T> currentNode = getNodeByIndex(index);
        T oldValue = currentNode.value;
        currentNode.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkValidIndexForGet(index);
        Node<T> currentNode = getNodeByIndex(index);
        T oldValue = currentNode.value;
        unlink(currentNode);
        size--;
        return oldValue;
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = getNodeByValue(object);
        if (currentNode != null) {
            unlink(currentNode);
            size--;
            return true;
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

    private void unlink(Node<T> node) {
        Node<T> prev = node.prev;
        Node<T> next = node.next;

        if (prev == null) {
            head = next;
        } else {
            prev.next = next;
            node.prev = null;
        }
        if (next == null) {
            tail = prev;
        } else {
            next.prev = prev;
            node.next = null;
        }

        node.value = null;
    }

    private Node<T> getNodeByIndex(int index) {
        Node<T> currentNode;
        if (index < size >> 1) {
            currentNode = head;
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

    private Node<T> getNodeByValue(T value) {
        for (Node<T> currentNode = head; currentNode != null; currentNode = currentNode.next) {
            if (currentNode.value == value
                    || currentNode != null && currentNode.value.equals(value)) {
                return currentNode;
            }
        }
        return null;
    }

    private void addFirst(T value) {
        Node<T> newNode = new Node<>(value, null, head);
        head.prev = newNode;
        head = newNode;
    }

    private void addLast(T value) {
        Node<T> newNode = new Node<>(value, tail,null);;
        if (head == null) {
            head = newNode;
        } else {
            tail.next = newNode;
        }
        tail = newNode;
    }

    private void checkValidIndexForGet(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(
                    String.format("Can't get element. Index: %d. Size: %d.", index, size));
        }
    }

    private void checkValidIndexForAdd(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException(
                    String.format("Can't add element. Index: %d. Size: %d.", index, size));
        }
    }

    private static class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        public Node(T value, Node<T> prev, Node<T> next) {
            this.value = value;
            this.prev = prev;
            this.next = next;
        }
    }
}
