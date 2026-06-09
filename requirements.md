# Protocol

- [x] Binary, length-prefixed framing over TCP
- [x] Commands: GET, SET, DEL, EXISTS, PING
- [x] Requests: command byte + key (+ value for SET)
- [x] Responses: status byte (OK, ERROR, NOT_FOUND) + optional value frame

# Server

- [x] Accept multiple concurrent connections
- [x] One virtual thread per connection (Java 21)
- [x] Graceful shutdown on SIGINT
- [x] Configurable port (default 6379... or pick your own to avoid Redis conflict)

# Store

- [x] HashMapStore as the first implementation
- [x] Thread-safe (ConcurrentHashMap under the hood)
- [x] GET, SET, DEL, EXISTS operations

# Error Handling

- [x] Malformed frames don't crash the server — connection is dropped cleanly
- [x] Unknown command returns an ERROR response
- [x] Client disconnect is handled gracefully

# Extensions

TTL / key expiration
Persistence / WAL
Authentication
Multiple databases
A CLI client to talk to your server
