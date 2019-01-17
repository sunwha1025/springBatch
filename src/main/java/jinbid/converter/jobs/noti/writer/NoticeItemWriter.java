package jinbid.converter.jobs.noti.writer;

import java.util.Calendar;
import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.batch.core.ItemWriteListener;
import org.springframework.batch.item.ItemWriter;

import jinbid.converter.comn.vo.Notice;
import jinbid.converter.comn.vo.NoticeSimpleInfo;

/**
 * 개찰 공고수집 ItemWriter
 *
 * @author Kim Jae Moon
 * @since 2019.1. 16.
 */
public class NoticeItemWriter implements ItemWriter<Notice>, ItemWriteListener<Notice> {

	Long startTime;
	
	Long total = 0L;
	
	/*		테스트 DB		*/
	@Resource(name="db1SqlSessionFactory")
	private SqlSessionFactory db1SqlSessionFactory;

	/*		테스트 DB		*/
	@Resource(name="db2SqlSessionFactory")
	private SqlSessionFactory db2SqlSessionFactory;
	
	
	
	
	@Override
	public void beforeWrite(List<? extends Notice> items) {
		// 시작 시간
				startTime = System.currentTimeMillis();
				int cnt= items.size();
				total += cnt;
	}
	
    @Override
    public void write(List<? extends Notice> noticeList) throws Exception {
    	/*
    	 *  구 DB inputTime 업데이트 , Log insert
    	 * 
    	 * */
    	SqlSession session = db2SqlSessionFactory.openSession();
    	for(Notice notice : noticeList){
    		if(notice.isNotiUpdate()){
    			session.update("updateNotice", notice);
    		}
    		
    		if(notice.isAreaUpdate() && notice.getNoticeAreaList().size()>0 ){
    			System.out.println("is Area : " + notice);
    			updateNoticeArea(session,notice);
    		}
    		
    		if(notice.isLicnsUpdate() && notice.getNoticeLicnsList().size()>0 ){
    			updateNoticeLicns(session,notice);
    		}
    		
    		// simpleInfo
    		if(notice.isAreaUpdate() || notice.isLicnsUpdate()){
    			updateNoticeSimpleInfo(session,notice);
    		}
    		
    		session.insert("insertLog", notice);
    	}
    	session.close();
    }
    
	@Override
	public void afterWrite(List<? extends Notice> items) {
		System.out.print("afterWrite:  " + (System.currentTimeMillis() - startTime) / 1000.0f + "초 / ");
		if(items.size()>0){
			System.out.println("size : "+ items.size()+" 기간 :  " +items.get(0).getPrfDt()+" ~ " + items.get(items.size()-1).getPrfDt());
		}
	}


	@Override
	public void onWriteError(Exception exception, List<? extends Notice> items) {
		System.out.println("NoticeItemListener : " + exception.getMessage());
		exception.printStackTrace();
		for(int i = 0 ; i < items.size(); i++){
		System.out.println("NoticeItemListener : " + items.get(i));
		}
	}
	
	
	public String formatTime(long lTime) {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(lTime);
		return (c.get(Calendar.HOUR_OF_DAY) + "시 " + c.get(Calendar.MINUTE) + "분 " + c.get(Calendar.SECOND) + "."
				+ c.get(Calendar.MILLISECOND) + "초");
	} // end function formatTime()
	
	
	public void updateNoticeArea(SqlSession session,Notice notice){
		session.delete("deleteNoticeArea",  notice);
		session.insert("insertNoticeArea",  notice);
	}
	
	public void updateNoticeLicns(SqlSession session,Notice notice){
		session.delete("deleteNoticeLicns",  notice);
		session.insert("insertNoticeLicns",  notice);
	}
	
	public void updateNoticeSimpleInfo(SqlSession session,Notice notice){
		NoticeSimpleInfo nsif= session.selectOne("selectNoticeSimpleInfo",  notice);
		notice.setNoticeSimpleInfo(nsif);
		session.update("updateNoticeSimpleInfo",  notice);
	}
	
	
	
}
