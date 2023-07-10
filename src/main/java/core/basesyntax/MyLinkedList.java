package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> head;
    private Node<T> tail;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(value, null, null);
        if (head == null) {
            head = newNode;
        } else {
            tail.next = newNode;
            newNode.previous = tail;
        }
        tail = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        }
        Node<T> newNode = new Node<>(value, null, null);
        if (size == index) {
            add(value);
            return;
        }

        if (index == 0) {
            newNode.next = head;
            head.previous = newNode;
            head = newNode;
            size++;
            return;
        }
        Node<T> nodeWithIndex = getNodeByIndex(index);
        indexValidator(index);
        newNode.next = nodeWithIndex;
        newNode.previous = nodeWithIndex.previous;
        if (nodeWithIndex.next != null) {
            nodeWithIndex.previous.next = newNode;
        }
        nodeWithIndex.previous = newNode;
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
        return getNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        indexValidator(index);
        Node<T> nodeWithIndex = getNodeByIndex(index);
        T element = nodeWithIndex.value;
        nodeWithIndex.value = value;
        return element;
    }

    @Override
    public T remove(int index) {
        indexValidator(index);
        Node<T> nodeWithIndex = getNodeByIndex(index);
        deleteNode(nodeWithIndex);
        return nodeWithIndex.value;
    }

    @Override
    public boolean remove(T object) {
        for (Node<T> node = head; node != null; node = node.next) {
            if (object == node.value || object != null && object.equals(node.value)) {
                deleteNode(node);
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

    private void indexValidator(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Incorrect index " + index
            + ". Index can't be less than 0 and bigger(equal) than size");
        }
    }

    private void deleteNode(Node<T> node) {
        if (node.previous == null) {
            head = node.next;
        } else {
            node.previous.next = node.next;
        }
        if (node.next == null) {
            tail = node.previous;
        } else {
            node.next.previous = node.previous;
        }
        size--;
    }

    private Node<T> getNodeByIndex(int index) {
        indexValidator(index);
        Node<T> current;
        if (index <= size / 2) {
            current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
            return current;
        } else if (index > size / 2) {
            current = tail;
            for (int i = size - 1; i > index; i--) {
                current = current.previous;
            }
            return current;
        } else {
            throw new IndexOutOfBoundsException();
        }
    }

    static class Node<T> {
        private T value;
        private Node<T> next;
        private Node<T> previous;

        public Node(T value, Node<T> next, Node<T> previous) {
            this.value = value;
            this.next = next;
            this.previous = previous;
        }
    }
}
