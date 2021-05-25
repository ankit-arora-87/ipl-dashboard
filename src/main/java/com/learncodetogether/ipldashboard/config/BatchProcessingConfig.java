package com.learncodetogether.ipldashboard.config;

import com.learncodetogether.ipldashboard.batchprocessor.MatchDataProcessor;
import com.learncodetogether.ipldashboard.data.MatchInputData;
import com.learncodetogether.ipldashboard.listener.JobCompletionNotificationListener;
import com.learncodetogether.ipldashboard.model.Match;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.sql.DataSource;


@Configuration
@EnableBatchProcessing
public class BatchProcessingConfig {

    private final String[] FIELD_NAMES = {"id", "city", "date", "playerOfMatch", "venue", "neutralVenue", "team1", "team2", "tossWinner", "tossDecision", "winner" ,"result", "resultMargin" ,"eliminator", "method", "umpire1", "umpire2"};

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Bean
    public FlatFileItemReader<MatchInputData> reader() {
        System.out.println("Reader Thread " + Thread.currentThread().getName() + " --> " + Thread.currentThread().getId());

        return new FlatFileItemReaderBuilder<MatchInputData>()
                .name("matchInputReader")
                .resource(new ClassPathResource("ipl-matches-2008-2020.csv"))
                .delimited()
                .names(FIELD_NAMES)
                .fieldSetMapper(new BeanWrapperFieldSetMapper<MatchInputData>() {{
                    setTargetType(MatchInputData.class);
                }})
                .build();
    }

    @Bean
    public MatchDataProcessor processor() {
        System.out.println("Processor Current Thread " + Thread.currentThread().getName() + " --> " + Thread.currentThread().getId());

        return new MatchDataProcessor();
    }

    @Bean
    public JdbcBatchItemWriter<Match> writer(DataSource dataSource) {
        System.out.println("Writer Current Thread " + Thread.currentThread().getName() + " --> " + Thread.currentThread().getId());

        return new JdbcBatchItemWriterBuilder<Match>()
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .sql("INSERT INTO matches (id, city, date, player_of_match, venue, team1, team2, toss_winner, toss_decision, winner, result, result_margin, eliminator, umpire1, umpire2) " +
                        "VALUES (:id, :city, :date, :playerOfMatch, :venue, :team1, :team2, :tossWinner, :tossDecision, :winner, :result, :resultMargin, :eliminator, :umpire1, :umpire2)")
                .dataSource(dataSource)
                .build();
    }


    @Bean
    public Job importMatchData(JobCompletionNotificationListener listener, Step step1) {
        System.out.println("Import Current Thread " + Thread.currentThread().getName() + " --> " + Thread.currentThread().getId());

        return jobBuilderFactory.get("importMatchData")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(step1)
                .end()
                .build();
    }

    @Bean
    public Step step1(JdbcBatchItemWriter<Match> writer) {
        System.out.println("Step Current Thread " + Thread.currentThread().getName() + " --> " + Thread.currentThread().getId());

        return stepBuilderFactory.get("step1")
                .<MatchInputData, Match> chunk(10)
                .reader(reader())
                .processor(processor())
                .writer(writer)
                .build();
    }

}