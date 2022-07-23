package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> first;
    private Node<T> last;

    @Override
    public void add(T value) {
        if (size == 0) {
            first = last = new Node<>(value, null, null);
            size++;
        } else {
            Node<T> node = last;
            last = new Node<>(value, node, null);
            node.next = last;
            size++;
        }
    }

    @Override
    public void add(T value, int index) {
        if (size == index) {
            add(value);
        } else {
            Node<T> nextNode = getNodeByIndex(index);
            Node<T> node = new Node<>(value, nextNode.prev, nextNode);
            if (nextNode.prev != null) {
                nextNode.prev.next = node;
            }
            nextNode.prev = node;
            if (index == 0) {
                first = node;
            }
            if (index == size - 1) {
                last = nextNode;
            }
            size++;
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
        if (checkIndex(index)) {
            return getNodeByIndex(index).element;
        } else {
            throw new IndexOutOfBoundsException("The index " + index + " out of bound!");
        }
    }

    @Override
    public T set(T value, int index) {
        if (checkIndex(index)) {
            Node<T> node = getNodeByIndex(index);
            T oldValue = node.element;
            node.element = value;
            return oldValue;
        } else {
            throw new IndexOutOfBoundsException("The index " + index + " out of bound!");
        }
    }

    @Override
    public T remove(int index) {
        if (checkIndex(index)) {
            Node<T> node = getNodeByIndex(index);
            if (node.prev != null && node.next != null) {
                node.prev.next = node.next;
                node.next.prev = node.prev;
            } else if (node.prev != null && node.next == null) {
                node.prev.next = null;
                last = node.prev;
            } else if (node.prev == null && node.next != null) {
                node.next.prev = null;
                first = node.next;
            } else {
                first = last = null;
            }
            size--;
            return node.element;

        } else {
            throw new IndexOutOfBoundsException("The index " + index + " out of bound!");
        }
    }

    @Override
    public boolean remove(T object) {
        int index = getIndex(object);
        if (index >= 0) {
            remove(index);
        }
        return index >= 0;
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
        private T element;
        private Node<T> prev;
        private Node<T> next;

        private Node(T element, Node<T> prev, Node<T> next) {
            this.element = element;
            this.prev = prev;
            this.next = next;
        }
    }

    private boolean checkIndex(int index) {
        return index >= 0 && index < size;
    }

    private Node<T> getNodeByIndex(int index) {
        if (checkIndex(index)) {
            Node<T> currentNode = first;
            int currentIndex = 0;
            while (currentIndex != index) {
                currentNode = currentNode.next;
                currentIndex++;
            }
            return currentNode;
        } else {
            throw new IndexOutOfBoundsException("The index " + index + " out of bound!");
        }
    }

    private int getIndex(T value) {
        if (size != 0) {
            Node<T> currentNode = first;
            int index = 0;
            while (currentNode != null) {
                if (currentNode.element == value || currentNode.element.equals(value)) {
                    return index;
                }
                currentNode = currentNode.next;
                index++;
            }
        }
        return -1;
    }
}
