package tk.spotimatch.api.filters;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
@Slf4j
public class MyCorsFilter implements Filter {

    public MyCorsFilter() {
        super();
    }

    private static final String LOCALHOST_URL = "http://localhost:4200";
    private static final String WEBSITE_URL = "https://spotimatch.tk";
    private static final String WEBSITE_URL_WWW = "https://www.spotimatch.tk";
    private static final String CORS_CHECK = "https://cors-test.codehappy.dev";

    private static final Set<String> ALLOWED_URLS = Set.of(
            "http://localhost:4200", "https://spotimatch.tk", "https://www.spotimatch.tk",
            "https://cors-test.codehappy.dev");

    @Override
    public final void doFilter(
            final ServletRequest req, final ServletResponse res, final FilterChain chain)
            throws IOException, ServletException {
        final HttpServletResponse response = (HttpServletResponse) res;
        final HttpServletRequest request = (HttpServletRequest) req;

        final String requestUrl = request.getRemoteAddr() + ":" + request.getRemotePort();

        log.info(String.format(
                "Request is received with origin header: %s",
                requestUrl));

        switch (requestUrl) {
            case LOCALHOST_URL:
                response.setHeader("Access-Control-Allow-Origin", LOCALHOST_URL);
            case WEBSITE_URL:
                response.setHeader("Access-Control-Allow-Origin", WEBSITE_URL);
            case CORS_CHECK:
                response.setHeader("Access-Control-Allow-Origin", CORS_CHECK);
            default:
                response.setHeader("Access-Control-Allow-Origin", WEBSITE_URL_WWW);
        }

        // without this header jquery.ajax calls returns 401 even after successful login and SSESSIONID being succesfully stored.
        response.setHeader("Access-Control-Allow-Credentials", "true");

        response.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader(
                "Access-Control-Allow-Headers",
                "X-Requested-With, Authorization, Origin, Content-Type, Version");
        response.setHeader(
                "Access-Control-Expose-Headers",
                "X-Requested-With, Authorization, Origin, Content-Type");

        if (!request.getMethod().equals("OPTIONS")) {
            chain.doFilter(req, res);
        }
    }

    @Override
    public void destroy() {

    }

    @Override
    public void init(FilterConfig filterConfig) {
    }
}
