package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private static final String IOOB_MESSAGE = "IndexOutOfBoundsException exception!";
    private static final String NPE_MESSAGE = "NullPointerException exception!";
    private int size;
    private Node<T> first;
    private Node<T> last;

    public int getCurrentIndex(T value) {
        Node<T> currentNode = first;
        for (int i = 0; i < size; i++) {
            if (currentNode.getValue() == value) {
                return i;
            } else if (currentNode.getValue() != null && currentNode.getValue().equals(value)) {
                return i;
            }
            currentNode = currentNode.getNext();
        }
        return -1;
    }

    public Node<T> getCurrentNode(int index) {
        Node<T> currentNode = first;
        for (int i = 0; i < index; i++) {
            currentNode = currentNode.getNext();
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
            last.setNext(newNode);
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
                newNode.setNext(currentNode);
                currentNode.setPrev(newNode);
                first = newNode;
            } else {
                currentNode.getPrev().setNext(newNode);
                newNode.setPrev(currentNode.getPrev());
                newNode.setNext(currentNode);
                currentNode.setPrev(newNode);
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
        return currentNode.getValue();
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        checkIndexWithSize(index);
        Node<T> currentNode = getCurrentNode(index);
        T oldValue = currentNode.getValue();
        currentNode.setValue(value);
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
                currentNode.getNext().setPrev(null);
                first = currentNode.getNext();
            } else if (index == size - 1) {
                currentNode.getPrev().setNext(null);
                last = currentNode.getPrev();
            } else {
                currentNode.getPrev().setNext(currentNode.getNext());
                currentNode.getNext().setPrev(currentNode.getPrev());
            }
        }
        size--;
        return currentNode.getValue();
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

    public Node<T> getFirst() {
        return first;
    }

    public void setFirst(Node<T> first) {
        this.first = first;
    }

    public Node<T> getLast() {
        return last;
    }

    public void setLast(Node<T> last) {
        this.last = last;
    }
}
