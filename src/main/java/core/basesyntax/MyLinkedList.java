package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> first;
    private Node<T> last;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(last, value, null);
        if (first == null) {
            first = newNode;
        } else {
            last.next = newNode;
        }
        last = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (size == index) {
            add(value);
        } else {
            Node<T> nodeByIndex = findNode(index);
            Node<T> newNode = new Node<>(nodeByIndex.prev, value, nodeByIndex);
            newNode.next.prev = newNode;
            if (index != 0) {
                newNode.prev.next = newNode;
            } else {
                first = newNode;
            }
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T value : list) {
            add(value);
        }
    }

    @Override
    public T get(int index) {
        return findNode(index).value;
    }

    private Node<T> findNode(int index) {
        if (index >= size || index < 0) {
            throw new ArrayIndexOutOfBoundsException("index out of board " + index);
        }
        Node<T> current;
        if (index < size / 2) {
            current = first;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
        } else {
            current = last;
            for (int i = size - 1; i > index; i--) {
                current = current.prev;
            }
        }
        return current;
    }

    @Override
    public T set(T value, int index) {
        Node<T> nodeByIndex = findNode(index);
        T firstValue = nodeByIndex.value;
        nodeByIndex.value = value;
        return firstValue;
    }

    @Override
    public T remove(int index) {
        Node<T> nodeByIndex = findNode(index);
        T removed = nodeByIndex.value;
        removeNode(nodeByIndex);
        size--;
        return removed;
    }

    @Override
    public boolean remove(T object) {
        Node<T> curren = first;
        while (curren != null) {
            if (object != null && object.equals(curren.value) || object == curren.value) {
                removeNode(curren);
                size--;
                return true;
            }
            curren = curren.next;
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

    private void removeNode(Node<T> current) {
        if (current == first) {
            if (first == last) {
                first = null;
                last = null;
            } else {
                first = first.next;
            }
        } else if (current == last) {
            last = last.prev;
            last.next = null;
        } else {
            current.prev.next = current.next;
            current.next.prev = current.prev;
        }
    }

    private static class Node<T> {
        private Node<T> next;
        private Node<T> prev;
        private T value;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }

}
