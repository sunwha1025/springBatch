package jinbid.converter.jobs.noti;


import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.batch.MyBatisPagingItemReader;
import org.springframework.batch.core.ChunkListener;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import jinbid.converter.comn.vo.Notice;
import jinbid.converter.jobs.noti.listener.NoticeChunkListener;
import jinbid.converter.jobs.noti.listener.NoticeStepListener;
import jinbid.converter.jobs.noti.processor.NoticeItemProcessor;
import jinbid.converter.jobs.noti.writer.NoticeItemWriter;


@Configuration
@EnableBatchProcessing
public class NoticeConfiguration {

	private final static int MaxCount = 500;
	
	@Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    
    @Resource(name="db1SqlSessionFactory")
    private SqlSessionFactory db1SqlSessionFactory;
    
    @Resource(name="db2SqlSessionFactory")
    private SqlSessionFactory db2SqlSessionFactory;
    
    
    @Bean("testJob")
    public Job job() {
        return  jobBuilderFactory.get("testJob")
                .incrementer(new RunIdIncrementer())
                .start(step1())    // 물품, 용역 개찰 공고
                .build();
    }
   
    @Bean("step1")	//신규 입력 (물품/용역 면허 ) 용
    public Step step1() {
    	Calendar c = Calendar.getInstance();
      long sDate = c.getTimeInMillis() / 1000 - 600;
      
       return stepBuilderFactory.get("step1")
                .<Notice, Notice>chunk(MaxCount)
                .reader(getItemReader("selectNotice",sDate))
                .processor(noticeItemProcessor())
                .writer(noticeItemWriter())
                .build();
    }
    
    private MyBatisPagingItemReader<Notice> getItemReader(String queryId, long sDate){
    	MyBatisPagingItemReader<Notice> itemReader = new MyBatisPagingItemReader<Notice>();
        itemReader.setSqlSessionFactory(db1SqlSessionFactory);
        Map<String,Object> params = new HashMap<String, Object>();
        params.put("sDate", sDate);
        try {
        	itemReader.setParameterValues(params);
        	itemReader.setQueryId(queryId);
			itemReader.afterPropertiesSet();
			itemReader.setPageSize(MaxCount);	//리스트크기
		} catch (Exception e) {
			e.printStackTrace();
		}
        return itemReader;
    }
    
	@Bean("noticeItemProcessor")
	public ItemProcessor<Notice, Notice> noticeItemProcessor() {
		return  new NoticeItemProcessor();
	}


	@Bean("noticeItemWriter")
	public ItemWriter<Notice> noticeItemWriter() {
		return new NoticeItemWriter();
	}

	@Bean("noticeStepListener")
	public StepExecutionListener noticeStepListener(){
		return new NoticeStepListener();
	}

	@Bean("noticeChunkListener")
	public ChunkListener noticeChunkListener(){
		return new NoticeChunkListener();
	}
	
	//다중 writer 쓸 때
	/*public CompositeItemWriter<?> compositeItemWriter(){
	    CompositeItemWriter writer = new CompositeItemWriter();
	    writer.setDelegates(Arrays.asList(new NoticeItemWriter(),new CodeUpdateItemWriter()));
	    return writer;
	}*/
	
}