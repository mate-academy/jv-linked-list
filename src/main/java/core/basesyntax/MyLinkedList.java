package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> head;
    private Node<T> tail;

    @Override
    public void add(T value) {
        if (head == null) {
            head = new Node<>(value, null, null);
            tail = head;
        } else {
            Node<T> newNode = new Node<>(value, tail, null);
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
        } else if (index == 0) {
            Node<T> newNode = new Node<>(value, null, head);
            if (head != null) {
                head.prev = newNode;
            }
            head = newNode;
            size++;
        } else {
            Node<T> previousNode = getNodeByIndex(index - 1);
            Node<T> newNode = new Node<>(value, previousNode, previousNode.next);
            previousNode.next = newNode;
            newNode.next.prev = newNode;
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            add(list.get(i));
        }
    }

    @Override
    public T get(int index) {
        return getNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        T oldValue = getNodeByIndex(index).value;
        getNodeByIndex(index).value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        Node<T> removedNode = getNodeByIndex(index);
        unlink(removedNode);
        return removedNode.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> removedNode = getNodeByValue(object);
        if (removedNode == null) {
            return false;
        }
        unlink(removedNode);
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

    private Node<T> getNodeByIndex(int index) {
        checkIndex(index);
        Node<T> tmp = head;
        for (int indexCount = 0; indexCount < size; indexCount++) {

            if (indexCount == index) {
                return tmp;
            }
            tmp = tmp.next;
        }
        return null;
    }

    private Node<T> getNodeByValue(T value) {
        Node<T> tmp = head;
        while (tmp != null) {
            if (value == null) {
                if (value == tmp.value) {
                    return tmp;
                }
            } else {
                if (value.equals(tmp.value)) {
                    return tmp;
                }
            }
            tmp = tmp.next;
        }
        return null;
    }

    public void checkIndex(int index) {
        if (isEmpty() || index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Index [" + index + "] out of bounds!");
        }
    }

    private void unlink(Node<T> removedNode) {
        if (removedNode == head) {
            if (removedNode == tail) {
                head = null;
                tail = null;
            } else {
                removedNode.next.prev = null;
                head = removedNode.next;
            }
        } else if (removedNode == tail) {
            removedNode.prev.next = null;
            tail = removedNode.prev;
        } else {
            removedNode.prev.next = removedNode.next;
            removedNode.next.prev = removedNode.prev;
        }
        size--;
    }

    private class Node<T> {
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
