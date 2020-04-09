package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> first;
    private Node<T> last;

    public MyLinkedList() {
    }

    @Override
    public boolean add(T elem) {
        Node<T> node = addAfter(last, elem);
        if (first != null) {
            last = node;
        } else {
            first = last = node;
        }
        size++;
        return true;
    }

    @Override
    public void add(T elem, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Illegal index: " + index);
        }
        if (index == size) {
            add(elem);
            return;
        }
        Node<T> node = addBefore(getNode(index), elem);
        if (index == 0) {
            first = node;
        }
        size++;
    }

    @Override
    public boolean addAll(List<T> list) {
        for (T element : list) {
            add(element);
        }
        return true;
    }

    @Override
    public T get(int index) {
        return getNode(index).data;
    }

    @Override
    public T set(T elem, int index) {
        Node<T> node = getNode(index);
        T oldData = node.data;
        node.data = elem;
        return oldData;
    }

    @Override
    public T remove(int index) {
        Node<T> node = getNode(index);
        doRemove(node);
        return node.data;
    }

    @Override
    public boolean remove(T elem) {
        Node<T> node = getNode(elem);
        if (node == null) {
            return false;
        }
        doRemove(node);
        return true;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private Node<T> addBefore(Node<T> curr, T elem) {
        if (curr == null) {
            return new Node<>(elem, null, null);
        }
        Node<T> newNode = new Node<T>(elem, curr.prev, curr);
        if (curr.prev != null) {
            curr.prev.next = newNode;
        }
        curr.prev = newNode;
        return newNode;
    }

    private Node<T> addAfter(Node<T> curr, T elem) {
        if (curr == null) {
            return new Node<>(elem, null, null);
        }
        Node<T> newNode = new Node<T>(elem, curr, curr.next);
        if (curr.next != null) {
            curr.next.prev = newNode;
        }
        curr.next = newNode;
        return newNode;
    }

    private Node<T> getNode(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Illegal index: " + index);
        }
        if (index == 0) {
            return first;
        }
        if (index == size - 1) {
            return last;
        }
        Node<T> curr;
        if (size >> 2 >= index) {
            curr = first;
            while (index-- > 0) {
                curr = curr.next;
            }
        } else {
            curr = last;
            index = size - index - 1;
            while (index-- > 0) {
                curr = curr.prev;
            }
        }
        return curr;
    }

    private Node<T> getNode(T elem) {
        if (size == 0) {
            return null;
        }
        Node<T> curr = first;
        while (curr != null) {
            if (curr.data == elem || curr.data != null && curr.data.equals(elem)) {
                return curr;
            }
            curr = curr.next;
        }
        return null;
    }

    private void doRemove(Node<T> node) {
        if (node.prev != null) {
            node.prev.next = node.next;
        }
        if (node.next != null) {
            node.next.prev = node.prev;
        }
        if (node == first) {
            first = node.next;
        }
        if (node == last) {
            last = node.prev;
        }
        --size;
    }

    private static class Node<T> {
        private T data;
        private Node<T> prev;
        private Node<T> next;

        Node(T data, Node<T> prev, Node<T> next) {
            this.data = data;
            this.prev = prev;
            this.next = next;
        }
    }
}
