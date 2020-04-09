package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {

    private Node<T> first;
    private Node<T> last;
    private int size;

    private static class Node<T> {
        T element;
        Node<T> next;
        Node<T> previous;

        private Node(Node<T> previous, T element, Node<T> next) {
            this.previous = previous;
            this.element = element;
            this.next = next;
        }
    }

    @Override
    public boolean add(T value) {
        Node<T> newNode = new Node<>(null, value, null);
        if (isEmpty()) {
            size = 0;
            first = newNode;
            last = first;
        } else {
            last.next = newNode;
            newNode.previous = last;
            last = newNode;
        }
        size++;
        return true;
    }

    @Override
    public void add(T value, int index) {
        if (index == 0) {
            addFirst(value);
        } else {
            if (index == size) {
                addToEnd(value);
            } else {
                if (isIndexExist(index)) {
                    Node<T> newNode = new Node<>(null,value,null);
                    Node<T> oldNode = findForIndex(index);
                    newNode.previous = oldNode.previous;
                    newNode.next = oldNode;
                    Node<T> oldPrev = oldNode.previous;
                    oldNode.previous = newNode;
                    oldPrev.next = newNode;
                    size++;
                }
            }
        }
    }

    @Override
    public boolean addAll(List<T> list) {
        for (T one: list) {
            add(one);
        }
        return true;
    }

    @Override
    public T get(int index) {
        return findForIndex(index).element;
    }

    @Override
    public T set(T value, int index) {
        if (isIndexExist(index)) {
            Node<T> requiredNode = findForIndex(index);
            T oldElement = requiredNode.element;
            requiredNode.element = value;
            return oldElement;
        }
        return null;
    }

    @Override
    public T remove(int index) {
        if (isIndexExist(index)) {
            Node<T> requiredNode = findForIndex(index);
            if (index == 0 && size < 2) {
                size--;
                first = null;
                last = null;
                requiredNode.element = null;
                return null;
            }
            if (index == 0) {
                Node<T> oldNode = first;
                first = findForIndex(index + 1);
                oldNode.next = null;
                size--;
                return requiredNode != null ? requiredNode.element : null;
            }
            if (index >= 1 && index != size() - 1) {
                Node<T> oldNext = findForIndex(index + 1);
                Node<T> oldPrevious = findForIndex(index - 1);
                oldPrevious.next = oldNext;
                oldNext.previous = oldPrevious;
                size--;
                T requiredElement = requiredNode.element;
                return requiredElement;
            } else {
                Node<T> oldNode = last;
                oldNode.next = null;
                oldNode.previous = null;
                last = findForIndex(index - 1);
                size--;
                return requiredNode != null ? requiredNode.element : null;
            }
        }
        return null;
    }

    @Override
    public boolean remove(T t) {
        if (size == 0) {
            return false;
        }
        int removingNodeIndex = findForValue(t);
        if (removingNodeIndex > - 1) {
            remove(removingNodeIndex);
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
        if (last == null && first == null) {
            return true;
        }
        return false;
    }

    private Node<T> findForIndex(int index) {
        if (isIndexExist(index)) {
            Node<T> requiredNode;
            if (index <= size() / 2) {
                requiredNode = first;
                for (int i = 0; i < index; i++) {
                    requiredNode = requiredNode.next;
                }
            } else {
                requiredNode = last;
                for (int i = size() - 1; i > index; i--) {
                    requiredNode = requiredNode.previous;
                }
            }
            return requiredNode;
        }
        return null;
    }

    private void addFirst(T value) {
        if (size == 0) {
            add(value);
        } else {
            Node<T> newNode = new Node<>(null, value, null);
            Node<T> oldNode = first;
            first = newNode;
            first.next = oldNode;
            oldNode.previous = first;
            size++;
        }
    }

    private void addToEnd(T value) {
        if (size == 0) {
            add(value);
        } else {
            Node<T> newNode = new Node<>(null, value, null);
            Node<T> oldNode = last;
            last = newNode;
            oldNode.next = last;
            last.previous = oldNode;
            size++;
        }
    }

    private int findForValue(T value) {
        for (int i = 0; i < size(); i++) {
            T temp = findForIndex(i).element;
            if (temp == value || (value != null && temp.equals(value))) {
                return i;
            }
        }
        return -1;
    }

    private boolean isIndexExist(int index) {
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException();
        }
        return true;
    }

    public static void main(String[] args) {
        for (int i = 10; i > 6; i--) {
            System.out.println(i);
        }
    }
}
