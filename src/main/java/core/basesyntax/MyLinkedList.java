package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    public Node<T> first;
    public Node<T> last;

    public MyLinkedList() {
        this.first = null;
        this.last = null;
    }

    private static class Node<T> {
        private T element;
        private Node<T> previous;
        private Node<T> next;

        public Node(Node previous, T element, Node next) {
            this.previous = previous;
            this.element = element;
            this.next = next;
        }
    }

    private Node<T> getNodeByIndex(int index) {
        if (index == 0) {
            return first;
        }
        if (index == size - 1) {
            return last;
        }
        Node result = first;
        for (int i = 1; i <= index; i++) {
            result = result.next;
        }
        return result;
    }

    private Node<T> getNodeByElement(T t) {
        Node<T> currentElement = first;
        while (currentElement.next != null) {
            if (t.equals(currentElement.element)) {
                return currentElement;
            }
            currentElement = currentElement.next;
        }
        return null;
    }



    @Override
    public void add(T value) {
        if (first == null) {
            first = new Node<>(null, value, null);
            last = first;
            size = 1;
        } else {
            Node node = new Node(last, value, null);
            last.next = node;
            last = node;
            size++;
        }

    }

    @Override
    public void add(T value, int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException();
        }
        if (index == size) {
            add(value);
        } else if (index == 0) {
            Node newNode = new Node(null, value, first);
            first.previous = newNode;
            first = newNode;
        } else if (index > 0 && index < size - 1) {
            Node temp = getNodeByIndex(index);
            Node newNode = new Node(temp.previous, value, temp);
            temp.previous.next = newNode;
            temp.previous = newNode;
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T temp : list) {
            add(temp);
        }
    }

    @Override
    public T get(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException();
        }
        if (index == 0) {
            return first.element;
        }
        return (T) getNodeByIndex(index).element;
    }

    @Override
    public void set(T value, int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException();
        }
        getNodeByIndex(index).element = value;
    }

    @Override
    public T remove(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException();
        }
        Node nodeToRemove = getNodeByIndex(index);
        if (index == 0 && size == 1) {
            first = null;
            last = null;
            size = 0;
            return (T) nodeToRemove.element;
        }
        if (index == 0) {
            first = nodeToRemove.next;
            first.previous = null;
            size--;
            return (T) nodeToRemove.element;
        }
        if (index == size - 1) {
            size--;
            last = nodeToRemove.previous;
            last.next = null;
            return (T) nodeToRemove.element;
        }
        Node previousNode = nodeToRemove.previous;
        Node nextNode = nodeToRemove.next;
        previousNode.next = nextNode;
        nextNode.previous = previousNode;
        size--;
        return (T) nodeToRemove.element;
    }

    @Override
    public T remove(T t) {
        Node nodeToRemove = getNodeByElement(t);
        if (nodeToRemove == null) {
            return null;
        }
        if (nodeToRemove.previous == null) {
            first = nodeToRemove.next;
            first.previous = null;
            size--;
            return (T) nodeToRemove.element;
        }
        if (nodeToRemove.next == null) {
            last = nodeToRemove.previous;
            last.next = null;
            size--;
            return (T) nodeToRemove.element;
        }
        Node previousNode = nodeToRemove.previous;
        Node nextNode = nodeToRemove.next;
        previousNode.next = nextNode;
        nextNode.previous = previousNode;
        size--;
        return (T) nodeToRemove.element;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }
}
