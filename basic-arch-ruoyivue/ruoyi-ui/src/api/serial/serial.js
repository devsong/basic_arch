import request from '@/utils/request';

export function listJob(query) {
  return request({
    url: '/monitor/job/list',
    method: 'get',
    params: query
  });
}

export function decode(query) {
  return request({
    url: '/api/job/list',
    method: 'get',
    params: query
  });
}