package finalproject.gudanginkuy.utils.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class Res {
    public static <T>ResponseEntity<?> renderJson(T data, String message, HttpStatus httpStatus){
        WebResponse<T> response = WebResponse.<T>builder()
                .status(httpStatus.getReasonPhrase())
                .message(message)
                .data(data)
                .build();
        return ResponseEntity.status(httpStatus).body(response);
    }
}
