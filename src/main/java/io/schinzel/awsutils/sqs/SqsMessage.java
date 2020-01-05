package io.schinzel.awsutils.sqs;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.AmazonSQSException;
import com.amazonaws.services.sqs.model.Message;
import io.schinzel.basicutils.str.Str;
import io.schinzel.queue.IMessage;
import lombok.Builder;
import lombok.experimental.Accessors;

/**
 * The purpose of this class is to represent an AWS SQS message.
 *
 * @author Schinzel
 */
@Builder
@Accessors(prefix = "m")
public class SqsMessage implements IMessage {
    private final AmazonSQS mSqsClient;
    private final String mQueueUrl;
    private final Message mMessage;


    /**
     * @return The body of the message
     */
    @Override
    public String getBody() {
        return mMessage.getBody();
    }


    /**
     * The delete has to be done while the message is invisible in queue. If this method is invoked after
     * the message has become visible an exception is thrown.
     *
     * @return Deletes the message from the queue
     */
    @Override
    public SqsMessage deleteMessageFromQueue() {
        try {
            mSqsClient.deleteMessage(mQueueUrl, mMessage.getReceiptHandle());
        } catch (AmazonSQSException e) {
            //If the error was that the message has become visible in queue again
            if (e.getMessage().contains("The receipt handle has expired")) {
                //Throw a clear error message
                Str.create()
                        .a("Could not delete message as it has become visible in queue again. ")
                        .a("Message id: ").aq(mMessage.getMessageId()).asp()
                        .a("Body: ").aq(mMessage.getBody()).asp()
                        .throwRuntime();
            } else {
                //rethrow message
                throw e;
            }
        }
        return this;
    }

}
