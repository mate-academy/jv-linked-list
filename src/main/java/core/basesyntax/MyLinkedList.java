package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> first;
    private Node<T> last;

    MyLinkedList() {
    }

    @Override
    public void add(T value) {
        if (size == 0) {
            first = new Node<>(null, value, null);
            last = first;
            size++;
        } else {
            Node node = new Node<>(last, value, null);
            last.next = node;
            last = node;
            size++;
        }
    }

    @Override
    public void add(T value, int index) {
        validateAddIndex(index);
        Node node = first;
        Node newNode = new Node<>(value);
        if (node == null) {
            first = newNode;
            last = newNode;
            size++;
            return;
        }
        for (int i = 0; i <= size; i++) {
            if (index == i) {
                if (node == null) {
                    last.next = newNode;
                    newNode.prev = last;
                    last = newNode;
                } else if (i == 0) {
                    node.prev = newNode;
                    newNode.next = node;
                    first = newNode;
                } else {
                    newNode.prev = node.prev;
                    node.prev.next = newNode;
                    node.prev = newNode;
                    newNode.next = node;
                }
                size++;
                return;
            }
            node = node.next;
        }
    }

    @Override
    public void addAll(List<T> list) {
        Node node = last;
        for (int i = 0; i < list.size(); i++) {
            add(list.get(i));
        }
    }

    @Override
    public T get(int index) {
        validateIndex(index);
        Node node = first;
        for (int i = 0; i < size; i++) {
            if (index == i) {
                return (T) node.item;
            }
            node = node.next;
        }
        return null;
    }

    @Override
    public T set(T value, int index) {
        validateIndex(index);
        final T replacedElement;
        Node node = first;
        for (int i = 0; i < size; i++) {
            if (index == i) {
                replacedElement = (T) node.item;
                node.item = value;
                return replacedElement;
            }
            node = node.next;
        }
        return null;
    }

    @Override
    public T remove(int index) {
        validateIndex(index);
        final T removedElement;
        Node node = first;
        for (int i = 0; i < size; i++) {
            if (index == i) {
                if (i == 0 && size == 1) {
                    removedElement = (T) node.item;
                    first = null;
                } else if (i == 0) {
                    removedElement = (T) node.item;
                    node.next.prev = node.prev;
                    first = node.next;
                } else if (i == size - 1) {
                    removedElement = (T) node.item;
                    node.prev.next = node.next;
                    last = node.prev;
                } else {
                    removedElement = (T) node.item;
                    node.prev.next = node.next;
                    node.next.prev = node.prev;
                }
                size--;
                return removedElement;
            }
            node = node.next;
        }
        return null;
    }

    @Override
    public boolean remove(T object) {
        Node node = first;
        for (int i = 0; i < size; i++) {
            if (node.item == object || (node.item != null && node.item.equals(object))) {
                if (i == 0 && size == 1) {
                    first = null;
                } else if (i == 0) {
                    node.next.prev = node.prev;
                    first = node.next;
                } else if (i == size - 1) {
                    node.prev.next = node.next;
                    last = node.prev;
                } else {
                    node.prev.next = node.next;
                    node.next.prev = node.prev;
                }
                size--;
                return true;
            }
            node = node.next;
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

    private void validateIndex(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Incorrect index provided");
        }
    }

    private void validateAddIndex(int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("Incorrect index provided");
        }
    }

    private class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        Node(T element) {
            this.item = element;
            this.next = null;
            this.prev = null;
        }

        Node(Node<T> prev, T element, Node<T> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }
}
