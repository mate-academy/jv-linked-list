package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> first;
    private Node<T> last;

    @Override
    public void add(T value) {
        Node<T> node = new Node<>(value, null, null);
        if (size == 0) {
            first = node;
        } else {
            last.next = node;
            node.prev = last;
        }
        last = node;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (size == index) {
            add(value);
        } else {
            Node<T> nextNode = getNode(index);
            Node<T> node = new Node<>(value, nextNode.prev, nextNode);
            if (nextNode.prev == null) {
                first = node;
            } else {
                nextNode.prev.next = node;
            }
            nextNode.prev = node;
            size++;
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
        return getNode(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> node = getNode(index);
        T oldValue = node.value;
        node.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> node = getNode(index);
        size--;
        return unlink(node);
    }

    @Override
    public boolean remove(T object) {
        int index = getIndex(object);
        if (index >= 0) {
            remove(index);
        }
        return index >= 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private static class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        private Node(T element, Node<T> prev, Node<T> next) {
            this.value = element;
            this.prev = prev;
            this.next = next;
        }
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("The index " + index + " out of bound!");
        }
    }

    private Node<T> getNode(int index) {
        checkIndex(index);
        Node<T> currentNode;
        if (index < size / 2) {
            currentNode = first;
            int currentIndex = 0;
            while (currentIndex != index) {
                currentNode = currentNode.next;
                currentIndex++;
            }
        } else {
            currentNode = last;
            int currentIndex = size - 1;
            while (currentIndex != index) {
                currentNode = currentNode.prev;
                currentIndex--;
            }
        }
        return currentNode;
    }

    private int getIndex(T value) {
        if (size != 0) {
            Node<T> currentNode = first;
            int index = 0;
            while (currentNode != null) {
                if (currentNode.value == value || currentNode.value.equals(value)) {
                    return index;
                }
                currentNode = currentNode.next;
                index++;
            }
        }
        return -1;
    }

    private T unlink(Node<T> node) {
        Node<T> prevNode = node.prev;
        Node<T> nextNode = node.next;
        if (prevNode == null) {
            first = nextNode;
        } else {
            prevNode.next = nextNode;
        }
        if (nextNode == null) {
            last = prevNode;
        } else {
            nextNode.prev = prevNode;
        }
        return node.value;
    }
}
