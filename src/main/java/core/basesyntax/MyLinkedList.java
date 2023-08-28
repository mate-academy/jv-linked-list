package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(null, value, null);
        if (isEmpty()) {
            addIfFirstNode(value);
        } else {
            newNode.prev = tail;
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkIndex(index, size + 1);
        Node<T> newNode = new Node<>(null, value, null);
        if (index == size) {
            add(value);
            return;
        } else if (index == 0) {
            newNode.next = head;
            head = newNode;
        } else {
            Node<T> newPrevious = getNodeByIndex(index);
            newNode.next = newPrevious;
            newNode.prev = newPrevious.prev;
            newPrevious.prev.next = newNode;
            newPrevious.prev = newNode;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T node : list) {
            add(node);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index, size);
        return getNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index, size);
        Node<T> changeNode = getNodeByIndex(index);
        T removedValue = changeNode.value;
        changeNode.value = value;
        return removedValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index, size);
        Node<T> removedNode = getNodeByIndex(index);
        T removedValue = removedNode.value;
        removeLink(removedNode);
        return removedValue;
    }

    @Override
    public boolean remove(T object) {
        for (Node<T> tempNode = head; tempNode != null; tempNode = tempNode.next) {
            if (object == tempNode.value || object != null && object.equals(tempNode.value)) {
                removeLink(tempNode);
                return true;
            }
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

    private void addIfFirstNode(T value) {
        head = tail = new Node<>(null, value, null);
    }

    private Node<T> getNodeByIndex(int index) {
        Node<T> current = index <= size / 2 ? head : tail;
        if (current == head) {
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
        } else {
            for (int i = size - 1; i > index; i--) {
                current = current.prev;
            }
        }
        return current;
    }

    private void checkIndex(int index, int size) {
        if (index < 0 || index > size - 1) {
            throw new IndexOutOfBoundsException("Index " + index + " isn't in valid range");
        }
    }

    private void removeLink(Node<T> node) {
        if (node == head) {
            head = node.next;
        } else {
            node.prev.next = node.next;
        }
        if (node == tail) {
            tail = node.prev;
        } else {
            node.next.prev = node.prev;
        }
        size--;
    }

    private static class Node<T> {
        private T value;
        private Node<T> next;
        private Node<T> prev;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.value = value;
            this.prev = prev;
            this.next = next;
        }
    }
}
