package jinbid.converter.comn.vo;

import lombok.Data;

/**
 * 공고지역
 *
 * @author Kim Jae Moon
 * @since 2019. 1. 16.
 */
@Data
public class NoticeSimpleInfo{

	String keyBaseTable;
	String LongLicense, Long1,Long2,Long3, LongLocal;
    int NoticeType = 0;

}
