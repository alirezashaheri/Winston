package io.shaheri.Winston.logger;

import io.shaheri.Winston.logger.util.MessageHandler;
import io.shaheri.jStage.ds.Stage;
import io.shaheri.jStage.ds.StageDAG;
import io.shaheri.jStage.log.DAGLogger;
import io.shaheri.jStage.log.StageLogger;

public class RabbitMQLogger implements StageLogger, DAGLogger {

    private final MessageHandler messageHandler;

    public RabbitMQLogger(String uri, String queueName) {
        messageHandler = new MessageHandler(uri, queueName);
    }

    public void onStart(StageDAG stageDAG) {
        messageHandler.onDAGStart(stageDAG);
    }

    public void onComplete(StageDAG stageDAG) {
        messageHandler.onDAGComplete(stageDAG);
    }

    public void onStart(String s, Stage stage) {
        messageHandler.onStageStart(s, stage);
    }

    public void onComplete(String s, Stage stage) {
        messageHandler.onStageComplete(s, stage);
    }

    public void onError(String s, Stage stage, Throwable throwable) {
        messageHandler.onStageError(s, stage, throwable);
    }

    public void onFinish(String s, Stage stage) {
        messageHandler.onStageFinish(s, stage);
    }
}
