package core.basesyntax;

import java.util.List;
import java.util.Objects;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        if (size == 0) {
            setHead(value);
        } else {
            setTail(value);
        }
    }

    @Override
    public void add(T value, int index) {
        if (!(index >= 0 && index <= size)) {
            throw new IndexOutOfBoundsException(
                    "Index passed to the method is negative or "
                            + "bigger than size of MyLinkedList. "
                            + "Index: " + index + ", Size: " + size);
        }
        if (index == size) {
            add(value);
            return;
        }
        if (index == 0) {
            setHead(value);
            return;
        }
        Node<T> currentNode = getCurrentNode(index);
        Node<T> newNode = new Node<>(currentNode.prev, value, currentNode);
        currentNode.prev.next = newNode;
        currentNode.prev = newNode;
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
        return getCurrentNode(index).value;
    }

    @Override
    public T set(T value, int index) {
        Node<T> currentNode = getCurrentNode(index);
        T oldValue = currentNode.value;
        currentNode.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        if (index == 0) {
            return removeHead();
        }
        if (index == (size - 1)) {
            return removeTail();
        }
        Node<T> currentNode = getCurrentNode(index);
        currentNode.prev.next = currentNode.next;
        currentNode.next.prev = currentNode.prev;
        size--;
        return currentNode.value;
    }

    @Override
    public boolean remove(T value) {
        int index = 0;
        for (Node<T> x = head; x != null; x = x.next) {
            if (Objects.equals(value, x.value)) {
                remove(index);
                return true;
            }
            index++;
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
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException(
                    "Index passed to the method is negative or "
                            + "bigger than size of MyLinkedList. "
                            + "Index: " + index + ", Size: " + size);
        }
    }

    private void setHead(T value) {
        if (head == tail && size == 0) {
            head = new Node<>(null, value, null);
            tail = head;
            size = 1;
        } else {
            Node<T> oldHead = head;
            Node<T> newNode = new Node<>(null, value, oldHead);
            head = newNode;
            if (oldHead == null) {
                tail = newNode;
            } else {
                oldHead.prev = newNode;
            }
            size++;
        }
    }

    private void setTail(T value) {
        Node<T> last = tail;
        tail = new Node<>(last, value, null);
        if (last == null) {
            head = tail;
        } else {
            last.next = tail;
        }
        size++;
    }

    private T removeHead() {
        T oldValue = head.value;
        if (size > 1) {
            head = head.next;
            head.prev = null;
            size--;
        } else {
            head.next = null;
            head.value = null;
            size = 0;
        }
        return oldValue;
    }

    private T removeTail() {
        T oldValue;
        oldValue = tail.value;
        tail = tail.prev;
        tail.next = null;
        size--;
        return oldValue;
    }

    private static class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }

    private Node<T> getCurrentNode(int index) {
        checkIndex(index);
        Node<T> currentNode;
        if (index < size / 2) {
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
}
