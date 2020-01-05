package io.schinzel.samples.sqs.wrapper;

import com.amazonaws.regions.Regions;
import io.schinzel.queue.IQueueProducer;
import io.schinzel.awsutils.sqs.SqsProducer;
import io.schinzel.basicutils.configvar.ConfigVar;

/**
 * The purpose of this class is to show how SqsProducer can be wrapped for easier use.
 *
 * @author Schinzel
 */
public class SqsMyProjectProducer implements IQueueProducer {
    private static final String AWS_SQS_ACCESS_KEY = ConfigVar.create(".env").getValue("AWS_SQS_ACCESS_KEY");
    private static final String AWS_SQS_SECRET_KEY = ConfigVar.create(".env").getValue("AWS_SQS_SECRET_KEY");
    private static final Regions REGION = Regions.EU_WEST_1;
    private final IQueueProducer mQueueProducer;


    public static SqsMyProjectProducer create(io.schinzel.samples.sqs.wrapper.IQueueName queue) {
        return new SqsMyProjectProducer(queue);
    }

    private SqsMyProjectProducer(io.schinzel.samples.sqs.wrapper.IQueueName queue) {
        mQueueProducer = SqsProducer.builder()
                .awsAccessKey(AWS_SQS_ACCESS_KEY)
                .awsSecretKey(AWS_SQS_SECRET_KEY)
                .region(REGION)
                .queueName(queue.getQueueName())
                .build();
    }


    @Override
    public IQueueProducer send(String message) {
        return mQueueProducer.send(message);
    }
}
