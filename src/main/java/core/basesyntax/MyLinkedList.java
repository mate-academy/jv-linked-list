package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> first;
    private Node<T> last;
    private int size;

    private static class Node<T> {
        private Node<T> prev;
        private T item;
        private Node<T> next;

        public Node(Node<T> prev, T item, Node<T> next) {
            this.prev = prev;
            this.item = item;
            this.next = next;
        }
    }

    @Override
    public void add(T value) {
        linkLast(value);
    }

    @Override
    public void add(T value, int index) {
        checkIndexForAdd(index);
        if (index == size) {
            linkLast(value);
        } else {
            linkBefore(value, index);
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
        checkIndex(index);
        return getNode(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> current = getNode(index);
        T oldValue = current.item;
        current.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> oldValue = getNode(index);
        unLink(oldValue);
        return oldValue.item;
    }

    @Override
    public boolean remove(T object) {
        if (object == null) {
            for (Node<T> i = first; i != null; i = i.next) {
                if (i.item == null) {
                    unLink(i);
                    return true;
                }
            }
        } else {
            for (Node<T> i = first; i != null; i = i.next) {
                if (i.item != null && i.item.equals(object)) {
                    unLink(i);
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
        return first == null;
    }

    @Override
    public String toString() {
        Node<T> current = first;
        StringBuilder printList = new StringBuilder();
        while (current != null) {
            printList.append(current.item);
            printList.append(" ");
            current = current.next;
        }
        return printList.toString();

    }

    private void linkLast(T element) {
        Node<T> prevElement = last;
        Node<T> newNode = new Node<>(prevElement, element, null);
        last = newNode;
        if (prevElement == null) {
            first = newNode;
        } else {
            prevElement.next = newNode;
        }
        size++;
    }

    private void linkBefore(T element, int index) {
        Node<T> currentNode = getNode(index);
        Node<T> newNode = new Node<>(currentNode.prev, element, currentNode);
        if (index == 0) {
            first.prev = newNode;
            first = newNode;
        } else {
            currentNode.prev.next = newNode;
            currentNode.prev = newNode;
        }
        size++;
    }

    private Node<T> getNode(int index) {
        Node<T> currentFirst = first;
        if (index < (size / 2)) {
            for (int i = 0; i < index; i++) {
                currentFirst = currentFirst.next;
            }
            return currentFirst;
        } else {
            Node<T> currentLast = last;
            for (int i = size - 1; i > index; i--) {
                currentLast = currentLast.prev;
            }
            return currentLast;
        }
    }

    private void checkIndexForAdd(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
    }

    private void unLink(Node<T> node) {
        if (node.next == null) {
            last = null;
        } else {
            node.next.prev = null;
        }
        if (node.prev == null) {
            first = node.next;
        } else if (node.next == null) {
            node.prev.next = null;
            last = node.prev;
        } else {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }
        size--;
    }
}


