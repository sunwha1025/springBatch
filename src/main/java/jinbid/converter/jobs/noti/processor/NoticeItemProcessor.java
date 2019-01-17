package jinbid.converter.jobs.noti.processor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.batch.item.ItemProcessor;

import jinbid.converter.comn.vo.Notice;
import jinbid.converter.comn.vo.NoticeArea;
import jinbid.converter.comn.vo.NoticeLicns;

/**
 * 개찰 공고수집 ItemWriter
 *
 * @author Kim Jae Moon
 * @since 2019. 1. 16.
 */
public class NoticeItemProcessor implements ItemProcessor<Notice, Notice> {

	@Resource(name="db1SqlSessionFactory")
	private SqlSessionFactory sqlSessionFactory1;
	
    @Resource(name="db2SqlSessionFactory")
    private SqlSessionFactory sqlSessionFactory2;
	
	@Override
	public Notice process(Notice notice) throws Exception{

		Map<String,Object> params = new HashMap<String,Object>();
		params.put("notiNo", notice.getNotiNo());
		params.put("rpsntOrgnzCd", notice.getRpsntOrgnzCd());
		params.put("notiSeq", notice.getNotiSeq());
//		params.put("sortOrdr", notice.getSortOrdr());
		params.put("notiKindCd", notice.getNotiKindCd());
		params.put("rebidNo", notice.getRebidNo());
		params.put("lastUpdtDt", notice.getLastUpdtDt());
		SqlSession session = sqlSessionFactory2.openSession();
		Notice tmp = session.selectOne("selectNoticeBase", params);
		session.close();
		
		
		// 조회된게 없다면 제외 
		if(tmp == null){
			return null;
		}
		//공고비교
		notice.setNotiUpdate(tmp);
			
		if(notice.getSortOrdr() == 0){
			//신DB 지역,면허 조회
			setNoitceSub(notice);
			//구DB 지역,면허 조회
			setTmpSub(tmp);
		}
		
		// 지역 비교  -> 사이즈가 다를 경우만 업데이트
		List<NoticeArea> areaN_List = notice.getNoticeAreaList();
		List<NoticeArea> areaT_List = tmp.getNoticeAreaList();
		if(areaN_List.size() != areaT_List.size()){
			notice.setAreaUpdate(true);
		}
		
		// 면허 비교 -> 사이즈가 다를 경우만 업데이트
		List<NoticeLicns> licnsN_List = notice.getNoticeLicnsList();
		List<NoticeLicns> licnsT_List = tmp.getNoticeLicnsList();
		if(licnsN_List.size() != licnsT_List.size()){
			notice.setLicnsUpdate(true);
		}
		
		if( !notice.isNotiUpdate() &&
			!notice.isAreaUpdate() &&
			!notice.isLicnsUpdate()
			) return null;
		
		return notice;
	}
	
	/* 신DB 지역,면허 조회 */
	private void setNoitceSub(Notice notice){
		SqlSession session = sqlSessionFactory1.openSession();
		notice.setNoticeAreaList( session.selectList("selectNoticeAreaList", notice.getId())  );
		notice.setNoticeLicnsList( session.selectList("selectNoticeLicnsList", notice.getId())  );
		session.close();
	}
	
	/* 구DB 지역,면허 조회 */
	private void setTmpSub(Notice tmp){
		SqlSession session = sqlSessionFactory2.openSession();
		tmp.setNoticeAreaList( session.selectList("selectNoticeArea", tmp.getPrfId())  );
		tmp.setNoticeLicnsList( session.selectList("selectNoticeLicense", tmp.getPrfId())  );
		session.close();
	}
	
	
}