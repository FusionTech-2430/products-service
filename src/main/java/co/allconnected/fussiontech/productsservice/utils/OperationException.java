package co.allconnected.fussiontech.productsservice.utils;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OperationException extends RuntimeException {
    private final Integer code;
    public OperationException(Integer code, String message) {
        super(message);
        this.code = code;
    }
}