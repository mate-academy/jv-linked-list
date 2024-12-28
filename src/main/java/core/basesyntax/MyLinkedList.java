package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> first;
    private Node<T> last;
    private int size;

    public MyLinkedList() {
    }

    @Override
    public void add(T value) {
        Node<T> oldLast = last;
        Node<T> newNode = new Node<>(oldLast, value, null);
        last = newNode;
        if (oldLast != null) {
            oldLast.next = newNode;
        } else {
            first = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkPositionIndex(index);
        if (index == size) {
            add(value);
        } else {
            if (index == 0) {
                insertToBeginning(value);
            } else {
                insertBeforeIndex(value, index);
            }
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        if (!list.isEmpty()) {
            for (T element : list) {
                add(element);
            }
        }
    }

    @Override
    public T get(int index) {
        checkElementIndex(index);
        Node<T> node = findNodeByIndex(index);
        return node.value;
    }

    @Override
    public T set(T value, int index) {
        checkPositionIndex(index);
        Node<T> node = findNodeByIndex(index);
        T oldValue = node.value;
        node.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkElementIndex(index);
        Node<T> node = findNodeByIndex(index);
        T removingValue = node.value;
        unlink(node);
        size--;
        return removingValue;
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = first;
        while (currentNode != null) {
            if (equalsElement(currentNode.value, object)) {
                unlink(currentNode);
                size--;
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

    private void checkPositionIndex(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Can't add value in position: " + index
                    + ". Index is invalid.");
        }
    }

    private Node<T> findNodeByIndex(int index) {
        checkElementIndex(index);
        Node<T> currentNode;
        if (index < (size >> 1)) {
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

    private void insertToBeginning(T value) {
        Node<T> afterNode = first;
        Node<T> newNode = new Node<>(null, value, afterNode);
        if (afterNode != null) {
            afterNode.prev = newNode;
        } else {
            last = newNode;
        }
        first = newNode;
    }

    private void insertBeforeIndex(T value, int index) {
        Node<T> linkBefore = findNodeByIndex(index - 1);
        Node<T> linkAfter = findNodeByIndex(index);
        Node<T> newNode = new Node<>(linkBefore, value, linkAfter);
        linkBefore.next = newNode;
        if (linkAfter != null) {
            linkAfter.prev = newNode;
        } else {
            last = newNode;
        }
    }

    private void checkElementIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid index:" + index);
        }
    }

    private boolean equalsElement(T value, Object object) {
        if (object == value) {
            return true;
        }
        if (object == null || value == null) {
            return false;
        }
        if (object.getClass() == value.getClass()) {
            T current = (T) object;
            return current == value || (current != null
                    && current.equals(value));
        }
        return false;
    }

    private void unlink(Node<T> node) {
        Node<T> prev = node.prev;
        Node<T> next = node.next;
        if (prev == null) {
            first = next;
        } else {
            prev.next = next;
        }

        if (next == null) {
            last = prev;
        } else {
            next.prev = prev;
        }
    }

    public static class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }
}
