package jinbid.converter.comn.vo;

import lombok.Data;

/**
 * 공고면허
 *
 * @author Kim Jae Moon
 * @since 2019. 1. 16.
 */
@Data
public class NoticeLicns  {

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
     * 면허코드
     */
    private String licnsCd;

    /**
     * 면허명
     */
    private String licnsNm;

    /**
     * 면허그룹
     */
    private int licnsGroup;

    /**
     * 면허카테고리명
     */
    private String licnsCtgyNm;

    /**
     * 면허구분코드
     */
    private String licnsDstnctCd;

    /**
     * 면허구분코드명
     */
    private String licnsDstnctCdNm;

	@Override
	public String toString() {
		return "NoticeLicns [id=" + id + ", notiId=" + notiId + ", licnsCd=" + licnsCd + ", licnsNm=" + licnsNm
				+ ", licnsGroup=" + licnsGroup + ", licnsCtgyNm=" + licnsCtgyNm + ", licnsDstnctCd=" + licnsDstnctCd
				+ ", licnsDstnctCdNm=" + licnsDstnctCdNm + ", firstInputDt=" + firstInputDt + ", firstInptps="
				+ firstInptps + ", lastUpdtDt=" + lastUpdtDt + ", lastAmndr=" + lastAmndr + ", useYn=" + useYn + "]";
	}
    
    
}
