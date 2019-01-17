package jinbid.converter.jobs.noti.listener;

import java.util.Calendar;


import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;

public class NoticeStepListener implements StepExecutionListener{

	Long startTime,endTime;
    
    @Override
    public void beforeStep(StepExecution stepExecution) {
    	startTime = System.currentTimeMillis();
		System.out.println("***** stepStart  : " + stepExecution.getStartTime());
    }
    
    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
    	
    	int readCnt = stepExecution.getReadCount();
    	int writeCnt = stepExecution.getWriteCount();
    	endTime = System.currentTimeMillis();
    	System.out.println("***** stepEnd : "+formatTime(endTime) + "  /  " + (endTime - startTime) / 1000.0f + "초 / read " + readCnt + " 건 / write " +writeCnt+" 건" );
    	
    	return null;
    }
    
	public String formatTime(long lTime) {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(lTime);
		return (c.get(Calendar.HOUR_OF_DAY) + "시 " + c.get(Calendar.MINUTE) + "분 " + c.get(Calendar.SECOND) + "."
				+ c.get(Calendar.MILLISECOND) + "초");
	} // end function formatTime()
	
}
