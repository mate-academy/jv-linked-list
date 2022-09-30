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

    private void linkFirst(T e) {
        final Node<T> f = first;
        final Node<T> newNode = new Node<>(null, e, f);
        first = newNode;
        if (f == null) {
            last = newNode;
        }
        else {
            f.left = newNode;
        }
        size++;
    }

    void linkLast(T e) {
        final Node<T> l = last;
        final Node<T> newNode = new Node<>(l, e, null);
        last = newNode;
        if (l == null) {
            first = newNode;
        }
        else {
            l.right = newNode;
        }
        size++;
    }

    @Override
    public void add(T value) {
        final Node<T> a = last;
        final Node<T> newNode = new Node<>(a, value, null);
        last = newNode;
        if (a == null) {
            first = newNode;
        } else {
            a.right = newNode;
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
        for (T l : list) {
            add(l);
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
        Node<T> x = node(index);
        T oldVal = x.element;
        x.element = value;
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
            for (Node<T> x = first; x != last.right; x = x.right) {
                if (x.element == null) {
                    unlink(x);
                    return true;
                }
            }
        } else {
            for (Node<T> x = first; x != last.right; x = x.right) {
                if (object.equals(x.element)) {
                    unlink(x);
                    return true;
                }
            }
       }
        return false;
    }

    T unlink(Node<T> x) {
        final T element = x.element;
        final Node<T> right = x.right;
        final Node<T> left = x.left;

        if (left == null) {
            first = right;
        } else {
            left.right = right;
            x.left = null;
        }
        if (right == null) {
            last = left;
        } else {
            right.right = left;
            x.right = null;
        }
        x.element = null;
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
       // Node<T> x;
        if (index < size / 2) {
            Node<T> x = first;
            for (int i = 0; i < index; i++) {
                x = x.right;
            }
            return x;
        } else {
             Node<T> x = last;
            for (int i = (size - 1); i > index; i--) {
                x = x.left;
            }
            return x;
        }
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
