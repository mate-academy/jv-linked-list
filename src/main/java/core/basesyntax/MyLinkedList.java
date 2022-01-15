package core.basesyntax;

import java.util.List;
import java.util.Objects;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> head;
    private Node<T> currentNode;
    private Node<T> tail;

    private static class Node<T> {
        private T value;
        private Node<T> next;
        private Node<T> prev;

        private Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }

    @Override
    public void add(T value) {
        add(value, size);
    }

    @Override
    public void add(T value, int index) {
        isValidIndex(index, true);

        if (size == 0) {
            tail = head = new Node<>(null, value, null);

            size += 1;

            return;
        }

        if (index == 0) {
            Node<T> oldHead = head;
            head = new Node<>(null, value, oldHead);

            if (oldHead.next != null) {
                oldHead.next.prev = oldHead;
            }

            size += 1;

            return;
        }

        if (index == size) {
            Node<T> oldTail = tail;
            tail = new Node<>(oldTail, value, null);
            oldTail.next = tail;

            if (oldTail.prev != null) {
                oldTail.prev.next = oldTail;
            } else {
                head = oldTail;
            }

            size += 1;

            return;
        }

        currentNode = head;

        for (int i = 0; i < index; i++) {
            currentNode = currentNode.next;
        }

        Node<T> node = new Node<>(currentNode.prev, value, currentNode);
        currentNode.prev.next = node;
        currentNode.prev = node;

        size += 1;
    }

    @Override
    public void addAll(List<T> list) {
        for (T t : list) {
            add(t);
        }
    }

    @Override
    public T get(int index) {
        isValidIndex(index, false);

        if (size == 0) {
            return null;
        }

        currentNode = head;

        for (int i = 0; i < index; i++) {
            currentNode = currentNode.next;
        }

        return currentNode.value;
    }

    @Override
    public T set(T value, int index) {
        isValidIndex(index, false);
        currentNode = head;

        for (int i = 0; i < index; i++) {
            currentNode = currentNode.next;
        }

        T oldValue = currentNode.value;
        currentNode.value = value;

        if (currentNode.prev != null) {
            currentNode.prev.next = currentNode;
        } else {
            head = currentNode;
        }

        if (currentNode.next != null) {
            currentNode.next.prev = currentNode;
        } else {
            tail = currentNode;
        }

        return oldValue;
    }

    @Override
    public T remove(int index) {
        isValidIndex(index, false);
        if (index == 0) {
            T oldValue = head.value;

            if (head.next != null) {
                head = head.next;
                head.prev = null;
            } else {
                head = tail = null;
            }

            size -= 1;

            return oldValue;
        }

        if (index == size - 1) {
            T oldValue = tail.value;

            tail = tail.prev;
            tail.next = null;

            size -= 1;

            return oldValue;
        }

        currentNode = head;

        for (int i = 0; i < index; i++) {
            currentNode = currentNode.next;
        }

        if (currentNode.prev != null && currentNode.next != null) {
            currentNode.prev.next = currentNode.next;
            currentNode.next.prev = currentNode.prev;
        }

        size -= 1;

        return currentNode.value;
    }

    @Override
    public boolean remove(T object) {
        if (isEmpty()) {
            return false;
        }

        if (size == 1) {
            if (Objects.equals(head.value, object)) {
                head = tail = null;
                size -= 1;

                return true;
            }

            return false;
        }

        currentNode = head;

        for (int i = 0; i < size(); i++) {
            if (Objects.equals(currentNode.value, object)) {
                if (currentNode.prev != null) {
                    if (currentNode.next != null) {
                        currentNode.prev.next = currentNode.next;
                        currentNode.next.prev = currentNode.prev;
                    } else {
                        tail = currentNode.prev;
                    }
                } else {
                    if (currentNode.next != null) {
                        head = currentNode.next;
                    } else {
                        head = tail = null;
                    }
                }

                size -= 1;

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

    private void isValidIndex(int index, boolean add) {
        if (add) {
            if (index > size || index < 0) {
                throw new IndexOutOfBoundsException("invalid index");
            }
        } else {
            if (index >= size || index < 0) {
                throw new IndexOutOfBoundsException("invalid index");
            }
        }
    }
}
