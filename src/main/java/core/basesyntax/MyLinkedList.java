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
            head = newNode;
        } else {
            newNode.prev = tail;
            tail.next = newNode;
        }
        tail = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        Node<T> newNode = new Node<>(null, value, null);
        if (index == 0) {
            if (isEmpty()) {
                head = newNode;
                tail = newNode;
            } else {
                newNode.next = head;
                head = newNode;
                newNode.next.prev = newNode;
            }
        } else if (index == size) {
            newNode.prev = tail;
            tail = newNode;
            newNode.prev.next = newNode;
        } else {
            checkException(index);
            Node finderNode = finderNode(index);
            newNode.next = finderNode;
            newNode.prev = finderNode.prev;
            finderNode.prev = newNode;
            newNode.next.prev = newNode;
            newNode.prev.next = newNode;
        }
        size++;

    }

    @Override
    public void addAll(List<T> list) {
        for (T value : list) {
            add(value);
        }
    }

    @Override
    public T get(int index) {
        checkException(index);
        Node finderNode = finderNode(index);
        return (T) finderNode.value;
    }

    @Override
    public T set(T value, int index) {
        Node<T> newNode = new Node<>(null, value, null);
        T returnValue = get(index);

        if (index == 0) {
            head.next.prev = newNode;
            newNode.next = head.next;
            head = newNode;
        } else {
            Node finderNode = finderNode(index);
            finderNode.next.prev = newNode;
            finderNode.prev.next = newNode;
        }

        return returnValue;
    }

    @Override
    public T remove(int index) {
        checkException(index);
        T returnValue = get(index);
        Node deleteNode = finderNode(index);
        deleterNode(deleteNode, index);
        return returnValue;
    }

    @Override
    public boolean remove(T object) {
        Boolean elementFound = false;
        Node<T> deleteNode = head;
        int index = findingIndexByValue(deleteNode, object);
        for (int i = 0; i < size; i++) {
            if (deleteNode.value == null && object != null) {
                deleteNode = deleteNode.next;
            } else if ((deleteNode.value == null && object == null)
                    || (deleteNode.value.equals(object))) {
                deleterNode(deleteNode, index);
                elementFound = true;
                break;
            } else {
                deleteNode = deleteNode.next;
            }
        }
        return elementFound;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private Node finderNode(int index) {
        Node finderNode = head;
        for (int i = 0; i < index; i++) {
            finderNode = finderNode.next;
        }
        return finderNode;
    }

    private void checkException(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Exception in thread \"main\" "
                    + "java.lang.IndexOutOfBoundsException");
        }
    }

    private void deleterNode(Node node, int index) {
        if (index == 0) {
            if (size != 1) {
                head = node.next;
                node.next.prev = node.prev;
            }
        } else if (index == size - 1) {
            tail = node.prev;
            node.prev.next = node.next;
        } else {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }
        size--;

    }

    private int findingIndexByValue(Node deleteNode, T value) {
        int index = 0;
        for (int i = 0; i < size; i++) {
            if (deleteNode.value.equals(value)) {
                break;
            }
            index++;
        }
        return index;
    }

    private class Node<T> {
        private T value;
        private Node next;
        private Node prev;

        Node(Node prev, T value, Node next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }
}
