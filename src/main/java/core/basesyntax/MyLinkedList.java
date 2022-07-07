package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        addToTail(value);
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: "
                    + index + " of bounds for size : " + size);
        }
        if (index == size) {
            addToTail(value);
        } else {
            addBefore(value, finNode(index));
        }
    }

    @Override
    public void addAll(List<T> list) {
        Object[] elements = list.toArray();
        if (elements.length != 0) {
            for (Object element : elements) {
                add((T) element);
            }
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return (T) finNode(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> node = finNode(index);
        T oldValue = node.item;
        node.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> element = finNode(index);
        T oldValue = element.item;
        unLink(element);
        return oldValue;
    }

    @Override
    public boolean remove(T object) {
        for (Node<T> element = head; element != null; element = element.right) {
            if (object == null) {
                if (element.item == null) {
                    unLink(element);
                    return true;
                }
            } else {
                if (object.equals(element.item)) {
                    unLink(element);
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

    private void addToTail(T element) {
        final Node<T> last = tail;
        final Node<T> newNode = new Node<>(last, element, null);
        tail = newNode;
        if (last == null) {
            head = newNode;
        } else {
            last.right = newNode;
        }
        size++;
    }

    private void addBefore(T element, Node<T> nextNode) {
        final Node<T> predNode = nextNode.left;
        final Node<T> newNode = new Node<>(predNode,element, nextNode);
        nextNode.left = newNode;
        if (predNode == null) {
            head = newNode;
        } else {
            predNode.right = newNode;
        }
        size++;
    }

    private void checkIndex(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: "
                    + index + " of bounds for size : " + size);
        }
    }

    private Node<T> finNode(int index) {
        if (index < (size >> 1)) {
            Node<T> current = head;
            for (int i = 0; i < index; i++) {
                current = current.right;
            }
            return current;
        } else {
            Node<T> current = tail;
            for (int i = size - 1; i > index; i--) {
                current = current.left;
            }
            return current;
        }
    }

    private void unLink(Node<T> element) {
        final Node<T> next = element.right;
        final Node<T> prev = element.left;
        if (prev == null) {
            head = next;
        } else {
            prev.right = next;
            element.left = null;
        }
        if (next == null) {
            tail = prev;
        } else {
            next.left = prev;
            element.right = null;
        }
        element.item = null;
        size--;
    }

    private static class Node<T> {
        private T item;
        private Node<T> right;
        private Node<T> left;

        public Node(Node<T> left, T element, Node<T> right) {
            this.item = element;
            this.right = right;
            this.left = left;
        }
    }
}
