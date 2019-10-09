package com.gzs.learn.rbac.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gzs.learn.common.util.BeanUtil;
import com.gzs.learn.rbac.dao.DictMapper;
import com.gzs.learn.rbac.dao.NoticeMapper;
import com.gzs.learn.rbac.inf.DictDto;
import com.gzs.learn.rbac.inf.NoticeDto;
import com.gzs.learn.rbac.po.DictPo;
import com.gzs.learn.rbac.po.NoticePo;
import com.gzs.learn.rbac.service.ICommonService;

import tk.mybatis.mapper.entity.Example;

@Component
public class CommonServiceImpl implements ICommonService {
    @Autowired
    private DictMapper dictMapper;

    @Autowired
    private NoticeMapper noticeMapper;

    @Override
    public DictDto getDict(Integer dictId) {
        DictPo dictPo = dictMapper.selectByPrimaryKey(dictId.longValue());
        DictDto dictDto = new DictDto();
        BeanUtil.copyProperties(dictPo, dictDto);
        return dictDto;
    }

    @Override
    public List<DictDto> getDicts(List<Integer> dictIds) {
        Example query = new Example(DictPo.class);
        query.createCriteria().andIn("id", dictIds);
        List<DictPo> dictPos = dictMapper.selectByExample(query);
        List<DictDto> dictDtos = BeanUtil.copyList(dictPos, DictDto.class);
        return dictDtos;
    }

    @Override
    public DictDto getDictByName(String name) {
        Example query = new Example(DictPo.class);
        query.createCriteria().andEqualTo("name", name);
        DictPo po = dictMapper.selectOneByExample(query);
        DictDto dto = new DictDto();
        BeanUtil.copyProperties(po, dto);
        return dto;
    }

    @Override
    public List<DictDto> getDictByPid(Long pid) {
        Example query = new Example(DictPo.class);
        query.createCriteria().andEqualTo("pid", pid);
        List<DictPo> pos = dictMapper.selectByExample(query);
        List<DictDto> dtos = BeanUtil.copyList(pos, DictDto.class);
        return dtos;
    }

    @Override
    public NoticeDto getNotice(Integer notictId) {
        NoticePo noticePo = noticeMapper.selectByPrimaryKey(notictId.longValue());
        NoticeDto noticeDto = new NoticeDto();
        BeanUtil.copyProperties(noticePo, noticeDto);
        return noticeDto;
    }

    @Override
    public List<DictDto> searchDict(String condition) {
        dictMapper.search(condition);
        return null;
    }

}
