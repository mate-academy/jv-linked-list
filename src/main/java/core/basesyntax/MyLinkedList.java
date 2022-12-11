package core.basesyntax;

import java.util.List;
import java.util.NoSuchElementException;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private static final String IOOB_MESSAGE = "IndexOutOfBoundsException exception!";
    private static final String NPE_MESSAGE = "NullPointerException exception!";
    private static final String NSE_MESSAGE = "NoSuchElementException exception!";
    Node<T> first;
    Node<T> last;

    public int getCurrentIndex(T value) {
        Node<T> currentNode = first;
        for (int i = 0; i < size; i++) {
            if (currentNode.value == value) {
                return i;
            } else if (currentNode.value != null && currentNode.value.equals(value)) {
                return i;
            }
            currentNode = currentNode.next;
        }
        return -1;
    }

    public Node<T> getCurrentNode(int index) {
        Node<T> currentNode = first;
        for (int i = 0; i < index; i++) {
            currentNode = currentNode.next;
        }
        return currentNode;
    }

    public void checkIndex(Integer index) {
        if (index == null) {
            throw new NullPointerException(NPE_MESSAGE);
        }
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException(IOOB_MESSAGE);
        }
    }

    public void checkIndexWithSize(int index) {
        if (index == size) {
            throw new ArrayIndexOutOfBoundsException(IOOB_MESSAGE);
        }
    }

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(last, value, null);
        if (size == 0) {
            first = newNode;
        } else {
            last.next = newNode;
        }
        last = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkIndex(index);
        if (size == index) {
            add(value);
        } else {
            Node<T> newNode = new Node<>(null, value, null);
            Node<T> currentNode = getCurrentNode(index);
            if (index == 0) {
                newNode.next = currentNode;
                currentNode.prev = newNode;
                first = newNode;
            } else {
                currentNode.prev.next = newNode;
                newNode.prev = currentNode.prev;
                newNode.next = currentNode;
                currentNode.prev = newNode;

            }
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T value: list) {
            add(value);
        }
    }

    @Override
    public T get(int index) {
        checkIndexWithSize(index);
        checkIndex(index);
        Node<T> currentNode = getCurrentNode(index);
        return currentNode.value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        checkIndexWithSize(index);
        Node<T> currentNode = getCurrentNode(index);
        T oldValue = currentNode.value;
        currentNode.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndexWithSize(index);
        checkIndex(index);
        Node<T> currentNode = getCurrentNode(index);
        if (index == 0 && size == 1) {
            first = null;
            last = null;
        } else {
            if (index == 0) {
                currentNode.next.prev = null;
                first = currentNode.next;
            } else if (index == size - 1) {
                currentNode.prev.next = null;
                last = currentNode.prev;
            } else {
                currentNode.prev.next = currentNode.next;
                currentNode.next.prev = currentNode.prev;
            }
        }
        size--;
        return currentNode.value;
    }

    @Override
    public boolean remove(T object) {
        int currentIndex = getCurrentIndex(object);
        if (currentIndex == -1) {
            return false;
        }
        remove(currentIndex);
        return true;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }
}
