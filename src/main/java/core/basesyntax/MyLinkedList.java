package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size = 0;
    private MyLinkedList.Node<T> first;
    private MyLinkedList.Node<T> last;

    @Override
    public void add(T value) {
        final MyLinkedList.Node<T> newNode = new MyLinkedList.Node<>(last, value, null);
        if (last == null) {
            first = newNode;
        } else {
            last.next = newNode;
        }
        last = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkPositionIndex(index);
        if (index == size) {
            add(value);
        } else {
            linkBefore(value, getNodeByIndex(index));
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T element : list) {
            add(element);
        }
    }

    @Override
    public T get(int index) {
        checkElementIndex(index);
        return getNodeByIndex(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkElementIndex(index);
        MyLinkedList.Node<T> newValue = getNodeByIndex(index);
        T oldValue = newValue.item;
        newValue.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkElementIndex(index);
        Node<T> currentNode = getNodeByIndex(index);
        T oldValue = currentNode.item;
        unlink(currentNode);
        return oldValue;
    }

    @Override
    public boolean remove(T element) {
        for (MyLinkedList.Node<T> removedNode = first; removedNode
                != null; removedNode = removedNode.next) {
            if (removedNode.item == element
                    || (element != null && element.equals(removedNode.item))) {
                unlink(removedNode);
                return true;
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

    private MyLinkedList.Node<T> getNodeByIndex(int index) {
        Node<T> foundedNode;
        if (index < (size / 2)) {
            foundedNode = first;
            for (int i = 0; i < index; i++) {
                foundedNode = foundedNode.next;
            }
        } else {
            foundedNode = last;
            for (int i = size - 1; i > index; i--) {
                foundedNode = foundedNode.prev;
            }
        }
        return foundedNode;
    }

    private void unlink(Node<T> removedNode) {
        MyLinkedList.Node<T> next = removedNode.next;
        MyLinkedList.Node<T> prev = removedNode.prev;
        if (prev == null) {
            first = next;
        } else {
            prev.next = next;
            removedNode.prev = null;
        }
        if (next == null) {
            last = prev;
        } else {
            next.prev = prev;
            removedNode.next = null;
        }
        removedNode.item = null;
        size--;
    }

    private void linkBefore(T element, MyLinkedList.Node<T> afterNode) {
        final MyLinkedList.Node<T> prev = afterNode.prev;
        final MyLinkedList.Node<T> newNode = new MyLinkedList.Node<>(prev, element, afterNode);
        if (prev == null) {
            first = newNode;
        } else {
            prev.next = newNode;
        }
        afterNode.prev = newNode;
        size++;
    }

    private void checkElementIndex(int index) {
        if (!(index >= 0 && index < size)) {
            throw new IndexOutOfBoundsException("Index: " + index + " is more then size " + size);
        }
    }

    private void checkPositionIndex(int index) {
        if (!(index >= 0 && index <= size)) {
            throw new IndexOutOfBoundsException("Index: " + index + " is more then size " + size);
        }
    }

    private static class Node<T> {
        private T item;
        private MyLinkedList.Node<T> next;
        private MyLinkedList.Node<T> prev;

        public Node(Node<T> prev, T item, Node<T> next) {
            this.item = item;
            this.next = next;
            this.prev = prev;
        }
    }
}
