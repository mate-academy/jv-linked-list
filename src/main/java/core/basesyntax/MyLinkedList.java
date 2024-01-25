package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {

    private static class Node<T> {
        private T value;
        private Node<T> next;
        private Node<T> prev;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }

    private Node<T> first;
    private Node<T> last;
    private int size;

    @Override
    public void add(T value) {
        if (isEmpty()) {
            linkFirst(value);
        } else {
            linkLast(value);
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkIndexForAdd(index);
        if (isEmpty()) {
            linkFirst(value);
        } else if (index == size()) {
            linkLast(value);
        } else if (index == 0) {
            Node<T> current = first;
            Node<T> newNode = new Node<>(null, value, current);
            first = newNode;
            current.prev = newNode;
        } else {
            Node<T> current = getNode(index);
            Node<T> newNode = new Node<>(current.prev, value, current);
            current.prev.next = newNode;
            current.prev = newNode;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T item : list) {
            add(item);
        }
    }

    @Override
    public T get(int index) {
        checkIndexForGet(index);
        Node<T> current = first;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current != null ? current.value : null;
    }

    @Override
    public T set(T value, int index) {
        checkIndexForGet(index);
        Node<T> current = getNode(index);
        T oldValue = current.value;
        current.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndexForGet(index);
        Node<T> unlickNode = unlinkNode(index);
        size--;
        return unlickNode.value;
    }

    @Override
    public boolean remove(T object) {
        int indexObject = searchIndex(object);
        if (indexObject > -1) {
            unlinkNode(indexObject);
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
        return size == 0;
    }

    private void linkLast(T value) {
        Node<T> newNode = new Node<T>(last, value, null);
        last.next = newNode;
        last = newNode;
    }

    private void linkFirst(T value) {
        Node<T> newNode = new Node<T>(null, value, first);
        first = newNode;
        last = newNode;
    }

    private boolean checkIndexForAdd(int index) {
        if (index > size() || index < 0) {
            throw new IndexOutOfBoundsException("Index " + index + " is bad for size " + size());
        }
        return true;
    }

    private boolean checkIndexForGet(int index) {
        if (index >= size() || index < 0) {
            throw new IndexOutOfBoundsException("Index " + index + " is bad for size " + size());
        }
        return true;
    }

    private int searchIndex(T object) {
        Node<T> current = first;
        for (int i = 0; i < size; i++) {
            if (current.value == object || current.value != null && current.value.equals(object)) {
                return i;
            }
            current = current.next;
        }
        return -1;
    }

    private Node<T> getNode(int index) {
        Node<T> current = index >= size / 2 ? last : first;
        if (current == first) {
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
        }
        if (current == last) {
            for (int i = size - 1; i > index; i--) {
                current = current.prev;
            }
        }
        return current;
    }

    private Node<T> unlinkNode(int index) {
        Node<T> current = getNode(index);
        if (current == first && size > 1) {
            current.next.prev = null;
            first = current.next;
        } else if (current == last && size > 1) {
            current.prev.next = null;
            last = current.prev;
        } else if (size > 1) {
            current.next.prev = current.prev;
            current.prev.next = current.next;
        } else if (index == 0 && size == 1) {
            first = last = null;
        }
        return current;
    }
}
