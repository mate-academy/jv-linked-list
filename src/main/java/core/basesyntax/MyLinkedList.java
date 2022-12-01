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
            last.next = newNode;
            last = newNode;
        } else {
            Node<T> current = findNodeIndex(index - 1);
            newNode.next = current.next;
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
        findNodeIndex(index);
        T deleteElement;
        if (index == 0) {
            deleteElement = first.value;
            first = first.next;
            if (first == null) {
                last = null;
            }
        } else {
            Node<T> newNode = findNodeIndex(index - 1);
            deleteElement = newNode.next.value;
            newNode.next = newNode.next.next;
            if (index == size - 1) {
                last = newNode;
            }
        }
        size--;
        return deleteElement;
    }

    @Override
    public boolean remove(T object) {
        int index = findIndexObject(object);
        if (index == 0) {
            first = first.next;
            if (first == null) {
                last = null;
            }
            size--;
            return true;
        } else if (index > 0 && index < size) {
            Node<T> newNode = findNodeIndex(index - 1);
            newNode.next = newNode.next.next;
            if (index == size - 1) {
                last = newNode;
            }
            size--;
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
        Node<T> newNode = first;
        for (int i = 0; i < index; i++) {
            newNode = newNode.next;
        }
        return newNode;
    }

    private int findIndexObject(Object o) {
        int index = 0;
        if (o == null) {
            for (Node<T> newNode = first; newNode != null; newNode = newNode.next) {
                if (newNode.value == null) {
                    return index;
                }
                index++;
            }
        } else {
            for (Node<T> newNode = first; newNode != null; newNode = newNode.next) {
                if (o.equals(newNode.value)) {
                    return index;
                }
                index++;
            }
        }
        return -1;
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
