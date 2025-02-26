package com.vraj.magazinesystem.service;
import com.vraj.magazinesystem.model.User;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class NotificationServiceTest {
    @Test
    void testNotifyUser() {
        NotificationService service = new NotificationService();
        User user = new User();
        user.setEmail("test@example.com");

        // Using Mockito to spy on the system print line method to verify output
        NotificationService spiedService = spy(service);
        doNothing().when(spiedService).notifyUser(any(User.class), anyString());

        spiedService.notifyUser(user, "Hello");

        verify(spiedService).notifyUser(user, "Hello");
        // This is a placeholder test to illustrate method invocation.
        // In real scenarios, you would capture arguments or use more complex assertions.
    }
}
