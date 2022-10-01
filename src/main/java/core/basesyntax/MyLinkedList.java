package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size = 0;
    private Node<T> first;
    private Node<T> last;

    public MyLinkedList() {
    }

    private static class Node<T> {
        T element;
        Node<T> left;
        Node<T> right;

        Node(Node<T> left, T element, Node<T> right) {
            this.element = element;
            this.left = left;
            this.right = right;
        }
    }

    @Override
    public void add(T value) {
        final Node<T> leftNode = last;
        final Node<T> newNode = new Node<>(leftNode, value, null);
        last = newNode;
        if (leftNode == null) {
            first = newNode;
        } else {
            leftNode.right = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkPositionIndex(index);
        if (index == size) {
            add(value);
        } else {
            final Node<T> left = node(index).left;
            final Node<T> newNode = new Node<>(left, value, node(index));
            node(index).left = newNode;
            if (left == null) {
                first = newNode;
            } else {
                left.right = newNode;
            }
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T elementList : list) {
            add(elementList);
        }
    }

    @Override
    public T get(int index) {
        checkElementIndex(index);
        return node(index).element;
    }

    @Override
    public T set(T value, int index) {
        checkElementIndex(index);
        Node<T> current = node(index);
        T oldVal = current.element;
        current.element = value;
        return oldVal;
    }

    @Override
    public T remove(int index) {
        checkElementIndex(index);
        return unlink(node(index));
    }

    @Override
    public boolean remove(T object) {
       if (object == null) {
            for (Node<T> current = first; current != last.right; current = current.right) {
                if (current.element == null) {
                    unlink(current);
                    return true;
                }
            }
        } else {
            for (Node<T> current = first; current != last.right; current = current.right) {
                if (object.equals(current.element)) {
                    unlink(current);
                    return true;
                }
            }
       }
        return false;
    }

    T unlink(Node<T> current) {
        final T element = current.element;
        final Node<T> right = current.right;
        final Node<T> left = current.left;
        if (left == null) {
            first = right;
        } else {
            left.right = right;
            current.left = null;
        }
        if (right == null) {
            last = left;
        } else {
            right.left = left;
            current.right = null;
        }
        current.element = null;
        size--;
        return element;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    Node<T> node(int index) {
        checkElementIndex(index);
        Node<T> current;
        if (index < size / 2) {
            current = first;
            for (int i = 0; i < index; i++) {
                current = current.right;
            }
        } else {
            current = last;
            for (int i = (size - 1); i > index; i--) {
                current = current.left;
            }
        }
        return current;
    }

    private void checkElementIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Can't element index: " + index);
        }
    }

    public void checkPositionIndex(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Can't element index: " + index);
        }
    }
}
