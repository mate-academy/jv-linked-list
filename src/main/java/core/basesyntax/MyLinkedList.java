package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {

    private Node<T> first;
    private Node<T> last;
    private int size = 0;

    public MyLinkedList() {
        first = null;
        last = null;
    }

    @Override
    public void add(T value) {
        Node<T> addedNode = new Node<>(value);
        if (size == 0) {
            first = addedNode;
        } else {
            addedNode.prev = last;
            last.next = addedNode;
        }
        last = addedNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index is out of bound");
        }
        if (index == size) {
            add(value);
        } else if (index == 0) {
            Node<T> addedNode = new Node<>(value);
            addedNode.next = first;
            first.prev = addedNode;
            first = addedNode;
            size++;
        } else {
            Node<T> searchedNode = findNodeByIndex(index);
            Node<T> addedNode = new Node<>(value);
            addedNode.next = searchedNode;
            addedNode.prev = searchedNode.prev;
            searchedNode.prev.next = addedNode;
            searchedNode.prev = addedNode;
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T node : list) {
            add(node);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        Node<T> searchedNode = findNodeByIndex(index);
        return searchedNode.value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> node = findNodeByIndex(index);
        T oldValue = node.value;
        node.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> node = findNodeByIndex(index);
        unlinkNode(node);
        size--;
        return node.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> node = first;
        while (node != null && !((node.value == object) || (node.value != null
                && node.value.equals(object)))) {
            node = node.next;
        }
        if (node == null) {
            return false;
        }
        unlinkNode(node);
        size--;
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

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index is out of bound");
        }
    }

    private Node<T> findNodeByIndex(int index) {
        int middle = size / 2;
        Node<T> searchedNode;
        if (index < middle) {
            searchedNode = first;
            int listIndex = 0;
            while (listIndex != index) {
                searchedNode = searchedNode.next;
                listIndex++;
            }
        } else {
            searchedNode = last;
            int listIndex = size - 1;
            while (listIndex != index) {
                searchedNode = searchedNode.prev;
                listIndex--;
            }
        }
        return searchedNode;
    }

    private void unlinkNode(Node<T> node) {
        if (node == first) {
            if (first == last) {
                first = null;
                last = null;
            } else {
                first = node.next;
                node.next.prev = null;
            }
        } else if (node == last) {
            last = node.prev;
            node.prev.next = null;
        } else {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }
        node.prev = null;
        node.next = null;
    }

    private static class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        public Node(T value) {
            this.value = value;
            next = null;
            prev = null;
        }

    }
}
