package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> head;
    private Node<T> tail;

    public static class Node<T> {
        private T value;
        private Node<T> next;
        private Node<T> prev;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.value = value;
            this.next = next;
            this.prev = prev;
        }
    }

    @Override
    public void add(T value) {
        final MyLinkedList.Node<T> l = tail;
        final MyLinkedList.Node<T> newNode = new MyLinkedList.Node<>(l, value, null);
        tail = newNode;
        if (l == null) {
            head = newNode;
        } else {
            l.next = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        isElementIndexForAdd(index);
        if (index == size) {
            add(value);
        } else {
            linkBefore(value, node(index));
        }
    }

    @Override
    public void addAll(List<T> list) {
        Object[] a = list.toArray();

        for (Object value : a) {
            final MyLinkedList.Node<T> l = tail;
            final MyLinkedList.Node<T> newNode = new MyLinkedList.Node<T>(l, (T) value, null);
            tail = newNode;
            if (l == null) {
                head = newNode;
            } else {
                l.next = newNode;
            }
            size++;
        }
    }

    @Override
    public T get(int index) {
        isElementIndex(index);
        if (index < (size >> 1)) {
            MyLinkedList.Node<T> x = head;
            for (int i = 0; i < index; i++) {
                x = x.next;
            }
            return x.value;
        } else {
            MyLinkedList.Node<T> x = tail;
            for (int i = size - 1; i > index; i--) {
                x = x.prev;
            }
            return x.value;
        }
    }

    @Override
    public T set(T value, int index) {
        isElementIndex(index);
        MyLinkedList.Node<T> x = node(index);
        T oldVal = x.value;
        x.value = value;
        return oldVal;
    }

    @Override
    public T remove(int index) {
        isElementIndex(index);
        return unlink(node(index));
    }

    @Override
    public boolean remove(T object) {
        if (object == null) {
            for (MyLinkedList.Node<T> x = head; x != null; x = x.next) {
                if (x.value == null) {
                    unlink(x);
                    return true;
                }
            }
        } else {
            for (MyLinkedList.Node<T> x = head; x != null; x = x.next) {
                if (object.equals(x.value)) {
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
        if (size == 0) {
            return true;
        }
        return false;
    }

    public void isElementIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Please enter right index from "
                    + "0 to " + (size - 1));
        }
    }

    public void isElementIndexForAdd(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Please enter right index from "
                    + "0 to " + (size - 1));
        }
    }

    MyLinkedList.Node<T> node(int index) {
        if (index < (size >> 1)) {
            MyLinkedList.Node<T> x = head;
            for (int i = 0; i < index; i++) {
                x = x.next;
            }
            return x;
        } else {
            MyLinkedList.Node<T> x = tail;
            for (int i = size - 1; i > index; i--) {
                x = x.prev;
            }
            return x;
        }
    }

    T unlink(MyLinkedList.Node<T> x) {
        final T element = x.value;
        final MyLinkedList.Node<T> next = x.next;
        final MyLinkedList.Node<T> prev = x.prev;

        if (prev == null) {
            head = next;
        } else {
            prev.next = next;
            x.prev = null;
        }

        if (next == null) {
            tail = prev;
        } else {
            next.prev = prev;
            x.next = null;
        }

        x.value = null;
        size--;
        return element;
    }

    void linkBefore(T value, MyLinkedList.Node<T> succ) {
        final MyLinkedList.Node<T> pred = succ.prev;
        final MyLinkedList.Node<T> newNode = new MyLinkedList.Node<>(pred, value, succ);
        succ.prev = newNode;
        if (pred == null) {
            head = newNode;
        } else {
            pred.next = newNode;
        }
        size++;
    }
}
