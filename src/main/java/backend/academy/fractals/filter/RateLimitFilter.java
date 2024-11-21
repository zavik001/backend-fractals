package backend.academy.fractals.filter;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Duration;
import java.util.concurrent.TimeUnit;
import org.apache.logging.log4j.core.config.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@Order(1)
public class RateLimitFilter extends OncePerRequestFilter {

    private static final int HTTP_TOO_MANY_REQUESTS = 429;

    private static final int MAX_REQUESTS_PER_SECOND = 1;
    private static final Duration REFILL_INTERVAL = Duration.ofSeconds(1);
    private static final Duration CACHE_EXPIRATION = Duration.ofMinutes(1);

    private final Cache<String, Bucket> ipBuckets = CacheBuilder.newBuilder()
            .expireAfterAccess(CACHE_EXPIRATION.toMinutes(), TimeUnit.MINUTES)
            .build();

    private static final Bandwidth BANDWIDTH = Bandwidth.classic(MAX_REQUESTS_PER_SECOND,
            Refill.intervally(MAX_REQUESTS_PER_SECOND, REFILL_INTERVAL));

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String clientIp = request.getRemoteAddr();

        Bucket bucket;
        try {
            bucket = ipBuckets.get(clientIp, () -> Bucket.builder().addLimit(BANDWIDTH).build());
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Internal Server Error");
            return;
        }

        if (bucket.tryConsume(1)) {
            filterChain.doFilter(request, response);
        } else {
            response.setStatus(HTTP_TOO_MANY_REQUESTS);
            response.getWriter().write("Rate limit exceeded. Try again later.");
        }
    }
}
