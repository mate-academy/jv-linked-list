package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {

    private Node<T> first;
    private Node<T> last;
    private int size;
    private int index;

    private static class Node<T> {
        T element;
        Node<T> next;
        Node<T> previous;

        public Node(Node<T> previous, T element, Node<T> next) {
            this.previous = previous;
            this.element = element;
            this.next = next;
        }
    }

    @Override
    public boolean add(T value) {
        Node<T> newNode = new Node<>(null, value, null);
        if (isEmpty()) {
            size=0;
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
            if (index == size-1) {
                addToEnd(value);
            } else {
                if(isIndexExist(index-1)) {
                    Node<T> newNode = new Node<>(null,value,null);
                    if (index == size) {
                        add(value);
                    } else {
                        Node<T> oldNode = findForIndex(index);
                        if (oldNode != null) {
                            newNode.next = oldNode;
                            newNode.previous = oldNode.previous;
                            size++;
                        }
                    }
                }
            }
        }
    }

    @Override
    public boolean addAll(List<T> list) {
        if (list == null) {
            throw new NullPointerException();
        }
        for (T one: list) {
            add(one);
        }
        return true;
    }

    @Override
    public T get(int index) {
        return isIndexExist(index) ? findForIndex(index).element : null;
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
            Node<T> oldNext = findForIndex(index+1);
            Node<T> oldPrevious = findForIndex(index-1);
            T requiredElement = requiredNode.element;
            oldPrevious.next = oldNext;
            oldNext.previous = oldPrevious;
            size--;
            return requiredElement;
        }
        return null;
    }

    @Override
    public boolean remove(T t) {
        if (size == 0) {
            return false;
        }
        int removingNodeIndex = findForValue(t);
        if (removingNodeIndex > 0) {
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
            Node<T> requiredNode = first;
            for (int i = 0; i < index; i++) {
                requiredNode = requiredNode.next;
            }
            return requiredNode;
        } else {
            return null;
        }
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

    private int findForValue (T value) {
        for (int i = 0; i < size; i++) {
            if (findForIndex(i).element == value
                    || (value != null && findForIndex(i).equals(value))) {
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
        MyLinkedList<String> s = new MyLinkedList<>();
        String one ="one";
        String two = "two";
        String three = "three";
        s.add(one,0);
        s.add(two);
        s.add(three);
        s.add(three,2);
        String four = s.get(0);
        System.out.println(four);
    }
}
