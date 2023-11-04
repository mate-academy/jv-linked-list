package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> first;
    private Node<T> last;
    private int size;

    private static class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        private Node(Node<T> prev, T element, Node<T> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }

    private void linkFirst(T element) {
        Node<T> prevFirst = first;
        Node<T> newNode = new Node<>(null, element, prevFirst);
        first = newNode;
        if (prevFirst == null) {
            last = newNode;
        } else {
            prevFirst.prev = newNode;
        }
        size++;
    }

    private void linkBefore(T element, Node<T> exist) {
        Node<T> prevExist = exist.prev;
        Node<T> newNode = new Node<>(prevExist, element, exist);
        exist.prev = newNode;
        if (prevExist == null) {
            first = newNode;
        } else {
            prevExist.next = newNode;
        }
        size++;
    }

    private void linkLast(T element) {
        Node<T> prevLast = last;
        Node<T> newNode = new Node<>(prevLast, element, null);
        last = newNode;
        if (prevLast == null) {
            first = newNode;
        } else {
            prevLast.next = newNode;
        }
        size++;
    }

    private Node<T> node(int index) {
        checkIndex(index);
        if (index < (size >> 1)) {
            Node<T> current = first;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
            return current;
        } else {
            Node<T> current = last;
            for (int i = size - 1; i > index; i--) {
                current = current.prev;
            }
            return current;
        }
    }

    private void deleteFirst() {
        Node<T> oldFirst = first;
        Node<T> newFirst = first.next;
        oldFirst.item = null;
        oldFirst.next = null;
        first = newFirst;
        if (newFirst == null) {
            last = null;
        } else {
            newFirst.prev = null;
        }
        size--;
    }

    private void deleteLast() {
        Node<T> oldLast = last;
        Node<T> newLast = last.prev;
        oldLast.item = null;
        oldLast.prev = null;
        last = newLast;
        if (newLast == null) {
            first = null;
        } else {
            newLast.next = null;
        }
        size--;
    }

    private void deleteNode(Node<T> node) {
        Node<T> next = node.next;
        Node<T> prev = node.prev;
        next.prev = prev;
        prev.next = next;
        node.item = null;
        node.next = null;
        node.prev = null;
        size--;
    }

    private void checkPositionIndex(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("incorrect position index: " + index);
        }
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("invalid index: " + index);
        }
    }

    @Override
    public void add(T value) {
        linkLast(value);
    }

    @Override
    public void add(T value, int index) {
        checkPositionIndex(index);
        if (index == size) {
            linkLast(value);
        } else if (index == 0) {
            linkFirst(value);
        } else {
            linkBefore(value, node(index));
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T elm: list) {
            add(elm);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        if (index < (size >> 1)) {
            Node<T> current = first;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
            return current.item;
        } else {
            Node<T> current = last;
            for (int i = size - 1; i > index; i--) {
                current = current.prev;
            }
            return current.item;
        }
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> current = node(index);
        T oldValue = current.item;
        current.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> node;
        T nodeValue = null;
        if (index < (size >> 1)) {
            node = first;
            for (int i = 0; i < index; i++) {
                node = node.next;
            }
            nodeValue = node.item;
            if (node == first) {
                deleteFirst();
            } else {
                deleteNode(node);
            }
        } else {
            node = last;
            for (int i = size - 1; i > index; i--) {
                node = node.prev;
            }
            nodeValue = node.item;
            if (node == last) {
                deleteLast();
            } else {
                deleteNode(node);
            }
        }
        return nodeValue;
    }

    @Override
    public boolean remove(T object) {
        for (Node<T> node = first; node != null; node = node.next) {
            if ((node.item != null && node.item.equals(object))
                    || node.item == object) {
                if (node == first) {
                    deleteFirst();
                    return true;
                }
                if (node == last) {
                    deleteLast();
                    return true;
                }
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
        return size() == 0;
    }
}
