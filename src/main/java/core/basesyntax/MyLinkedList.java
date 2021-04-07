package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private static final String MESSAGE = "Index is out of bounds";
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public boolean add(T value) {
        Node<T> newNode = new Node<T>(head, value, null);
        if (tail == null) {
            head = newNode;
            tail = newNode;
        } else {
            head.next = newNode;
            head = newNode;
        }
        size++;
        return true;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
        } else if (index < size && index > 0) {
            Node<T> nodeToBeMoved = searchNodeByIndex(index);
            Node<T> newNode = new Node<>(nodeToBeMoved.previous, value, nodeToBeMoved);
            nodeToBeMoved.getPrevious().next = newNode;
            nodeToBeMoved.previous = newNode;
            size++;
        } else if (index == 0) {
            Node<T> nodeToBeMoved = tail;
            Node<T> newNode = new Node<>(null, value, nodeToBeMoved);
            nodeToBeMoved.previous = newNode;
            tail = newNode;
            size++;
        } else {
            throw new IndexOutOfBoundsException(MESSAGE);
        }
    }

    @Override
    public boolean addAll(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            add(list.get(i));
        }
        return true;
    }

    @Override
    public T get(int index) {
        return searchNodeByIndex(index).getValue();
    }

    @Override
    public T set(T value, int index) {
        Node<T> nodeToBeRemoved = searchNodeByIndex(index);
        Node<T> newNode = new Node<>(nodeToBeRemoved.previous, value, nodeToBeRemoved.next);
        rearrangeLinks(index, nodeToBeRemoved, newNode);
        size++;
        return unlink(nodeToBeRemoved);
    }

    @Override
    public T remove(int index) {
        Node<T> nodeToBeRemoved = searchNodeByIndex(index);
        rearrangeLinks(index, nodeToBeRemoved);
        return unlink(nodeToBeRemoved);
    }

    @Override
    public boolean remove(T object) {
        return searchNodeByValue(object);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private Node<T> searchNodeByIndex(int index) {
        indexCheck(index);
        Node<T> temporaryNode;
        if (index <= size / 2) {
            temporaryNode = tail;
            while (index > 0) {
                temporaryNode = temporaryNode.getNext();
                index--;
            }
        } else {
            temporaryNode = head;
            while (index + 1 != size) {
                temporaryNode = temporaryNode.getPrevious();
                index++;
            }
        }
        return temporaryNode;
    }

    private boolean searchNodeByValue(T value) {
        Node<T> nodeToBeRemoved = tail;
        int index = 0;
        while (index < size) {
            if (nodeToBeRemoved.value != null && nodeToBeRemoved.value.equals(value)
                    || nodeToBeRemoved.value == value) {
                rearrangeLinks(index, nodeToBeRemoved);
                unlink(nodeToBeRemoved);
                return true;
            }
            nodeToBeRemoved = nodeToBeRemoved.getNext();
            index++;
        }
        return false;
    }

    private void rearrangeLinks(int index, Node<T> nodeToBeRemoved) {
        if (size == 1) {
            tail = null;
            head = null;
        } else {
            if (index == 0 && size > 1) {
                tail = nodeToBeRemoved.getNext();
                tail.next = nodeToBeRemoved.getNext().next;
            }
            if (index == size - 1) {
                head = nodeToBeRemoved.getPrevious();
                head.previous = nodeToBeRemoved.previous;
            }
            if (nodeToBeRemoved.next != null) {
                Node<T> next = nodeToBeRemoved.getNext();
                next.previous = nodeToBeRemoved.previous;
            }
            if (nodeToBeRemoved.previous != null) {
                Node<T> previous = nodeToBeRemoved.getPrevious();
                previous.next = nodeToBeRemoved.next;
            }
        }
    }

    private void rearrangeLinks(int index, Node<T> nodeToBeRemoved, Node<T> newNode) {
        if (index == 0) {
            tail = newNode;
            tail.next = nodeToBeRemoved.next;
        }
        if (index == size - 1) {
            head = newNode;
            head.previous = nodeToBeRemoved.previous;
        }
        if (nodeToBeRemoved.next != null) {
            Node<T> next = nodeToBeRemoved.getNext();
            next.previous = newNode;
        }
        if (nodeToBeRemoved.previous != null) {
            Node<T> previous = nodeToBeRemoved.getPrevious();
            previous.next = newNode;
        }
    }

    private void indexCheck(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException(MESSAGE);
        }
    }

    private T unlink(Node<T> nodeToBeRemoved) {
        nodeToBeRemoved.next = null;
        nodeToBeRemoved.previous = null;
        size--;
        return nodeToBeRemoved.getValue();
    }

    private static class Node<T> {
        private T value;
        private Node<T> previous;
        private Node<T> next;

        public Node(Node<T> previous, T value, Node<T> next) {
            this.previous = previous;
            this.value = value;
            this.next = next;
        }

        public Node<T> getNext() {
            return next;
        }

        public void setNext(Node<T> next) {
            this.next = next;
        }

        public T getValue() {
            return value;
        }

        public void setValue(T value) {
            this.value = value;
        }

        public Node<T> getPrevious() {
            return previous;
        }

        public void setPrevious(Node<T> previous) {
            this.previous = previous;
        }
    }
}
