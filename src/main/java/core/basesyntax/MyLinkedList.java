package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> first;
    private Node<T> last;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(value);
        if (size == 0) {
            first = newNode;
            last = newNode;
        } else {
            last.next = newNode;
            last = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        Node<T> newNode = new Node<>(value);
        if (index == 0) {
            newNode.next = first;
            first = newNode;
            if (first.next == null) {
                last = first;
            }
        } else if (index == size) {
            last.next = newNode;
            last = newNode;;
        } else {
            Node<T> node = getNodeByIndex(index - 1);
            newNode.next = node.next;
            node.next = newNode;
        }
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
        return getNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        MyLinkedList.Node<T> current = getNodeByIndex(index);
        T oldVal = current.value;
        current.value = value;
        return oldVal;
    }

    @Override
    public T remove(int index) {
        indexOfBoundException(index);
        return unlink(index);
    }

    @Override
    public boolean remove(T object) {
        for (int i = 0; i < size; i++) {
            if (object == get(i) || object != null && object.equals(get(i))) {
                unlink(i);
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
        return size == 0;
    }

    private Node<T> getNodeByIndex(int index) {
        indexOfBoundException(index);
        if (index == size - 1) {
            return last;
        } else {
            Node<T> currentNode = first;
            for (int i = 0; i < index; i++) {
                currentNode = currentNode.next;
            }
            return currentNode;
        }
    }

    private T unlink(int index) {
        T removedElement;
        if (index == 0) {
            removedElement = first.value;
            first = first.next;
            if (first == null) {
                last = null;
            }
        } else {
            Node<T> prev = getNodeByIndex(index - 1);
            removedElement = new Node<T>(get(index)).value;
            prev.next = prev.next.next;
            if (index == size - 1) {
                last = prev;
            }
        }
        size--;
        return removedElement;
    }

    private void indexOfBoundException(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Incorrect index " + index);
        }
    }

    private static class Node<T> {
        private T value;
        private Node<T> next;
        private Node<T> prev;

        private Node(T value) {
            this.value = value;
        }
    }
}
