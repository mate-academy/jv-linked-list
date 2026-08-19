package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    private static class Node<T> {
        private Node<T> next;
        private Node<T> prev;
        private T value;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.value = value;
            this.prev = prev;
            this.next = next;
        }
    }

    public Node<T> getNodeByIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index is not valid :" + index);
        }
        Node<T> current = head;
        for (int i = 0;i < index;i++) {
            current = current.next;
        }
        return current;
    }

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(tail, value, null);
        if (tail == null) {
            head = newNode;
        }
        if (tail != null) {
            tail.next = newNode;
        }
        tail = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        Node<T> newNode = new Node<>(null, value, null);
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index is not valid :" + index);
        }
        if (head == null && index == 0) {
            head = newNode;
            tail = newNode;
        }
        if (index == 0) {
            newNode.next = head;
            head.prev = newNode;
            head = newNode;
        }
        if (index == size) {
            newNode.prev = tail;
            if (tail != null) {
                tail.next = newNode;
            }
            tail = newNode;
        }
        if (index > 0 && index < size) {
            Node<T> current = getNodeByIndex(index);
            newNode.prev = current.prev;
            newNode.next = current;
            if (current.prev != null) {
                current.prev.next = newNode;
            }
            current.prev = newNode;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        for (T t : list) {
            add(t);
        }
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index is not valid :" + index);
        }
        Node<T> searchNode = getNodeByIndex(index);
        return searchNode.value;
    }

    @Override
    public T set(T value, int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index is not valid :" + index);
        }
        Node<T> searchNode = getNodeByIndex(index);
        T oldValue = searchNode.value;
        searchNode.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index is not valid: " + index);
        }

        Node<T> current = getNodeByIndex(index);
        final T oldValue = current.value;

        if (current.prev != null) {
            current.prev.next = current.next;
        } else {
            head = current.next;
        }

        if (current.next != null) {
            current.next.prev = current.prev;
        } else {
            tail = current.prev;
        }

        current.value = null;
        current.next = null;
        current.prev = null;

        size--;
        return oldValue;
    }

    @Override
    public boolean remove(T value) {
        if (value == null) {
            Node<T> current = head;
            while (current != null) {
                if (current.value == null) {
                    if (current.prev != null) {
                        current.prev.next = current.next;
                    } else {
                        head = current.next;
                    }

                    if (current.next != null) {
                        current.next.prev = current.prev;
                    } else {
                        tail = current.prev;
                    }

                    size--;
                    return true;
                }
                current = current.next;
            }
        } else {
            Node<T> current = head;
            while (current != null) {
                if (value.equals(current.value)) {
                    if (current.prev != null) {
                        current.prev.next = current.next;
                    } else {
                        head = current.next;
                    }

                    if (current.next != null) {
                        current.next.prev = current.prev;
                    } else {
                        tail = current.prev;
                    }

                    size--;
                    return true;
                }
                current = current.next;
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
}
