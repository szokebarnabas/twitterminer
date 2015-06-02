package org.bs.messaging;

import java.io.Serializable;

public interface MessagingService {
    <T extends Serializable> void send(String queue, T data);
}
