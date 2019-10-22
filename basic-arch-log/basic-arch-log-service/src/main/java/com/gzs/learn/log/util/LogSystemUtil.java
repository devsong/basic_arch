package com.gzs.learn.log.util;

import static com.gzs.learn.log.ILogConstant.DEFAULT_DATA_DURATION;

import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;

import com.gzs.learn.log.inf.search.PageSearchRequestDto;

/**
 * 默认时间查询范围工具类
 * @author guanzhisong
 *
 */
public class LogSystemUtil {
    /**
     * 设置默认查询时间范围,默认最近三个月
     * @param requestDto
     */
    public static void setDefaultSearchRange(PageSearchRequestDto requestDto) {
        Date now = new Date();
        if (requestDto.getCreateTimeEnd() == null) {
            requestDto.setCreateTimeEnd(now);
        }
        if (requestDto.getCreateTimeStart() == null) {
            requestDto.setCreateTimeStart(DateUtils.addMonths(requestDto.getCreateTimeEnd(), DEFAULT_DATA_DURATION));
        }
    }
}
