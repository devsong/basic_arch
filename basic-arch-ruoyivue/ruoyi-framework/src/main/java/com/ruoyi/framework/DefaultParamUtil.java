package com.ruoyi.framework;

import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;

import com.gzs.learn.inf.PageRequestDto;
import com.ruoyi.log.dto.PageSearchRequestDto;

/**
 * 默认时间查询范围工具类
 * @author guanzhisong
 *
 */
public class DefaultParamUtil {
    /**
     * 设置默认查询时间范围,默认最近三个月
     * @param requestDto
     */
    public static void setDefaultSearchRange(PageSearchRequestDto pageSearchRequestDto) {
        if (pageSearchRequestDto.getPage() == null) {
            pageSearchRequestDto.setPage(1);
        }
        if (pageSearchRequestDto.getPageSize() == null) {
            pageSearchRequestDto.setPageSize(10);
        }
        if (pageSearchRequestDto.getCreateTimeEnd() == null) {
            pageSearchRequestDto.setCreateTimeEnd(new Date());
        }
        if (pageSearchRequestDto.getCreateTimeStart() == null) {
            // 默认查询最近一个月的数据
            pageSearchRequestDto.setCreateTimeStart(DateUtils.addMonths(pageSearchRequestDto.getCreateTimeEnd(), -1));
        }
    }

    public static void setDefaultSearchRange(PageRequestDto pageRequestDto) {
        if (pageRequestDto.getPageNum() == null) {
            pageRequestDto.setPageNum(1);
        }
        if (pageRequestDto.getPageSize() == null) {
            pageRequestDto.setPageSize(10);
        }
    }
}
