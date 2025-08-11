import org.chatroom.UserName;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class UserNameTest {

    @BeforeEach
    void clearUsernames() {
        // Reset state before each test
        UserName.deleteUser("Alice");
        UserName.deleteUser("Bob");
    }

    @Test
    void testAddUserAndCheckExistence() {
        assertFalse(UserName.doesUserExist("Alice"), "User should not exist initially");

        UserName.addUser("Alice");
        assertTrue(UserName.doesUserExist("Alice"), "User should exist after adding");
    }

    @Test
    void testDeleteUser() {
        UserName.addUser("Bob");
        assertTrue(UserName.doesUserExist("Bob"), "User should exist after adding");

        UserName.deleteUser("Bob");
        assertFalse(UserName.doesUserExist("Bob"), "User should not exist after deletion");
    }

    @Test
    void testDuplicateUsernames() {
        UserName.addUser("Alice");
        UserName.addUser("Alice"); // adding duplicate should have no side effect

        assertTrue(UserName.doesUserExist("Alice"), "User should still exist");
    }

    @Test
    void testCaseSensitivity() {
        UserName.addUser("Alice");
        assertFalse(UserName.doesUserExist("alice"), "Username check should be case-sensitive");
    }
}
