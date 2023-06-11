package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> first;
    private Node<T> last;

    class Node<T> {
        private T element;
        private Node<T> next;
        private Node<T> prev;

        public Node(T element) {
            this.element = element;
        }
    }

    @Override
    public void add(T value) {
        if (first == null) {
            addToEmptyList(value);
        } else {
            addLastNode(value);
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        checkIndex(index, size + 1);
        if (first == null) {
            addToEmptyList(value);
        }
        if (index == 0) {
            addToFirstIndex(value);
        } else {
            addToMiddleIndex(value, index);
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
        checkIndex(index, size);
        return getNodeByIndex(index).element;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index, size);
        Node<T> node = getNodeByIndex(index);
        T oldValue = node.element;
        node.element = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index, size);
        T removedElement = removeFirstElementInLinkedList(index);
        if (removedElement == null) {
            removedElement = removeMiddleElementInLinkedList(index);
        }
        if (removedElement == null) {
            removedElement = removeLastElementInLinkedList(index);
        }
        size--;
        return removedElement;
    }

    @Override
    public boolean remove(T object) {
        int index = -1;
        for (int i = 0; i < size; i++) {
            if ((object != null && get(i) != null && get(i).equals(object))
                    || (object == null && get(i) == null)) {
                index = i;
                break;
            }
        }
        if (index != -1) {
            remove(index);
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

    private Node<T> getNodeByIndex(int index) {
        Node<T> current = first;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current;
    }

    private int checkIndex(int index, int size) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index is out of bounds: " + index);
        }
        return index;
    }

    private void addToEmptyList(T value) {
        Node<T> newNode = new Node<>(value);
        first = last = newNode;
    }

    private void addLastNode(T value) {
        Node<T> newNode = new Node<>(value);
        last.next = newNode;
        newNode.prev = last;
        last = newNode;
    }

    private void addToFirstIndex(T value) {
        Node<T> newNode = new Node<>(value);
        newNode.next = first;
        first.prev = newNode;
        first = newNode;
    }

    private void addToMiddleIndex(T value, int index) {
        Node<T> newNode = new Node<>(value);
        Node<T> current = getNodeByIndex(index);
        Node<T> prev = current.prev;
        newNode.next = current;
        newNode.prev = prev;
        prev.next = newNode;
        current.prev = newNode;
    }

    private T removeFirstElementInLinkedList(int index) {
        if (index == 0) {
            T removedElement = first.element;
            first = first.next;
            if (first == null) {
                last = null;
            } else {
                first.prev = null;
            }
            return removedElement;
        }
        return null;
    }

    private T removeMiddleElementInLinkedList(int index) {
        Node<T> current = getNodeByIndex(index);
        Node<T> prev = current.prev;
        Node<T> next = current.next;
        prev.next = next;
        if (next != null) {
            next.prev = prev;
        }
        return current.element;
    }

    private T removeLastElementInLinkedList(int index) {
        if (index == size - 1) {
            T removedElement = last.element;
            last = last.prev;
            if (last == null) {
                first = null;
            } else {
                last.next = null;
            }
            return removedElement;
        }
        return null;
    }
}
