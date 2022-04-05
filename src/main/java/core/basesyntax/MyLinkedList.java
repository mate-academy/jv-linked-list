package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size = 0;
    private Node<T> first;
    private Node<T> last;

    @Override
    public void add(T value) {
        if (size == 0) {
            firstAdd(value);
        } else {
            addAfter(value);
        }
    }

    @Override
    public void add(T value, int index) {
        indexCheck(index);
        if (size == 0 && index == 0) {
            firstAdd(value);
        } else if (index == 0 && size > 0) {
            addBefore(value);
        } else if (index == size) {
            addAfter(value);
        } else {
            addMiddle(value, index);
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            add(list.get(i));
        }
    }

    @Override
    public T get(int index) {
        if (size == index) {
            throw new IndexOutOfBoundsException("Index out of bound");
        }
        indexCheck(index);
        return startFrom(index).item;
    }

    @Override
    public T set(T value, int index) {
        indexCheck(index);
        add(value, index);
        return remove(index + 1);
    }

    @Override
    public T remove(int index) {
        indexCheck(index);
        if (index == 0) {
            return removeFirst();
        } else if (index == size - 1) {
            return removeLast();
        } else {
            return removeMiddle(index);
        }
    }

    @Override
    public boolean remove(T object) {
        Node<T> x = first;
        for (int i = 0; i < size; i++) {
            if (object == x.item || (object != null && object.equals(x.item))) {
                remove(i);
                return true;
            }
            x = x.right;
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
        private Node<T> right;
        private Node<T> left;
        private T item;

        Node(Node<T> left, T item, Node<T> right) {
            this.right = right;
            this.left = left;
            this.item = item;
        }
    }

    private void indexCheck(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
    }

    private void firstAdd(T value) {
        Node<T> firstNode = new Node<>(null, value, null);
        first = firstNode;
        last = firstNode;
        size++;
    }

    private void addAfter(T value) {
        Node<T> newNode = new Node<>(last, value, null);
        last.right = newNode;
        newNode.left = last;
        last = newNode;
        size++;
    }

    private void addBefore(T value) {
        Node<T> newNode = new Node<>(null, value, first);
        first.left = newNode;
        newNode.right = first;
        first = newNode;
        size++;
    }

    private void addMiddle(T value, int index) {
        Node<T> x = startFrom(index);
        Node<T> newNode = new Node<T>(x.left, value, x);
        x.left.right = newNode;
        newNode.left = x.left;
        x.left = newNode;
        newNode.right = x;
        size++;
    }

    private T removeFirst() {
        if (size == 1) {
            size--;
            return null;
        }
        Node<T> removeObj = first;
        first = first.right;
        size--;
        return removeObj.item;
    }

    private T removeLast() {
        Node<T> removeObj = last;
        last = last.left;
        size--;
        return removeObj.item;
    }

    private T removeMiddle(int index) {
        if (size == index) {
            throw new IndexOutOfBoundsException("Index out of bound");
        }
        Node<T> x = startFrom(index);
        x.left.right = x.right;
        x.right.left = x.left;
        size--;
        return x.item;
    }

    private Node<T> startFrom(int index) {
        if (index < (size / 2)) {
            Node<T> x = first;
            for (int i = 0; i < index; i++) {
                x = x.right;
            }
            return x;
        } else {
            Node<T> x = last;
            for (int i = size - 1; i > index; i--) {
                x = x.left;
            }
            return x;
        }
    }

}
