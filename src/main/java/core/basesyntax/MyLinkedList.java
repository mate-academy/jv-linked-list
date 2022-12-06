package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> first;
    private Node<T> last;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(value);
        if (first == null) {
            first = last = newNode;
        } else {
            this.last.next = newNode;
            newNode.previous = last;
            last = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index out of range: " + index);
        }
        Node<T> newNode = new Node<>(value);
        if (first == null) {
            first = last = newNode;
        } else if (index == 0) {
            newNode.next = first;
            first = newNode;
        } else if (index == size) {
            Node<T> current = last;
            current.next = newNode;
            newNode.previous = current;
            last = newNode;
        } else {
            Node<T> current = findNodeIndex(index - 1);
            newNode.next = current.next;
            newNode.previous = current;
            current.next = newNode;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            add(list.get(i));
        }
    }

    @Override
    public T get(int index) {
        return findNodeIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        Node<T> newNode = findNodeIndex(index);
        T element = newNode.value;
        newNode.value = value;
        return element;
    }

    @Override
    public T remove(int index) {
        if (index == 0) {
            return firstUnlink(findNodeIndex(index));
        }
        return unlink(findNodeIndex(index));
    }

    @Override
    public boolean remove(T object) {
        int index = findIndexObject(object);
        if (index == 0) {
            firstUnlink(findNodeIndex(index));
            return true;
        } else if (index > 0 && index < size) {
            unlink(findNodeIndex(index));
            return true;
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

    private Node<T> findNodeIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of range: " + index);
        }
        Node<T> newNode;
        newNode = first;
        for (int i = 0; i < index; i++) {
            newNode = newNode.next;
        }

        return newNode;
    }

    private int findIndexObject(Object o) {
        int index = 0;
        for (Node<T> newNode = first; newNode != null; newNode = newNode.next) {
            if (o == null) {
                if (newNode.value == null) {
                    return index;
                }
            } else if (o.equals(newNode.value)) {
                return index;
            }
            index++;
        }
        return -1;
    }

    private T firstUnlink(Node<T> node) {
        T element = node.value;
        Node<T> n = node.next;
        first = n;
        size--;
        return element;
    }

    private T unlink(Node<T> node) {
        final T e = node.value;
        Node<T> p = node.previous;
        Node<T> n = node.next;

        if (p == null) {
            first = n;
        } else {
            p.next = n;
            node.previous = null;
        }
        if (n == null) {
            last = p;
        } else {
            n.previous = p;
            node.next = null;
        }

        node.value = null;
        size--;
        return e;
    }

    private static class Node<T> {
        private T value;
        private Node<T> previous;
        private Node<T> next;

        public Node(T value) {
            this.value = value;
        }
    }
}
