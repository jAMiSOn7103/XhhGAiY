// 代码生成时间: 2025-10-08 22:21:38
public class CSRFProtectionService {

    private static final String CSRF_TOKEN_KEY = "CSRF_TOKEN";
    private static final String CSRF_TOKEN_HEADER = "X-CSRF-TOKEN";

    // Method to generate a CSRF token
    public String generateCSRFToken() {
        // Generate a secure random token
        String csrfToken = UUID.randomUUID().toString();
        // Store the token in the session for validation
        HttpSession session = null;
        try {
            session = RequestContextHolder.currentRequest().getSession();
            session.setAttribute(CSRF_TOKEN_KEY, csrfToken);
        } catch (Exception e) {
            // Handle session creation error
            throw new RuntimeException("Error generating CSRF token", e);
        }
        return csrfToken;
    }

    // Method to validate the CSRF token from the request
    public boolean validateCSRFToken(String tokenFromRequest) {
        HttpSession session = null;
        try {
            session = RequestContextHolder.currentRequest().getSession(false);
            if (session != null) {
                String sessionToken = (String) session.getAttribute(CSRF_TOKEN_KEY);
                // Invalidate the token after use to prevent replay attacks
                if (sessionToken != null && sessionToken.equals(tokenFromRequest)) {
                    session.removeAttribute(CSRF_TOKEN_KEY);
                    return true;
                }
            }
        } catch (Exception e) {
            // Handle session validation error
            throw new RuntimeException("Error validating CSRF token", e);
        }
        return false;
    }

    // Utility method to get the CSRF token from the request header
    public String getCSRFTokenFromHeader(HttpServletRequest request) {
        return request.getHeader(CSRF_TOKEN_HEADER);
    }

    // Interceptor method to check for CSRF token in the request
    public boolean checkCSRFToken(HttpServletRequest request) {
        // Get the CSRF token from the request header
        String tokenFromHeader = getCSRFTokenFromHeader(request);

        // If no token is present in the header, return false (CSRF protection failed)
        if (tokenFromHeader == null) {
            return false;
        }

        // Get the CSRF token from the session
        HttpSession session = request.getSession(false);
        String sessionToken = (String) session.getAttribute(CSRF_TOKEN_KEY);

        // If the session token and the header token do not match, return false
        if (sessionToken == null || !sessionToken.equals(tokenFromHeader)) {
            return false;
        }

        // Invalidate the token after use to prevent replay attacks
        session.removeAttribute(CSRF_TOKEN_KEY);
        return true;
    }
}
