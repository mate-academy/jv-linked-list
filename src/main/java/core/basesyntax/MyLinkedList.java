package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {

    private Node<T> first;
    private Node<T> last;
    private int size;

    public MyLinkedList() {
        size = 0;
    }

    @Override
    public boolean add(T value) {
        Node<T> temp = new Node<>(value, null, last);
        if (size == 0) {
            first = temp;
        } else {
            last.next = temp;
        }
        last = temp;
        size++;
        return true;
    }

    @Override
    public void add(T value, int index) {
        if (index >= 0 && index <= size) {
            if (index == size) {
                add(value);
            } else {
                Node<T> next = getNodaByIndex(index);
                Node<T> prev = next.prev;
                Node<T> newNode = new Node<>(value, next, prev);
                if (prev == null) {
                    first = newNode;
                } else {
                    prev.next = newNode;
                }
                next.prev = newNode;
                size++;
            }
        } else {
            throw new IndexOutOfBoundsException();
        }
    }

    @Override
    public boolean addAll(List<T> list) {
        for (T i : list) {
            add(i);
        }
        return true;
    }

    @Override
    public T get(int index) {
        if (indexIsExist(index)) {
            Node<T> temp = getNodaByIndex(index);
            return temp.data;
        }
        throw new IndexOutOfBoundsException();
    }

    @Override
    public T set(T value, int index) {
        if (index < size && index >= 0) {
            Node<T> temp = getNodaByIndex(index);
            T oldValue = temp.data;
            temp.data = value;
            return oldValue;
        }
        throw new IndexOutOfBoundsException();
    }

    @Override
    public T remove(int index) {
        if (indexIsExist(index)) {
            Node<T> temp = getNodaByIndex(index);
            removeThis(getNodaByIndex(index));
            return temp.data;
        }
        throw new IndexOutOfBoundsException();
    }

    @Override
    public boolean remove(T t) {
        Node<T> temp = first;
        int i = 0;
        while (i != size) {
            if (temp.data == t || t != null && temp.data.equals(t)) {
                removeThis(temp);
                return true;
            }
            temp = temp.next;
            i++;
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

    public void removeThis(Node<T> temp) {
        if (temp.prev == null) {
            first = temp.next;
        } else {
            temp.prev.next = temp.next;
        }
        if (temp.next == null) {
            last = temp.prev;
        } else {
            temp.next.prev = temp.prev;
        }
        size--;
    }

    private boolean indexIsExist(int index) {
        return index >= 0 && index < size;
    }

    private Node<T> getNodaByIndex(int index) {
        Node<T> temp = first;
        int i = 0;
        while (i != index) {
            temp = temp.next;
            i++;
        }
        return temp;
    }

    private class Node<T> {
        private T data;
        private Node<T> next;
        private Node<T> prev;

        private Node(T data, Node<T> next, Node<T> prev) {
            this.data = data;
            this.next = next;
            this.prev = prev;
        }

    }
}
