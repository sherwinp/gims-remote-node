package gov.fda.slimsgate.remote;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

public class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController{

    private static final String PATH = "/error";

    @RequestMapping(value = PATH)
    public String error(WebRequest request, HttpServletResponse response) {
        return "Error handling";
    }

    @Override
    public String getErrorPath() {
        return PATH;
    }
}
