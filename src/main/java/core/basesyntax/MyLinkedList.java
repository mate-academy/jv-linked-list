package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(null, value, null);
        if (size == 0) {
            head = newNode;
            tail = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        isIndexValidToAddNode(index);
        Node<T> oldNode = findByIndex(index);
        Node<T> newNode = new Node<>(null, value, null);

        if (index == 0 && size != 0) {
            head.prev = newNode;
            newNode.next = head;
            head = newNode;
            size++;
        } else if (index == 0) {
            add(value);
        } else if (index == size) {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
            size++;
        } else if (oldNode != null) {
            oldNode.prev.next = newNode;
            newNode.prev = oldNode.prev;

            newNode.next = oldNode;
            oldNode.prev = newNode;
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T item : list) {
            add(item);
        }
    }

    @Override
    public T get(int index) {
        isIndexValid(index);
        return findByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        isIndexValid(index);
        Node<T> node = findByIndex(index);
        T oldValue = node.value;
        node.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        isIndexValid(index);
        Node<T> node = findByIndex(index);
        unlink(node);
        size--;
        return node.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> node = findByValue(object);
        if (node != null) {
            unlink(node);
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

    /**
     * Removes all connections of a certain node
     * The method changes links of <strong>a previous node</strong>
     * and <strong>a next node</strong>:
     * The next pointer of <strong>previous node</strong>
     * will be connected to <strong>the next node</strong> <br>
     * The previous pointer of <strong>next node</strong>
     * will be connected to <strong>the previous node</strong>
     * @param node The instance of node which will be removed from the LinkedList
     */
    private void unlink(Node<T> node) {
        if (node.prev != null) {
            node.prev.next = node.next;
            if (isTail(node)) {
                tail = node.prev;
            }
        }
        if (node.next != null) {
            node.next.prev = node.prev;
            if (isHead(node)) {
                head = node.next;
            }
        }
    }

    private Node<T> findByIndex(int index) {
        return index <= size / 2 ? searchFromHeadByIndex(index) : searchFromTailByIndex(index);
    }

    private Node<T> findByValue(T value) {
        Node<T> iteratorNode = head;
        while (iteratorNode != null) {
            if ((iteratorNode.value == value) || (iteratorNode.value != null
                                                  && iteratorNode.value.equals(value))) {
                return iteratorNode;
            }
            iteratorNode = iteratorNode.next;
        }
        return null;
    }

    private Node<T> searchFromHeadByIndex(int index) {
        int counter = 0;
        Node<T> iteratorNode = head;
        while (iteratorNode != null) {
            if (counter == index) {
                return iteratorNode;
            }
            iteratorNode = iteratorNode.next;
            counter++;
        }
        return iteratorNode;
    }

    private Node<T> searchFromTailByIndex(int index) {
        int counter = size - 1;
        Node<T> iteratorNode = tail;
        while (iteratorNode != null) {
            if (counter == index) {
                return iteratorNode;
            }
            iteratorNode = iteratorNode.prev;
            counter--;
        }
        return iteratorNode;
    }

    private void isIndexValidToAddNode(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("The index - " + index + " is out of bound."
                    + "Because the size of LinkedList is " + size);
        }
    }

    private void isIndexValid(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("The index - " + index + " is out of bound."
                    + "Because the size of LinkedList is " + size);
        }
    }

    private boolean isHead(Node<T> node) {
        return node == head;
    }

    private boolean isTail(Node<T> node) {
        return node == tail;
    }

    private static class Node<T> {
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
