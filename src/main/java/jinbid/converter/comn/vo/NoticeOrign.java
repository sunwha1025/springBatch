package jinbid.converter.comn.vo;

import lombok.Data;

/**
 * 공고원문
 *
 * @author Kim Jae Moon
 * @since 2019. 1. 16.
 */
@Data
public class NoticeOrign {

    /** ID */
    Long id ;
    
    /** 공고번호 */
    Long notiId;

    /** 최초입력일시 */
    String firstInputDt;

    /** 최초입력자 */
    String firstInptps;

    /** 최종수정일시 */
    String lastUpdtDt;

    /** 최종수정자 */
    String lastAmndr;

    /** 사용여부 */
    String useYn = "Y";
	
	
    /**
     * 공고첨부파일 ID
     */
    private Long notiAtchmnflId;
    
    
    /**
     * 공고첨부파일여부
     */
    private String notiAtchmnflYn = "N";

    
    /**
     * 원문타입코드
     */
    private String orignTpCd;

    /**
     * 원문내용
     */
    private String orignText;
    
}
