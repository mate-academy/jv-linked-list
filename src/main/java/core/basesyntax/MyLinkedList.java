package core.basesyntax;

import java.util.List;
import java.util.Objects;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> first;
    private Node<T> last;

    private int size;

    @Override
    public void add(T element) {
        Node<T> newNode = new Node<>(element);
        if (first == null) {
            first = newNode;
            last = newNode;
        } else {
            last.next = newNode;
            newNode.previous = last;
            last = newNode;
        }
        size++;
    }

    @Override
    public void add(T element, int index) {
        if (index == size) {
            add(element);
            return;
        }
        isIndexOk(index);
        Node<T> newNode = new Node(element);
        if (index == 0) {
            Node<T> oldNode = first;
            oldNode.previous = newNode;
            newNode.next = oldNode;
            first = newNode;
            size++;
        } else {
            Node<T> oldNode = getNode(index);
            Node<T> oldPrevious = oldNode.previous;
            oldPrevious.next = newNode;
            oldNode.previous = newNode;
            newNode.previous = oldPrevious;
            newNode.next = oldNode;
            size++;
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
        isIndexOk(index);
        Node<T> result = getNode(index);
        return result.element;
    }

    @Override
    public T set(T value, int index) {
        isIndexOk(index);
        Node<T> node = first;
        int count = 0;
        while (node.next != null) {
            if (count == index) {
                T oldValue = node.element;
                node.element = value;
                return oldValue;
            }
            count++;
            node = node.next;
        }
        return node.element;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public T remove(int index) {
        isIndexOk(index);
        Node<T> result = getNode(index);
        T element = result.element;
        unLink(result);
        size--;
        return element;
    }

    @Override
    public boolean remove(T value) {
        Node<T> node = first;
        for (int i = 0; i < size; i++) {
            if (Objects.equals(value, node.element)) {
                remove(i);
                return true;
            }
            node = node.next;
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    private void isIndexOk(int index) {
        if (index < 0 || index + 1 > size) {
            throw new IndexOutOfBoundsException("Index can`t be < 0 and > size, index = " + index
                    + " size = " + size);
        }
    }

    private Node<T> getNode(int index) {
        Node<T> oldNode = first;
        for (int i = 0; i < index; i++) {
            oldNode = oldNode.next;
        }
        return oldNode;
    }

    private void unLink(Node<T> node) {
        Node<T> prev = node.previous;
        Node<T> next = node.next;
        if (size == 1) {
            first = null;
            last = null;
        }
        if (next == null && prev == null) {
            size = 1;
            return;
        }

        if (next == null) {
            prev.next = null;
            last = prev;
        } else {

            if (prev == null) {
                next.previous = null;
                first = next;
            } else {
                prev.next = next;
                next.previous = prev;
            }
        }
    }

    private static class Node<T> {
        private T element;
        private Node<T> next;
        private Node<T> previous;

        public Node(T element) {
            this.element = element;
        }
    }
}
