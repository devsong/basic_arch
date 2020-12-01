import request from '@/utils/request';

// 列表
export function list(query) {
  return request({
    url: '/api/serial/segment/list',
    method: 'get',
    params: query
  });
}

// 新增
export function add(form) {
  return request({
    url: '/api/serial/segment/add',
    method: 'post',
    params: form
  });
}

// 禁用
export function forbidden(form) {
  form.status = 1;
  return request({
    url: '/api/serial/segment/update_status',
    method: 'post',
    params: form
  });
}

// 删除
export function remove(form) {
  form.status = 2;
  return request({
    url: '/api/serial/segment/update_status',
    method: 'post',
    params: form
  });
}

// 解码
export function decode(query) {
  return request({
    url: '/api/serial/snowflake/decode',
    method: 'get',
    params: query
  });
}

// 导出操作日志
export function exportBizKey(query) {
  return request({
    url: '/api/serial/segment/export',
    method: 'get',
    params: query
  });
}

export function changeSegmentStatus(row) {
  return request({
    url: '/api/serial/segment/updateStatus',
    method: 'post',
    params: row
  });
}
