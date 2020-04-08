package core.basesyntax;


import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {

    private Node<T> first;
    private Node<T> last;
    private int size;

    public MyLinkedList() {
        size = 0;
    }

    @Override
    public boolean add(T value) {
        if (first == null) {
            first = new Node<>(value, last, null);
            last = first;
            size++;
        } else {
            Node<T> temp = last;
            last = new Node<>(value, null, temp);
            temp.next = last;
            size++;
        }
        return true;
    }

    @Override
    public void add(T value, int index) {
        if (index <= size && index >= 0) {
            if (first == null) {
                Node<T> temp = new Node<T>(value, null, null);
                first = temp;
                last = temp;
                size++;
                return;
            }
            Node<T> newNode = new Node<T>(value, null, null);
            Node<T> temp = first;
            int i = 0;
            while (i != index) {
                temp = temp.getNext();
                i++;
            }
            if (temp == null) {
                temp = new Node<T>(value, null, last);
            }
            if (temp.getNext() == null) {
                newNode.setPrev(temp);
                temp.setPrev(newNode);
                last = newNode;
                size++;
                return;
            }
            Node<T>next=temp.getNext();
            newNode.setPrev(temp);
            newNode.setNext(temp.getNext());
            temp.setNext(newNode);
            next.setPrev(newNode);
            size++;
        }
        throw new IndexOutOfBoundsException();
    }

    @Override
    public boolean addAll(List<T> list) {
        for (T i : list) {
            add(i);
        }
        return true;
    }

    @Override
    public T get(int index) {
        if (size > index && index >= 0) {
            Node<T> temp = first;
            int i = 0;
            while (i != index) {
                temp = temp.getNext();
                i++;
            }
            return temp.data;
        }
        throw new IndexOutOfBoundsException();
    }

    @Override
    public T set(T value, int index) {
        if (size > index && index >= 0) {
            Node<T> temp = first;
            int i = 0;
            while (i != index) {
                temp = temp.getNext();
                i++;
            }
            T oldValue = temp.getData();
            temp.setData(value);
            return oldValue;
        }
        throw new IndexOutOfBoundsException();
    }

    @Override
    public T remove(int index) {
        if (size > index && index >= 0) {
            Node<T> temp = first;
            int i = 0;
            while (i != index) {
                temp = temp.getNext();
                i++;
            }
            remove(temp);
            return temp.getData();
        }
        throw new IndexOutOfBoundsException();
    }

    @Override
    public boolean remove(T t) {
        Node<T> temp = first;
        int i = 0;
        while (i != size) {
            if (temp.getData() == t || temp.getData().equals((T) t)) {
                remove(temp);

                return true;
            }
            temp = temp.getNext();
            i++;
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

    public void remove(Node<T> temp) {
        Node<T> perv = temp.getPrev();
        Node<T> next = temp.getNext();
        if (perv == null) {
            first = next;
        } else {
            perv.setNext(next);
        }
        if (next == null) {
            last = perv;
        } else {
            next.setPrev(perv);
        }
        size--;
    }


    private class Node<T> {
        private T data;
        private Node<T> next;
        private Node<T> prev;

        private Node(T data, Node<T> next, Node<T> prev) {
            this.data = data;
            this.next = next;
            this.prev = prev;
        }

        public T getData() {
            return data;
        }

        public void setData(T data) {
            this.data = data;
        }

        private Node<T> getNext() {
            return this.next;
        }

        public void setNext(Node<T> next) {
            this.next = next;
        }

        public Node<T> getPrev() {
            return prev;
        }

        public void setPrev(Node<T> prev) {
            this.prev = prev;
        }
    }
}
