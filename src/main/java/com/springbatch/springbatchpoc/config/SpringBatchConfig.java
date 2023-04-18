package com.springbatch.springbatchpoc.config;


import com.springbatch.springbatchpoc.entity.Customer;
import com.springbatch.springbatchpoc.entity.Customer_bkp;
import com.springbatch.springbatchpoc.listener.StepSkipListener;
import com.springbatch.springbatchpoc.partitioning.ColumnRangePartitioner;
import com.springbatch.springbatchpoc.repository.CustomerBkpRepo;
import com.springbatch.springbatchpoc.repository.CustomerCustomRepo;
import com.springbatch.springbatchpoc.repository.CustomerRepository;
import com.springbatch.springbatchpoc.repository.WritterCustomRepo;
import lombok.AllArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.partition.PartitionHandler;
import org.springframework.batch.core.partition.support.TaskExecutorPartitionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableBatchProcessing
@AllArgsConstructor
public class SpringBatchConfig {

    private JobBuilderFactory jobBuilderFactory;

    private StepBuilderFactory stepBuilderFactory;

    private CustomerRepository customerRepository;
    private CustomerCustomRepo customerCustomRepo;
    private CustomerBkpRepo customerBkpRepo;
    private CustomerWriter customerWriter;
    private DynamicCustomerWriter dynamicCustomerWriter;


//    @Bean
//    public RepositoryItemWriter<Customer_bkp> writer() {
//        RepositoryItemWriter<Customer_bkp> writer = new RepositoryItemWriter<>();
//        writer.setRepository(customerBkpRepo);
//        writer.setMethodName("save");
//        return writer;
//    }

    //single step Start
/*    @Bean
    public Step step1() {
        return stepBuilderFactory.get("csv-step").<Customer, Customer_bkp>chunk(10)
                .reader(new CustomItemReader(customerRepository))
//                .reader(new CustomFlatFileReader())
                .processor(new CustomerProcessor())
                .writer(writer())
                .taskExecutor(taskExecutor())
                .build();
    }
    @Bean
    public Job runJob() {
        return jobBuilderFactory.get("importCustomers")
                .flow(step1()).end().build();

    }
        @Bean
    public TaskExecutor taskExecutor() {
        SimpleAsyncTaskExecutor asyncTaskExecutor = new SimpleAsyncTaskExecutor();
        asyncTaskExecutor.setConcurrencyLimit(10);
        return asyncTaskExecutor;
    }


    */

    //single step End

    // Master Slave Start

    @Bean
    public PartitionHandler partitionHandler() {
        TaskExecutorPartitionHandler taskExecutorPartitionHandler = new TaskExecutorPartitionHandler();
        taskExecutorPartitionHandler.setGridSize(4);
        taskExecutorPartitionHandler.setTaskExecutor(taskExecutor());
        taskExecutorPartitionHandler.setStep(slaveStep());
        return taskExecutorPartitionHandler;
    }
//non dynamic
/*    @Bean
    public Step slaveStep() {
        return stepBuilderFactory.get("slaveStep").<Customer, Customer_bkp>chunk(500)
                .reader(new CustomItemReader(customerRepository))
                .processor(new CustomerProcessor())
                //.writer(writer())
                .writer(customerWriter)
                .faultTolerant()
                //.skipLimit(100)
                //.skip(NumberFormatException.class)
                //.noSkip(IllegalArgumentException.class)
                .listener(new StepSkipListener())
                .skipPolicy(new ExceptionSkipPolicy())
                .build();
    }   */
    // end non dynamic
    @Bean
    public Step slaveStep() {
        return stepBuilderFactory.get("slaveStep").<Object, Object>chunk(500)
                .reader(new DynamicCustomItemReader(customerRepository, customerCustomRepo))
                .processor(new CustomerProcessor())
                //.writer(writer())
                .writer(dynamicCustomerWriter)
                .faultTolerant()
                //.skipLimit(100)
                //.skip(NumberFormatException.class)
                //.noSkip(IllegalArgumentException.class)
                .listener(new StepSkipListener())
                .skipPolicy(new ExceptionSkipPolicy())
                .build();
    }

    @Bean
    public Step masterStep() {
        return stepBuilderFactory.get("masterStep").
                partitioner(slaveStep().getName(), new ColumnRangePartitioner())
                .partitionHandler(partitionHandler())
                .build();
    }


    @Bean
    public Job runJob() {
        return jobBuilderFactory.get("importCustomers")
                .flow(masterStep()).end().build();

    }
    @Bean
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setMaxPoolSize(4000);
        taskExecutor.setCorePoolSize(4000);
        taskExecutor.setQueueCapacity(4000);
        return taskExecutor;
    }
    // Master Slave End

}
