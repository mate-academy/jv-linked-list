package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> first;
    private Node<T> last;
    private int size;

    private static class Node<T> {
        private T value;
        private Node<T> previous;
        private Node<T> next;

        public Node(Node<T> previous, T value, Node<T> next) {
            this.previous = previous;
            this.value = value;
            this.next = next;
        }
    }

    @Override
    public void add(T value) {
        if (first == null) {
            addFirst(value);
            last = first;
        } else {
            addLast(value);
        }
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }

        if (index == 0) {
            addFirst(value);
        } else if (index == size) {
            addLast(value);
        } else {
            Node<T> searchNode = getNodeByIndex(index);
            Node<T> addedNode = new Node<T>(searchNode.previous, value, searchNode);

            searchNode.previous.next = addedNode;
            searchNode.previous = addedNode;
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
        return getNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        T oldValue = getNodeByIndex(index).value;
        getNodeByIndex(index).value = value;
        return oldValue;

    }

    @Override
    public T remove(int index) {
        Node<T> nodeToRemove = getNodeByIndex(index);
        T oldValue = nodeToRemove.value;
        unlink(nodeToRemove);
        return oldValue;
    }

    @Override
    public boolean remove(T object) {
        Node<T> nodeToRemove = getNodeByValue(object);
        if (nodeToRemove != null) {
            unlink(nodeToRemove);
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
        return size == 0;
    }

    private void addFirst(T value) {
        Node<T> newNode = new Node<>(null, value, first);
        if (first != null) {
            first.previous = newNode;
        }
        first = newNode;
        if (last == null) {
            last = first;
        }
        size++;
    }

    private void addLast(T value) {
        Node<T> newNode = new Node<T>(last, value, null);
        if (last != null) {
            last.next = newNode;
        }
        last = newNode;
        size++;
    }

    private void unlink(Node<T> node) {
        if (node == first) {
            first = node.next;
        } else {
            node.previous.next = node.next;
        }

        if (node == last) {
            last = node.previous;
        } else {
            node.next.previous = node.previous;
        }

        node.value = null;
        node.next = null;
        node.previous = null;
        size--;
    }

    private Node<T> getNodeByIndex(int index) {
        checkIndex(index);
        if (index == size - 1) {
            return last;
        }
        Node<T> tmpNode;
        if (index <= size / 2 && index >= 0) {
            tmpNode = first;
            for (int i = 0; i < index; i++) {
                tmpNode = tmpNode.next;
            }
            return tmpNode;
        } else if (index > size / 2 && index < size) {
            tmpNode = last;
            for (int i = size - 1; i > index; i--) {
                tmpNode = tmpNode.previous;
            }
            return tmpNode;
        } else {
            throw new IndexOutOfBoundsException();
        }
    }

    private void checkIndex(int index) {
        if (!(index >= 0 && index <= size)) {
            throw new IndexOutOfBoundsException();
        }
    }

    private Node<T> getNodeByValue(T object) {
        Node<T> tmp = first;
        while (tmp != null) {
            if (object == null && tmp.value == null) {
                return tmp;
            } else if (object != null && object.equals(tmp.value)) {
                return tmp;
            }
            tmp = tmp.next;
        }
        return null;
    }
}
