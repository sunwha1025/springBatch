package jinbid.converter.comn.vo;

import lombok.Data;

/**
 * 공고지역
 *
 * @author Kim Jae Moon
 * @since 2019. 1. 16.
 */
@Data
public class NoticeArea{

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
    
    /**     * 지역코드     */
    private String areaCd;
    /**     * 지역코드     */
    private String areaNm;
    /**     * 도내코드     */
    private String prvnclCd;
    /**     * 도내명     */
    private String prvnclNm;
    /**     * 지역레벨     */
    private int areaLvl;
    
    
    /*	구DB 용 */
    
    
    

}
