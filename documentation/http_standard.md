Http standard (RFC 7230) -> https://datatracker.ietf.org/doc/html/rfc7230

![http_schema.png](images%2Fhttp_schema.png)

3 marked parts:
1. Start-line
2. Headers
3. Body

Interesting HTTP status code responses
- 501 (Not implemented) -> When we do not recognize HTTP method
- 405 (Method not allowed) -> When we recognize the HTTP method but for some reason we can't execute it
