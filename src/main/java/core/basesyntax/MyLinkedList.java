package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {

    Link<T> start;
    Link<T> finish;
    int size = 0;
    @Override
    public void add(T value) {
        Link<T> current = new Link<>(value);
        if (start == null) {
            start = current;
        } else {
            finish.setNext(current);
            current.setPrevious(finish);
        }
        finish = current;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if(index > size || index < 0) {
            throw new IndexOutOfBoundsException();
        }
        Link<T> current = new Link<>(value);
        if(index == 0) {
            current.setNext(start);
            start = current;
            if(size == 0) {
                finish = current;
            }
            size++;
            return;
        }
        if (index < size){
            Link<T> previous = start;
            for(int i = 0; i < index; i++) {
                previous = previous.getNext();
            }
            previous.getPrevious().setNext(current);
            current.setPrevious(previous.getPrevious());
            current.setNext(previous);
            previous.setPrevious(current);
            size++;
            return;
        }
        if(index == size) {
            finish.setNext(current);
            current.setPrevious(finish);
            finish = current;
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for(T curent : list) {
            add(curent);
        }
    }

    @Override
    public T get(int index ) {
        if(index >= size || index < 0) {
            throw new IndexOutOfBoundsException();
        }
        Link<T> need = start;
        for(int i = 0; i < index; i++){
            need = need.getNext();
        }
        return need.getValue();
    }

    @Override
    public T set(T value, int index) {
        if(index >= size || index < 0) {
            throw new IndexOutOfBoundsException();
        }
        Link<T> need = start;
        for(int i = 0; i < index; i++){
            need = need.getNext();
        }
        T t = need.getValue();
        need.setValue(value);
        return t;
    }

    @Override
    public T remove(int index) {
        if(index >= size || index < 0) {
            throw new IndexOutOfBoundsException();
        }
        Link<T> current = start;
        if(index == 0) {
            T t = start.getValue();
            start = start.getNext();
            size--;
            return t;
        }
        if(index == size -1) {
            T t = finish.getValue();
            finish = finish.getPrevious();
            size--;
            return t;
        }
        for(int i = 0; i < index; i++){
            current = current.getNext();
        }
        current.getNext().setPrevious(current.getPrevious());
        current.getPrevious().setNext(current.getNext());
        size--;
        return current.getValue();
    }

    @Override
    public boolean remove(T object) {
        Link<T> current = start;
        while (!(current.getValue() == object || (current.getValue() != null && current.getValue().equals(object)))) {
            if( current == finish) {
                return false;
            }
            current = current.getNext();
        }
        if(current == start) {
            start = start.getNext();
            if(start != null) {
                start.setPrevious(null);
            }
            size--;
            return true;
        }
        if(current == finish) {
            finish = finish.getPrevious();
            finish.setNext(null);
            size--;
            return true;
        }
        current.getNext().setPrevious(current.getPrevious());
        current.getPrevious().setNext(current.getNext());
        size--;
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
}
