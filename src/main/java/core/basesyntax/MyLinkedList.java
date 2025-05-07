package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private class Node {
        private T data;
        private Node next;
        private Node prev;

        Node(T data) {
            this.data = data;
        }
    }

    private Node first;
    private Node last;
    private int size = 0;

    @Override
    public void add(T value) {
        Node newNode = new Node(value);
        if (last == null) {
            first = newNode;
            last = newNode;
        } else {
            last.next = newNode;
            newNode.prev = last;
            last = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkIndexForAdd(index);
        Node newNode = new Node(value);

        if (index == 0) {
            if (first == null) {
                first = newNode;
                last = newNode;
            } else {
                newNode.next = first;
                first.prev = newNode;
                first = newNode;
            }
        } else if (index == size) {
            last.next = newNode;
            newNode.prev = last;
            last = newNode;
        } else {
            Node current = getNode(index - 1);
            newNode.next = current.next;
            newNode.prev = current;
            if (current.next != null) {
                current.next.prev = newNode;
            }
            current.next = newNode;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        if (list == null) {
            throw new NullPointerException("The provided list is null");
        }
        for (T item : list) {
            this.add(item);
        }
    }

    @Override
    public T get(int index) {
        Node current = getNode(index);
        return current.data;
    }

    @Override
    public T set(T value, int index) {
        Node current = getNode(index);
        T oldValue = current.data;
        current.data = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        Node current = getNode(index);
        T oldValue;
        if (index == 0) {
            oldValue = first.data;
            first = first.next;
            if (first == null) {
                last = null;
            } else {
                first.prev = null;
            }
        } else {
            oldValue = current.data;
            if (current.prev != null) {
                current.prev.next = current.next;
            }
            if (current.next != null) {
                current.next.prev = current.prev;
            } else {
                last = current.prev;
            }
        }
        size--;
        return oldValue;
    }

    @Override
    public boolean remove(T object) {
        Node current = first;
        while (current != null) {
            if (object == null) {
                if (current.data == null) {
                    if (current.prev != null) {
                        current.prev.next = current.next;
                    } else {
                        first = current.next;
                    }
                    if (current.next != null) {
                        current.next.prev = current.prev;
                    } else {
                        last = current.prev;
                    }
                    size--;
                    return true;
                }
            } else {
                if (object.equals(current.data)) {
                    if (current.prev != null) {
                        current.prev.next = current.next;
                    } else {
                        first = current.next;
                    }
                    if (current.next != null) {
                        current.next.prev = current.prev;
                    } else {
                        last = current.prev;
                    }
                    size--;
                    return true;
                }
            }
            current = current.next;
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
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }

    private void checkIndexForAdd(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }

    private Node getNode(int index) {
        checkIndex(index);
        Node current = first;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current;
    }
}
