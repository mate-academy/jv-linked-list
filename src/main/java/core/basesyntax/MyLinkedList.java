package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size = 0;
    private Node<T> first;
    private Node<T> last;

    @Override
    public void add(T value) {
        Node<T> oldValue = last;
        Node<T> newValue = new Node<>(oldValue, value, null);
        last = newValue;
        if (oldValue == null) {
            first = newValue;
        } else {
            oldValue.next = last;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkIndexAdd(index);
        if (index == size) {
            add(value);
            return;
        } else {
            Node<T> oldValue = node(index);
            Node<T> oldValuePrev = oldValue.prev;
            Node<T> newValue = new Node<>(oldValuePrev, value, oldValue);

            if (oldValuePrev == null) {
                first = newValue;
            } else {
                oldValuePrev.next = newValue;
            }
            oldValue.prev = newValue;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T node : list) {
            add(node);
        }
    }

    @Override
    public T get(int index) {
        checkIndexSet(index);
        return node(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkIndexSet(index);
        Node<T> oldValue = node(index);
        T oldValueFromNode = oldValue.item;
        oldValue.item = value;
        return oldValueFromNode;
    }

    @Override
    public T remove(int index) {
        checkIndexSet(index);
        return unlink(node(index));
    }

    @Override
    public boolean remove(T object) {
        Node<T> nodeToDelete;
        if (object == null) {
            for (int i = 0; i < size; i++) {
                if (object == node(i).item) {
                    nodeToDelete = node(i);
                    unlink(nodeToDelete);
                    return true;
                }
            }
        } else {
            for (int i = 0; i < size; i++) {
                if (object.equals(node(i).item)) {
                    nodeToDelete = node(i);
                    unlink(nodeToDelete);
                    return true;
                }
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

    private Node<T> node(int index) {
        Node<T> oldValueAtIndex;
        if (index < size / 2) {
            oldValueAtIndex = first;
            for (int i = 0; i < index; i++) {
                oldValueAtIndex = oldValueAtIndex.next;
            }
        } else {
            oldValueAtIndex = last;
            for (int i = size - 1; i > index; i--) {
                oldValueAtIndex = oldValueAtIndex.prev;
            }
        }
        return oldValueAtIndex;
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
