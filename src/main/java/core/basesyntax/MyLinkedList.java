package core.basesyntax;

import java.util.List;
import java.util.NoSuchElementException;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> first;
    private Node<T> last;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(last, value, null);
        if (last == null) {
            first = newNode;
        } else {
            last.next = newNode;
        }
        last = newNode;
        ++size;
    }

    @Override
    public void add(T value, int index) {
        checkIndexForAdd(index);

        if (index == size) {
            add(value);
        } else {
            Node<T> exIndexNode = findNodeByIndex(index);
            Node<T> prevNode = exIndexNode.prev;
            Node<T> newNode = new Node<>(prevNode, value, exIndexNode);

            if (prevNode != null) {
                prevNode.next = newNode;
            } else {
                first = newNode;
            }

            exIndexNode.prev = newNode;

            if (index == size - 1) {
                last = exIndexNode;
            }
            ++size;
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
        checkIndex(index);
        return findNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        T oldValue = findNodeByIndex(index).value;
        findNodeByIndex(index).value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> node = findNodeByIndex(index);
        T value = node.value;
        deleteNode(node);
        return value;
    }

    @Override
    public boolean remove(T object) {
        if (object == null) {
            for (Node<T> node = first; node != null; node = node.next) {
                if (node.value == null) {
                    deleteNode(node);
                    return true;
                }
            }
        } else {
            for (Node<T> node = first; node != null; node = node.next) {
                if (node.value != null && node.value.equals(object)) {
                    deleteNode(node);
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

    public T getFirst() {
        if (first == null) {
            throw new NoSuchElementException();
        }
        return first.value;
    }

    public T getLast() {
        if (last == null) {
            throw new NoSuchElementException();
        }
        return last.value;
    }

    private void deleteNode(Node<T> node) {
        Node<T> prevNode = node.prev;
        Node<T> nextNode = node.next;
        if (prevNode == null) {
            first = nextNode;
        } else {
            prevNode.next = nextNode;
            node.next = null;
        }
        if (nextNode == null) {
            last = prevNode;
        } else {
            nextNode.prev = prevNode;
            node.prev = null;
        }
        --size;
    }

    private Node<T> findNodeByIndex(int index) {
        if (index < (size / 2)) {
            Node<T> node = first;
            for (int i = 0; i < index; ++i) {
                node = node.next;
            }
            return node;
        } else {
            Node<T> node = last;
            for (int i = size - 1; i > index; --i) {
                node = node.prev;
            }
            return node;
        }
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index " + index + ", Size " + size);
        }
    }

    private void checkIndexForAdd(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index " + index + ", Size " + size);
        }
    }

    public static class Node<T> {
        private T value;
        private Node prev;
        private Node next;

        public Node(Node prev, T value, Node next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }

        /*Message for Mentor
        I would like not to use private modifies, setters and getters
        But auto compiler from Mate Academy says
        Variable 'value' must be private and have accessor methods.
         */

        public T getValue() {
            return value;
        }

        public void setValue(T value) {
            this.value = value;
        }

        public Node getPrev() {
            return prev;
        }

        public void setPrev(Node prev) {
            this.prev = prev;
        }

        public Node getNext() {
            return next;
        }

        public void setNext(Node next) {
            this.next = next;
        }
    }
}
