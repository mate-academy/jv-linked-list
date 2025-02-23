package core.basesyntax;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class Link<T> {

    public Link (T value){
        this.value = value;
    }

    Link<T> next;
    T value;
    Link<T> previous;

}
