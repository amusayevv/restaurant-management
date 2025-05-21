package az.edu.ada.wm2.backend.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/error")
public class ErrorController {
    @GetMapping
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleError(HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        if(status != null) {
            int statusCode = Integer.parseInt(status.toString());
            return "Error Code: " + statusCode;
        }
        return "Unknown Error Occured!";
    }

}
