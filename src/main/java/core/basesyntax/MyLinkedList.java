package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {

    private int size;
    private Node<T> first;
    private Node<T> last;

    public MyLinkedList() {
        size = 0;
    }

    @Override
    public boolean add(T value) {
        addLast(value);
        return true;
    }

    @Override
    public void add(T value, int index) {
        checkPosition(index);
        if (index == this.size) {
            this.addLast(value);
        } else {
            this.addInside(value, this.getNodeByIndex(index));
        }
    }

    @Override
    public boolean addAll(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            this.add(list.get(i));
        }
        return true;
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return getNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> oldNode = getNodeByIndex(index);
        T returnValue = oldNode.value;
        oldNode.value = value;
        return returnValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> removed = getNodeByIndex(index);
        Node<T> next = removed.next;
        Node<T> prev = removed.prev;

        if (prev == null) {
            this.first = next;
        } else {
            prev.next = next;
            removed.prev = null;
        }
        if (next == null) {
            this.last = prev;
        } else {
            next.prev = prev;
            removed.next = null;
        }
        size--;
        return removed.value;
    }

    @Override
    public boolean remove(T t) {
        Node<T> checked = first;
        for (int i = 0; i < size; i++) {
            if (checked.value == t || checked.value != null && checked.value.equals(t)) {
                remove(i);
                return true;
            }
            checked = checked.next;
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

    private void checkPosition(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index + "out of Size: " + this.size);
        }
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= this.size) {
            throw new IndexOutOfBoundsException("Index: " + index + "out of Size: " + this.size);
        }
    }

    private void addLast(T element) {
        Node<T> last = this.last;
        Node<T> newNode = new Node(last, element, null);
        this.last = newNode;
        if (last == null) {
            this.first = newNode;
        } else {
            last.next = newNode;
        }
        size++;
    }

    private Node<T> getNodeByIndex(int index) {
        Node<T> x;
        if (index < size >> 1) {
            x = this.first;

            for (int i = 0; i < index; ++i) {
                x = x.next;
            }
        } else {
            x = this.last;

            for (int i = this.size - 1; i > index; --i) {
                x = x.prev;
            }
        }
        return x;
    }

    private void addInside(T value, Node<T> nodeBefore) {
        Node<T> prev = nodeBefore.prev;
        Node<T> newNode = new Node<T>(prev, value, nodeBefore);
        nodeBefore.prev = newNode;
        if (prev == null) {
            this.first = newNode;
        } else {
            prev.next = newNode;
        }
        ++this.size;
    }

    private class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        private Node(Node<T> prev, T curr, Node<T> next) {
            value = curr;
            this.prev = prev;
            this.next = next;
        }
    }
}
