package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    private static class Node<T> {
        private T element;
        private Node<T> previous;
        private Node<T> next;

        public Node(Node<T> previous, T element, Node<T> next) {
            this.previous = previous;
            this.element = element;
            this.next = next;
        }
    }

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(tail, value, null);
        if (size == 0) {
            head = newNode;
        } else {
            tail.next = newNode;
        }
        tail = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("The index is wrong");
        }
        if (index == size) {
            add(value);
            return;
        }
        Node<T> currentNode = getNodeByIndex(index);
        Node<T> previousNode = currentNode.previous;
        Node<T> insertNode = new Node<>(previousNode, value, currentNode);
        if (previousNode == null) {
            head = insertNode;
        } else {
            currentNode.previous = insertNode;
            previousNode.next = insertNode;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T t : list) {
            add(t);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return getNodeByIndex(index).element;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> node = getNodeByIndex(index);
        T oldElement = node.element;
        node.element = value;
        return oldElement;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        return unlink(getNodeByIndex(index));
    }

    @Override
    public boolean remove(T object) {
        Node<T> node = head;
        while (node != null) {
            if ((object == node.element)
                    || (node.element.equals(object))) {
                unlink(node);
                return true;
            }
            node = node.next;
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return head == null;
    }

    private Node<T> getNodeByIndex(int index) {
        Node<T> node = head;
        if (index >= size / 2) {
            node = tail;
            while (index < size - 1) {
                node = node.previous;
                index++;
            }
        } else {
            while (index > 0) {
                node = node.next;
                index--;
            }
        }
        return node;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Wrong index");
        }
    }

    private T unlink(Node<T> node) {
        Node<T> nextNode = node.next;
        Node<T> prevNode = node.previous;
        if (prevNode == null) {
            head = nextNode;
        } else {
            prevNode.next = nextNode;
        }
        if (nextNode == null) {
            tail = prevNode;
        } else {
            nextNode.previous = prevNode;
        }
        size--;
        return node.element;
    }
}
