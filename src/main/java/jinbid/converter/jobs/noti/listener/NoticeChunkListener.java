package jinbid.converter.jobs.noti.listener;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.batch.core.ChunkListener;
import org.springframework.batch.core.scope.context.ChunkContext;

public class NoticeChunkListener implements ChunkListener{

	
    @Resource(name="db1SqlSessionFactory")
    private SqlSessionFactory db1SqlSessionFactory;
	
    
	@Override
	public void beforeChunk(ChunkContext context) {
	}
	
	@Override
	public void afterChunk(ChunkContext context) {
    	SqlSession session = db1SqlSessionFactory.openSession();
    	session.update("updateOrderOffiCd");	/*** 공고 발주처코드 변경   **/    
    	session.update("updateActulDnttCd");	/*** 공고 실수요기관코드 변경   **/
    	session.update("updateCfmtUndwrtStId");	/*** 적격심사기준ID 변경   * */
    	session.close();
	}
	@Override
	public void afterChunkError(ChunkContext context) {
		// TODO Auto-generated method stub
		
	}
}
