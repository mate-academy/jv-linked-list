package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> first;
    private Node<T> last;
    private Node<T> nodeTemp;
    private int size = 0;

    @Override
    public boolean add(T value) {
        add(value, size);
        return true;
    }

    @Override
    public void add(T value, int index) {
        Node<T> nodeNew;
        if (size == 0 && index == 0) {
            nodeNew = new Node<>(null, value, null);
            first = last = nodeNew;
            size++;
            return;
        }
        if (index == size) {
            nodeNew = new Node<>(last, value, null);
            last.next = nodeNew;
            last = nodeNew;
            size++;
            return;
        }
        isValid(index);
        nodeTemp = getNode(index);
        if (nodeTemp == first) {
                nodeNew = new Node<>(null, value, nodeTemp);
                nodeTemp.previous = nodeNew;
                first = nodeNew;
            size++;
            return;
            } else {
                nodeNew = new Node<>(nodeTemp.previous, value, nodeTemp);
                nodeTemp.previous.next = nodeNew;
                nodeTemp.previous = nodeNew;
                size++;
            }
    }
    @Override
    public boolean addAll(List<T> list) {
        for (T object : list) {
            add(object);
        }
        return true;
    }

    @Override
    public T get(int index) {
        return getNode(index).value;
    }

    @Override
    public T set(T value, int index) {
        nodeTemp = getNode(index);
        T value1 = nodeTemp.value;
        nodeTemp.value = value;
        return value1;
    }

    @Override
    public T remove(int index) {
        nodeTemp = getNode(index);
        deletedNode(nodeTemp);
        return nodeTemp.value;
    }

    @Override
    public boolean remove(T object) {
       nodeTemp = first;
        while (nodeTemp != null) {
            if (nodeTemp.value == null || nodeTemp.value.equals(object)) {
                deletedNode(nodeTemp);
                return true;
            }
            nodeTemp = nodeTemp.next;
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
        private Node<T> previous;
        private T value;
        private Node<T> next;

        Node(Node<T> previous, T value, Node<T> next) {
            this.previous = previous;
            this.value = value;
            this.next = next;
        }
    }

    private Node<T> getNode(int index) {
        isValid(index);
        if (index < size / 2) {
            nodeTemp = first;
            for (int i = 0; i < index; i++) {
                nodeTemp = nodeTemp.next;
            }
        } else {
            nodeTemp = last;
            for (int i = size - 1; i > index; i--) {
                nodeTemp = nodeTemp.previous;
            }
        }
        return nodeTemp;
    }

    private void deletedNode(Node<T> node) {
        if (node == last) {
            last.next = null;
            last = node.previous;
        } else if (node == first) {
            first = node.next;
            first.previous = null;
        } else {
            node.previous.next = node.next;
            node.next.previous = node.previous;
        }
        size--;
    }

    private void isValid(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Wrong index");
        }
    }

}