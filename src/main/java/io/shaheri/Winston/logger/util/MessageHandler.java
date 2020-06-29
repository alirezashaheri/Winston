package io.shaheri.Winston.logger.util;

import com.google.gson.Gson;
import io.shaheri.Winston.logger.model.LogModel;
import io.shaheri.Winston.logger.model.Status;
import io.shaheri.Winston.logger.rabbitMQ.Utility;
import io.shaheri.jStage.ds.Stage;
import io.shaheri.jStage.ds.StageDAG;
import io.shaheri.jStage.model.Void;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.concurrent.TimeoutException;

public class MessageHandler {

    private final Utility utility;

    public MessageHandler(String uri, String queueName) {
        utility = new Utility(uri, queueName);
    }

    private LogModel getLogModel(StageDAG stageDAG, Status status){
        LogModel loggable = new LogModel();
        loggable.setEcho(stageDAG.getEcho());
        loggable.setTimestamp(Instant.now().toEpochMilli());
        loggable.setIdentifier(stageDAG.getIdentifier());
        loggable.setStatus(status);
        return loggable;
    }

    private LogModel getLogModel(String s, Stage stage, Status status){
        LogModel loggable = new LogModel();
        loggable.setEcho(stage.getEcho());
        loggable.setTimestamp(Instant.now().toEpochMilli());
        loggable.setIdentifier(s);
        loggable.setStatus(status);
        loggable.setInput(new Gson().toJson(stage.getPredecessors()));
        loggable.setOutput(new Gson().toJson(stage.getOutput().orElse(new Void())));
        return loggable;
    }

    private LogModel getLogModel(String s, Stage stage, Throwable throwable){
        LogModel loggable = new LogModel();
        loggable.setEcho(stage.getEcho());
        loggable.setTimestamp(Instant.now().toEpochMilli());
        loggable.setIdentifier(s);
        loggable.setStatus(Status.UNSUCCESSFULLY_COMPLETED);
        loggable.setInput(new Gson().toJson(stage.getPredecessors()));
        loggable.setOutput(new Gson().toJson(throwable));
        return loggable;
    }

    private LogModel getLogModel(Stage stage, Status status){
        return getLogModel(stage.getName(), stage, status);
    }

    public void onDAGStart(StageDAG stageDAG){
        try {
            utility.publish(getLogModel(stageDAG, Status.START));
        } catch (IOException | TimeoutException | NoSuchAlgorithmException | KeyManagementException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public void onDAGComplete(StageDAG stageDAG) {
        try {
            utility.publish(getLogModel(stageDAG, Status.FINISHED));
        } catch (IOException | TimeoutException | NoSuchAlgorithmException | KeyManagementException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public void onStageStart(String s, Stage stage) {
        try {
            utility.publish(getLogModel(s, stage, Status.START));
        } catch (IOException | TimeoutException | NoSuchAlgorithmException | KeyManagementException | URISyntaxException e) {
            e.printStackTrace();
        }
    }


    public void onStageComplete(String s, Stage stage) {
        try {
            utility.publish(getLogModel(s, stage, Status.SUCCESSFULLY_COMPLETED));
        } catch (IOException | TimeoutException | NoSuchAlgorithmException | KeyManagementException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public void onStageError(String s, Stage stage, Throwable throwable) {
        try {
            utility.publish(getLogModel(s, stage, throwable));
        } catch (IOException | TimeoutException | NoSuchAlgorithmException | KeyManagementException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public void onStageFinish(String s, Stage stage) {
        try {
            utility.publish(getLogModel(s, stage, Status.FINISHED));
        } catch (IOException | TimeoutException | NoSuchAlgorithmException | KeyManagementException | URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
