package jinbid.converter.comn.vo;

import lombok.Data;

/**
 * 공고첨부문서
 *
 * @author Kim Jae Moon
 * @since 2019. 1. 16.
 */
@Data
public class NoticeAtchmnfl {

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
     * 파일구분명
     */
    private String fileDstnctNm;

    /**
     * 파일명
     */
    private String fileNm;

    /**
     * 파일경로명
     */
    private String filePathNm;

    /**
     * 파일순서
     */
    private int fileOrdr;

    /**
     * 외부파일여부
     */
    private String extrnlFileYn = "N";

}
