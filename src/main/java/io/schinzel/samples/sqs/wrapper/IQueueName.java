package io.schinzel.samples.sqs.wrapper;

/**
 * The purpose of this interface is to return the name of a AWS SQS queue.
 *
 * @author Schinzel
 */
public interface IQueueName {

    /**
     *
     * @return The name of the queue
     */
    String getQueueName();
}
