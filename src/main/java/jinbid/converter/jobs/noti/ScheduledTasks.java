package jinbid.converter.jobs.noti;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

//@Component
public class ScheduledTasks {
	
	
//	@Autowired
	NoticeConfiguration noticeConfiguration;
	
	
//	@Autowired
	private SimpleJobLauncher simpleJobLauncher;
	
	public void jobRun(Job job,String jobName){
		JobParametersBuilder jpb = new JobParametersBuilder();
		jpb.addString("JOB_NAME", jobName);
		jpb.addString("DATE", String.valueOf(System.currentTimeMillis()));
		JobParameters param = jpb.toJobParameters();
		simpleJobLauncher.setTaskExecutor( new SimpleAsyncTaskExecutor());
		try {
			simpleJobLauncher.run(job, param);
		} catch (JobExecutionAlreadyRunningException e) {
			e.printStackTrace();
		} catch (JobRestartException e) {
			e.printStackTrace();
		} catch (JobInstanceAlreadyCompleteException e) {
			e.printStackTrace();
		} catch (JobParametersInvalidException e) {
			e.printStackTrace();
		}
	}
	
	@Scheduled(fixedRate = 1000*60*5) // 1분마다 
	public void noticeConfRun(){
		jobRun(noticeConfiguration.job(),new Object(){}.getClass().getEnclosingMethod().getName() );
	}

}
