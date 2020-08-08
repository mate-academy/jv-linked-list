package core.basesyntax;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

class Train {
    public static void main(String[] args) {
        MyLinkedList<Integer> myLinkedList = new MyLinkedList<>();

        myLinkedList.add(7);
        myLinkedList.add(2);
        myLinkedList.remove(0);
            System.out.println(myLinkedList.get(0));
    }
}

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> firsNode;
    private Node<T> lastNode;
    int modCount = 0;

    @Override
    public boolean add(T value) {
        linkLast(value);
        return true;
    }

    @Override
    public void add(T value, int index) {
//        if (index >= 0 && index <= size)
//            throw new IndexOutOfBoundsException();
        if (index == size)
            linkLast(value);
        else
            linkBefore(value, node(index));
    }

    @Override
    public boolean addAll(List<T> list) {
//        for (int i = 0; i < list.size(); i++) {
//            add(list.get(i));
//        }
        return addAll(size, list);
    }

    @Override
    public T get(int index) {
//        if (index > 0 && index < size)
//            throw new IndexOutOfBoundsException();
        return node(index).element;
    }

    @Override
    public T set(T value, int index) {
        Node<T> x = node(index);
        T oldVal = x.element;
        x.element = value;
        return oldVal;
    }

    @Override
    public T remove(int index) {
        return unlink(node(index));
    }

    @Override
    public boolean remove(T t) {
        if (t == null) {
            for (Node<T> x = firsNode; x != null; x = x.next) {
                if (x.element == null) {
                    unlink(x);
                    return true;
                }
            }
        } else {
            for (Node<T> x = firsNode; x != null; x = x.next) {
                if (t.equals(x.element)) {
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

    private static class Node<T> {
        T element;
        Node<T> next;
        Node<T> prev;

        Node(Node<T> prev, T element, Node<T> next) {
            this.element = element;
            this.prev = prev;
            this.next = next;

        }
    }

    public boolean addAll(int index, Collection<? extends T> c) {

        Object[] a = c.toArray();
        int numNew = a.length;
        if (numNew == 0)
            return false;

        Node<T> pred, succ;
        if (index == size) {
            succ = null;
            pred = lastNode;
        } else {
            succ = node(index);
            pred = succ.prev;
        }

        for (Object o : a) {
            @SuppressWarnings("unchecked") T e = (T) o;
            Node<T> newNode = new Node<T>(pred, e, null);
            if (pred == null)
                firsNode = newNode;
            else
                pred.next = newNode;
            pred = newNode;
        }

        if (succ == null) {
            lastNode = pred;
        } else {
            pred.next = succ;
            succ.prev = pred;
        }

        size += numNew;
        modCount++;
        return true;
    }

    T unlink(Node<T> x) {
        // assert x != null;
        final T element = x.element;
        final Node<T> next = x.next;
        final Node<T> prev = x.prev;

        if (prev == null) {
            firsNode = next;
        } else {
            prev.next = next;
            x.prev = null;
        }

        if (next == null) {
            lastNode = prev;
        } else {
            next.prev = prev;
            x.next = null;
        }

        x.element = null;
        size--;
        modCount++;
        return element;
    }


        public void linkLast(T value) {
            final Node<T> tempLast = lastNode;
            final Node<T> newNode = new Node<T>(tempLast, value, null);
            lastNode = newNode;
            if (tempLast == null) {
                firsNode = newNode;
            } else {
                tempLast.next = newNode;
            }
            size++;
        }

        public void linkBefore(T value, Node<T> succ) {
            final Node<T> pred = succ.prev;
            final Node<T> newNode = new Node<>(pred, value, succ);
            succ.prev = newNode;
            if (pred == null)
                firsNode = newNode;
            else
                pred.next = newNode;
            size++;
        }

        Node<T> node(int index) {
            if (index < (size >> 1)) {
                Node<T> x = firsNode;
                for (int i = 0; i < index; i++)
                    x = x.next;
                return x;
            } else {
                Node<T> x = lastNode;
                for (int i = size - 1; i > index; i--)
                    x = x.prev;
                return x;
            }
        }

}



