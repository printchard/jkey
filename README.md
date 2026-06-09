# jkey

A lightweight, Redis-inspired key-value store written in Java.

## Features

- Concurrent connections via Java 21 virtual threads
- Commands: `PING`, `GET`, `SET`, `DEL`, `EXISTS`
- Pluggable `Store` interface for swappable storage backends

## Requirements

- Java 21+

## Building

Compile all sources from the project root:

```bash
javac -d out shared/*.java server/*.java client/*.java
```

## Running

Start the server (default port 8080):

```bash
java -cp out server.Server
```

Or specify a custom port:

```bash
java -cp out server.Server 7379
```

Run the client:

```bash
java -cp out client.Client <command> [arguments]
```

## Commands

| Command  | Usage               | Description           |
| -------- | ------------------- | --------------------- |
| `ping`   | `ping`              | Health check          |
| `set`    | `set <key> <value>` | Store a value         |
| `get`    | `get <key>`         | Retrieve a value      |
| `delete` | `delete <key>`      | Remove a key          |
| `exists` | `exists <key>`      | Check if a key exists |

### Examples

```bash
java -cp out client.Client ping
# OK

java -cp out client.Client set hello world
# OK

java -cp out client.Client get hello
# OK
# world

java -cp out client.Client exists hello
# OK

java -cp out client.Client delete hello
# OK

java -cp out client.Client get hello
# NOT_FOUND
```

## Protocol

jkey uses a simple binary protocol over TCP.

**Request:**

```
[1 byte]  command
[4 bytes] key length
[N bytes] key
[4 bytes] value length  (SET only)
[M bytes] value         (SET only)
```

**Response:**

```
[1 byte]  status (OK=0x0, NOT_FOUND=0x1, ERROR=0x2)
[4 bytes] value length  (GET only, on OK)
[N bytes] value         (GET only, on OK)
```

## Roadmap

- [ ] Persistence via write-ahead log (WAL)
- [ ] TTL / key expiration
- [ ] REPL mode in the client
- [ ] Authentication
- [ ] Multiple databases
