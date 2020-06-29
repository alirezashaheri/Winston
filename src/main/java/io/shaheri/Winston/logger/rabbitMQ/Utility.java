package io.shaheri.Winston.logger.rabbitMQ;

import com.google.gson.Gson;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import io.shaheri.Winston.logger.model.LogModel;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeoutException;

public class Utility {

    private final String uri;
    private final String queueName;

    public Utility(String uri, String queueName) {
        this.uri = uri;
        this.queueName = queueName;
    }

    public void publish(String queueName, String message) throws IOException, TimeoutException, NoSuchAlgorithmException, KeyManagementException, URISyntaxException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUri(uri);
        try (Connection connection = factory.newConnection(); Channel channel = connection.createChannel()) {
            channel.queueDeclare(queueName, false, false, false, null);
            channel.basicPublish("", queueName, null, message.getBytes(StandardCharsets.UTF_8));
        }
    }

    public void publish(String message) throws IOException, TimeoutException, NoSuchAlgorithmException, KeyManagementException, URISyntaxException {
        publish(queueName, message);
    }

    public void publish(LogModel message) throws IOException, TimeoutException, NoSuchAlgorithmException, KeyManagementException, URISyntaxException {
        publish(queueName, new Gson().toJson(message));
    }

}
