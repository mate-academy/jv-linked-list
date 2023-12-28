package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    int size;
    transient Node<T> first;
    transient Node<T> last;
    MyLinkedList() {
    }

    @Override
    public void add(T value) {
        if (isEmpty()) {
            Node newNode = new Node(null, value, null);
            first = newNode;
            last = newNode;

        } else {
            Node newNode = new Node(last, value, null);
            last.next = newNode;
            last = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index)  {
        if (index == size()) {
            add(value);
            return;
        }
        if (index == 0) {
            Node newNode = new Node(null, value, first);
            first.prev = newNode;
            first = newNode;
            size++;
            return;
        }
        Node node = indexSearch(index);
        Node newNode = new Node(node.prev, value, node);
        node.prev.next = newNode;
        node.prev = newNode;
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (int i = 0; i < list.size(); i++){
            add(list.get(i));
        }
    }

    @Override
    public T get(int index) {
        indexSearch(index);
        int i = 0;
        Node node = first;
        while (i != index){
            node = node.next;
            i++;
        }
        return (T) node.item;
    }

    @Override
    public T set(T value, int index)  {
        Node node = indexSearch(index);
        node.item = value;
        return value;
    }

    @Override
    public T remove(int index)  {
        Node node = indexSearch(index);
        unlink(node);
        return (T) node.item;
    }


    @Override
    public boolean remove(T object) {
        if (isEmpty()){
            return false;
        }
        Node node = first;
        while (node != null){
            if (object != null) {
            if (object.equals(node.item) ){
                unlink(node);
                return true;
            }} else {
                if (node.item == null ){
                    unlink(node);
                    return true;
            }}
            node = node.next;
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return first == null;
    }

    private Node indexSearch(int index) throws IndexOutOfBoundsException {
        if ((index >= size()) || (index<0)) {
            throw new IndexOutOfBoundsException("Index dosn't much");
        }
        if ( index <= size / 2 ) {
            int i = 0;
            Node node = first;
            while (i != index){
                node = node.next;
                i++;
            }
            return node;
        } else {
            int i = size-1;
            Node node = last;
            while (i != index){
                node = node.prev;
                i--;
            }
            return node;
        }
    }
    private void unlink(Node node) {
        if (node.prev == null) {
            if (node.next != null) {
                node.next.prev = null;
                first = node.next;
                size--;
                return ;
            } else {
                first = null;
                size = 0;
                return ;
            }
        }
        if (node.next != null) {
            node.next.prev = node.prev;
            node.prev.next = node.next;
            size--;
        } else {
            node.prev.next = null;
            last = node.prev;
            size--;
        }
    }
}
