package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> first;
    private Node<T> last;
    private int size;

    @Override
    public void add(T value) {
        Node<T> prevNode = last;
        Node<T> newNode = new Node<>(prevNode, value, null);
        if (size == 0) {
            first = last = newNode;
        } else {
            last.next = newNode;
            last = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkIndexForAdd(index);
        Node<T> prevNode = last;
        Node<T> newNode = new Node<>(prevNode, value, null);
        if (first == null) {
            first = last = newNode;
        } else if (index == 0) {
            newNode.next = first;
            first = newNode;
        } else if (index == size) {
            last.next = newNode;
            last = newNode;
        } else {
            Node<T> prev = getNodeByIndex(index - 1);
            newNode.next = prev.next;
            prev.next = newNode;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T t : list) {
            add(t);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return getNodeByIndex(index).element;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> node = getNodeByIndex(index);
        T oldElement = node.element;
        node.element = value;
        return oldElement;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        return unlink(getNodeByIndex(index));
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode;
        for (currentNode = first; currentNode != null; currentNode = currentNode.next) {
            if (currentNode.element == object
                    || object != null && object.equals(currentNode.element)) {
                unlink(currentNode);
                return true;
            }
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

    private T unlink(Node<T> current) {
        final T element = current.element;
        final Node<T> next = current.next;
        final Node<T> prev = current.prev;
        if (prev == null) {
            first = next;
        } else {
            prev.next = next;
            current.prev = null;
        }
        if (next == null) {
            last = prev;
        } else {
            next.prev = prev;
            current.next = null;
        }
        current.element = null;
        size--;
        return element;
    }

    private Node<T> getNodeByIndex(int index) {
        Node<T> current = first;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("This index " + index + " doesn't exist");
        }
    }

    private void checkIndexForAdd(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("This index " + index + " doesn't exist");
        }
    }

    private static class Node<T> {
        private T element;
        private Node<T> prev;
        private Node<T> next;

        public Node(Node<T> prev, T element, Node<T> next) {
            this.prev = prev;
            this.element = element;
            this.next = next;
        }
    }
}
