package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> first;
    private Node<T> last;

    @Override
    public void add(T value) {
        last = new Node<>(last, value, null);
        if (size == 0) {
            first = last;
        } else {
            last.prev.next = last;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkIndexAdd(index);
        if (index == size) {
            add(value);
            return;
        }
        Node<T> oldValue = getNode(index);
        Node<T> oldValuePrev = oldValue.prev;
        Node<T> newValue = new Node<>(oldValuePrev, value, oldValue);

        if (oldValuePrev == null) {
            first = newValue;
        } else {
            oldValuePrev.next = newValue;
        }
        oldValue.prev = newValue;
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T element : list) {
            add(element);
        }
    }

    @Override
    public T get(int index) {
        checkIndexSet(index);
        return getNode(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkIndexSet(index);
        Node<T> oldValue = getNode(index);
        T oldValueFromNode = oldValue.item;
        oldValue.item = value;
        return oldValueFromNode;
    }

    @Override
    public T remove(int index) {
        checkIndexSet(index);
        return unlink(getNode(index));
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = first;
        for (int i = 0; i < size; i++) {
            if ((object == null && object == currentNode.item)
                    || (object != null && object.equals(currentNode.item))) {
                unlink(currentNode);
                return true;
            }
            currentNode = currentNode.next;
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

    private void checkIndexAdd(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("This index is incorrect");
        }
    }

    private void checkIndexSet(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("This index is incorrect");
        }
    }

    private Node<T> getNode(int index) {
        Node<T> currentNode;
        if (index < size / 2) {
            currentNode = first;
            for (int i = 0; i < index; i++) {
                currentNode = currentNode.next;
            }
        } else {
            currentNode = last;
            for (int i = size - 1; i > index; i--) {
                currentNode = currentNode.prev;
            }
        }
        return currentNode;
    }

    private T unlink(Node<T> node) {
        final T element = node.item;
        final Node<T> next = node.next;
        final Node<T> prev = node.prev;

        if (prev == null) {
            first = next;
        } else {
            prev.next = next;
            node.prev = null;
        }

        if (next == null) {
            last = prev;
        } else {
            next.prev = prev;
            node.next = null;
        }

        node.item = null;
        size--;
        return element;
    }
    
    private static class Node<E> {
        private Node<E> next;
        private Node<E> prev;
        private E item;

        public Node(Node<E> prev, E item, Node<E> next) {
            this.next = next;
            this.prev = prev;
            this.item = item;
        }
    }
}
