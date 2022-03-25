package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        if (isEmpty()) {
            head = new Node<>(value, null, null);
            tail = head;
        } else {
            Node<T> newNode = new Node<>(value, null, tail);
            tail.setNext(newNode);
            tail = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkIndexForAdd(index);
        if (index == size) {
            add(value);
        } else {
            insert(value, index);
        }

    }

    private void insert(T value, int index) {
        Node<T> indexPositionNode = findNode(index);
        Node<T> previousIndexPositionNode = indexPositionNode.getPrevious();
        Node<T> newNode = new Node<>(value, indexPositionNode, previousIndexPositionNode);
        indexPositionNode.setPrevious(newNode);
        if (previousIndexPositionNode == null) {
            head = newNode;
        } else {
            previousIndexPositionNode.setNext(newNode);
        }
        size++;
    }

    private void checkIndexForAdd(int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("Wrong index!");
        }
    }

    private void checkIndex(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Wrong index!");
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
        Node<T> foundedNode = findNode(index);
        return foundedNode == null ? null : foundedNode.getValue();
    }

    private Node<T> findNode(T content) {
        Node<T> iterationNode = head;
        while (iterationNode != null) {
            if (iterationNode.getValue() != null && iterationNode.getValue().equals(content)
                    || (iterationNode.getValue() == content)) {
                return iterationNode;
            }
            iterationNode = iterationNode.getNext();
        }
        return null;
    }

    private Node<T> findNode(int index) {
        return (size - index) > index ? searchFromHead(index) : searchFromTail(index);
    }

    private Node<T> searchFromHead(int index) {
        Node<T> neededNode = head;
        for (int i = 0; i < index; i++) {
            neededNode = neededNode.getNext();
        }
        return neededNode;
    }

    private Node<T> searchFromTail(int index) {
        Node<T> neededNode = tail;
        int whileIndex = size - 1;
        while (index < whileIndex) {
            neededNode = neededNode.getPrevious();
            whileIndex--;
        }
        return neededNode;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> indexPositionNode = findNode(index);
        T oldContent = indexPositionNode.getValue();
        indexPositionNode.setValue(value);
        return oldContent;
    }

    private T unLink(Node<T> node) {
        T removedNodeContent = node.getValue();
        if (node == head && node == tail) {
            head = null;
            tail = null;
        } else if (node == head) {
            head = head.getNext();
            head.setPrevious(null);
        } else if (node == tail) {
            tail = tail.getPrevious();
            tail.setNext(null);
        } else {
            Node<T> next = node.getNext();
            Node<T> previous = node.getPrevious();
            next.setPrevious(previous);
            previous.setNext(next);
        }
        size--;
        return removedNodeContent;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        return unLink(findNode(index));
    }

    @Override
    public boolean remove(T object) {
        Node<T> nodeForRemoving = findNode(object);
        if (nodeForRemoving == null) {
            return false;
        }
        unLink(nodeForRemoving);
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

    private class Node<T> {
        private T value;
        private Node<T> next;
        private Node<T> previous;

        public Node(T value, Node<T> next, Node<T> previous) {
            this.value = value;
            this.next = next;
            this.previous = previous;
        }

        public Node<T> getNext() {
            return next;
        }

        public void setNext(Node<T> next) {
            this.next = next;
        }

        public Node<T> getPrevious() {
            return previous;
        }

        public void setPrevious(Node<T> previous) {
            this.previous = previous;
        }

        public T getValue() {
            return value;
        }

        public void setValue(T value) {
            this.value = value;
        }
    }
}
