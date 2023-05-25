package core.basesyntax;

import java.util.List;
import java.util.Objects;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> first;
    private Node<T> last;
    private int size;

    private static class Node<T> {
        private T item;
        private Node<T> prev;
        private Node<T> next;

        public Node(T item, Node<T> prev, Node<T> next) {
            this.item = item;
            this.prev = prev;
            this.next = next;
        }
    }

    @Override
    public void add(T value) {
        if (size == 0) {
            addFirst(value);
        } else {
            addLast(value);
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == 0) {
            addFirst(value);
        } else if (index == size) {
            addLast(value);
        } else {
            checkIndex(index);
            addMiddle(getNodeByIndex(index), value);
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T item : list) {
            add(item);
        }
    }

    private void addFirst(T value) {
        if (size == 0) {
            first = new Node<>(value, null, last);
            last = first;
            return;
        }
        Node<T> newNode = new Node<>(value, null, first);
        first.prev = newNode;
        first = newNode;
    }

    private void addLast(T value) {
        Node<T> newNode = new Node<>(value, last, null);
        last.next = newNode;
        last = newNode;
    }

    private void addMiddle(Node<T> nodeBefore, T value) {
        Node<T> newNode = new Node<>(value,nodeBefore.prev, nodeBefore);
        nodeBefore.prev.next = newNode;
        nodeBefore.prev = newNode;
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return getNodeByIndex(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> nodeToModify = getNodeByIndex(index);
        T returnValue = nodeToModify.item;
        nodeToModify.item = value;
        return returnValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        return unlink(getNodeByIndex(index));
    }

    @Override
    public boolean remove(T object) {
        Node<T> nodeToRemove = first;
        while (nodeToRemove != null) {
            if (Objects.equals(object, nodeToRemove.item)) {
                unlink(nodeToRemove);
                return true;
            }
            nodeToRemove = nodeToRemove.next;
        }
        return false;
    }

    private T unlink(Node<T> nodeToRemove) {
        T returnValue;
        if (nodeToRemove == first) {
            returnValue = removeFirst();
        } else if (nodeToRemove == last) {
            returnValue = removeLast();
        } else {
            returnValue = nodeToRemove.item;
            nodeToRemove.prev.next = nodeToRemove.next;
            nodeToRemove.next.prev = nodeToRemove.prev;
        }
        size--;
        return returnValue;
    }

    private T removeFirst() {
        T returnValue = first.item;
        if (size == 1) {
            first = null;
        } else {
            first = first.next;
            first.prev = null;
        }
        return returnValue;
    }

    private T removeLast() {
        T returnValue = last.item;
        last = last.prev;
        last.next = null;
        return returnValue;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid index " + index);
        }
    }

    private Node<T> getNodeByIndex(int index) {
        Node<T> pointer;
        if (index <= size / 2) {
            pointer = first;
            for (int i = 0; i < index; i++) {
                pointer = pointer.next;
            }
        } else {
            pointer = last;
            for (int i = size - 1; i > index; i--) {
                pointer = pointer.prev;
            }
        }
        return pointer;
    }
}
