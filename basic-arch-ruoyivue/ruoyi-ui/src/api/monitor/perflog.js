import request from '@/utils/request';

// 查询系统接口日志列表
export function listLog(query) {
  return request({
    url: '/system/log/list',
    method: 'get',
    params: query
  });
}

// 查询系统接口日志详细
export function getLog(id) {
  return request({
    url: '/system/log/' + id,
    method: 'get'
  });
}

// 新增系统接口日志
export function addLog(data) {
  return request({
    url: '/system/log',
    method: 'post',
    data: data
  });
}

// 修改系统接口日志
export function updateLog(data) {
  return request({
    url: '/system/log',
    method: 'put',
    data: data
  });
}

// 删除系统接口日志
export function delLog(id) {
  return request({
    url: '/system/log/' + id,
    method: 'delete'
  });
}

// 导出系统接口日志
export function exportLog(query) {
  return request({
    url: '/system/log/export',
    method: 'get',
    params: query
  });
}

// 获取meta分组信息
export function getMetaLog(query) {
  return request({
    url: '/system/log/meta_info',
    method: 'get',
    params: query
  });
}
