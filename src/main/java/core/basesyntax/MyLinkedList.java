package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size = 0;
    private Node<T> first;
    private Node<T> last;

    public MyLinkedList() {
    }

    private static class Node<T> {
        public T element;
        public Node<T> next;
        public Node<T> prev;

        public Node(Node<T> prev, T element, Node<T> next) {
            this.element = element;
            this.next = next;
            this.prev = prev;
        }
    }

    @Override
    public boolean add(T value) {
        Node<T> newNode = new Node<T>(last, value, null);
        if (size == 0) {
            first = newNode;
        } else {
            last.next = newNode;
        }
        last = newNode;
        size++;
        return true;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        checkIndex(index);
        if (index == 0) {
            // создали объект который ссылается на first и в нём находится value
            Node<T> addedNode = new Node<>(null, value, first);
            first.prev = addedNode;
            first = addedNode;
        } else {
            Node<T> nextNode = getNodeByIndex(index);
            Node<T> addedNode = new Node<>(nextNode.prev, value, nextNode);
            nextNode.prev.next = addedNode;
            nextNode.prev = addedNode;
        }
        size++;
    }

    @Override
    public boolean addAll(List<T> list) {
        for (T l : list) {
            add(l);
        }
        return true;
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return getNodeByIndex(index).element;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);

        Node<T> replaceableNode = getNodeByIndex(index);
        T oldElement = replaceableNode.element;
        replaceableNode.element = value;
        return oldElement;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> x = getNodeByIndex(index);
        final T element = x.element;
        Node<T> prev = x.prev;
        Node<T> next = x.next;
        if (prev == null) {
            first = next;
        } else {
            prev.next = next;
            x.prev = null;
        }
        if (next == null) {
            last = prev;
        } else {
            next.prev = prev;
            x.next = null;
        }
        x.element = null;
        size--;
        return element;
    }

    @Override
    public boolean remove(T t) {
        for (int i = 0; i < size; i++) {
            Node<T> node = getNodeByIndex(i);
            if ((node.element != null && node.element.equals(t)) || node.element == t) {
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

    private void checkIndex(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException();
        }
    }

    private Node<T> getNodeByIndex(int index) {
        Node<T> result = first;
        if (size / 2 > index) {
            for (int i = 0; i < index; i++) {
                result = result.next;
            }
        } else {
            result = last;
            for (int i = size - 1; i > index; i--) {
                result = result.prev;
            }
        }
        return result;
    }
}
