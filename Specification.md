# 🧩 Chatroom Server Project – Final Specification

---

## ⚙️ Tech Stack (Java-based)

### ✅ Language:
- Java 17+ (recommended)
- Java 21+ (ideal, for virtual threads via Project Loom)

---

### ✅ Core Libraries

| Purpose        | Library                                                     | Notes                                                  |
|----------------|-------------------------------------------------------------|--------------------------------------------------------|
| **Networking** | `java.net.ServerSocket`, `Socket`                           | Blocking I/O for basic socket communication            |
| **Concurrency**| `java.util.concurrent.ExecutorService`, `ConcurrentHashMap`, `ReentrantLock`, `BlockingQueue` | Thread-safe concurrency support |
| *(Optional)*   | `Executors.newVirtualThreadPerTaskExecutor()`              | Java 21+ – simplifies handling many clients            |

---

## 🛠️ Architecture Overview

- **Client Thread**: Each user handled in a thread (or virtual thread).
- **User Manager**: Tracks usernames and active sockets.
- **Room Manager**: Maps chatrooms to users.
- **Command Parser**: Parses all `/commands`.
- **Message Dispatcher**: Sends messages within chatrooms.
- **Admin Handler**: Manages admin login and commands.

---

## 📋 TODO Checklist

### ✅ Phase 1: Core Server Setup
- [ ] Start TCP server using `ServerSocket`.
- [ ] Accept client connections.
- [ ] Prompt for and receive username.
- [ ] Ensure username is unique and valid.
- [ ] Store users in `ConcurrentHashMap<String, Socket>`.

> **Concurrency**: Use a thread per client via `ExecutorService` or Java 21’s virtual threads.

---

### 🛠️ Phase 2: Chatroom System
- [ ] `/create <room>` – Create chatroom.
- [ ] `/join <room>` – Join chatroom.
- [ ] `/leave` – Leave current room.
- [ ] `/list` – Show all active rooms.
- [ ] Track:
    - `ConcurrentHashMap<String, Set<String>> roomToUsers`
    - `ConcurrentHashMap<String, String> userToRoom`

> Use `ConcurrentHashMap.newKeySet()` for thread-safe sets.

---

### 💬 Phase 3: Messaging
- [ ] Enable users to send messages in chatrooms.
- [ ] Prefix messages with sender's name.
- [ ] Broadcast within the room only.
- [ ] Restrict messaging to joined users only.

> Create helper: `sendMessage(String user, String msg)`

---

### 🧠 Phase 4: Command Parsing
- [ ] Parse `/commands`.
- [ ] Respond to unknown commands with errors.
- [ ] Sanitize input (e.g., room/user names).

---

### 📦 Phase 5: Server Stability
- [ ] Detect client disconnects.
- [ ] Clean up users and rooms on disconnect.
- [ ] Notify room members on join/leave.
- [ ] Handle bad input and prevent crashes.

> Use `try-catch-finally` for cleanup on disconnect.

---

### 🔐 Phase 6: Admin Support

#### Admin Login
- [ ] `/adminlogin <password>` – Authenticate admin.
- [ ] Flag user as admin on success.

#### Admin Commands
- [ ] `/shutdown` – Graceful server shutdown.
- [ ] `/kick <username>` – Disconnect a user.
- [ ] `/rooms` – Show all rooms and user counts.
- [ ] `/users` – Show all users and their rooms.
- [ ] `/broadcast <msg>` – Message all users globally.

> Track `isAdmin` flag in session state.

---

### 🧪 Phase 7: Testing & Polishing
- [ ] Simulate multiple clients and chat scenarios.
- [ ] Validate command and error handling.
- [ ] Test edge cases (disconnects, race conditions).
- [ ] Log events and messages (optional).

---

## 🧠 Tips
- Use `BufferedReader` and `PrintWriter` for I/O.
- Use newline-terminated strings for clean protocol.
- Debug with logs or `System.out.println`.
- Java 21+: Prefer virtual threads for better scalability.

---

