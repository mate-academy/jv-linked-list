package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> last = tail;
        Node<T> newNode = new Node<>(last, value, null);
        tail = newNode;
        if (last == null) {
            head = newNode;
        } else {
            last.next = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
        } else {
            Node<T> node = findNode(index);
            linkBefore(value, node);
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T listElement : list) {
            add(listElement);
        }
    }

    @Override
    public T get(int index) {
        return findNode(index).element;
    }

    @Override
    public T set(T value, int index) {
        Node<T> node = findNode(index);
        T oldElement = node.element;
        node.element = value;
        return oldElement;
    }

    @Override
    public T remove(int index) {
        return unlink(findNode(index));
    }

    @Override
    public boolean remove(T object) {
        Node<T> node = head;
        while (node != null) {
            if (node.element == object || node.element != null
                    && node.element.equals(object)) {
                unlink(node);
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

    @Override
    public boolean isEmpty() {
        return size == 0;
    }
    
    private Node<T> findNode(int index) {
        checkIndex(index);
        Node<T> neededNode;
        int i;
        if (index < (size >> 1)) {
            neededNode = head;
            i = 0;
            while (i < index) {
                neededNode = neededNode.next;
                i++;
            }
        } else {
            neededNode = tail;
            i = size - 1;
            while (i > index) {
                neededNode = neededNode.prev;
                i--;
            }
        }
        return neededNode;
    }

    private void linkBefore(T value, Node<T> node) {
        Node<T> newNode = new Node<>(node.prev, value, node);
        if (node.prev == null) {
            head = newNode;
        } else {
            node.prev.next = newNode;
        }
        node.prev = newNode;
        size++;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid index");
        }
    }

    private T unlink(Node<T> node) {
        Node<T> prev = node.prev;
        Node<T> next = node.next;
        T element = node.element;
        if (prev == null) {
            head = next;
        } else {
            node.prev = null;
            prev.next = next;
        }
        if (next == null) {
            tail = prev;
        } else {
            node.next = null;
            next.prev = prev;
        }
        node.element = null;
        size--;
        return element;
    }

    private static class Node<T> {
        private T element;
        private Node<T> prev;
        private Node<T> next;

        public Node(Node<T> prev, T element, Node<T> next) {
            this.element = element;
            this.prev = prev;
            this.next = next;
        }
    }
}
