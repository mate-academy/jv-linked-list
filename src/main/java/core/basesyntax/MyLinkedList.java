package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        if (size >= 1) {
            Node<T> previousTail = tail;
            tail = new Node<>(previousTail, value, null);
            previousTail.next = tail;
        } else {
            Node<T> fistNode = new Node<>(null, value, null);
            head = fistNode;
            tail = fistNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkIndexForBoundAdd(index);
        if (index == size) {
            add(value);
            return;
        } else if (index == 0) {
            Node<T> initialHeadNode = head;
            head = new Node<>(null, value, initialHeadNode);
            initialHeadNode.prev = head;
            size++;
            return;
        }
        if (index < (size - 1) / 2) {
            int indexCounter = 0;
            Node<T> tempNode = head.next;
            while (indexCounter != index) {
                indexCounter++;
                if (indexCounter == index) {
                    linkNewNode(tempNode, value);
                }
                tempNode = tempNode.next;

            }
        } else {
            int indexCounter = size;
            Node<T> tempNode = tail;
            while (indexCounter != index) {
                indexCounter--;
                if (indexCounter == index) {
                    linkNewNode(tempNode, value);
                }
                tempNode = tempNode.prev;
            }
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
        checkIndexForBound(index);
        return getNode(index).body;
    }

    @Override
    public T set(T value, int index) {
        checkIndexForBound(index);
        T previousBody = getNode(index).body;
        getNode(index).body = value;
        return previousBody;
    }

    @Override
    public T remove(int index) {
        Node<T> toRemoveNode = getNode(index);

        unlinkNode(toRemoveNode);
        size--;
        return toRemoveNode.body;
    }

    @Override
    public boolean remove(T object) {
        for (int i = 0; i < size; i++) {
            if (get(i) == object || get(i) != null && get(i).equals(object)) {
                remove(i);
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

    private void linkNewNode(Node<T> tempNode, T value) {
        Node<T> toAdd = new Node<>(tempNode.prev, value, tempNode);
        tempNode.prev.next = toAdd;
        tempNode.prev = toAdd;
        size++;
    }

    private void checkIndexForBound(int index) {
        String exception = String.format("Wrong index! %d index is out of bounds.", index);
        if (index < 0 || index > size - 1 && size > 1) {
            throw new IndexOutOfBoundsException(exception);
        }
    }

    private void checkIndexForBoundAdd(int index) {
        String exception = String.format("Wrong index! %d index is out of bounds.", index);
        if (index < 0 || index > size && size > 1) {
            throw new IndexOutOfBoundsException(exception);
        }
    }

    private void unlinkNode(Node<T> node) {
        Node<T> nextNode = node.next;
        Node<T> previousNode = node.prev;
        if (node == head) {
            head = nextNode;
        } else if (node == tail) {
            tail = previousNode;
        }
        if (previousNode != null) {
            previousNode.next = nextNode;
        }
        if (nextNode != null) {
            nextNode.prev = previousNode;
        }
    }

    private Node<T> getNode(int index) {
        checkIndexForBound(index);
        int firstHalf = size / 2;
        Node<T> correctNode = null;
        if (index <= firstHalf) {
            Node<T> tempNode = head;
            for (int i = 0; i < size; i++) {
                if (i == index) {
                    correctNode = tempNode;
                }
                tempNode = tempNode.next;
            }
        } else {
            Node<T> tempNode = tail;
            for(int i = size - 1; i > 0; i--) {
                if (i == index) {
                    correctNode = tempNode;
                }
                tempNode = tempNode.prev;
            }
            }
        return correctNode;
    }

    private static class Node<T> {
        private Node<T> prev;
        private Node<T> next;
        private T body;

        public Node(Node<T> prev, T body, Node<T> next) {
            this.prev = prev;
            this.next = next;
            this.body = body;
        }
    }
}
