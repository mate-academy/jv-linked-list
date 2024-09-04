package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {

    private int size = 0;
    private Node<T> head;
    private Node<T> tail;

    @Override
    public void add(T value) {
        Node<T> node = new Node<>(null, value, null);
        if (size == 0) {
            head = node;
            tail = head;
        } else {
            node.prev = tail;
            if (size == 1) {
                tail.prev = head;
                head.next = node;
            } else {
                tail.next = node;
            }
            tail = node;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        Node node = new Node(null, value, head);
        if (index != size) {
            validIndexCheck(index);
        }
        if (index == 0 && size != 0) {
            head.prev = node;
            node.next = head;
            head = node;
            size++;
        } else {
            if (index == size || size == 0) {
                add(value);
            } else {
                node.prev = head;
                node.next = head.next;
                for (int i = 1; i < index; i++) {
                    node.prev = node.next;
                    node.next = node.next.next;
                }
                node.next.prev = node;
                node.prev.next = node;
                size++;
            }
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
        validIndexCheck(index);
        Node<T> node = head;
        for (int i = 0; i < index; i++) {
            node = node.next;
        }
        return node.item;
    }

    @Override
    public T set(T value, int index) {
        validIndexCheck(index);
        T oldValue;
        Node<T> node = head;
        if (index == 0) {
            oldValue = head.item;
            head.item = value;
            return oldValue;
        }
        if (index == size - 1) {
            oldValue = tail.item;
            tail.item = value;
        } else {
            for (int i = 0; i < index; i++) {
                node = node.next;
            }
            oldValue = node.item;
            node.item = value;
        }
        return oldValue;
    }

    @Override
    public T remove(int index) {
        validIndexCheck(index);
        Node<T> node = head;
        T removedValue;
        if (size == 1) {
            removedValue = head.item;
            head.item = null;
            tail.item = null;
        } else {
            if (index == 0) {
                removedValue = head.item;
                head = head.next;
                head.prev = null;
                size--;
                return removedValue;
            }
            if (index == size - 1) {
                removedValue = tail.item;
                tail = tail.prev;
                tail.next = null;
            } else {
                for (int i = 0; i < index; i++) {
                    node = node.next;

                }
                removedValue = node.item;
                node.next.prev = node.prev;
                node.prev.next = node.next;
            }
        }
        size--;
        return removedValue;
    }

    @Override
    public boolean remove(T object) {
        Node<T> node = head;
        int index = 0;
        found: {
                for (; index < size; index++) {
                    if (node.item == null) {
                        if (object == null) {
                            break found;
                        }
                    } else {
                        if (node.item.equals(object)) {
                            break found;
                        }
                    }
                    if (node.next != null) {
                        node = node.next;
                    }
                }
            return false;
        }
        remove(index);
        return true;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private void validIndexCheck(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        }
    }

    private static class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        Node(Node<T> prev, T element, Node<T> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }
}


