package jinbid.converter.comn.vo;

import lombok.Data;

/**
 * 공고복수예가
 *
 * @author Kim Jae Moon
 * @since 2019. 1. 16.
 */
@Data
public class NoticeCompnoPrdprc {

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
     * 복수예가
     */
    private String compnoPrdprc;

    /**
     * 복수예가율
     */
    private double compnoPrdprcRt;

    /**
     * 정렬순서
     */
    private int sortOrdr;

    /**
     * 입찰선택여부
     */
    private String opbdChoiceYn;

    /**
     * 입찰선택횟수
     */
    private int opbdChoiceTmcnt;

}