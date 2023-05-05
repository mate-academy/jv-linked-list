package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> t = tail;
        Node<T> newNode = new Node<>(t, value, null);
        tail = newNode;
        if (t == null) {
            head = newNode;
        } else {
            t.next = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkPositionIndex(index);
        if (index == size) {
            add(value);
        } else {
            Node<T> nextNode = isIndexInTheList(index);
            Node<T> prevNode = nextNode.prev;
            Node<T> newNode = new Node<>(prevNode, value, nextNode);
            nextNode.prev = newNode;
            if (prevNode == null) {
                head = newNode;
            } else {
                prevNode.next = newNode;
            }
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        final int sizeOfInputList = list.toArray().length;
        checkPositionIndex(size);
        Node<T> prev;
        Node<T> next;
        if (size == size) {
            next = null;
            prev = tail;
        } else {
            next = isIndexInTheList(size);
            prev = next.prev;
        }
        for (Object elementOfInputList : list.toArray()) {
            T element = (T) elementOfInputList;
            Node<T> newNode = new Node<>(prev, element, null);
            if (prev == null) {
                head = newNode;
            } else {
                prev.next = newNode;
            }
            prev = newNode;
        }
        if (next == null) {
            tail = prev;
        } else {
            prev.next = next;
            next.prev = prev;
        }
        size += sizeOfInputList;
    }

    @Override
    public T get(int index) {
        checkElementIndex(index);
        return isIndexInTheList(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkElementIndex(index);
        Node<T> node = isIndexInTheList(index);
        T oldValue = node.value;
        node.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkElementIndex(index);
        return deletingTheNode(isIndexInTheList(index));
    }

    @Override
    public boolean remove(T object) {
        for (Node<T> node = head; node != null; node = node.next) {
            if (object == null) {
                if (node.value == null) {
                    deletingTheNode(node);
                    return true;
                }
            } else {
                if (object.equals(node.value)) {
                    deletingTheNode(node);
                    return true;
                }
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

    private Node<T> isIndexInTheList(int index) {
        Node<T> node = head;
        for (int i = 0; i < index; i++) {
            node = node.next;
        }
        return node;
    }

    private void checkPositionIndex(int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("Incorrect index: " + index);
        }
    }

    private void checkElementIndex(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Incorrect index: " + index);
        }
    }

    private T deletingTheNode(Node<T> node) {
        final T nodeDeleted = node.value;
        Node<T> next = node.next;
        Node<T> prev = node.prev;
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
        size--;
        return nodeDeleted;
    }

    private static class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.value = value;
            this.prev = prev;
            this.next = next;
        }
    }
}
