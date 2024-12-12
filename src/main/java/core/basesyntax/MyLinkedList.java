package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private transient MyLinkedList.Node<T> first;
    private transient MyLinkedList.Node<T> last;
    private transient int size = 0;

    @Override
    public void add(T value) {
        Node<T> val = new Node<>(null, value, null);
        if (size == 0) {
            first = last = val;
        } else {
            swapLast(val);
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }
        Node<T> givenNode = new Node<>(null, value, null);
        Node<T> current = node(index);

        if (index == 0) {
            swapFirst(givenNode);
        } else if (index == size) {
            swapLast(givenNode);
        } else {
            current.prev.next = givenNode;
            givenNode.prev = current.prev;

            current.prev = givenNode;
            givenNode.next = current;
        }
        size++;
    }

    private void swapFirst(Node<T> node) {
        if (size == 0) {
            first = last = node;
            return;
        }
        first.prev = node;
        node.next = first;
        first = node;
    }

    private void swapLast(Node<T> node) {
        if (size == 0) {
            first = last = node;
            return;
        }
        last.next = node;
        node.prev = last;
        last = node;
    }

    MyLinkedList.Node<T> node(int index) {
        Node<T> x;
        if (index < (size >> 1)) {
            x = first;
            for (int i = 0; i < index; i++) {
                x = x.next;
            }
        } else {
            x = last;
            for (int i = size - 1; i > index; i--) {
                x = x.prev;
            }
        }
        return x;
    }

    private void checkElementIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }

    @Override
    public void addAll(List<T> list) {
        if (list.isEmpty()) {
            return;
        }
        for (T node : list) {
            add(node);
        }
    }

    @Override
    public T get(int index) {
        checkElementIndex(index);
        return node(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkElementIndex(index);
        Node<T> newNode = new Node<>(null, value, null);
        newNode.item = node(index).item;
        node(index).item = value;
        return newNode.item;
    }

    private T unlink(Node<T> node) {
        final T element = node.item;
        final MyLinkedList.Node<T> next = node.next;
        final MyLinkedList.Node<T> prev = node.prev;

        if (prev == null) {
            first = next;
        } else {
            prev.next = next;
            node.prev = null;
        }
        if (next == null) {
            last = prev;
        } else {
            next.prev = prev;
            node.next = null;
        }

        node.item = null;
        size--;
        return element;
    }

    @Override
    public T remove(int index) {
        checkElementIndex(index);
        return unlink(node(index));
    }

    @Override
    public boolean remove(T object) {
        if (object == null) {
            for (MyLinkedList.Node<T> x = first; x != null; x = x.next) {
                if (x.item == null) {
                    unlink(x);
                    return true;
                }
            }
        } else {
            for (MyLinkedList.Node<T> x = first; x != null; x = x.next) {
                if (object.equals(x.item)) {
                    unlink(x);
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

    private static class Node<E> {
        private E item;
        private MyLinkedList.Node<E> next;
        private MyLinkedList.Node<E> prev;

        Node(MyLinkedList.Node<E> prev, E element, MyLinkedList.Node<E> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }
}
