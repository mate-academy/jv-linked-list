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
            newNode.previous = current;
            newNode.next = current.next;
            current.next.previous = newNode;
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
        if (index < (size / 2)) {
            newNode = first;
            for (int i = 0; i < index; i++) {
                newNode = newNode.next;
            }
        } else {
            newNode = last;
            for (int i = size - 1; i > index; i--) {
                newNode = newNode.previous;
            }
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
        Node<T> nextTmp = node.next;
        first = nextTmp;
        size--;
        return element;
    }

    private T unlink(Node<T> node) {
        final T elementTmp = node.value;
        Node<T> prevTmp = node.previous;
        Node<T> nextTmp = node.next;

        if (prevTmp == null) {
            first = nextTmp;
        } else {
            prevTmp.next = nextTmp;
            node.previous = null;
        }
        if (nextTmp == null) {
            last = prevTmp;
        } else {
            nextTmp.previous = prevTmp;
            node.next = null;
        }

        node.value = null;
        size--;
        return elementTmp;
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
