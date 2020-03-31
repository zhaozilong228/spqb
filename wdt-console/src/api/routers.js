import axiosBak from '@/libs/api.request'

export const getRouterReq = (access) => {
  return axiosBak.request({
    url: 'get_router',
    params: {
      access
    },
    method: 'get'
  })
}
