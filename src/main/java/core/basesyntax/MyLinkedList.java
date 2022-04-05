package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
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
        if (size == index) {
            add(value);
            return;
        }
        if (index == 0) {
            addBefore(value);
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
        return getNodeByIndex(index).item;
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
        Node<T> currentNode = first;
        for (int i = 0; i < size; i++) {
            if (object == currentNode.item || (object != null && object.equals(currentNode.item))) {
                remove(i);
                return true;
            }
            currentNode = currentNode.right;
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
        Node<T> currentNode = getNodeByIndex(index);
        Node<T> newNode = new Node<T>(currentNode.left, value, currentNode);
        currentNode.left.right = newNode;
        newNode.left = currentNode.left;
        currentNode.left = newNode;
        newNode.right = currentNode;
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
        Node<T> currentNode = getNodeByIndex(index);
        currentNode.left.right = currentNode.right;
        currentNode.right.left = currentNode.left;
        size--;
        return currentNode.item;
    }

    private Node<T> getNodeByIndex(int index) {
        if (index < (size / 2)) {
            Node<T> currentNode = first;
            for (int i = 0; i < index; i++) {
                currentNode = currentNode.right;
            }
            return currentNode;
        } else {
            Node<T> currentNode = last;
            for (int i = size - 1; i > index; i--) {
                currentNode = currentNode.left;
            }
            return currentNode;
        }
    }
}
