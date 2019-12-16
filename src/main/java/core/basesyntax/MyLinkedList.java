package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {

    private Node<T> first;
    private Node<T> last;
    private int size;

    public MyLinkedList() {
        size = 0;
    }

    private void testOfIndexOutBorders(int index) {
        if (index < 0 || index > size()) {
            throw new IndexOutOfBoundsException();
        }
    }

    private void testIndexInBorders(int index) {
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException();
        }
    }

    private void changeLinksWhenAdd(Node<T> newNode, Node<T> tempNode) {
        newNode.setNext(tempNode);
        newNode.setPrevious(tempNode.getPrevious());
        tempNode.getPrevious().setNext(newNode);
    }

    private Node nodeByIndex(int index) {
        Node<T> tempNode;
        if (index < size / 2) {
            tempNode = first;
            for (int i = 0; i <= index; i++) {
                if (i == index) {
                    break;
                }
                tempNode = tempNode.getNext();
            }
        } else {
            tempNode = last;
            for (int i = size() - 1; i >= index; i--) {
                if (i == index) {
                    break;
                }
                tempNode = tempNode.getPrevious();
            }
        }
        return tempNode;
    }

    @Override
    public void add(T value, int index) {
        testOfIndexOutBorders(index);
        if (index == 0 || index == size) {
            add(value);
        } else {
            Node<T> newNode = new Node<T>(value);
            Node<T> tempNode = nodeByIndex(index);
            changeLinksWhenAdd(newNode, tempNode);
            size++;
        }
    }

    @Override
    public void add(T value) {
        Node newNode = new Node(value);
        if (first == null) {
            first = newNode;
            last = newNode;
        } else {
            newNode.setPrevious(last);
            last.setNext(newNode);
            last = newNode;
        }
        size++;

    }

    @Override
    public void addAll(List<T> list) {
        for (T element : list) {
            add(element);
        }
    }

    @Override
    public T get(int index) {
        testIndexInBorders(index);
        return (T) nodeByIndex(index).getElement();
    }

    @Override
    public void set(T value, int index) {
        testIndexInBorders(index);
        Node<T> temp = nodeByIndex(index);
        temp.setElement(value);
    }

    private void changeLinksAtRemove(Node node) {
        Node<T> next = node.getNext();
        Node<T> prev = node.getPrevious();
        next.setPrevious(prev);
        prev.setNext(next);
    }

    @Override
    public T remove(int index) {
        testIndexInBorders(index);
        Node<T> temp;
        if (index == 0) {
            temp = first;
            first = temp.getNext();
            size--;
            return temp.getElement();
        }
        if (index == size - 1) {
            temp = last;
            last = null;
            size--;
            return temp.getElement();
        }
        temp = nodeByIndex(index);
        changeLinksAtRemove(temp);
        size--;
        return temp.getElement();
    }

    @Override
    public T remove(T t) {
        Node<T> temp;
        if ((t == null && first.getElement() == null)
                || (t != null && first.getElement() != null
                && first.getElement().equals(t))) {
            temp = first;
            first = first.getNext();
            size--;
            return temp.getElement();
        }
        if ((t == null && last.getElement() == null)
                || (t != null && last.getElement() != null
                && last.getElement().equals(t))) {
            temp = last;
            last = null;
            size--;
            return temp.getElement();
        }
        temp = first;
        for (int i = 0; i < size(); i++) {
            if ((temp.getElement() == null && t == null)
                    || (temp.getElement() != null && temp.getElement().equals(t))) {
                changeLinksAtRemove(temp);
                size--;
                return temp.getElement();
            }
            temp = temp.getNext();
        }
        return null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return first == null;
    }

    private static class Node<T> {
        private T element;
        private Node<T> next;
        private Node<T> previous;

        public Node(T element) {
            this.element = element;
        }

        public Node(Node<T> next, Node<T> previous, T element) {
            this.element = element;
            this.next = next;
            this.previous = previous;
        }

        public T getElement() {
            return element;
        }

        public void setElement(T element) {
            this.element = element;
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

    }
}
